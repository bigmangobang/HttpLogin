package com.example.jf.myapp.Adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.example.jf.myapp.Model.Zone;
import com.example.jf.myapp.R;

import java.text.SimpleDateFormat;
import java.util.List;


public class ZoneAdapter extends ArrayAdapter {
    private Context context;
    private int resourceId;
    @SuppressLint("ResourceType")
    public ZoneAdapter(@NonNull Context context,
                       int resource,
                       @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Zone zoneData = (Zone) getItem(position);
        ZoneLayout zoneLayout = new ZoneLayout();
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            zoneLayout.nameView = view.findViewById(R.id.user_zone_name);
            zoneLayout.userimg = view.findViewById(R.id.user_zone_image);
            zoneLayout.content = view.findViewById(R.id.user_zone_word);
            view.setTag(zoneLayout);
        } else {
            view = convertView;
            zoneLayout = (ZoneLayout) view.getTag();
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        zoneLayout.nameView.setText(zoneData.getUserName());
        zoneLayout.content.setText(zoneData.getContent());
        return view;
    }

    class ZoneLayout {
        TextView nameView;
        TextView userimg;
        TextView content;
        ImageView image;
        TextView showtime;
    }
}
