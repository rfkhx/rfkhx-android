package edu.upc.mishu.dto;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
/**
 * 本地存储用户信息
 */
public class User extends SugarRecord<User> {
    //用户的邮箱
    private String email;
    //用户的密码提示
    private String hint;
    //用户的密码加密次数
//    private Integer times;
    //用户的密码（加密）
    private String emailEncoded;
}
