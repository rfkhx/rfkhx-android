package edu.upc.mishu;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.dto.PasswordRecordJSON;
import edu.upc.mishu.http.OKHttpSyncHttpService;


public class OKHttpSycnHttpService {

    OKHttpSyncHttpService okHttpSyncHttpService;
    @Before
    public void init(){
        okHttpSyncHttpService=new OKHttpSyncHttpService();
    }

    @Test
    public void testLogin() throws IOException, JSONException {
        okHttpSyncHttpService.login("test@test.com","test");
    }
    @Test
    public void testGetAll(){
        okHttpSyncHttpService.login("test@test.com","test");
        System.out.println(okHttpSyncHttpService.getAllRecords());;
    }
    @Test
    public void testCreateOrEditRecord(){
        List<PasswordRecordJSON> passwordRecordJSONS =new ArrayList();;
        PasswordRecordJSON password=new PasswordRecordJSON();
        password.setName("test");
        password.setNote("test");
        password.setPassword("test");
        password.setUrl("test");
        password.setUsername("test");
        passwordRecordJSONS.add(password);
        System.out.println(password.toString());
        okHttpSyncHttpService.login("1736862060@qq.com","111111");
        okHttpSyncHttpService.createOrEditRecord(passwordRecordJSONS);

    }
}
