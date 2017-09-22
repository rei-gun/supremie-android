package fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.State;

import java.util.ArrayList;

import me.himanshusoni.quantityview.QuantityView;
import model.DrinkStock;
import model.ToppingStock;

/**
 * Created by rei on 2/09/17.
 */

public class DrinkGridAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<DrinkStock> items;
    LayoutInflater inflater;
    int[] quantities;

    public DrinkGridAdapter(Context context, ArrayList<DrinkStock> items, int[] quantities) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.quantities = quantities;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.grid_mie_flavour_item, null);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 500));
        }

        DrinkStock drink = getItem(i);
        String uri = "@drawable/"+drink.brand;
        uri = uri.replace(" ", "").toLowerCase();
        Log.v("HERP", uri);
        Log.v("HERP", String.valueOf(i));
        int imgResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getDrawable(imgResource);
        ImageView imgView = (ImageView)view.findViewById(R.id.mie_flavour_img);
        imgView.setImageDrawable(res);

        TextView nameView = (TextView)view.findViewById(R.id.mie_flavour);
        nameView.setText(drink.brand);
        nameView.setTextColor(ContextCompat.getColor(context, R.color.black));

        TextView priceView = (TextView)view.findViewById(R.id.price);
        priceView.setText(State.getInstance().addDot("RP "+drink.price));

        QuantityView quantityView = (QuantityView)view.findViewById(R.id.quantity);
        quantityView.setQuantity(quantities[i]);
        quantityView.setMaxQuantity(2);
        setQuantityListener(quantityView, i);

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
        return items.get(i).id;
    }

    private void setQuantityListener(final QuantityView quantityView, final Integer i) {
        quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                quantities[i] = newQuantity;
                //TODO: save this info when fragment is paused instead of here
                notifyDataSetChanged();
            }

            @Override
            public void onLimitReached() {

            }
        });
    }

}
