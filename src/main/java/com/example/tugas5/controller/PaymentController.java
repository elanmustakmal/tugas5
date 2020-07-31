package com.example.tugas5.controller;

import com.example.tugas5.model.Kurir;
import com.example.tugas5.model.LayananKurir;
import com.example.tugas5.model.Payment;
import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.repository.KurirRepository;
import com.example.tugas5.repository.LayananKurirRepository;
import com.example.tugas5.repository.PaymentRepository;
import com.example.tugas5.repository.PembayaranRepository;
import com.example.tugas5.service.KurirService;
import com.example.tugas5.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/")
public class PaymentController {

    @Autowired
    PaymentRepository repo;

    @Autowired
    PaymentService service;

    @Autowired
    PembayaranRepository prepo;

    @GetMapping("")
    List<Payment> getPayment() {
        return (List<Payment>) service.findAllPayment();
    }


    @PostMapping("insert")
    public void insertPayment (@RequestBody Payment payment, String id) {
        service.inserPayment(payment, id);
    }

    @DeleteMapping("delete")
    Map<String, Object> deletePayment(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try  {
            service.pullData(id);
            result.put("Success", true);
        } catch (Exception e){
            result.put("Gagal", false);
        }
        return result;
    }

    @PutMapping("update")
    public void updatePayment (@RequestBody Payment payment, String id) {
        service.updatePayment(payment, id);
    }

    @GetMapping("getById")
    public Payment getDataById(String id) {
        Pembayaran pResult = prepo.findById(id).get();
        Payment payment = pResult.getPayment();
        return payment;
    }


}
