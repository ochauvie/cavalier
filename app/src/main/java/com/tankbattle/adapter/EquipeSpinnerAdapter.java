package com.tankbattle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.tankbattle.model.Equipe;

import java.util.List;

/**
 * Created by olivier on 10/09/15.
 */
public class EquipeSpinnerAdapter extends BaseAdapter {


    private List<Equipe> dataList;
    private Context mContext;
    private LayoutInflater mInflater;

    public EquipeSpinnerAdapter(Context mContext, List<Equipe> dataList) {
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

        Equipe item = dataList.get(position);

        TextView main_text = (TextView) mySpinner .findViewById(R.id.text_main_seen);
        main_text.setText(item.getNom() + " (" + String.valueOf(item.tanks().size()) + ")");


        return mySpinner;

    }
}
