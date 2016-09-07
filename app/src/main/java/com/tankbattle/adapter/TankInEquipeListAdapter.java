package com.tankbattle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.tankbattle.listner.MyTouchListener;
import com.tankbattle.listner.TankInBatailleListener;
import com.tankbattle.model.Tank;
import com.tankbattle.model.TankVictoires;

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
    private boolean dragDrop;
    private boolean butActivated;
    private LayoutInflater mInflater;
    private List<TankInBatailleListener> listeners = new ArrayList<TankInBatailleListener>();
    private String origine;
    private String nomBataille;

    public TankInEquipeListAdapter(Context mContext, List<Tank> tankList, boolean butActivated, String origine, boolean dragDrop, String nomBataille) {
        this.tankList = tankList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.butActivated = butActivated;
        this.origine = origine;
        this.dragDrop = dragDrop;
        this.nomBataille = nomBataille;
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

    private void sendListenerToVictoire(Tank item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClickVictoire(item, position);
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

        RelativeLayout layoutTank = (RelativeLayout)layoutItem.findViewById(R.id.item_tank);
        ImageView imageNationFlag = (ImageView)layoutItem.findViewById(R.id.imageNationFlag);
        ImageView imageGenreFlag = (ImageView)layoutItem.findViewById(R.id.imageGenreFlag);
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_pv = (TextView)layoutItem.findViewById(R.id.pv);
        TextView tv_victoire = (TextView)layoutItem.findViewById(R.id.victoire);
        ImageButton butAdd = (ImageButton)layoutItem.findViewById(R.id.butAdd);
        ImageButton butRemove = (ImageButton)layoutItem.findViewById(R.id.butRemove);
        ImageButton butVictoire = (ImageButton)layoutItem.findViewById(R.id.butVictoire);

            butAdd.setActivated(butActivated);
            butRemove.setActivated(butActivated);

        // Renseignement des valeurs
        Tank currentTank = tankList.get(position);

        imageNationFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getNation().getFlag()));
        imageGenreFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getGenre().getFlag()));
        tv_nom.setText(currentTank.getNom());
        tv_pv.setText(String.valueOf(currentTank.getPv()));

        int vic = 0;
        if (nomBataille!=null) {
            for (TankVictoires victoire:currentTank.victoires()) {
                if (nomBataille.equals(victoire.getNomBataille())) {
                    vic++;
                }
            }
        }
        tv_victoire.setText(String.valueOf(vic));

        // Tank detroyed
        if (currentTank.isDestroyed()) {
            tv_nom.setTextColor(Color.RED);
            tv_pv.setTextColor(Color.RED);
            layoutTank.setBackgroundColor(Color.GRAY);

        // Tank alive
        } else {
            tv_nom.setTextColor(Color.GREEN);
            tv_pv.setTextColor(Color.GREEN);
            layoutTank.setBackgroundColor(Color.BLACK);
        }

        // On memorise la position  dans le composant textview
        TagTank tagTank = new TagTank(position, this.origine);
        layoutTank.setTag(tagTank);
        butAdd.setTag(position);
        butRemove.setTag(position);
        butVictoire.setTag(position);

        butAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToAddPv(tankList.get(position), position);
            }

        });

        butRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToDeletePv(tankList.get(position), position);
            }

        });

        butVictoire.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToVictoire(tankList.get(position), position);
            }

        });


        // Drag
        if (dragDrop) {
            layoutTank.setOnTouchListener(new MyTouchListener());
        }

        return layoutItem;
    }
}
