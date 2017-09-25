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
    OnQuantityChangeListener onQuantityChangeListener;

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
        ImageView imgView = (ImageView)view.findViewById(R.id.grid_img);
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

    public void addQuantity(Integer i) {
        Integer prevQuantity;
        prevQuantity = quantities[i];
        if (State.getInstance().getSubMieId() != null) {
            Integer prevSubMieId = State.getInstance().getSubMieId();
            if (i != prevSubMieId) {
                Arrays.fill(quantities, 0);
            }
        }
        if (prevQuantity < 3) {
            quantities[i] = prevQuantity + 1;

            //TODO: save this info when fragment is paused instead of here
            State.getInstance().setChooseMieFragmentId(i, quantities[i]);
            State.getInstance().setMieId(items.get(i).id);
            //TODO: change this to first time onClick hears something
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onQuantityChange(quantities[i]);
            }
            notifyDataSetChanged();
            setState(i);
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
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onQuantityChange(quantities[i]);
            }
            notifyDataSetChanged();
            setState(i);
        }
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(int quantity);
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }

    public void setState(int i) {
        State.getInstance().setChooseMieFragmentId(i, quantities[i]);
        State.getInstance().setMieId(items.get(i).id);
    }
}
