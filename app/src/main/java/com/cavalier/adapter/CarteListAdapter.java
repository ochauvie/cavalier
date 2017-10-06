package com.cavalier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.cavalier.listner.CarteListener;
import com.cavalier.listner.PersonneListener;
import com.cavalier.model.Carte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CarteListAdapter extends BaseAdapter {

    private List<Carte> carteList;
    private LayoutInflater mInflater;
    private List<CarteListener> listeners = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

    public CarteListAdapter(Context mContext, List<Carte> carteList) {
        this.carteList = carteList;
        mInflater = LayoutInflater.from(mContext);
    }

    public CarteListAdapter() {
        super();
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(CarteListener aListener) {
        listeners.add(aListener);
    }


    private void sendListenerToUpdate(Carte item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onUpdateCarte(item, position);
        }
    }

    private void sendListenerToDelete(Carte item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onDeleteCarte(item, position);
        }
    }

    @Override
    public int getCount() {
        if (carteList!=null) {
            return carteList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (carteList!=null) {
            return carteList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_cavalier_list_carte_item, parent, false);
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_dateOuverture = (TextView)layoutItem.findViewById(R.id.dateOuverture);
        TextView tv_points = (TextView)layoutItem.findViewById(R.id.points);
        ImageButton img_update = (ImageButton) layoutItem.findViewById(R.id.update);
        ImageButton img_delete = (ImageButton) layoutItem.findViewById(R.id.delete);



        // Renseignement des valeurs
        Carte current = carteList.get(position);

        tv_nom.setText(current.getNom());
        tv_dateOuverture.setText(sdf.format(current.getDateOuverture()));
        tv_points.setText(current.getPointRestant() + "/" + current.getCapacite());

        img_update.setTag(position);
        img_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToUpdate(carteList.get(position), position);
            }
        });

        img_delete.setTag(position);
        img_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToDelete(carteList.get(position), position);
            }
        });

        return layoutItem;
    }
}
