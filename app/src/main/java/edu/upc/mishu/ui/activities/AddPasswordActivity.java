package edu.upc.mishu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.orm.StringUtil;
import com.xuexiang.xutil.common.StringUtils;

import java.util.List;

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
    private List<PasswordRecord> passwordRecordList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        passwordRecordList= PasswordRecord.listAll(PasswordRecord.class);
        Button button1 = findViewById(R.id.add_save);

        button1.setOnClickListener(v -> {
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
            passwordRecord.setType("");
            passwordRecord.setName(name.getText().toString());
            passwordRecord.setUrl(url.getText().toString());
            passwordRecord.setUsername(username.getText().toString());
            passwordRecord.setPassword(password.getText().toString());
            passwordRecord.setNote(note.getText().toString());

            Log.e(TAG,"P1的空值："+passwordRecord.getName().toString());
            Log.e(TAG,"P1的空值："+passwordRecord.getName());
            //保证项目名的唯一性
            if(StringUtils.isEmpty(passwordRecord.getName().toString())||StringUtils.isEmptyTrim(passwordRecord.getName().toString())){
                //Toast.makeText(getApplicationContext(), "项目名不能为空", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                        .setTitle("提示")//标题
                        .setMessage("项目名不能为空")//内容
                        .create();
                alertDialog1.show();
            }else{
                boolean flag=false;
                for(PasswordRecord p1:passwordRecordList) {
                    p1.decode(App.encoder, 1);//得到解密的内容
                    if(p1.getName().toString().equals(passwordRecord.getName().toString())){
                        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                                .setTitle("提示")//标题
                                .setMessage("项目名已存在")//内容
                                .create();
                        alertDialog1.show();
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    passwordRecord.encode(App.encoder,1);
                    passwordRecord.save();
                    Log.e(TAG, "onClick: pr"+passwordRecord.toString() );
                    Intent intent = new Intent();
                    intent.putExtra("name",passwordRecord.getName());
                    intent.putExtra("username",passwordRecord.getUsername());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }

}
