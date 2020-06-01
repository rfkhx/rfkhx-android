package edu.upc.mishu.dto;

import lombok.Data;

@Data
public class PasswordRecordJSON {

    private int id;
    private String type;
    private String name;
    private String url;
    private String username;
    private String password;
    private String note;
    private String synctimetamp;
}
