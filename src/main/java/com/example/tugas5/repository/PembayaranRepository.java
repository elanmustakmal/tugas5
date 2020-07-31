package com.example.tugas5.repository;

import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PembayaranRepository extends MongoRepository<Pembayaran, String> {
    Optional<Pembayaran> findByNama(String nama);

}
