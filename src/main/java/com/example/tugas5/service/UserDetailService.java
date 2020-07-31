package com.example.tugas5.service;

import com.example.tugas5.model.User;
import com.example.tugas5.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.tugas5.repository.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<UserDetail> findAllUserDetail() {
        List<User> result = mongoTemplate.findAll(User.class);
        List<UserDetail> userDetailList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getUserDetail() != null) userDetailList.add(result.get(i).getUserDetail());
        }
        return userDetailList;
    }

    public Map inserUserDetail(UserDetail userDetail, String userId) {
        User userResult = userRepository.findById(userId).get();
        Map<String, Object> resultMap = new HashMap<>();
        if (userResult != null) {
            User userSave = userResult;
            userResult.setUserDetail(userDetail);
            try {
                userRepository.save(userSave);
                resultMap.put("success", true);
                resultMap.put("message", "user detail saved");
            } catch (Exception e) {
                resultMap.put("success", true);
                resultMap.put("message", "user detail faileds");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no  data");
        }
        return resultMap;
    }

    public Map updateUserDetail(UserDetail userDetail, String userId) {
        User userResult = userRepository.findById(userId).get();
        Map<String, Object> resultMap = new HashMap<>();
        if (userResult != null) {
            User userSave = userResult;
            userResult.setUserDetail(userDetail);
            try {
                userRepository.save(userSave);
                resultMap.put("success", true);
                resultMap.put("message", "user detail updated");
            } catch (Exception e) {
                resultMap.put("success", true);
                resultMap.put("message", "user detail failed");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no data");
        }
        return resultMap;
    }

    public Map<String, Object> pullData(String id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            Update update = new Update();
            update.unset("userDetail");
            mongoTemplate.updateMulti(query, update, User.class);
            resultMap.put("success", true);
            resultMap.put("message", "user detail deleted");
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "user detail delete failed");
        }
        return resultMap;
    }
//
//    public UserDetail getDataByUserId(String id) {
//        User userResult = userRepository.findById(id).get();
//        UserDetail userDetail = userResult.getUserDetail();
//        return userDetail;
//    }

}
