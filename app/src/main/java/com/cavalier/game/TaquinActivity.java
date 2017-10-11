package com.cavalier.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.model.Monture;
import com.cavalier.service.MontureService;
import com.cavalier.tools.PictureUtils;

import java.util.List;


public class TaquinActivity extends Activity {

    private GridLayout taquin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taquin);

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
                            Toast.makeText(getBaseContext(), v.getContentDescription(), Toast.LENGTH_LONG).show();
                        }

                    });

                    taquin.addView(imgView);
                }
            }
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
