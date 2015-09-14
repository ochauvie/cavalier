package com.tankbattle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tankbattle.R;
import com.tankbattle.listner.MyTouchListener;
import com.tankbattle.listner.TankInBatailleListener;
import com.tankbattle.model.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olivier on 07/09/15.
 */
public class TankInEquipeListAdapter extends BaseAdapter {

    public static final String ORIGINE_HANGAR = "HANGAR";
    public static final String ORIGINE_EQUIPE = "EQUIPE";

    private List<Tank> tankList;
    private Context mContext;
    private boolean butActivated;
    private LayoutInflater mInflater;
    private List<TankInBatailleListener> listeners = new ArrayList<TankInBatailleListener>();
    private String origine;

    public TankInEquipeListAdapter(Context mContext, List<Tank> tankList, boolean butActivated, String origine) {
        this.tankList = tankList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.butActivated = butActivated;
        this.origine = origine;
    }


    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(TankInBatailleListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToAddPv(Tank item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClickAddPv(item, position);
        }
    }

    private void sendListenerToDeletePv(Tank item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClickDeletePv(item, position);
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

    public List<Tank> getTankList() {
        return tankList;
    }

    public String getItemOrigine() {
        return this.origine;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_battle_tank_item, parent, false);

        // Recuperation des TextView de notre layout
        RelativeLayout layoutTank = (RelativeLayout)layoutItem.findViewById(R.id.item_tank);
        ImageView imageNationFlag = (ImageView)layoutItem.findViewById(R.id.imageNationFlag);
        ImageView imageGenreFlag = (ImageView)layoutItem.findViewById(R.id.imageGenreFlag);
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_pv = (TextView)layoutItem.findViewById(R.id.pv);
        ImageButton butAdd = (ImageButton)layoutItem.findViewById(R.id.butAdd);
        ImageButton butRemove = (ImageButton)layoutItem.findViewById(R.id.butRemove);

            butAdd.setActivated(butActivated);
            butRemove.setActivated(butActivated);

        //(3) : Renseignement des valeurs
        Tank currentTank = tankList.get(position);

        imageNationFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getNation().getFlag()));
        imageGenreFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getGenre().getFlag()));
        tv_nom.setText(currentTank.getNom());
        tv_pv.setText(String.valueOf(currentTank.getPv()));


        // TODO: si tank destroy, nouveau fond ?

        // On memorise la position  dans le composant textview
        TagTank tagTank = new TagTank(position, this.origine);
        layoutTank.setTag(tagTank);

        butAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();
                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToAddPv(tankList.get(position), position);
            }

        });

        butRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();
                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToDeletePv(tankList.get(position), position);
            }

        });


        // Drag
        layoutTank.setOnTouchListener(new MyTouchListener());


        return layoutItem;
    }
}
