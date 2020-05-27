package edu.upc.mishu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;

public class ModifyPssswordActivity extends AppCompatActivity {

    private static final String TAG = "ModifyActivity";

    private EditText type;
    private EditText name;
    private EditText url;
    private EditText username;
    private EditText password;
    private EditText note;
    private List<PasswordRecord> passwordRecordList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent_last=getIntent();
        final String name_alter=intent_last.getStringExtra("name");
        final Long id=intent_last.getLongExtra("project_id",0);
        Log.e("传入后的name_alter", "getName"+ name_alter);

        name = findViewById(R.id.add_name);
        url =  findViewById(R.id.add_url);
        username = findViewById(R.id.add_username);
        password = findViewById(R.id.add_password);
        note = findViewById(R.id.add_note);

        passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
        for(PasswordRecord p1:passwordRecordList){
            p1.decode(App.encoder,1);//得到解密的内容
            Log.e("P1getName的值",p1.getName());
            if(p1.getId()==id){
                name.setText(p1.getName());
                url.setText(p1.getUrl());
                username.setText(p1.getUsername());
                password.setText(p1.getPassword());
                note.setText(p1.getNote());
                Log.e("P1的值",p1.toString());
                break;
            }
        }


        Button button1 = findViewById(R.id.add_save);

        button1.setOnClickListener(v -> {

            //保存密码

            PasswordRecord passwordRecord = new PasswordRecord();
            passwordRecord.setId(id);
            passwordRecord.setType("");
            passwordRecord.setName(name.getText().toString());
            passwordRecord.setUrl(url.getText().toString());
            passwordRecord.setUsername(username.getText().toString());
            passwordRecord.setPassword(password.getText().toString());
            passwordRecord.setNote(note.getText().toString());

            passwordRecord.encode(App.encoder,1);
            passwordRecord.save();

            Log.e(TAG, "onClick: pr"+passwordRecord.toString() );
            Intent intent = new Intent();
            intent.putExtra("name",passwordRecord.getName());
            intent.putExtra("username",passwordRecord.getUsername());
            intent.putExtra("project_id",id);
            setResult(RESULT_OK,intent);
            finish();
            passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
            for(PasswordRecord p1:passwordRecordList){
                p1.decode(App.encoder,1);
                Log.i("修改后密码值",p1.toString());
            }
        });
    }
}
