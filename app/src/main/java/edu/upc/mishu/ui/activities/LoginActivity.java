package edu.upc.mishu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String TAG="LoginActivity";
    private TextView tvRegister;
    public static LoginActivity instance;
    private EditText textEmail;
    private EditText textPassword;
    private Button btnSubmit;
    private List<LoginObserver> loginObserverList=new ArrayList<>();
    private List<String> emaillist = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private void attachObservers(){
        attach(new ExampleLoginObserver());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Iterator<User> userIterator= User.findAll(User.class);

        while(userIterator.hasNext()){
            User user = userIterator.next();
            emaillist.add(user.getEmail());
            Log.i(TAG, "onCreate: "+emaillist.toString());
        }


        tvRegister=findViewById(R.id.login_tv_register);
        tvRegister.setOnClickListener(this);

        textEmail=findViewById(R.id.login_email);
        textPassword=findViewById(R.id.login_password);
        btnSubmit=findViewById(R.id.login_submit);
        btnSubmit.setOnClickListener(this);

        attachObservers();

        if(!emaillist.isEmpty()){
            textEmail.setText(emaillist.get(0));
        }
        textEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showlist();
                }
            }
        });
    }

    private void showlist(){
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,emaillist));
        listPopupWindow.setAnchorView(textEmail);
        listPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        listPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textEmail.setText(emaillist.get(position));
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
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
                    Toast.makeText(this,getString(R.string.login_wrong_username_or_password),Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: "+getString(R.string.login_email_invalid));
                    break;
                }
                Iterator<User> userIterator=User.findAsIterator(User.class,"email=?",textEmail.getText().toString());
                if(!userIterator.hasNext()){
                    ToastUtils.toast(getString(R.string.login_wrong_username_or_password));
                    Log.i(TAG, "onClick: "+getString(R.string.login_wrong_username_or_password));
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
