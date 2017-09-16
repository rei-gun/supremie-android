package fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.MainActivity;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.grid_mie_flavour_item, null);
        }
        ImageView imgView = (ImageView)view.findViewById(R.id.mie_flavour_img);
//        imgView.setImageAlpha();

        TextView brandView = (TextView)view.findViewById(R.id.mie_flavour);
        brandView.setText((getItem(i).flavour));

        QuantityView quantityView = (QuantityView)view.findViewById(R.id.quantity);
        quantityView.setQuantity(quantities[i]);
        quantityView.setMaxQuantity(3);
        setQuantityListener(quantityView, i);

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
                ((MainActivity)context).enableTab(3);
                ((MainActivity)context).enableTab(4);
                ((MainActivity)context).enableTab(5);
                ((MainActivity)context).enableTab(6);
                notifyDataSetChanged();
            }
            @Override
            public void onLimitReached() {

            }
        });
    }
}
