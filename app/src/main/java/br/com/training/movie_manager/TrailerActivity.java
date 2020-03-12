package br.com.training.movie_manager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Represents Trailer Activity.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
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
        viewTrailer.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there is history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && viewTrailer.canGoBack()) {
            viewTrailer.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
