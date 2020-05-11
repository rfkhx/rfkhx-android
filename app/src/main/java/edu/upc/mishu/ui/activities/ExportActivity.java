package edu.upc.mishu.ui.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.utils.ImportAndExport;

public class ExportActivity extends AppCompatActivity {

    private EditText input;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_activity);

        init();

        input = findViewById(R.id.export_input);


        input.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER&& event.getAction() == KeyEvent.ACTION_DOWN){

                Log.i("test", "onCreate: 输入的密码"+input.getText());
                Log.i("test", "onCreate: 系统的密码"+App.password);
                Log.i("test", "onCreate: 匹配结果"+input.getText().toString().equals(App.password));
                Log.i("test", "onCreate: 不加 toString 匹配结果"+input.getText().equals(App.password));
                if(input.getText().toString().equals(App.password.trim())){
                    ImportAndExport importAndExport = new ImportAndExport();
                    importAndExport.Export("test", ExportActivity.this);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    Toast.makeText(this,"导出成功"+importAndExport.getDir(),Toast.LENGTH_LONG).show();
                    imm.hideSoftInputFromWindow(new View(this).getWindowToken(), 0);
                    this.finish();
                    return true;
                }
                else {
                    Toast.makeText(this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });

    }

    private void init(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        WindowManager.LayoutParams layoutParams =getWindow().getAttributes();
        layoutParams.height = (int) (height*0.2);
        layoutParams.width = (int) (width*0.8);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundResource(R.drawable.dialog_background);

    }
}
