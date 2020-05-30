package edu.upc.mishu;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import edu.upc.mishu.http.OKHttpSyncHttpService;

@RunWith(AndroidJUnit4.class)
public class OKHttpSycnHttpService {

    OKHttpSyncHttpService okHttpSyncHttpService;
    @Before
    public void init(){
        okHttpSyncHttpService=new OKHttpSyncHttpService();
    }

    @Test
    public void testGetAll(){
        okHttpSyncHttpService.login("test@test.com","test");
        okHttpSyncHttpService.getAllRecords();
    }
}
