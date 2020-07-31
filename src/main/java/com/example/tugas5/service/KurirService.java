package com.example.tugas5.service;

import com.example.tugas5.model.Kurir;
import com.example.tugas5.model.LayananKurir;
import com.example.tugas5.model.User;
import com.example.tugas5.model.UserDetail;
import com.example.tugas5.repository.KurirRepository;
import com.example.tugas5.repository.LayananKurirRepository;
import com.example.tugas5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class KurirService {

    @Autowired
    KurirRepository repo;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    LayananKurirRepository lkrepo;

    public List<Kurir> findAllKurir() {
        List<LayananKurir> result = mongoTemplate.findAll(LayananKurir.class);
        List<Kurir> kurirList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getKurir() != null) kurirList.add(result.get(i).getKurir());
        }
        return kurirList;
    }

    public Map inserKurir(Kurir kurir, String id) {
        LayananKurir layananResult = lkrepo.findById(id).get();
        Map<String, Object> resultMap = new HashMap<>();
        if (layananResult != null) {
            LayananKurir layananSave = layananResult;
            layananResult.setKurir(kurir);
            try {
                lkrepo.save(layananSave);
                resultMap.put("success", true);
                resultMap.put("message", "kurir saved");
            } catch (Exception e) {
                resultMap.put("success", true);
                resultMap.put("message", "kurir failed");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no  data");
        }
        return resultMap;
    }

    public Map updateKurir(Kurir kurir, String id) {
        LayananKurir layananResult = lkrepo.findById(id).get();
        Map<String, Object> resultMap = new HashMap<>();
        if (layananResult != null) {
            LayananKurir layananSave = layananResult;
            layananResult.setKurir(kurir);
            try {
                lkrepo.save(layananSave);
                resultMap.put("success", true);
                resultMap.put("message", "kurir updated");
            } catch (Exception e) {
                resultMap.put("success", true);
                resultMap.put("message", "kurir update failed");
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
            update.unset("kurir");
            mongoTemplate.updateMulti(query, update, LayananKurir.class);
            resultMap.put("success", true);
            resultMap.put("message", "kurir deleted");
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "kurir delete failed");
        }
        return resultMap;
    }
}
