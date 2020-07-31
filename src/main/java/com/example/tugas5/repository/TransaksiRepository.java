package com.example.tugas5.repository;

import com.example.tugas5.model.Transaksi;
import com.example.tugas5.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransaksiRepository extends MongoRepository<Transaksi, String> {
    Optional<Transaksi> findByTanggal(String tanggal);
}
