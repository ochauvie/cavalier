package com.cavalier.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cavalier.R;
import com.cavalier.listner.CarteListener;
import com.cavalier.model.CarteItem;

import java.util.ArrayList;
import java.util.List;


public class CarteItemListAdapter extends BaseAdapter {

    private List<CarteItem> carteItemsList;
    private LayoutInflater mInflater;
    private List<CarteListener> listeners = new ArrayList<>();

    public CarteItemListAdapter(Context mContext, List<CarteItem> carteItemsList) {
        this.carteItemsList = carteItemsList;
        mInflater = LayoutInflater.from(mContext);
    }

    public CarteItemListAdapter() {
        super();
    }

    public void addListener(CarteListener aListener) {
        listeners.add(aListener);
    }


    private void sendListenerToUpdate(CarteItem item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onUpdateCarteItem(item, position);
        }
    }

    @Override
    public int getCount() {
        if (carteItemsList!=null) {
            return carteItemsList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (carteItemsList!=null) {
            return carteItemsList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = (RelativeLayout) mInflater.inflate(R.layout.activity_cavalier_list_carte_item_item, parent, false);
        CheckBox cb = (CheckBox)layoutItem.findViewById(R.id.checkBox1);
        ImageView pic = (ImageView)layoutItem.findViewById(R.id.pic);

        // Renseignement des valeurs
        CarteItem current = carteItemsList.get(position);
        cb.setChecked(current.isCheck());
        cb.setTag(position);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToUpdate(carteItemsList.get(position), position);
            }
        });

        if (current.isCheck()) {
            pic.setImageResource(R.drawable.horsejump);
        } else {
            pic.setImageResource(R.drawable.van);
        }

        return layoutItem;
    }
}
