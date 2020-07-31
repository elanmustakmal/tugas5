package com.example.tugas5.repository;

import com.example.tugas5.model.Item;
import com.example.tugas5.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> findByNama(String nama);
}
