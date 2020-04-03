package edu.upc.mishu.dto;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

import edu.upc.mishu.translate.Transformable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 存储密码记录的表（加密过）
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PasswordRecord extends SugarRecord<PasswordRecord> implements Serializable {
    //记录的类型，当前阶段应为login
    private String type;
    //记录的名字。如“百度”
    @Ignore
    private String name;
    //记录对应的网址，区分不同记录的主要识别依据
    @Ignore
    private String url;
    //用户名
    @Ignore
    private String username;
    //密码
    @Ignore
    private String password;
    //文本记录，留给用户自己记点东西
    @Ignore
    private String note;

    /**
     * 下面的字段对应上面的，是加密后的字段
     */
    private String nameEncoded;
    private String urlEncoded;
    private String usernameEncoded;
    private String passwordEncoded;
    private String noteEncoded;

    public PasswordRecord encode(Transformable encoder,int times){
        nameEncoded=name;
        urlEncoded=url;
        usernameEncoded=username;
        passwordEncoded=password;
        noteEncoded=note;

        for (int i = 0; i <times; i++) {
            if(nameEncoded!=null){
                nameEncoded=encoder.encode(nameEncoded);
            }
            if(urlEncoded!=null) {
                urlEncoded=encoder.encode(urlEncoded);
            }
            if(usernameEncoded!=null){
                usernameEncoded=encoder.encode(usernameEncoded);
            }
            if(passwordEncoded!=null){
                passwordEncoded=encoder.encode(passwordEncoded);
            }
            if(noteEncoded!=null){
                noteEncoded=encoder.encode(noteEncoded);
            }
        }
        return this;
    }

    public PasswordRecord decode(Transformable encoder,int times){
        name=nameEncoded;
        url=urlEncoded;
        username=usernameEncoded;
        password=passwordEncoded;
        note=noteEncoded;

        for (int i = 0; i < times; i++) {
            if(name!=null){
                name=encoder.decode(name);
            }
            if(url!=null){
                url=encoder.decode(url);
            }
            if(username!=null){
                username=encoder.decode(username);
            }
            if(password!=null){
                password=encoder.decode(password);
            }
            if(note!=null){
                note=encoder.decode(note);
            }
        }
        return this;
    }
}
