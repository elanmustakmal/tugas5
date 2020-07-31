package com.example.tugas5.controller;

import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.PembayaranRepository;
import com.example.tugas5.repository.UserRepository;
import com.example.tugas5.service.PembayaranService;
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
@RequestMapping("/api/pembayaran/")
public class PembayaranController {

    @Autowired
    PembayaranRepository repo;

    @Autowired
    PembayaranService service;

    @GetMapping("")
    List<Pembayaran> getPembayaran() {
        return (List<Pembayaran>) repo.findAll();
    }


    @PostMapping("insert")
    public Map<String, Object> insertPembayaran (@RequestBody Pembayaran pembayaran) {
        Optional<Pembayaran> pembayaranResult = repo.findByNama(pembayaran.getNama());
        Map<String, Object> resultMap = new HashMap<>();
        if (pembayaranResult.isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "pembayaran telah terdaftar");
        } else {
            try {
                repo.save(pembayaran);
                resultMap.put("success", true);
                resultMap.put("message", "insert pembayaran berhasil");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "insert pembayaran gagal");
            }
        }
        return resultMap;
    }

    @DeleteMapping("delete")
    Map<String, Object> deletePembayaran(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try  {
            service.deletePembayaran(id);
            result.put("Success", true);
        } catch (Exception e){
            result.put("Gagal", false);
        }
        return result;
    }

    @PutMapping("update")
    public void updatePembayaran (@RequestBody Pembayaran pembayaran) {
        service.updatePembayaran(pembayaran);
    }

    @GetMapping("page")
    public Page<Pembayaran> getAllPembayaranPaginated(String search, Pageable pageable) {
        return service.getAllPembayaranPaginated(search, pageable);
    }

    @GetMapping("findById")
    public Pembayaran getById(String id) {
        try {
            if (repo.findById(id).isPresent())
                return repo.findById(id).get();
        } catch (Exception e) {
            return null;
        }
        return null;
    }


}
