package edu.upc.mishu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuexiang.xutil.common.RegexUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.Iterator;

import edu.upc.mishu.R;
import edu.upc.mishu.dto.User;
import edu.upc.mishu.interfaces.RegisterObserver;
import edu.upc.mishu.interfaces.Transformable;
import edu.upc.mishu.model.AES256Enocder;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RegisterObserver {
    private TextView tvRegister;
    public static LoginActivity instance;
    private EditText textEmail;
    private EditText textPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegister=findViewById(R.id.login_tv_register);
        tvRegister.setOnClickListener(this);

        textEmail=findViewById(R.id.login_email);
        textPassword=findViewById(R.id.login_password);
        btnSubmit=findViewById(R.id.login_submit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_tv_register:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_submit:
                if(!RegexUtils.isEmail(textEmail.getText().toString())){
                    ToastUtils.toast("邮箱输入有误，请检查输入");
                    break;
                }
                //TODO 检查密码是否正确
                Iterator<User> userIterator=User.findAsIterator(User.class,"email=?",textEmail.getText().toString());
                if(!userIterator.hasNext()){
                    ToastUtils.toast("用户名或密码错误");
                    break;
                }
                User user=userIterator.next();
                Transformable encoder=new AES256Enocder(textPassword.getText().toString());
                if(encoder.decode(user.getEmailEncoded()).equals(user.getEmail())){
                    //TODO 密码正确
                    finish();
                }else{
                    //TODO 密码错误
                }
                break;
        }
    }

    @Override
    public void onUserRegister(User user) {
        if(user!=null){
            //注册完用户名直接填在登录里
            textEmail.setText(user.getEmail());
        }
    }
}
