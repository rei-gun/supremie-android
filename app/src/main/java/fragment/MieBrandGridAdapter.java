package fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class MieBrandGridAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<MieStock> items;
    LayoutInflater inflater;

    public MieBrandGridAdapter(Context context, ArrayList<MieStock> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.grid_mie_brand_item, null);
        }
        ImageView imgView = (ImageView)view.findViewById(R.id.mie_brand_img);
//        imgView.setImageAlpha();

        TextView brandView = (TextView)view.findViewById(R.id.mie_brand);
        brandView.setText((getItem(i).brand));

        TextView priceView = (TextView)view.findViewById(R.id.mie_brand_price);
        priceView.setText((getItem(i).price.toString()));

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
        return 0;
    }


}
