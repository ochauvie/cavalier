package com.tankbattle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tankbattle.R;
import com.tankbattle.listner.EquipeListener;
import com.tankbattle.model.Equipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olivier on 07/09/15.
 */
public class EquipeListAdapter extends BaseAdapter {

    private List<Equipe> equipeList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<EquipeListener> listeners = new ArrayList<EquipeListener>();

    public EquipeListAdapter(Context mContext, List<Equipe> equipeList) {
        this.equipeList = equipeList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public EquipeListAdapter() {
        super();
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(EquipeListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToUpdate(Equipe item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClickEquipe(item, position);
        }
    }

    @Override
    public int getCount() {
        if (equipeList!=null) {
            return equipeList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (equipeList!=null) {
            return equipeList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;

        // Reutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item a partir du  layout XML
            layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_list_equipe_item, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        // Recuperation des TextView de notre layout
        RelativeLayout layout = (RelativeLayout)layoutItem.findViewById(R.id.item_equipe);
        TextView tv_nomEquipe = (TextView)layoutItem.findViewById(R.id.nomEquipe);
        TextView tv_nbTank = (TextView)layoutItem.findViewById(R.id.nbTank);


        //(3) : Renseignement des valeurs
        Equipe currentEquipe = equipeList.get(position);

        tv_nomEquipe.setText(currentEquipe.getNom());
        tv_nbTank.setText(String.valueOf( currentEquipe.tanks()!=null?currentEquipe.tanks().size():0));


        // On memorise la position  dans le composant textview
        layout.setTag(position);
        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position
                Integer position = (Integer) v.getTag();

                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToUpdate(equipeList.get(position), position);
            }

        });

        return layoutItem;
    }
}
