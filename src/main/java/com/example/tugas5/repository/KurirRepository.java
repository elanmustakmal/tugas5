package com.example.tugas5.repository;

import com.example.tugas5.model.Kurir;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KurirRepository extends MongoRepository<Kurir, String> {
}
