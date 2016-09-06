package com.cavalier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.listner.PersonneListener;
import com.cavalier.model.Personne;
import com.tankbattle.R;
import com.tankbattle.listner.TankListener;
import com.tankbattle.model.Tank;

import java.util.ArrayList;
import java.util.List;


public class PersonneListAdapter extends BaseAdapter {

    private List<Personne> personneList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<PersonneListener> listeners = new ArrayList<PersonneListener>();

    public PersonneListAdapter(Context mContext, List<Personne> personneList) {
        this.personneList = personneList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public PersonneListAdapter() {
        super();
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(PersonneListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToUpdate(Personne item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClick(item, position);
        }
    }

    @Override
    public int getCount() {
        if (personneList!=null) {
            return personneList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (personneList!=null) {
            return personneList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_cavalier_list_personne_item, parent, false);
        RelativeLayout layoutTank = (RelativeLayout)layoutItem.findViewById(R.id.item_tank);
        ImageView imageNationFlag = (ImageView)layoutItem.findViewById(R.id.imageNationFlag);
        ImageView imageGenreFlag = (ImageView)layoutItem.findViewById(R.id.imageGenreFlag);
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_pv = (TextView)layoutItem.findViewById(R.id.pv);
        ImageView imageTank = (ImageView)layoutItem.findViewById(R.id.imageTank);


        // Renseignement des valeurs
        Personne current = personneList.get(position);

        imageNationFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getNation().getFlag()));
        imageGenreFlag.setImageDrawable(mContext.getResources().getDrawable(currentTank.getGenre().getFlag()));
        tv_nom.setText(currentTank.getNom());
        tv_pv.setText(String.valueOf(currentTank.getPv()) + " PV et "+ currentTank.victoires().size() + " Vic.");

        // Image du tank
        if (currentTank.getImage() != null) {
            int idImg = mContext.getResources().getIdentifier(currentTank.getImage(), "drawable", mContext.getPackageName());
            if (idImg!=0) {
                imageTank.setImageResource(idImg);
            }
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
