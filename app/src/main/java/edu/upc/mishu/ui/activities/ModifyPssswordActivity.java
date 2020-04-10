package edu.upc.mishu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.model.AES256Enocder;

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
        final int id=intent_last.getIntExtra("id",0);
        Log.e("传入后的name_alter", "getName"+ name_alter);

        type = findViewById(R.id.add_type);
        name = findViewById(R.id.add_name);
        url =  findViewById(R.id.add_url);
        username = findViewById(R.id.add_username);
        password = findViewById(R.id.add_password);
        note = findViewById(R.id.add_note);

        passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
        for(PasswordRecord p1:passwordRecordList){
            if(p1.getNameEncoded().equals(name_alter)){
                type.setText("type");
                name.setText(p1.getNameEncoded().toString());
                url.setText(p1.getUrlEncoded().toString());
                username.setText(p1.getUsernameEncoded().toString());
                password.setText(p1.getPasswordEncoded().toString());
                note.setText(p1.getNoteEncoded().toString());
                Log.e("P1的值",p1.toString());
            }
        }


        Button button1 = findViewById(R.id.add_save);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //保存密码

                PasswordRecord passwordRecord = new PasswordRecord();
                passwordRecord.setType("login");
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
                intent.putExtra("id",id);
                setResult(RESULT_OK,intent);
                finish();
                passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
                for(PasswordRecord p1:passwordRecordList){
                    Log.i("修改后密码值",p1.toString());
            }
        }
        });
    }
}