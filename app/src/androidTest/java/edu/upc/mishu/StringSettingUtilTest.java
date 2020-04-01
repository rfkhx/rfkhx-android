package edu.upc.mishu;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.upc.mishu.utils.StringSettingUtil;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class StringSettingUtilTest {
    @Before
    public void writeTest(){
        StringSettingUtil.writeSetting("test","Hello World!");
    }

    @Test
    public void readTest(){
        assertEquals("Hello World!",StringSettingUtil.getSetting("test"));
    }
}
