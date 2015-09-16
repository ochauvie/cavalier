package com.tankbattle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tankbattle.R;
import com.tankbattle.model.TankVictoires;

import java.util.List;

/**
 * Created by Olivier on 07/09/15.
 */
public class VictoireListAdapter extends BaseAdapter {

    private List<TankVictoires> victoires;
    private Context mContext;
    private LayoutInflater mInflater;
    private long ownerId;


    public VictoireListAdapter(Context mContext, List<TankVictoires> victoires, long ownerId) {
        this.victoires = victoires;
        this.mContext = mContext;
        this.ownerId = ownerId;
        mInflater = LayoutInflater.from(mContext);
    }

    public VictoireListAdapter() {
        super();
    }



    @Override
    public int getCount() {
        if (victoires!=null) {
            return victoires.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (victoires!=null) {
            return victoires.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_list_victoire_item, parent, false);

        // Recuperation des TextView de notre layout
        TextView tv_nomBataille = (TextView)layoutItem.findViewById(R.id.nomBataille);
        TextView tv_tank = (TextView)layoutItem.findViewById(R.id.tank);

        //(3) : Renseignement des valeurs
        TankVictoires victoire = victoires.get(position);

        tv_nomBataille.setText(victoire.getNomBataille());

        if (ownerId == victoire.getTankVictorieux().getId()) {
            tv_tank.setText(victoire.getTankDetruit().getNom());
            tv_tank.setTextColor(Color.GREEN);
        } else {
            tv_tank.setText(victoire.getTankVictorieux().getNom());
            tv_tank.setTextColor(Color.RED);
        }

        return layoutItem;
    }
}
