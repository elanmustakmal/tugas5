package com.example.tugas5.repository;

import com.example.tugas5.model.Pembayaran;
import com.example.tugas5.model.TransaksiDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransaksiDetailRepository extends MongoRepository<TransaksiDetail, String> {

    Optional<TransaksiDetail> findById(String Id);

}
