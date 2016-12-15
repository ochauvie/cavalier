package com.cavalier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cavalier.R;
import com.cavalier.listner.MontureListener;
import com.cavalier.model.EvenementMonture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class EvenementListAdapter extends BaseAdapter {

    private List<EvenementMonture> evenementList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MontureListener> listeners = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

    public EvenementListAdapter(Context mContext, List<EvenementMonture> evenementList) {
        this.evenementList = evenementList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public EvenementListAdapter() {
        super();
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(MontureListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToDelete(EvenementMonture item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onDeleteEvenement(item, position);
        }
    }

    @Override
    public int getCount() {
        if (evenementList!=null) {
            return evenementList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (evenementList!=null) {
            return evenementList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_cavalier_list_evenement_item, parent, false);
        TextView tv_type = (TextView)layoutItem.findViewById(R.id.type);
        TextView tv_date = (TextView)layoutItem.findViewById(R.id.date);
        ImageButton img_delete = (ImageButton) layoutItem.findViewById(R.id.delete);
        ImageView imageView = (ImageView)layoutItem.findViewById(R.id.pic);

        // Renseignement des valeurs
        EvenementMonture current = evenementList.get(position);

        imageView.setImageResource(current.getType().getFlag());
        tv_type.setText(current.getType().getLabel());
        if (!"".equals(current.getObservation()) && current.getObservation() != null) {
            tv_type.setText(tv_type.getText() + "\n" + current.getObservation());
            tv_type.setLines(2);
        }
        tv_date.setText(sdf.format(current.getDate()));

        img_delete.setTag(position);
        img_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToDelete(evenementList.get(position), position);
            }
        });

        return layoutItem;
    }
}
