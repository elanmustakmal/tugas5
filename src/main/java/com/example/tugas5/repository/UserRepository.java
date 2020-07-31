package com.example.tugas5.repository;

import com.example.tugas5.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("{'userDetail.alamat': { $regex: ?0 } }")
    List<User> findByAlamat(String alamat);
//    User delete(String id);

//    Page<User> findByUsernameContaining(String search, Pageable pageable);
//    List<User> findByAddress_CityContaining(String search);
//    List<User> findByAddress_ProvinceContaining(String search);
//    List<User> findByAddress_CountryContaining(String search);

}
