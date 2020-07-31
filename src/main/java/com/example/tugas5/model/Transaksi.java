package com.example.tugas5.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "transaksi")
public class Transaksi {

    @Id
    private String id;
    private String tanggal;
    private String total;
    private Pembayaran pembayaran;
}
