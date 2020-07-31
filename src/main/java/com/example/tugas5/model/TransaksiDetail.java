package com.example.tugas5.model;

import lombok.*;
import org.springframework.data.annotation.Id;
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
    private Transaksi transaksi;
    private Item item;
    private Kurir kurir;
    private int jumlah;
    private UserDetail alamat;
    private Item berat;
    private Kurir resi;
    private Item harga;

}
