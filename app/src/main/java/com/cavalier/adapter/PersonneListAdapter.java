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
import com.cavalier.R;
import com.cavalier.tools.PictureUtils;

import java.util.ArrayList;
import java.util.List;


public class PersonneListAdapter extends BaseAdapter {

    private List<Personne> personneList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<PersonneListener> listeners = new ArrayList<>();

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
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_prenom = (TextView)layoutItem.findViewById(R.id.prenom);
        ImageView imageView = (ImageView)layoutItem.findViewById(R.id.pic);
//        TextView tv_sexe = (TextView)layoutItem.findViewById(R.id.sexe);
        TextView galop =  (TextView)layoutItem.findViewById(R.id.galop);


        // Renseignement des valeurs
        Personne current = personneList.get(position);

        tv_nom.setText(current.getNom());
        tv_prenom.setText(current.getPrenom());
//        tv_sexe.setText(current.getSexe().getLabel());
        if (current.getImg() != null) {
            imageView.setImageBitmap(PictureUtils.getImage(current.getImg()));
        }
        if (galop != null && current.getGalop() != null) {
            galop.setText(current.getGalop().getLabel());
        }

        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        layoutItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();

                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToUpdate(personneList.get(position), position);
            }

        });

        return layoutItem;
    }
}
