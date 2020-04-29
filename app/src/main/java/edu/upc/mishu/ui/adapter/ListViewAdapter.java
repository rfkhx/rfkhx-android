package edu.upc.mishu.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.daimajia.swipe.SwipeLayout;

import java.util.List;

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
    private Context context;
    private LayoutInflater layoutInflater;
    private List<PasswordRecord> list;
    public ListViewAdapter(Context context , List<PasswordItem> list){
        super();
        layoutInflater=LayoutInflater.from(context);
        this.context = context;
        data = list;
    }

    public final class Zujian {
      ImageView imageView ;
      TextView webtext;
      TextView usertext;
      Button delete;
      Button alter;
      SwipeLayout swipeLayout;

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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.listview_item,null);
            zujian = new Zujian();
            zujian.imageView = convertView.findViewById(R.id.image_view);
            zujian.webtext = convertView.findViewById(R.id.web_text);
            zujian.usertext = convertView.findViewById(R.id.username_text);
            zujian.swipeLayout = convertView.findViewById(R.id.swipe_layout);
            zujian.delete = convertView.findViewById(R.id.delete);
            zujian.alter = convertView.findViewById(R.id.alter);
            convertView.setTag(zujian);
        }
        else   {
            zujian = (Zujian) convertView.getTag();
        }
        zujian.imageView.setImageResource(data.get(position).getImageId());
        zujian.webtext.setText(data.get(position).getWebsite());
        zujian.usertext.setText(data.get(position).getUsername());

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
                        for(PasswordRecord item:list){
                            item.decode(App.encoder,1);
                            Log.i(TAG, "onClick: "+data.get(position).getUsername());
                            Log.i(TAG, "onClick: item:"+item.toString());
                            if(item.getUsername().equals(data.get(position).getUsername())){
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
                        Log.i(TAG, "onClick: id"+position);
                        zujian.swipeLayout.close();
                        //adapter 活动跳转
                        ((Activity)context).startActivityForResult(intent_alter,2);
                    }
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
}
