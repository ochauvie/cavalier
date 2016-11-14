package com.cavalier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.cavalier.model.Monture;
import com.cavalier.tools.PictureUtils;

import java.util.List;


public class MontureSpinnerAdapter extends BaseAdapter {


    private List<Monture> dataList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MontureSpinnerAdapter(Context mContext, List<Monture> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        RelativeLayout mySpinner  = (RelativeLayout) mInflater.inflate(R.layout.custom_spinner, parent, false);

        Monture item = dataList.get(position);

        TextView main_text = (TextView) mySpinner.findViewById(R.id.text_main_seen);
        if (item.getRobe() != null && !"".equals(item.getRobe())) {
            main_text.setText(item.getNom() + " (" + item.getRobe() + ")");
        } else {
            main_text.setText(item.getNom());
        }

        ImageView left_pic = (ImageView) mySpinner.findViewById(R.id.left_pic);
        left_pic.setImageBitmap(PictureUtils.getImage(item.getImg()));

        return mySpinner;

    }
}
