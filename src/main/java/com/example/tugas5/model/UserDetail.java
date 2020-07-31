package com.example.tugas5.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "detail_user")
public class UserDetail {

    private String alamat;
    private String phone1;
    private String phone2;
    private String type;
    private String jenis_kelamin;
    private int kodepos;

}
