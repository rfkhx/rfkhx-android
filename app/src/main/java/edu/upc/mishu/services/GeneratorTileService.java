package edu.upc.mishu.services;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import com.xuexiang.xutil.tip.ToastUtils;

import edu.upc.mishu.App;
import edu.upc.mishu.utils.SimplePasswordGenerator;

@TargetApi(Build.VERSION_CODES.N)
public class GeneratorTileService extends TileService {
    private static String TAG="Mishu.GeneratorTileService";

    public GeneratorTileService(){
        super();
    }

    @Override
    public void onClick() {
        ClipboardManager clipboardManager= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("mishu", SimplePasswordGenerator.GenerateAPassword()));
        ToastUtils.toast("密码已经复制！");
    }
}
