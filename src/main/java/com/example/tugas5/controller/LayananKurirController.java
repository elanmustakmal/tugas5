package com.example.tugas5.controller;

import com.example.tugas5.model.Item;
import com.example.tugas5.model.LayananKurir;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.LayananKurirRepository;
import com.example.tugas5.repository.UserRepository;
import com.example.tugas5.service.LayananKurirService;
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
@RequestMapping("/api/layananKurir/")
public class LayananKurirController {

    @Autowired
    LayananKurirRepository repo;

    @Autowired
    LayananKurirService service;

    @GetMapping("")
    List<LayananKurir> getusers() {
        return (List<LayananKurir>) repo.findAll();
    }


    @PostMapping("insert")
    public Map<String, Object> insertLayanan (@RequestBody LayananKurir layananKurir) {
        Optional<LayananKurir> layananResult = repo.findByNama(layananKurir.getNama());
        Map<String, Object> resultMap = new HashMap<>();
        if (layananResult.isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "layanan telah terdaftar");
        } else {
            try {
                repo.save(layananKurir);
                resultMap.put("success", true);
                resultMap.put("message", "insert layanan berhasil");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "insert layanan gagal");
            }
        }
        return resultMap;
    }

    @DeleteMapping("delete")
    Map<String, Object> deleteLayanan(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try  {
            service.deleteLayanan(id);
            result.put("Success", true);
        } catch (Exception e){
            result.put("Gagal", false);
        }
        return result;
    }

    @PutMapping("update")
    public void updateLayanan (@RequestBody LayananKurir layananKurir) {
        service.updateLayanan(layananKurir);
    }


    @GetMapping("page")
    public Page<LayananKurir> getAllLayananPaginated(String search, Pageable pageable) {
        return service.getAllLayananPaginated(search, pageable);
    }

    @GetMapping("findById")
    public LayananKurir getByName(String id) {
        try {
            if (repo.findById(id).isPresent())
                return repo.findById(id).get();
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
