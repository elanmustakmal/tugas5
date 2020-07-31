package com.example.tugas5.service;

import com.example.tugas5.model.TransaksiDetail;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.TransaksiDetailRepository;
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
public class TransaksiDetailService {

    @Autowired
    TransaksiDetailRepository repo;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<TransaksiDetail> getAllTransaksiDetail() {
        return repo.findAll();
    }

    public Map<String, Object> updateTransaksiDetail(TransaksiDetail transaksiDetail) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Query query = new Query(new Criteria("id").is(transaksiDetail.getId()));
            Map<String, Object> objectMap = Utility.objectToMap(transaksiDetail);
            Update updateQuery = new Update();
            objectMap.forEach((key, value) -> {
                if (value != null) {
                    updateQuery.set(key, value);
                }
            });
            mongoTemplate.findAndModify(query, updateQuery, TransaksiDetail.class);
            resultMap.put("success", true);
            resultMap.put("message", "transaksi detail updated");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("message", "transaksi detail update failed");
        }
        return resultMap;
    }

    public Map<String, Object> deleteTransaksiDetail(String id) {
        Optional transaksiDetailResult = repo.findById(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (transaksiDetailResult.isPresent()) {
            try {
                repo.deleteById(id);
                resultMap.put("success", true);
                resultMap.put("message", "transaksi detail deleted");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "transaksi detail delete failed");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no data");
        }
        return resultMap;
    }

    public Page<TransaksiDetail> getAllTransaksiDetailPaginated(String search, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("jumlah").regex(".*" + search + ".*", "i"));
        query.with(pageable);
        List<TransaksiDetail> list = mongoTemplate.find(query, TransaksiDetail.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, TransaksiDetail.class));
    }


}
