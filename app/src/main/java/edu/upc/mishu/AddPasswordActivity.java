package edu.upc.mishu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.translate.AES256Enocder;

public class AddPasswordActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";

    private AES256Enocder encoder = new AES256Enocder("");

    private EditText type;
    private EditText name;
    private EditText url;
    private EditText username;
    private EditText password;
    private EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button button1 = (Button) findViewById(R.id.add_save);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = (EditText) findViewById(R.id.add_type);
                name = (EditText) findViewById(R.id.add_name);
                url = (EditText) findViewById(R.id.add_url);
                username = (EditText) findViewById(R.id.add_username);
                password = (EditText) findViewById(R.id.add_password);
                note = (EditText) findViewById(R.id.add_note);
                Log.e(TAG, "onClick: "+ name.getText().toString());
                Log.e(TAG, "onClick: "+url.getText().toString() );
                Log.e(TAG, "onClick: "+username.getText().toString() );
                Log.e(TAG, "onClick: "+password.getText().toString() );
                Log.e(TAG, "onClick: "+note.getText().toString() );
                PasswordRecord passwordRecord = new PasswordRecord();
                passwordRecord.setType("login");
                passwordRecord.setName(name.getText().toString());
                passwordRecord.setUrl(url.getText().toString());
                passwordRecord.setUsername(username.getText().toString());
                passwordRecord.setPassword(password.getText().toString());
                passwordRecord.setNote(note.getText().toString());

                passwordRecord.encode(encoder,1);
                passwordRecord.save();
                Log.e(TAG, "onClick: pr"+passwordRecord.toString() );
                Intent intent = new Intent();
                intent.putExtra("name",passwordRecord.getName());
                intent.putExtra("username",passwordRecord.getUsername());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

}
