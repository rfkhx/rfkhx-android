package edu.upc.mishu.ui.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.utils.AppInfo;
import edu.upc.mishu.utils.CheckPassword;
import edu.upc.mishu.utils.CheckPswMeter;

public class ShowPasswordActivity extends AppCompatActivity {
    private TextView username;
    private TextView password;
    private TextView url;
    private TextView note;
    private List<PasswordRecord> passwordRecordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_show);


        init();

        Intent intent_show = getIntent();
        Long project_id = intent_show.getLongExtra("project_id", 0);
        Log.e("P1传入的值Project_id", project_id.toString() != null ? project_id.toString() : "null");
        username = findViewById(R.id.show_username_text);
        password = findViewById(R.id.show_password_text);
        url = findViewById(R.id.show_url_text);
        note = findViewById(R.id.show_note_text);

        passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);

        for (PasswordRecord p1 : passwordRecordList) {
            p1.decode(App.encoder, 1);//得到解密的内容
            Log.e("展示", p1.toString());
            Log.e("展示进入", p1.getId().toString());
            Log.e("展示进入", project_id.toString());
            if (p1.getId().equals(project_id)) {
                Log.e("展示进入", p1.getId().toString());
                url.setText(p1.getUrl());
                username.setText(p1.getUsername());
                password.setText(p1.getPassword());
                note.setText(p1.getNote());

                Log.e("P1有相等的值", p1.getUsername());
                Log.e("P1的url", p1.getUsername());
                Log.e("P1d额username", p1.getUsername());
                Log.e("P1的password", p1.getPassword());

                ImageButton imagebutton_password = findViewById(R.id.ImageButton_copy_password);
                ImageButton imagebutton_username = findViewById(R.id.ImageButton_copy_username);
                ImageButton imagebutton_url = findViewById(R.id.ImageButton_copy_url);
                ImageButton imagebutton_note = findViewById(R.id.ImageButton_copy_note);
                ImageButton imageButton_check = findViewById(R.id.ImageButton_check_password);
                final ClipboardManager cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);

                imagebutton_password.setOnClickListener(v -> {
                    cm.setPrimaryClip(ClipData.newPlainText(null, password.getText().toString()));
                    Toast toast = Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT);
                    toast.show();
                });
                imagebutton_username.setOnClickListener(v -> {
                    cm.setPrimaryClip(ClipData.newPlainText(null, username.getText().toString()));
                    Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                });
                imagebutton_url.setOnClickListener(v -> {
                    cm.setPrimaryClip(ClipData.newPlainText(null, url.getText().toString()));
                    Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                });
                imagebutton_note.setOnClickListener(v -> {
                    cm.setPrimaryClip(ClipData.newPlainText(null, note.getText().toString()));
                    Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                });
                imageButton_check.setOnClickListener(v -> {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CheckPassword checkPassword = new CheckPassword();
                            checkPassword.setPassword(p1.getPassword());
                            String info = null;

                            if(p1.getPassword().length()<8){
                                info = "您的密码长度过短，请及时修改密码";
                            }else{
                                CheckPswMeter checkPswMeter = new CheckPswMeter(p1.getPassword());
                                int s =checkPswMeter.jiafen() + checkPswMeter.jianfen();
                                if(s < 60){
                                    info = "您的密码不够复杂，有些泄露的风险，请尽快修改密码";
                                }else{
                                    checkPassword.run();
                                    int count = checkPassword.getSamePasswordCount();
                                    if(count >0){
                                        info = "已知您的密码在已经泄露的密码库中共使用过"+count+"次，请尽快修改密码";
                                    }else{
                                        info = "您的密码很安全";
                                    }
                                }
                            }
                            Looper.prepare();
                            Toast.makeText(ShowPasswordActivity.this, info, Toast.LENGTH_SHORT).show();
                            Looper.loop();


                        }
                    }).start();

                });
            }
        }
    }

    private void init() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = (int) (width * 0.8);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundResource(R.drawable.dialog_background);

    }


}