package edu.upc.mishu;
import org.junit.Before;
import org.junit.Test;

import edu.upc.mishu.translate.*;

import static org.junit.Assert.*;

public class AESEncodeTest {
    private Transformable tem;
    @Before
    public void init() {
        tem = new AES256Enocder("fgchgvjhbjk.fhtjdhjkgkg");
    }

    @Test
    public void encodeTest1() {
        String miwen = tem.encode("1111");
        String mingwen = tem.decode(miwen);
        assertEquals("1111",mingwen);
    }

    @Test
    public void encodeTest2() {
        String miwen1 = tem.encode("liufukang");
        String mingwen1 = tem.decode(miwen1);
        assertEquals("liufukang",mingwen1);
    }

    @Test
    public void encodeTest3() {
        String miwen2 = tem.encode("liuhaixin..");
        String mingwen2 = tem.decode(miwen2);
        assertEquals("liuhaixin..",mingwen2);
    }

    @Test
    public void encodeTest4(){
        String miwen3=tem.encode("lirui123");
        String mingwen3=tem.decode(miwen3);
        assertEquals("lirui123",mingwen3);
    }
}
