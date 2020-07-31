package com.example.tugas5.service;

import com.example.tugas5.model.Transaksi;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.TransaksiRepository;
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
public class TransaksiService {

    @Autowired
    TransaksiRepository repo;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Transaksi> getAllTransaksi() {
        return repo.findAll();
    }

    public Map<String, Object> updateTransaksi(Transaksi transaksi) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Query query = new Query(new Criteria("id").is(transaksi.getId()));
            Map<String, Object> objectMap = Utility.objectToMap(transaksi);
            Update updateQuery = new Update();
            objectMap.forEach((key, value) -> {
                if (value != null) {
                    updateQuery.set(key, value);
                }
            });
            mongoTemplate.findAndModify(query, updateQuery, Transaksi.class);
            resultMap.put("success", true);
            resultMap.put("message", "transaksi updated");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("message", "transaksi update failed");
        }
        return resultMap;
    }

    public Map<String, Object> deleteTransaksi(String id) {
        Optional transaksiResult = repo.findById(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (transaksiResult.isPresent()) {
            try {
                repo.deleteById(id);
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

    public Page<Transaksi> getAllTransaksiPaginated(String search, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tanggal").regex(".*" + search + ".*", "i"));
        query.with(pageable);
        List<Transaksi> list = mongoTemplate.find(query, Transaksi.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, Transaksi.class));
    }


}
