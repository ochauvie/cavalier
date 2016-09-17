package com.cavalier.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.cavalier.R;

public class HttpActivity extends Activity  {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_http);

        webView = (WebView)  findViewById(R.id.webView);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_http, menu);
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

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            String url =  bundle.getString("URL");
            if (url != null) {
                webView.loadUrl(url);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

}
