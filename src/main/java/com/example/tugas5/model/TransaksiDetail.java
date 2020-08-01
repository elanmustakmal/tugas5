package com.example.tugas5.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "transaksi_detail")
public class TransaksiDetail {

    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private Transaksi transaksi;
    @DBRef
    private Item item;
    @DBRef
    private LayananKurir layanankurir;
    private String jumlah;
    private String berat;
    private String harga;

}
