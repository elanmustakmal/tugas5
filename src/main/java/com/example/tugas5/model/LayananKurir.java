package com.example.tugas5.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "layanan_kurir")
public class LayananKurir {
    @Id
    private String id;
    private String nama;
    private int harga;
    private String estimasi;
    private Kurir kurir;
}
