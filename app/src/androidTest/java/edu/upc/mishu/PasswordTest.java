package edu.upc.mishu;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;

import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.model.Encoder;
import edu.upc.mishu.interfaces.Transformable;

import static org.junit.Assert.*;

/**
 * 对密码记录的CRUD
 */
@RunWith(AndroidJUnit4.class)
public class PasswordTest {
    private Transformable encoder=new Encoder();
    private int times=3;

    private String encodeString(String str){
        for (int i = 0; i <times; i++) {
            str=encoder.encode(str);
        }
        return str;
    }

    private String decodeString(String str){
        for (int i = 0; i < times; i++) {
            str=encoder.decode(str);
        }
        return str;
    }

    @Before
    public void addSomeData(){
        PasswordRecord.deleteAll(PasswordRecord.class);
        PasswordRecord.builder().type("login").name("百度").username("user1").password("password1").build().encode(encoder,times).save();
        PasswordRecord.builder().type("login").name("阿里").username("user2").password("password2").build().encode(encoder,times).save();
        PasswordRecord.builder().type("login").name("腾讯").username("user3").password("password3").build().encode(encoder,times).save();
        PasswordRecord.builder().type("login").name("新浪").username("user4").password("password4").build().encode(encoder,times).save();
        assertEquals(4,PasswordRecord.count(PasswordRecord.class,"",new String[]{}));
    }

    @Test
    public void readTest(){
        Iterator<PasswordRecord> iterator=PasswordRecord.findAsIterator(PasswordRecord.class,"name_encoded=?", encodeString("腾讯"));
        assertTrue(iterator.hasNext());
        PasswordRecord tmp=iterator.next();
        tmp.decode(encoder,times);
        assertEquals("user3",tmp.getUsername());
        assertEquals("password3",tmp.getPassword());

    }

    @Test
    public void editTest(){
        Iterator<PasswordRecord> iterator=PasswordRecord.findAsIterator(PasswordRecord.class,"name_encoded=?",encodeString("百度"));
        assertTrue(iterator.hasNext());
        PasswordRecord tmp=iterator.next();
        tmp.decode(encoder,times);
        tmp.setUrl("www.baidu.com");
        tmp.encode(encoder,times);
        tmp.save();
        iterator=PasswordRecord.findAsIterator(PasswordRecord.class,"name_encoded=?",encodeString("百度"));
        tmp=iterator.next();
        tmp.decode(encoder,times);
        assertEquals("www.baidu.com",tmp.getUrl());
    }

    @Test
    public void delTest(){
        Iterator<PasswordRecord> iterator=PasswordRecord.findAsIterator(PasswordRecord.class,"name_encoded=?",encodeString("新浪"));
        assertTrue(iterator.hasNext());
        iterator.next().delete();
        assertEquals(3,PasswordRecord.count(PasswordRecord.class,"",new String[]{}));
    }
}
