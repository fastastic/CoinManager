package app.raulvalverde.com.coinmanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import app.raulvalverde.com.coinmanager.Adapters.CoinAdapter;
import app.raulvalverde.com.coinmanager.Coin;
import app.raulvalverde.com.coinmanager.CoinData;
import app.raulvalverde.com.coinmanager.R;

public class AddCoinActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etCurrency,etValue,etYear,etCountry,etDescription;
    Button bAdd, bAddImg1, bAddImg2;
    CoinData coinData;
    ImageView img1;
    Uri img1path;

    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coinData = new CoinData(this);
        coinData.open();

        etCurrency = (EditText) findViewById(R.id.addCurrency);
        etValue = (EditText) findViewById(R.id.addValue);
        etYear = (EditText) findViewById(R.id.addYear);
        etCountry = (EditText) findViewById(R.id.addCountry);
        etDescription = (EditText) findViewById(R.id.addDescription);
        bAdd = (Button) findViewById(R.id.addCoin);
        bAddImg1 = (Button) findViewById(R.id.addImg1);
        bAddImg2 = (Button) findViewById(R.id.addImg2);
        img1 = (ImageView) findViewById(R.id.Img1);


        bAdd.setOnClickListener(this);
        bAddImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Take a photo", "Pick from gallery", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddCoinActivity.this);
                builder.setTitle("Choose an option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which] == "Take a photo") {
                            openCamera(1);
                        }
                        else if (options[which] == "Pick from gallery") {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Select source from image"), SELECT_PICTURE);
                        }
                        else if (options[which] == "Cancel") {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        bAddImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Take a photo", "Pick from gallery", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddCoinActivity.this);
                builder.setTitle("Choose an option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which] == "Take a photo") {
                            openCamera(2); //SE HACE LA FOTO PERO NO SE MUESTRA
                        }
                        else if (options[which] == "Pick from gallery") {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Select source from image"), SELECT_PICTURE);
                        }
                        else if (options[which] == "Cancel") {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    public boolean tryParseDouble(String s) {
        try {
            Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            System.out.println("This is not a double.");
            return false;
        }
        return true;
    }

    public boolean tryParseInteger(String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            System.out.println("This is not a double.");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //SOLUCIONADO PROBLEMA DE DAR ATRAS Y QUE NO SE VIERA EL ULTIMO ELEMENTO AÑADIDO DALEEEEE
        finish();
        startActivity(new Intent(AddCoinActivity.this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addCoin:
                if (etCurrency.getText().toString().equals("")  || etValue.getText().toString().equals("") || etYear.getText().toString().equals("") || etCountry.getText().toString().equals("") || etDescription.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Did you forget filling some fields?", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean d,i;
                    d = tryParseDouble(etValue.getText().toString());
                    i = tryParseInteger(etYear.getText().toString());
                    if (d && i) {
                        Double value = Double.parseDouble(etValue.getText().toString());
                        int year = Integer.parseInt(etYear.getText().toString());
                        Coin coin = new Coin(etCurrency.getText().toString(), value, year, etCountry.getText().toString(), etDescription.getText().toString(), img1path.toString());
                        coin = coinData.createCoin(coin.getCurrency(), coin.getValue(), coin.getYear(), coin.getCountry(), coin.getDescription(), coin.getImg1path());
                        Toast.makeText(getApplicationContext(), "Coin added succesfully", Toast.LENGTH_SHORT).show();
                        coinData.close();
                        finish();
                        startActivity(new Intent(AddCoinActivity.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Value and Year attributes only accept numbers", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    private void openCamera(int num) {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + file.separator + MEDIA_DIRECTORY
                + file.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    String dir = Environment.getExternalStorageDirectory() + File.separator
                            + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    Log.d("dir", dir);
                    img1path = Uri.parse(dir);
                    bAddImg1.setBackgroundColor(Color.GREEN);
                    bAddImg1.setText("IMAGE SELECTED");
                }
            break;

            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                        img1path = data.getData();
                        bAddImg1.setBackgroundColor(Color.GREEN);
                        bAddImg1.setText("IMAGE SELECTED");
                }
        }
    }

    /*private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        img1.setImageBitmap(bitmap);
    }*/
}
