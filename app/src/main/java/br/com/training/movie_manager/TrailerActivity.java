package br.com.training.movie_manager;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerActivity extends AppCompatActivity {

    @BindView(R.id.view_trailer)
    WebView viewTrailer;

    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        ButterKnife.bind(this);

        viewTrailer.getSettings().setJavaScriptEnabled(true);
        viewTrailer.loadUrl(getIntent().getStringExtra(EXTRA_URL));
        viewTrailer.setWebChromeClient(new WebChromeClient());
    }
}
