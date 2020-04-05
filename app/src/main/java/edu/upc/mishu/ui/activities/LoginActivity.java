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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.upc.mishu.R;
import edu.upc.mishu.dto.User;
import edu.upc.mishu.interfaces.LoginObservable;
import edu.upc.mishu.interfaces.LoginObserver;
import edu.upc.mishu.interfaces.RegisterObserver;
import edu.upc.mishu.interfaces.Transformable;
import edu.upc.mishu.model.AES256Enocder;
import edu.upc.mishu.model.ExampleLoginObserver;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RegisterObserver , LoginObservable {
    private TextView tvRegister;
    public static LoginActivity instance;
    private EditText textEmail;
    private EditText textPassword;
    private Button btnSubmit;
    private List<LoginObserver> loginObserverList=new ArrayList<>();

    private void attachObservers(){
        attach(new ExampleLoginObserver());
    }

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
        btnSubmit.setOnClickListener(this);

        attachObservers();
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
                    ToastUtils.toast(getString(R.string.login_email_invalid));
                    break;
                }
                Iterator<User> userIterator=User.findAsIterator(User.class,"email=?",textEmail.getText().toString());
                if(!userIterator.hasNext()){
                    ToastUtils.toast(getString(R.string.login_wrong_username_or_password));
                    break;
                }
                User user=userIterator.next();
                Transformable encoder=new AES256Enocder(textPassword.getText().toString());
                if(encoder.decode(user.getEmailEncoded()).equals(user.getEmail())){
                    notify(user);
                    Intent intent1=new Intent(this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                }else{
                    notify(null);
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

    @Override
    public void attach(LoginObserver observer) {
        loginObserverList.add(observer);
    }

    @Override
    public void detach(LoginObserver observer) {
        loginObserverList.remove(observer);
    }

    @Override
    public void notify(User user) {
        for (LoginObserver observer :
                loginObserverList) {
            observer.onLogin(user);
        }
    }
}
