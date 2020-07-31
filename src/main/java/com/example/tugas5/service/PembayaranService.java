package com.example.tugas5.service;

import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.PembayaranRepository;
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
public class PembayaranService {

    @Autowired
    PembayaranRepository repo;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Pembayaran> getAllPembayaran() {
        return repo.findAll();
    }

    public Map<String, Object> updatePembayaran(Pembayaran pembayaran) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Query query = new Query(new Criteria("id").is(pembayaran.getId()));
            Map<String, Object> objectMap = Utility.objectToMap(pembayaran);
            Update updateQuery = new Update();
            objectMap.forEach((key, value) -> {
                if (value != null) {
                    updateQuery.set(key, value);
                }
            });
            mongoTemplate.findAndModify(query, updateQuery, Pembayaran.class);
            resultMap.put("success", true);
            resultMap.put("message", "pembayaran updated");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("message", "pembayaran update failed");
        }
        return resultMap;
    }

    public Map<String, Object> deletePembayaran(String id) {
        Optional pembayaranResult = repo.findById(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (pembayaranResult.isPresent()) {
            try {
                repo.deleteById(id);
                resultMap.put("success", true);
                resultMap.put("message", "pembayaran deleted");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "pembayaran delete failed");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no data");
        }
        return resultMap;
    }

    public Page<Pembayaran> getAllPembayaranPaginated(String search, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").regex(".*" + search + ".*", "i"));
        query.with(pageable);
        List<Pembayaran> list = mongoTemplate.find(query, Pembayaran.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, Pembayaran.class));
    }


}
