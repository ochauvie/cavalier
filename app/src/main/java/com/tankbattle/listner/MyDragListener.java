package com.tankbattle.listner;

import android.view.DragEvent;
import android.view.View;

import com.tankbattle.adapter.TagTank;
import com.tankbattle.adapter.TankInEquipeListAdapter;
import com.tankbattle.model.Tank;

/**
 * Created by Olivier on 12/09/15.
 */
public class MyDragListener implements View.OnDragListener {

    private TankInEquipeListAdapter adapterDestination;
    private TankInEquipeListAdapter adapterOrigine;
    private float oldY;

    public MyDragListener(TankInEquipeListAdapter adapterDestination, TankInEquipeListAdapter adapterOrigine) {
        this.adapterDestination = adapterDestination;
        this.adapterOrigine = adapterOrigine;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                oldY = event.getY();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                View view = (View) event.getLocalState();
                TagTank tagTank = (TagTank) view.getTag();
                int position = tagTank.getPosition();
                String origine = tagTank.getOrigine();


                // Dag and drop dans des listes différentes
                if (!adapterDestination.getItemOrigine().equals(origine)) {
                    Tank currentTank = (Tank)adapterOrigine.getItem(position);

                    adapterDestination.getTankList().add(currentTank);
                    adapterOrigine.getTankList().remove(position);

                    adapterDestination.notifyDataSetChanged();
                    adapterOrigine.notifyDataSetChanged();

                // Dag and drop dans la même liste: on monte ou on dessend d'une case
                } else {
                    Tank currentTank = (Tank)adapterDestination.getItem(position);
                    // Drag down
                    if (event.getY()>oldY) {
                        // Not the last
                        if (position<adapterDestination.getTankList().size()-1) {
                            Tank oldTank = (Tank) adapterDestination.getItem(position + 1);
                            adapterDestination.getTankList().set(position + 1, currentTank);
                            adapterDestination.getTankList().set(position, oldTank);
                        }
                    // Drag up
                    } else if (event.getY()<oldY) {
                        // Not the first
                        if (position>0) {
                            Tank oldTank = (Tank) adapterDestination.getItem(position - 1);
                            adapterDestination.getTankList().set(position - 1, currentTank);
                            adapterDestination.getTankList().set(position, oldTank);
                        }

                    }
                    adapterDestination.notifyDataSetChanged();
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:
            default:
                break;
        }
        return true;
    }
}

