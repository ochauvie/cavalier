package com.tankbattle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tankbattle.R;
import com.tankbattle.model.Tank;

import java.util.List;

/**
 * Created by Olivier on 07/09/15.
 */
public class TankListAdapter extends BaseAdapter {

    private List<Tank> tankList;
    private Context mContext;
    private LayoutInflater mInflater;

    public TankListAdapter(Context mContext, List<Tank> tankList) {
        this.tankList = tankList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public TankListAdapter() {
        super();
    }

    @Override
    public int getCount() {
        if (tankList!=null) {
            return tankList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (tankList!=null) {
            return tankList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;

        // Reutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item a partir du  layout XML
            layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_list_tank_item, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        // Recuperation des TextView de notre layout
        ImageView imageNationFlag = (ImageView)layoutItem.findViewById(R.id.imageNationFlag);
        TextView tv_nation = (TextView)layoutItem.findViewById(R.id.nation);
        ImageView imageGenreFlag = (ImageView)layoutItem.findViewById(R.id.imageGenreFlag);
        TextView tv_genre = (TextView)layoutItem.findViewById(R.id.genre);
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_pv = (TextView)layoutItem.findViewById(R.id.pv);
        ImageView imageTank = (ImageView)layoutItem.findViewById(R.id.imageTank);


        //(3) : Renseignement des valeurs
        Tank currentTank = tankList.get(position);

        imageNationFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getNation().getFlag()));
        tv_nation.setText(currentTank.getNation().getLabel());
        imageGenreFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getGenre().getFlag() ));
        tv_genre.setText(currentTank.getGenre().getLabel());
        tv_nom.setText(currentTank.getNom());
        tv_pv.setText(String.valueOf(currentTank.getPv()));

        // Image du tank
        if (currentTank.getImage() != null) {
            Bitmap bitmap  = BitmapFactory.decodeFile(currentTank.getImage());
            imageTank.setImageBitmap(bitmap);
        }

        // On memorise la position  dans le composant textview
        tv_nation.setTag(position);

        return layoutItem;
    }
}
