package app.raulvalverde.com.coinmanager.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import app.raulvalverde.com.coinmanager.Coin;
import app.raulvalverde.com.coinmanager.CoinData;
import app.raulvalverde.com.coinmanager.R;

public class ShowCoin extends AppCompatActivity {

    TextView showcurrency, showdescription;
    ImageView showimg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_coin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showcurrency = (TextView) findViewById(R.id.showCurrency);
        String currency = getIntent().getExtras().getString("currency");
        showcurrency.setText(currency);

        showdescription = (TextView) findViewById(R.id.showDescription);
        String description = getIntent().getExtras().getString("description");
        showdescription.setText(description);

        showimg1 = (ImageView) findViewById(R.id.showImg1);
        String path1 = getIntent().getExtras().getString("img1path");
        Uri img1 = Uri.parse(path1);
        showimg1.setImageURI(img1);

    }

}
