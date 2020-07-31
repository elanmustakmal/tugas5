package com.example.tugas5.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "item")
public class Item {

    @Id
    private String id;
    private String nama;
    private int stock;
    private int harga;
    private String description;
    private int terjual;
    private int berat;
}
