package com.example.tugas5.service;

import com.example.tugas5.model.Payment;
import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.model.User;
import com.example.tugas5.model.UserDetail;
import com.example.tugas5.repository.PembayaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {

    @Autowired
    PembayaranRepository repo;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Payment> findAllPayment() {
        List<Pembayaran> result = mongoTemplate.findAll(Pembayaran.class);
        List<Payment> paymentList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getPayment() != null) paymentList.add(result.get(i).getPayment());
        }
        return paymentList;
    }

    public Map inserPayment(Payment payment, String id) {
        Pembayaran pembayaranResult = repo.findById(id).get();
        Map<String, Object> resultMap = new HashMap<>();
        if (pembayaranResult != null) {
            Pembayaran pembayaranSave = pembayaranResult;
            pembayaranResult.setPayment(payment);
            try {
                repo.save(pembayaranSave);
                resultMap.put("success", true);
                resultMap.put("message", "payment saved");
            } catch (Exception e) {
                resultMap.put("success", true);
                resultMap.put("message", "payment faileds");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no  data");
        }
        return resultMap;
    }

    public Map updatePayment(Payment payment, String id) {
        Pembayaran pembayaranResult = repo.findById(id).get();
        Map<String, Object> resultMap = new HashMap<>();
        if (pembayaranResult != null) {
            Pembayaran pembayaranSave = pembayaranResult;
            pembayaranResult.setPayment(payment);
            try {
                repo.save(pembayaranSave);
                resultMap.put("success", true);
                resultMap.put("message", "payment updated");
            } catch (Exception e) {
                resultMap.put("success", true);
                resultMap.put("message", "payment failed");
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
            update.unset("payment");
            mongoTemplate.updateMulti(query, update, Pembayaran.class);
            resultMap.put("success", true);
            resultMap.put("message", "payment deleted");
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "payment delete failed");
        }
        return resultMap;
    }

}
