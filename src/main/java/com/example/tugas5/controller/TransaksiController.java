package com.example.tugas5.controller;

import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.model.Transaksi;
import com.example.tugas5.repository.TransaksiRepository;
import com.example.tugas5.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaksi/")
public class TransaksiController {

    @Autowired
    TransaksiRepository repo;

    @Autowired
    TransaksiService service;

    @GetMapping("")
    List<Transaksi> getAllTransaksi(){
        return (List<Transaksi>) repo.findAll();
    }

    @PostMapping("insert")
    public Map<String, Object> insertTransaksi (@RequestBody Transaksi transaksi) {
        Optional<Transaksi> transaksiResult = repo.findByTanggal(transaksi.getTanggal());
        Map<String, Object> resultMap = new HashMap<>();
        if (transaksiResult.isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "transaksi telah terdaftar");
        } else {
            try {
                repo.save(transaksi);
                resultMap.put("success", true);
                resultMap.put("message", "insert transaksi berhasil");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "insert transaksi gagal");
            }
        }
        return resultMap;
    }

    @DeleteMapping("delete")
    Map<String, Object> deleteTransaksi(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try  {
            service.deleteTransaksi(id);
            result.put("Success", true);
        } catch (Exception e){
            result.put("Gagal", false);
        }
        return result;
    }

    @PutMapping("update")
    public void updateTransaksi (@RequestBody Transaksi transaksi) {
        service.updateTransaksi(transaksi);
    }

    @GetMapping("page")
    public Page<Transaksi> getAllTransaksiPaginated(String search, Pageable pageable) {
        return service.getAllTransaksiPaginated(search, pageable);
    }

    @GetMapping("findById")
    public Transaksi getById(String id) {
        try {
            if (repo.findById(id).isPresent())
                return repo.findById(id).get();
        } catch (Exception e) {
            return null;
        }
        return null;
    }



}
