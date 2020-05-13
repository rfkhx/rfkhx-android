package edu.upc.mishu.ui.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private static final String TAG = "ExportActivity";

    private EditText input;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        init();

        input = findViewById(R.id.export_input);

        input.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == KeyEvent.KEYCODE_ENDCALL){
                if(input.getText().toString().equals(App.password.trim())){
                    ImportAndExport importAndExport=new ImportAndExport();
                    importAndExport.Export("mishu",ExportActivity.this);
                    InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    Toast.makeText(ExportActivity.this,"导出成功"+importAndExport.getDir(),Toast.LENGTH_LONG).show();
                    imm.hideSoftInputFromWindow(new View(ExportActivity.this).getWindowToken(),0);
                    ExportActivity.this.finish();
                    return true;
                }
                else{
                    input.setText("");
                    Toast.makeText(ExportActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
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
