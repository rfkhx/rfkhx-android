package edu.upc.mishu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;

public class LiscenceActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonOk;
    CheckBox checkBoxRead;
    WebView webLiscence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liscence);

        buttonOk=findViewById(R.id.btn_ok);
        checkBoxRead=findViewById(R.id.chk_read);
        webLiscence=findViewById(R.id.web_liscence);

        buttonOk.setEnabled(false);
        WebSettings webSettings=webLiscence.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webLiscence.loadUrl("file:///android_asset/liscense.html");

        checkBoxRead.setOnClickListener(this);
        buttonOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chk_read:
                Log.i(this.getLocalClassName(),""+checkBoxRead.isChecked());
                buttonOk.setEnabled(checkBoxRead.isChecked());
                break;
            case R.id.btn_ok:
                SharedPreferences shared=getSharedPreferences("is", MODE_PRIVATE);
                SharedPreferences.Editor editor=shared.edit();
                editor.putBoolean("isfer", false);
                editor.apply();
                finish();
                break;
        }
    }
}
