package edu.upc.mishu.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;

import com.daimajia.swipe.SwipeLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.ui.activities.MainActivity;
import edu.upc.mishu.ui.activities.ModifyPssswordActivity;
import edu.upc.mishu.ui.fragment.PasswordFragment;
import edu.upc.mishu.vo.PasswordItem;


public class ListViewAdapter extends BaseAdapter {
    private static final String TAG = "ListViewAdapter";
    private List<PasswordItem> data;
    public Context context;
    private LayoutInflater layoutInflater;
    private List<PasswordRecord> list;
    private Fragment fm;

    public ListViewAdapter() {
    }

    ;



    //Fragment 和 Activity 有点不同
    public ListViewAdapter(Context context, List<PasswordItem> list, Fragment fm) {
        super();
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        data = list;
        this.fm = fm;
    }

    public final class Zujian {
        ImageView imageView;
        TextView webtext;
        TextView usertext;
        Button delete;
        Button alter;
        SwipeLayout swipeLayout;
        Button open;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian;

        if (convertView == null) {
            zujian = new Zujian();
            convertView = layoutInflater.inflate(R.layout.listview_item, null);
            zujian.imageView = convertView.findViewById(R.id.image_view);
            zujian.webtext = convertView.findViewById(R.id.web_text);
            zujian.usertext = convertView.findViewById(R.id.username_text);
            zujian.swipeLayout = convertView.findViewById(R.id.swipe_layout);
            zujian.delete = convertView.findViewById(R.id.delete);
            zujian.alter = convertView.findViewById(R.id.alter);
            zujian.open = convertView.findViewById(R.id.open);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        zujian.imageView.setImageResource(data.get(position).getImageId());

        String strUrl = data.get(position).getUrl();
        URL url = null;
        try {
            url = new URL(new URL(strUrl), "/favicon.ico");
            Glide.with(context)
                    .load(url.toString())
                    .placeholder(R.drawable.create_navigation)//图片加载出来前，显示的图片
                    .error(R.drawable.create_navigation)//图片加载失败后，显示的图片
                    .into(zujian.imageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        zujian.webtext.setText(data.get(position).getWebsite());
        zujian.usertext.setText(data.get(position).getUsername());


        zujian.swipeLayout.setClickToClose(true);
        zujian.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        zujian.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

                zujian.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list = PasswordRecord.listAll(PasswordRecord.class);
                        for (PasswordRecord item : list) {
                            item.decode(App.encoder, 1);
//                            Log.i(TAG, "onClick: " + data.get(position).getUsername());
//                            Log.i(TAG, "onClick: item:" + item.toString());
                            if (item.getUsername().equals(data.get(position).getUsername())) {
                                item.delete();
                                break;
                            }
                        }
                        data.remove(position);
                        zujian.swipeLayout.close();
                        notifyDataSetChanged();
                    }
                });

                zujian.alter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_alter = new Intent(context, ModifyPssswordActivity.class);
                        intent_alter.putExtra("name", data.get(position).getWebsite());
                        Log.i(TAG, "onClick: name" + data.get(position).getWebsite());
                        intent_alter.putExtra("id", position);
                        Log.i(TAG, "onClick: id" + position);
                        zujian.swipeLayout.close();
                        //adapter 活动跳转
                        fm.startActivityForResult(intent_alter, 2);
                        Log.i(TAG, "onClick: start by adapter");
                    }
                });

                zujian.open.setOnClickListener(v -> {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                    context.startActivity(intent);
                });
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        return convertView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult" + requestCode + resultCode + data.toString());
    }
}
