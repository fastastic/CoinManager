package app.raulvalverde.com.coinmanager.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.raulvalverde.com.coinmanager.Coin;
import app.raulvalverde.com.coinmanager.R;

/**
 * Created by Raul on 09/05/2017.
 */

public class CoinAdapter extends BaseAdapter {

    Context c;
    List<Coin> coins;
    LayoutInflater inflater;

    public CoinAdapter(Context c, List<Coin> coins) {
        this.c = c;
        this.coins = coins;
    }

    public void addCoin(Coin c) {
        coins.add(c);
    }

    @Override
    public int getCount() {
        return coins.size();
    }

    @Override
    public Object getItem(int position) {
        return coins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(Coin object) {
        coins.remove(object);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.coinitem,parent,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.coinName);
        ImageView img= (ImageView) convertView.findViewById(R.id.coinImage);
        TextView descript = (TextView) convertView.findViewById(R.id.coinDescription);


        final String name = coins.get(position).getCurrency();
        final String descr = coins.get(position).getDescription();
        final String img1path = coins.get(position).getImg1path();
        //int image=coins.get(position).getImage();

        Uri img1 = Uri.parse(img1path);
        img.setImageURI(img1);

        nameTxt.setText(name);
        descript.setText(descr);


        return convertView;
    }
}
