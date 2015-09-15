package com.tankbattle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tankbattle.R;
import com.tankbattle.listner.TankListener;
import com.tankbattle.model.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Olivier on 07/09/15.
 */
public class TankListAdapter extends BaseAdapter {

    private List<Tank> tankList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TankListener> listeners = new ArrayList<TankListener>();

    public TankListAdapter(Context mContext, List<Tank> tankList) {
        this.tankList = tankList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public TankListAdapter() {
        super();
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(TankListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToUpdate(Tank item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClickTank(item, position);
        }
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
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_list_tank_item, parent, false);

        // Recuperation des TextView de notre layout
        RelativeLayout layoutTank = (RelativeLayout)layoutItem.findViewById(R.id.item_tank);
        ImageView imageNationFlag = (ImageView)layoutItem.findViewById(R.id.imageNationFlag);
        //TextView tv_nation = (TextView)layoutItem.findViewById(R.id.nation);
        ImageView imageGenreFlag = (ImageView)layoutItem.findViewById(R.id.imageGenreFlag);
        //TextView tv_genre = (TextView)layoutItem.findViewById(R.id.genre);
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_pv = (TextView)layoutItem.findViewById(R.id.pv);
        ImageView imageTank = (ImageView)layoutItem.findViewById(R.id.imageTank);


        //(3) : Renseignement des valeurs
        Tank currentTank = tankList.get(position);

        imageNationFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getNation().getFlag()));
        //tv_nation.setText(currentTank.getNation().getLabel());
        imageGenreFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getGenre().getFlag()));
        //tv_genre.setText(currentTank.getGenre().getLabel());
        tv_nom.setText(currentTank.getNom());
        tv_pv.setText(String.valueOf(currentTank.getPv()));

        // Image du tank
        if (currentTank.getImage() != null) {
            int idImg = mContext.getResources().getIdentifier(currentTank.getImage(), "drawable", mContext.getPackageName());
            imageTank.setImageResource(idImg);
        }

        // On memorise la position  dans le composant textview
        layoutTank.setTag(position);
        layoutTank.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();

                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToUpdate(tankList.get(position), position);
            }

        });

        return layoutItem;
    }
}
