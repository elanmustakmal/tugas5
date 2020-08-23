package com.example.tugas5.controller;

import com.example.tugas5.model.Kurir;
import com.example.tugas5.model.LayananKurir;
import com.example.tugas5.model.User;
import com.example.tugas5.model.UserDetail;
import com.example.tugas5.repository.KurirRepository;
import com.example.tugas5.repository.LayananKurirRepository;
import com.example.tugas5.service.KurirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kurir/")
public class KurirController {

    @Autowired
    KurirRepository repo;

    @Autowired
    KurirService service;

    @Autowired
    LayananKurirRepository lkrepo;

    @GetMapping("")
    List<Kurir> getKurir() {
        return (List<Kurir>) service.findAllKurir();
    }


    @PostMapping("insert")
    public void insertKurir (@RequestBody Kurir kurir, String id) {
        service.inserKurir(kurir, id);
    }

    @DeleteMapping("delete")
    Map<String, Object> deleteKurir(@RequestParam String id) {
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
    public void updateKurir (@RequestBody Kurir kurir, String id) {
        service.updateKurir(kurir, id);
    }


    @GetMapping("findByid")
    public Kurir getByNama(String id) {
        LayananKurir lkResult = lkrepo.findByNama(id).get();
        Kurir kurir = lkResult.getKurir();
        return kurir;
    }

}
