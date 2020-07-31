package com.example.tugas5.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "pembayaran")
public class Pembayaran {

    @Id
    private String id;
    private String nama;
    private Payment payment;
}
