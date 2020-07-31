package com.example.tugas5.controller;

import com.example.tugas5.model.Item;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.ItemRepository;
import com.example.tugas5.repository.UserRepository;
import com.example.tugas5.service.ItemService;
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
@RequestMapping("/api/item/")
public class ItemController {

    @Autowired
    ItemRepository repo;

    @Autowired
    ItemService service;

    @GetMapping("")
    List<Item> getusers() {
        return (List<Item>) repo.findAll();
    }

    @PostMapping("insert")
    public Map<String, Object> insertItem (@RequestBody Item item) {
        Optional<Item> itemResult = repo.findByNama(item.getNama());
        Map<String, Object> resultMap = new HashMap<>();
        if (itemResult.isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "item telah terdaftar");
        } else {
            try {
                repo.save(item);
                resultMap.put("success", true);
                resultMap.put("message", "insert item berhasil");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "insert item gagal");
            }
        }
        return resultMap;
    }

    @DeleteMapping("delete")
    Map<String, Object> deleteItem(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try  {
            service.deleteItem(id);
            result.put("Success", true);
        } catch (Exception e){
            result.put("Gagal", false);
        }
        return result;
    }

    @PutMapping("update")
    public void updateItem (@RequestBody Item item) {
        service.updateItem(item);
    }

    @GetMapping("page")
    public Page<Item> getAllItemPaginated(String search, Pageable pageable) {
        return service.getAllItemPaginated(search, pageable);
    }

    @GetMapping("findByName")
    public Item getByItemName(String nama) {
        try {
            if (repo.findByNama(nama).isPresent())
                return repo.findByNama(nama).get();
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
