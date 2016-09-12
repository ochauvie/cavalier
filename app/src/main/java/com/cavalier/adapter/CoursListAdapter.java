package com.cavalier.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.cavalier.listner.CoursListener;
import com.cavalier.listner.MontureListener;
import com.cavalier.model.Cours;
import com.cavalier.model.Monture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CoursListAdapter extends BaseAdapter {

    private List<Cours> coursList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CoursListener> listeners = new ArrayList<CoursListener>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public CoursListAdapter(Context mContext, List<Cours> coursList) {
        this.coursList = coursList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public CoursListAdapter() {
        super();
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(CoursListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToUpdate(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClick(item, position);
        }
    }

    private void sendListenerToDelete(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onDelete(item, position);
        }
    }

    @Override
    public int getCount() {
        if (coursList!=null) {
            return coursList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (coursList!=null) {
            return coursList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_cavalier_list_cours_item, parent, false);
        TextView tv_cavalier = (TextView)layoutItem.findViewById(R.id.cavalier);
        TextView tv_monture = (TextView)layoutItem.findViewById(R.id.monture);
        TextView tv_moniteur = (TextView)layoutItem.findViewById(R.id.moniteur);
        TextView tv_lieu = (TextView)layoutItem.findViewById(R.id.lieu);
        TextView tv_duree = (TextView)layoutItem.findViewById(R.id.duree);
        TextView tv_date = (TextView)layoutItem.findViewById(R.id.date);
        ImageButton img_delete = (ImageButton) layoutItem.findViewById(R.id.deleteCours);


        // Renseignement des valeurs
        Cours current = coursList.get(position);

        tv_date.setText(sdf.format(current.getDate()));
        tv_cavalier.setText(current.getCavalier().getPrenom());
        tv_monture.setText(current.getMonture().getNom());
        // Landscape only
        if (tv_moniteur != null) {
            tv_moniteur.setText(current.getMoniteur().getPrenom());
        }
        if (tv_lieu != null) {
            tv_lieu.setText(current.getTypeLieu().getLabel());
        }
        if (tv_duree != null) {
            tv_duree.setText(String.valueOf(current.getDuree()));
        }


        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        layoutItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();

                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToUpdate(coursList.get(position), position);
            }

        });

        img_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToDelete(coursList.get(position), position);
            }
        });

        return layoutItem;
    }
}
