package fragment;

import android.content.Context;
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
import java.util.Arrays;

import me.himanshusoni.quantityview.QuantityView;
import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class MieFlavourGridAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<MieStock> items;
    LayoutInflater inflater;
    Integer chosenFlavour;
    int[] quantities;

    public MieFlavourGridAdapter(Context context, ArrayList<MieStock> items,
                                 int[] quantities, Integer chosenFlavour) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chosenFlavour = chosenFlavour;
        this.quantities = quantities;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.grid_quantity_item, null);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 700));
        }
        MieStock mie = getItem(i);
        //set image
        String uri = "@drawable/"+mie.brand+"_"+mie.flavour;
        uri = uri.replaceAll(" |\\(|\\)", "").toLowerCase();
        int imgResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getDrawable(imgResource);
        ImageView imgView = (ImageView)view.findViewById(R.id.mie_flavour_img);
        imgView.setImageDrawable(res);

        TextView flavourView = (TextView)view.findViewById(R.id.mie_flavour);
        flavourView.setText(mie.flavour);
        flavourView.setTextColor(ContextCompat.getColor(context, R.color.supremieRed));

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
//        QuantityView quantityView = (QuantityView)view.findViewById(R.id.quantity);
//        quantityView.addQuantity(quantities[i]);
//        quantityView.setMaxQuantity(3);
//        setQuantityListener(quantityView, i);

        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MieStock getItem(int i) {
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
                if (chosenFlavour != i) {
                    Arrays.fill(quantities, 0);
//                    ((View)quantityView.getParent()).setBackgroundColor();
                }
                quantities[i] = newQuantity;
                //TODO: save this info when fragment is paused instead of here
                State.getInstance().setChooseMieFragmentId(i, newQuantity);
                State.getInstance().setMieId(items.get(i).id);
                Log.v("SAVED", items.get(i).id.toString()+items.get(i).flavour);
                //TODO: change this to first time onClick hears something
//                ((MainActivity)context).enableTab(3);
//                ((MainActivity)context).enableTab(4);
//                ((MainActivity)context).enableTab(5);
//                ((MainActivity)context).enableTab(6);
                notifyDataSetChanged();
            }
            @Override
            public void onLimitReached() {

            }
        });
    }

    public void addQuantity(Integer i) {
        if (quantities[i] < 3) {
            quantities[i] += 1;
            //TODO: save this info when fragment is paused instead of here
            State.getInstance().setChooseMieFragmentId(i, quantities[i]);
            State.getInstance().setMieId(items.get(i).id);
            Log.v("SAVED", items.get(i).id.toString() + items.get(i).flavour);
            //TODO: change this to first time onClick hears something
//        ((MainActivity)context).enableTab(3);
//        ((MainActivity)context).enableTab(4);
//        ((MainActivity)context).enableTab(5);
//        ((MainActivity)context).enableTab(6);
            notifyDataSetChanged();
        }
    }

    public void minusQuantity(Integer i) {
        if (quantities[i] > 0) {
            quantities[i] -= 1;
            //TODO: save this info when fragment is paused instead of here
            State.getInstance().setChooseMieFragmentId(i, quantities[i]);
            State.getInstance().setMieId(items.get(i).id);
            Log.v("SAVED", items.get(i).id.toString() + items.get(i).flavour);
            //TODO: change this to first time onClick hears something
//        ((MainActivity)context).enableTab(3);
//        ((MainActivity)context).enableTab(4);
//        ((MainActivity)context).enableTab(5);
//        ((MainActivity)context).enableTab(6);
            notifyDataSetChanged();
        }
    }
}
