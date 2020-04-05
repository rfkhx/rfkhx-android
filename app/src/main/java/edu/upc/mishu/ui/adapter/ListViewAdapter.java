package edu.upc.mishu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import edu.upc.mishu.R;
import edu.upc.mishu.vo.PasswordItem;


public class ListViewAdapter extends BaseAdapter {
    private List<PasswordItem> data;
    private Context context;
    private LayoutInflater layoutInflater;
    public ListViewAdapter(Context context , List<PasswordItem> list){
        super();
        layoutInflater=LayoutInflater.from(context);
        data = list;
    }

    public final class Zujian {
      public ImageView imageView ;
      public TextView webtext;
      public TextView usertext;

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
        Zujian zujian = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.listview_item,null);
            zujian = new Zujian();
            zujian.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            zujian.webtext = (TextView) convertView.findViewById(R.id.web_text);
            zujian.usertext = (TextView) convertView.findViewById(R.id.username_text);
            convertView.setTag(zujian);
        }
        else   {
            zujian = (Zujian) convertView.getTag();
        }
        zujian.imageView.setImageResource(data.get(position).getImageId());
        zujian.webtext.setText(data.get(position).getWebsite());
        zujian.usertext.setText(data.get(position).getUsername());
        return convertView;
    }
}
