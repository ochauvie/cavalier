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
import com.cavalier.listner.CoursListener;
import com.cavalier.model.Cours;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CoursListAdapter extends BaseAdapter {

    private List<Cours> coursList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CoursListener> listeners = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

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

    private void sendListenerToObservation(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onShowObservation(item, position);
        }
    }


    private void sendListenerToDelete(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onDelete(item, position);
        }
    }

    private void sendListenerToCavalier(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onSelectCavalier(item, position);
        }
    }

    private void sendListenerToMonture(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onSelectMonture(item, position);
        }
    }

    private void sendListenerToMoniteur(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onSelectMoniteur(item, position);
        }
    }

    private void sendListenerToLieu(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onSelectLieu(item, position);
        }
    }

    private void sendListenerToDate(Cours item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onSelectDate(item, position);
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
        ImageButton img_observation = (ImageButton) layoutItem.findViewById(R.id.obsCours);

        // Renseignement des valeurs
        Cours current = coursList.get(position);

        tv_date.setText(sdf.format(current.getDate()));
        tv_cavalier.setText(current.getCavalier().getPrenom());
        tv_monture.setText(current.getMonture().getNom());

        // Landscape only
            if (tv_moniteur != null) {
                tv_moniteur.setText(current.getMoniteur().getPrenom());
                tv_moniteur.setTag(position);
                tv_moniteur.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Integer position = (Integer) v.getTag();
                        sendListenerToMoniteur(coursList.get(position), position);
                    }
                });

            }
            if (tv_lieu != null) {
                tv_lieu.setText(current.getTypeLieu().getLabel());
                tv_lieu.setTag(position);
                tv_lieu.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Integer position = (Integer) v.getTag();
                        sendListenerToLieu(coursList.get(position), position);
                    }
                });
            }
            if (tv_duree != null) {
                tv_duree.setText(String.valueOf(current.getDuree()));
            }

        img_observation.setTag(position);
        img_observation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToObservation(coursList.get(position), position);
            }
        });

        img_delete.setTag(position);
        img_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToDelete(coursList.get(position), position);
            }
        });

        tv_cavalier.setTag(position);
        tv_cavalier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToCavalier(coursList.get(position), position);
            }
        });

        tv_monture.setTag(position);
        tv_monture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToMonture(coursList.get(position), position);
            }
        });

        tv_date.setTag(position);
        tv_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToDate(coursList.get(position), position);
            }
        });



        return layoutItem;
    }
}