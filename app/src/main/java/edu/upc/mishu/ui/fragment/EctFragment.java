package edu.upc.mishu.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xuexiang.xutil.tip.ToastUtils;

import edu.upc.mishu.R;
import edu.upc.mishu.interfaces.PasswordGenerator;
import edu.upc.mishu.model.SimplePasswordGenerator;
import edu.upc.mishu.utils.StringSettingUtil;

public class EctFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static EctFragment instance  = null;
    private PasswordGenerator passwordGenerator=null;
    private int length=5;
    private boolean useUppercase=true;
    private boolean useLowercase =true;
    private boolean useNumber=true;
    private boolean useSymbol=true;

    private TextView tvGeneratedPassword=null;
    private Button btnGenerateAgain=null;
    private Button btnGeneratorCopy=null;
    private TextView tvLength=null;
    private SeekBar seekBarLength=null;
    private Switch swUppercase=null;
    private Switch swLowerCase=null;
    private Switch swNumber=null;
    private Switch swSymbol=null;

    public static EctFragment newInstance(){
        if(instance == null){
            instance = new EctFragment();
        }
        return instance;
    }

    private void generatePassword(){
        tvGeneratedPassword.setText(passwordGenerator.generateAPassword());
    }

    private void readStatus(){
        // 读取用户配置
        seekBarLength.setProgress(Integer.parseInt(StringSettingUtil.getString("generator_length","0")));
        swUppercase.setChecked(Boolean.parseBoolean(StringSettingUtil.getString("generator_upper","true")));
        swLowerCase.setChecked(Boolean.parseBoolean(StringSettingUtil.getString("generator_lower","true")));
        swNumber.setChecked(Boolean.parseBoolean(StringSettingUtil.getString("generator_number","true")));
        swSymbol.setChecked(Boolean.parseBoolean(StringSettingUtil.getString("generator_symbol","true")));
    }

    private void changeGenerator(){
        length=seekBarLength.getProgress();
        useUppercase=swUppercase.isChecked();
        useLowercase =swLowerCase.isChecked();
        useNumber=swNumber.isChecked();
        useSymbol=swSymbol.isChecked();
        if(!(useUppercase|| useLowercase ||useNumber||useSymbol)){
            ToastUtils.toast("至少需要选择一种类型的字符！");
            swUppercase.setChecked(true);
            useUppercase=true;
        }

        // 保存用户的选择
        StringSettingUtil.writeSetting("generator_length",Integer.toString(length));
        StringSettingUtil.writeSetting("generator_upper",Boolean.toString(useUppercase));
        StringSettingUtil.writeSetting("generator_lower",Boolean.toString(useLowercase));
        StringSettingUtil.writeSetting("generator_number",Boolean.toString(useNumber));
        StringSettingUtil.writeSetting("generator_symbol",Boolean.toString(useSymbol));

        passwordGenerator= SimplePasswordGenerator.builder().length(length).lowercaseLetters(useLowercase).uppercaseLetters(useUppercase).numbers(useNumber).symbols(useSymbol).build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etc,container,false);

        tvGeneratedPassword=view.findViewById(R.id.generator_generated_password);

        btnGenerateAgain=view.findViewById(R.id.generator_generate_again);
        btnGenerateAgain.setOnClickListener(this);

        btnGeneratorCopy=view.findViewById(R.id.generator_copy);
        btnGeneratorCopy.setOnClickListener(this);

        tvLength=view.findViewById(R.id.generator_password_length);

        seekBarLength=view.findViewById(R.id.generator_length_slider);
        seekBarLength.setOnSeekBarChangeListener(this);

        swUppercase=view.findViewById(R.id.generator_uppercase_switch);
        swLowerCase=view.findViewById(R.id.generator_lowercase_switch);
        swNumber=view.findViewById(R.id.generator_number_switch);
        swSymbol=view.findViewById(R.id.generator_symbol_switch);
        swUppercase.setOnClickListener(this);
        swLowerCase.setOnClickListener(this);
        swNumber.setOnClickListener(this);
        swSymbol.setOnClickListener(this);

        readStatus();

        changeGenerator();
        generatePassword();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.generator_generate_again:
                generatePassword();
                break;
            case R.id.generator_copy:
                ClipboardManager clipboardManager= (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("mishu",tvGeneratedPassword.getText()));
                ToastUtils.toast("密码已经复制！");
                break;
            case R.id.generator_uppercase_switch: case R.id.generator_lowercase_switch: case R.id.generator_number_switch: case R.id.generator_symbol_switch:
                changeGenerator();
                generatePassword();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvLength.setText(Integer.toString(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        changeGenerator();
        generatePassword();
    }
}
