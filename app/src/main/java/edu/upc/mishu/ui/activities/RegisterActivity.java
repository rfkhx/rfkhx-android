package edu.upc.mishu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xuexiang.xutil.common.RegexUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.upc.mishu.R;
import edu.upc.mishu.dto.User;
import edu.upc.mishu.interfaces.RegisterObservable;
import edu.upc.mishu.interfaces.RegisterObserver;
import edu.upc.mishu.model.ExampleRegisterObserver;
import edu.upc.mishu.model.AES256Enocder;
import edu.upc.mishu.interfaces.Transformable;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterObservable {
    private EditText textEmail;
    private EditText textPassword;
    private EditText textPasswordRepeat;
    private EditText textHint;
    private Button btnSubmit;
    private Transformable encoder;
    private List<RegisterObserver> registerObserverList=new ArrayList<>();
    public static RegisterActivity instance;

    private void attachObservers(){
        attach(new ExampleRegisterObserver());
        if(LoginActivity.instance!=null){
            attach(LoginActivity.instance);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textEmail=findViewById(R.id.register_text_email);
        textPassword=findViewById(R.id.register_text_password);
        textPasswordRepeat=findViewById(R.id.register_text_password_repeat);
        textHint=findViewById(R.id.register_text_hint);
        btnSubmit=findViewById(R.id.register_submit);

        btnSubmit.setOnClickListener(this);
        attachObservers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_submit:
                User user=check();
                if(user!=null){
                    user.save();
                    finish();
                }
                notify(user);
                break;
        }
    }

    private User check(){
        String email=textEmail.getText().toString();
        if(!RegexUtils.isEmail(email)){
            ToastUtils.toast("输入的邮箱地址不合法");
            return null;
        }
        Iterator<User> iterator=User.findAsIterator(User.class,"email=?",email);
        User res=null;
        if(iterator.hasNext()){
            res=iterator.next();
        }else {
            res=new User();
        }
        res.setEmail(email);
        String pwd1=textPassword.getText().toString();
        String pwd2=textPasswordRepeat.getText().toString();
        if(pwd1.trim().length()==0){
            ToastUtils.toast("主密码不能为空");
            return null;
        }
        if(!pwd1.equals(pwd2)){
            ToastUtils.toast("两次输入密码不一致");
            return null;
        }
        encoder=new AES256Enocder(pwd1);
        res.setEmailEncoded(encoder.encode(res.getEmail()));
        res.setHint(textHint.getText().toString());
        return res;
    }

    @Override
    public void attach(RegisterObserver observer) {
        registerObserverList.add(observer);
    }

    @Override
    public void detach(RegisterObserver observer) {
        registerObserverList.remove(observer);
    }

    @Override
    public void notify(User user) {
        for (RegisterObserver i :
                registerObserverList) {
            i.onUserRegister(user);
        }
    }
}
