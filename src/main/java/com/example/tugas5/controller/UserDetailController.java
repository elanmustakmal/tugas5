package com.example.tugas5.controller;

import com.example.tugas5.model.User;
import com.example.tugas5.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.tugas5.repository.UserRepository;
import com.example.tugas5.service.UserDetailService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userDetail/")
public class UserDetailController {

    @Autowired
    UserRepository urepo;

    @Autowired
    UserDetailService service;

    @GetMapping("")
    List<UserDetail> getusers() {
        return (List<UserDetail>) service.findAllUserDetail();
    }

    @PostMapping("insert")
    public void insertUserDetail (@RequestBody UserDetail userDetail, String userId) {
        service.inserUserDetail(userDetail, userId);
    }

    @DeleteMapping("delete")
    Map<String, Object> deleteUserDetail(@RequestParam String id) {
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
    public void updateUserDetail (@RequestBody UserDetail userDetail, String userId) {
        service.updateUserDetail(userDetail, userId);
    }


    @GetMapping("getByUserId")
    public UserDetail getDataByUserId(String id) {
        User userResult = urepo.findById(id).get();
        UserDetail userDetail = userResult.getUserDetail();
        return userDetail;
    }


}
