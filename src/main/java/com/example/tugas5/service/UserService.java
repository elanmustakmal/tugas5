package com.example.tugas5.service;

import com.example.tugas5.model.User;
import com.example.tugas5.repository.UserDetailRepository;
import com.example.tugas5.repository.UserRepository;
import com.example.tugas5.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Map<String, Object> updateUser(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Query query = new Query(new Criteria("id").is(user.getId()));
            Map<String, Object> objectMap = Utility.objectToMap(user);
            Update updateQuery = new Update();
            objectMap.forEach((key, value) -> {
                if (value != null) {
                    updateQuery.set(key, value);
                }
            });
            mongoTemplate.findAndModify(query, updateQuery, User.class);
            resultMap.put("success", true);
            resultMap.put("message", "user updated");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("message", "user update failed");
        }
        return resultMap;
    }

    public Map<String, Object> deleteUser(String id) {
        Optional userResult = userRepository.findById(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (userResult.isPresent()) {
            try {
                userRepository.deleteById(id);
                resultMap.put("success", true);
                resultMap.put("message", "user deleted");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "user delete failed");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no user data");
        }
        return resultMap;
    }

    public Page<User> getAllUserPaginated(String search, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").regex(".*" + search + ".*", "i"));
        query.with(pageable);
        List<User> list = mongoTemplate.find(query, User.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, User.class));
    }

    public String getAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    public User findByUsername(String userName) {

            return userRepository.findByUsername(userName).get();

    }
}
