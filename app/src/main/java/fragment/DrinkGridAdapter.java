package fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.State;

import java.util.ArrayList;

import model.DrinkStock;

/**
 * Created by rei on 2/09/17.
 */

public class DrinkGridAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<DrinkStock> items;
    LayoutInflater inflater;
    int[] quantities;
    OnQuantityChangeListener onQuantityChangeListener;

    public DrinkGridAdapter(Context context, ArrayList<DrinkStock> items, int[] quantities) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.quantities = quantities;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.grid_quantity_price_item, null);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 750));
        }

        DrinkStock drink = getItem(i);
        String uri = "@drawable/"+drink.brand;
        uri = uri.replace(" ", "").toLowerCase();
        Log.v("HERP", uri);
        Log.v("HERP", String.valueOf(i));
        int imgResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res;
        try {
            res = context.getDrawable(imgResource);
        } catch (Resources.NotFoundException e) {
            res = context.getDrawable(R.drawable.supremie_logo);
        }
        ImageView imgView = (ImageView)view.findViewById(R.id.grid_img);
        imgView.setImageDrawable(res);

        TextView nameView = (TextView)view.findViewById(R.id.name);
        nameView.setText(drink.brand);
        nameView.setTextColor(ContextCompat.getColor(context, R.color.black));

        TextView priceView = (TextView)view.findViewById(R.id.price);
        priceView.setText(State.getInstance().addDot("RP "+drink.price));
        TextView quantityView = (TextView)view.findViewById(R.id.item_quantity);
        quantityView.setText(String.valueOf(quantities[i]));

        Button minus = (Button)view.findViewById(R.id.minus_button);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusQuantity(i);
            }
        });

        Button plus = (Button)view.findViewById(R.id.plus_button);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuantity(i);
            }
        });

        if (quantities[i] > 0) {
            view.setBackgroundColor(context.getColor(R.color.lightGrey));
        } else {
            view.setBackgroundColor(context.getColor(R.color.white));
        }

        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DrinkStock getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addQuantity(Integer i) {
        if (quantities[i] < 3) {
            quantities[i] += 1;
            //TODO: save this info when fragment is paused instead of here
            State.getInstance().setDrinkQuantity(i, quantities[i]);
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onQuantityChange(quantities[i]);
            }
            //TODO: change this to first time onClick hears something
            notifyDataSetChanged();
        }
    }

    public void minusQuantity(Integer i) {
        if (quantities[i] > 0) {
            quantities[i] -= 1;
            //TODO: save this info when fragment is paused instead of here
            State.getInstance().setDrinkQuantity(i, quantities[i]);
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onQuantityChange(quantities[i]);
            }
            //TODO: change this to first time onClick hears something
            notifyDataSetChanged();
        }
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(int quantity);
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }
}
