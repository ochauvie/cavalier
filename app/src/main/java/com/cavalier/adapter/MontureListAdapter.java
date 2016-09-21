package com.cavalier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.cavalier.listner.MontureListener;
import com.cavalier.model.Monture;
import com.cavalier.tools.PictureUtils;

import java.util.ArrayList;
import java.util.List;


public class MontureListAdapter extends BaseAdapter {

    private List<Monture> montureList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MontureListener> listeners = new ArrayList<MontureListener>();

    public MontureListAdapter(Context mContext, List<Monture> montureList) {
        this.montureList = montureList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public MontureListAdapter() {
        super();
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(MontureListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToUpdate(Monture item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClick(item, position);
        }
    }

    @Override
    public int getCount() {
        if (montureList!=null) {
            return montureList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (montureList!=null) {
            return montureList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_cavalier_list_monture_item, parent, false);
        TextView tv_nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_genre = (TextView)layoutItem.findViewById(R.id.genre);
        TextView tv_robe = (TextView)layoutItem.findViewById(R.id.robe);
        TextView tv_race = (TextView)layoutItem.findViewById(R.id.race);
        ImageView imageView = (ImageView)layoutItem.findViewById(R.id.pic);

        // Renseignement des valeurs
        Monture current = montureList.get(position);

        tv_nom.setText(current.getNom());
        if (tv_genre != null) {
            tv_genre.setText(current.getGenre().getLabel());
        }
        tv_robe.setText(current.getRobe());
        if (tv_race != null) {
            tv_race.setText(current.getRace());
        }
        if (imageView != null && current.getImg() != null) {
            imageView.setImageBitmap(PictureUtils.getImage(current.getImg()));
        }

        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        layoutItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();

                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToUpdate(montureList.get(position), position);
            }

        });

        return layoutItem;
    }
}
