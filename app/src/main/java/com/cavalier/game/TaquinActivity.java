package com.cavalier.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.model.Monture;
import com.cavalier.service.MontureService;
import com.cavalier.tools.PictureUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TaquinActivity extends Activity {

    private GridLayout taquin;
    private List<ImageView> views = new ArrayList<>();
    private TextView textView;
    private int iter;
    private Random r = new Random();
    private Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taquin);

        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        taquin = (GridLayout) findViewById(R.id.gridLayout);

        int screenPixel=this.getWindowManager().getDefaultDisplay().getWidth();
        int imgWidth = 256;
        int imgHeight = 280;
        int columns = (int) ((float) screenPixel / (float) imgWidth);
        taquin.setColumnCount(columns);

        List<Monture> montures = MontureService.getAll();
        if (montures != null) {
            for (Monture monture:montures) {
                if (monture.getImg() != null && monture.getImg().length > 0) {

                    ImageView imgView = new ImageView(this);
                    imgView.setImageBitmap(PictureUtils.getImage(monture.getImg()));
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(imgWidth, imgHeight);
                    imgView.setLayoutParams(parms);
                    imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgView.setContentDescription(monture.getNom());

                    imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View v) {

                            v.startAnimation(shake);

                            if (textView.getText().toString().equals(v.getContentDescription())) {
                                Toast toast = Toast.makeText(getBaseContext(), "Bravo !", Toast.LENGTH_LONG);
                                TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
                                tv.setTextColor(Color.GREEN);
                                toast.show();
                                iter = r.nextInt(views.size());
                                textView.setText(views.get(iter).getContentDescription());
                            } else {
                                Toast toast = Toast.makeText(getBaseContext(), "Non", Toast.LENGTH_SHORT);
                                TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
                                tv.setTextColor(Color.RED);
                                toast.show();
                            }
                        }
                    });
                    views.add(imgView);
                    taquin.addView(imgView);
                }
            }
        }

        textView = (TextView) findViewById(R.id.textView);

        iter = r.nextInt(views.size());
        if (views.size() > 0) {
            textView.setText(views.get(iter).getContentDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_taquin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }
}
