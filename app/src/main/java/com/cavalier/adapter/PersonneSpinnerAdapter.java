package com.cavalier.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.cavalier.model.Personne;
import com.cavalier.tools.PictureUtils;

import java.util.HashMap;
import java.util.List;


public class PersonneSpinnerAdapter extends BaseAdapter {

    private List<Personne> dataList;
    private LayoutInflater mInflater;
    HashMap<Long, Bitmap> bitmaps = new HashMap<>();

    public PersonneSpinnerAdapter(Context mContext, List<Personne> dataList) {
        this.dataList = dataList;
        mInflater = LayoutInflater.from(mContext);
        loadBitmaps();
    }

    @Override
    public int getCount() {
        if (dataList!=null) {
            return dataList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (dataList!=null) {
            return dataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    private void loadBitmaps() {
        for (Personne item:dataList) {
            bitmaps.put(item.getId(), PictureUtils.getImage(item.getImg()));
        }
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        RelativeLayout mySpinner  = (RelativeLayout) mInflater.inflate(R.layout.custom_spinner, parent, false);
        Personne item = dataList.get(position);
        TextView main_text = (TextView) mySpinner .findViewById(R.id.text_main_seen);
        main_text.setText(item.getPrenom() + " " + item.getNom());

        ImageView left_pic = (ImageView) mySpinner.findViewById(R.id.left_pic);
        left_pic.setImageBitmap(bitmaps.get(item.getId()));

        return mySpinner;
    }
}
