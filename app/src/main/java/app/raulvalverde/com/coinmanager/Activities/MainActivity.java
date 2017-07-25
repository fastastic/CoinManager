package app.raulvalverde.com.coinmanager.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.raulvalverde.com.coinmanager.Adapters.CoinAdapter;
import app.raulvalverde.com.coinmanager.Coin;
import app.raulvalverde.com.coinmanager.CoinData;
import app.raulvalverde.com.coinmanager.R;


public class MainActivity extends AppCompatActivity {
    private CoinData coinData;
    ListView lv;
    CoinAdapter coinAdapter;
    List<Coin> values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinData = new CoinData(this);
        coinData.open();
        int r = coinData.getProfilesCount();

        if (r == 0) { //PROBLEMA SOLUSIONAO LOCO
            addInitialCoins();
        }

        values = coinData.getAllCoins();

        coinAdapter = new CoinAdapter(MainActivity.this,values);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.coinListView);
        lv.setAdapter(coinAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowCoin.class);
                intent.putExtra("currency", values.get(position).getCurrency());
                intent.putExtra("value", values.get(position).getValue());
                intent.putExtra("year", values.get(position).getYear());
                intent.putExtra("country", values.get(position).getCountry());
                intent.putExtra("description", values.get(position).getDescription()); //PROBLEMA SOLUSIONAOOOOO AL PILLAR LA DESCRIPCION
                intent.putExtra("img1path", values.get(position).getImg1path());
                startActivity(intent);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete this coin?");

                final int positionToRemove = position;
                adb.setNegativeButton("Cancel",null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Coin coinToDelete;
                        coinToDelete = (Coin) coinAdapter.getItem(positionToRemove);
                        coinData.deleteCoin(coinToDelete);
                        coinAdapter.remove(coinToDelete);
                    }
                });
                adb.show();
                return true;
            }
        });

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddCoinActivity.class));
                finish(); //SOLUCIONADO EL PROBLEMA DE QUE AL DAR ATRAS DESAPARECIERA EL ITEM AÑADIDO
            }
        });

    }


    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            finish();
            return;
        }else {
            Toast.makeText(this, "Press back again to quit", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addInitialCoins() {
        Coin coin = new Coin("Euro",2,2000,"UE","Descripcion euro","android.resource://app.raulvalverde.com.coinmanager/drawable/euro");
        coin = coinData.createCoin(coin.getCurrency(),coin.getValue(), coin.getYear(),coin.getCountry(),coin.getDescription(),coin.getImg1path());

        coin = new Coin("Dolar",3,1000,"EEUU","Descripcion dolar","android.resource://app.raulvalverde.com.coinmanager/drawable/dolar");
        coin = coinData.createCoin(coin.getCurrency(),coin.getValue(), coin.getYear(),coin.getCountry(),coin.getDescription(),coin.getImg1path());

        coin = new Coin("Rupia",3,1000,"India","Descripcion rupia","android.resource://app.raulvalverde.com.coinmanager/drawable/rupia");
        coin = coinData.createCoin(coin.getCurrency(),coin.getValue(), coin.getYear(),coin.getCountry(),coin.getDescription(),coin.getImg1path());

        coin = new Coin("Libra",3,1000,"UK","Descripcion Libra","android.resource://app.raulvalverde.com.coinmanager/drawable/libra");
        coin = coinData.createCoin(coin.getCurrency(),coin.getValue(), coin.getYear(),coin.getCountry(),coin.getDescription(),coin.getImg1path());
    }

    @Override
    protected void onResume() {
        coinData.open();
        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onPause() {
        coinData.close();
        super.onPause();
    }

}
