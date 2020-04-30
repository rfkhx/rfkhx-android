package edu.upc.mishu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class PasswordItem {
    private  Long id_database;
    private int imageId;
    private String website;
    private String username;
    public PasswordItem (){};
}
