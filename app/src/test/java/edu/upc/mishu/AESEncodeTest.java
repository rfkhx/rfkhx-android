package edu.upc.mishu;
import org.junit.Before;
import org.junit.Test;

import edu.upc.mishu.interfaces.Transformable;
import edu.upc.mishu.model.AES256Enocder;

import static org.junit.Assert.*;

public class AESEncodeTest {
    private Transformable tem;
    @Before
    public void init() {
        tem =  AES256Enocder.getInstance("fgchgvjhbjk.fhtjdhjkgkg");
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

    @Test
    /**
     * 加密一个128位密码15次耗时不应该超过3秒
     */
    public void sppedTest(){
        String text="2zLK75caWPjpwj5C545e2zLg7nXbS4vQvj2YfSt3892PpsLRLANcE8mpZA4jNg8GS99E8Sr2u59zcq2Px4HESwv6Lm38Hgbv29Z6272FQV9zwsU5K8V93yDPzLFKXrrU";
        long preTime=System.currentTimeMillis();
        for (int i = 0; i < 15; i++) {
            text=tem.encode(text);
        }
        long postTime=System.currentTimeMillis();
        assertTrue(postTime-preTime<=3000);
    }
}
