package com.example.tugas5.controller;

import com.example.tugas5.model.Payment;
import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.model.TransaksiDetail;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.*;
import com.example.tugas5.service.PaymentService;
import com.example.tugas5.service.TransaksiDetailService;
import com.example.tugas5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaksiDetail/")
public class TransaksiDetailController {

    @Autowired
    TransaksiDetailRepository repo;

    @Autowired
    TransaksiDetailService service;

    @GetMapping("")
    List<TransaksiDetail> getTransaksiDetail() {
        List<TransaksiDetail> result = repo.findAll();

        if (result.isEmpty()){
            System.out.println("no data");
        } else {
            return result;
        }
        return result;
    }

    @PostMapping("insert")
    public Map<String, Object> insertTransaksiDetail (@RequestBody TransaksiDetail transaksiDetail) {
        Optional<TransaksiDetail> tdResult = repo.findById(transaksiDetail.getId());
        Map<String, Object> resultMap = new HashMap<>();
        if (tdResult.isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "TransaksiDetail telah terdaftar");
        } else {
            try {
                repo.save(transaksiDetail);
                resultMap.put("success", true);
                resultMap.put("message", "insert TransaksiDetail berhasil");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "insert TransaksiDetail gagal");
            }
        }
        return resultMap;
    }

    @DeleteMapping("delete")
    Map<String, Object> deleteTransaksiDetail(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try  {
            service.deleteTransaksiDetail(id);
            result.put("Success", true);
        } catch (Exception e){
            result.put("Gagal", false);
        }
        return result;
    }

    @PutMapping("update")
    public void updateTransaksiDetail (@RequestBody TransaksiDetail transaksiDetail) {
        service.updateTransaksiDetail(transaksiDetail);
    }

    @GetMapping("page")
    public Page<TransaksiDetail> getAllTransaksiDetailPaginated(String search, Pageable pageable) {
        return service.getAllTransaksiDetailPaginated(search, pageable);
    }

}
