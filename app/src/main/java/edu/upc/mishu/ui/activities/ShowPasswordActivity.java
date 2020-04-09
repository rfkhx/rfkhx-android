package edu.upc.mishu.ui.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.PointerIcon;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;

public class ShowPasswordActivity extends AppCompatActivity {
    private TextView username;
    private TextView password;
    private TextView url;
    private List<PasswordRecord> passwordRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_show);

        init();

        Intent intent_show = getIntent();
        String project_name = intent_show.getStringExtra("project_name");
        Log.e("P1传入的值Project_name",project_name);
        username = findViewById(R.id.show_username_text);
        password = findViewById(R.id.show_password_text);
        url = findViewById(R.id.show_url_text);

        passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
        for (PasswordRecord p1 : passwordRecordList) {
            if (p1.getUsernameEncoded().equals(project_name)) {
                url.setText(p1.getUrlEncoded().toString());
                username.setText(p1.getUsernameEncoded().toString());
                password.setText(p1.getPasswordEncoded().toString());
                Log.e("P1有相等的值",p1.getUsernameEncoded().toString());
                Log.e("P1的url",p1.getUsernameEncoded().toString());
                Log.e("P1d额username",p1.getUsernameEncoded().toString());
                Log.e("P1的password",p1.getPasswordEncoded().toString());

                ImageButton imagebutton_password=findViewById(R.id.ImageButton_copy_password);
                ImageButton imagebutton_username=findViewById(R.id.ImageButton_copy_username);
                ImageButton imagebutton_url=findViewById(R.id.ImageButton_copy_url);
                final ClipboardManager cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);

                imagebutton_password.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        cm.setPrimaryClip(ClipData.newPlainText(null,password.getText().toString()));
                        Toast toast=Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                imagebutton_username.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        cm.setPrimaryClip(ClipData.newPlainText(null,username.getText().toString()));
                        Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                    }
                });
                imagebutton_url.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        cm.setPrimaryClip(ClipData.newPlainText(null,url.getText().toString()));
                        Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    private void init(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        WindowManager.LayoutParams layoutParams =getWindow().getAttributes();
        layoutParams.height = (int) (height*0.5);
        layoutParams.width = (int) (width*0.8);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundResource(R.drawable.dialog_background);

    }


}