package com.example.tugas5.controller;

import com.example.tugas5.model.Login;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.UserRepository;
import com.example.tugas5.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    UserRepository repo;

    @Autowired
    UserService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    List<User> getusers() {
        return (List<User>) repo.findAll();
    }


    @PostMapping("insert")
    public Map<String, Object> insertUser (@RequestBody User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        Optional<User> userResult = repo.findByUsername(user.getUsername());
        Map<String, Object> resultMap = new HashMap<>();
        if (userResult.isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "user telah terdaftar");
        } else {
            try {
                repo.save(user);
                resultMap.put("success", true);
                resultMap.put("message", "insert user berhasil");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "insert user gagal");
            }
        }
        return resultMap;
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("delete")
    Map<String, Object> deleteUser(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try  {
            service.deleteUser(id);
            result.put("Success", true);
        } catch (Exception e){
            result.put("Gagal", false);
        }
        return result;
    }

    @PutMapping("update")
    public void updateUser (@RequestBody User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        service.updateUser(user);
    }


    @GetMapping("getByAlamat")
    public List<User> getByAlamat(String alamat) {
        List<User> user = repo.findByAlamat(alamat);
        System.out.println("user result : " + user.toString());
        return user;
    }

    @GetMapping("page")
    public Page<User> getAllUserPaginated(String search, Pageable pageable) {
        return service.getAllUserPaginated(search, pageable);
    }

    @GetMapping("findByUsername")
    public User getByUserName(String username) {
        try {
            if (repo.findByUsername(username).isPresent())
            return repo.findByUsername(username).get();
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody Login body) {
        System.out.println(body.toString());
        User result = service.findByUsername(body.getUsername());
        Map<String, Object> resultMap = new HashMap<>();

        if (result != null) {
            boolean isMatch = passwordEncoder.matches(body.getPassword(), result.getPassword());
            if (isMatch) {
                String token = Jwts.builder()
                        .setSubject(body.getUsername())
                        .claim("roles", result.getRoles())
                        .signWith(SignatureAlgorithm.HS256, "12345")
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                        .compact();
                resultMap.put("success", true);
                resultMap.put("record", result);
                resultMap.put("token", token);
            }
        } else {
            resultMap.put("success", false);
        }
        return resultMap;

    }

}
