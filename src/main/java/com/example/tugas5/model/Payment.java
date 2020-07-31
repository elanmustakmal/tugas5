package com.example.tugas5.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "payment")
public class Payment {

    @Id
    private ObjectId id;
    private String nama;
    private String code;
    private String jenis;
}
