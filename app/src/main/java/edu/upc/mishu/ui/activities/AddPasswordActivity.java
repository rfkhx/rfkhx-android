package edu.upc.mishu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;

public class AddPasswordActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";

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

        Button button1 = findViewById(R.id.add_save);

        button1.setOnClickListener(v -> {
            type = findViewById(R.id.add_type);
            name = findViewById(R.id.add_name);
            url =  findViewById(R.id.add_url);
            username = findViewById(R.id.add_username);
            password = findViewById(R.id.add_password);
            note = findViewById(R.id.add_note);
//                Log.e(TAG, "onClick: "+ name.getText().toString());
//                Log.e(TAG, "onClick: "+url.getText().toString() );
//                Log.e(TAG, "onClick: "+username.getText().toString() );
//                Log.e(TAG, "onClick: "+password.getText().toString() );
//                Log.e(TAG, "onClick: "+note.getText().toString() );
            PasswordRecord passwordRecord = new PasswordRecord();
            passwordRecord.setType("login");
            passwordRecord.setName(name.getText().toString());
            passwordRecord.setUrl(url.getText().toString());
            passwordRecord.setUsername(username.getText().toString());
            passwordRecord.setPassword(password.getText().toString());
            passwordRecord.setNote(note.getText().toString());
//                passwordRecord.encode(encoder,1);
//                passwordRecord.save();

            new Thread(() -> {
                Log.i(TAG, "run: is run ");
                PasswordRecord passwordRecord1 = new PasswordRecord();
                passwordRecord1.setType("login");
                passwordRecord1.setName(name.getText().toString());
                passwordRecord1.setUrl(url.getText().toString());
                passwordRecord1.setUsername(username.getText().toString());
                passwordRecord1.setPassword(password.getText().toString());
                passwordRecord1.setNote(note.getText().toString());
                passwordRecord1.encode(App.encoder,1);
                passwordRecord1.save();
                Log.i(TAG, "run: "+ passwordRecord1.toString());
            }).start();

            Log.e(TAG, "onClick: pr"+passwordRecord.toString() );
            Intent intent = new Intent();
            intent.putExtra("name",passwordRecord.getName());
            intent.putExtra("username",passwordRecord.getUsername());
            setResult(RESULT_OK,intent);
            finish();
        });

    }

}
