package com.example.tugas5.service;

import com.example.tugas5.model.LayananKurir;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.LayananKurirRepository;
import com.example.tugas5.repository.UserDetailRepository;
import com.example.tugas5.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LayananKurirService {
    @Autowired
    LayananKurirRepository repo;
    
    @Autowired
    MongoTemplate mongoTemplate;

    public List<LayananKurir> getAllLayanan() {
        return repo.findAll();
    }

    public Map<String, Object> updateLayanan(LayananKurir layananKurir) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Query query = new Query(new Criteria("id").is(layananKurir.getId()));
            Map<String, Object> objectMap = Utility.objectToMap(layananKurir);
            Update updateQuery = new Update();
            objectMap.forEach((key, value) -> {
                if (value != null) {
                    updateQuery.set(key, value);
                }
            });
            mongoTemplate.findAndModify(query, updateQuery, LayananKurir.class);
            resultMap.put("success", true);
            resultMap.put("message", "service updated");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("message", "service update failed");
        }
        return resultMap;
    }

    public Map<String, Object> deleteLayanan(String id) {
        Optional layananResult = repo.findById(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (layananResult.isPresent()) {
            try {
                repo.deleteById(id);
                resultMap.put("success", true);
                resultMap.put("message", "service deleted");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "service delete failed");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no service data");
        }
        return resultMap;
    }

    public Page<LayananKurir> getAllLayananPaginated(String search, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nama").regex(".*" + search + ".*", "i"));
        query.with(pageable);
        List<LayananKurir> list = mongoTemplate.find(query, LayananKurir.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, LayananKurir.class));
    }

}
