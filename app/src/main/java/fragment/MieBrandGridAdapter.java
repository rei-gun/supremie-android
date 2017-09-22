package fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
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

import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class MieBrandGridAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<MieStock> items;
    LayoutInflater inflater;
    String selectedBrand;

    public MieBrandGridAdapter(Context context, ArrayList<MieStock> items, String selectedBrand) {
        this.context = context;
        this.items = items;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selectedBrand = selectedBrand;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.grid_mie_brand_item, null);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 500));
        }
        //set image
        String uri = "@drawable/"+getItem(i).brand.replace(" ", "").toLowerCase();
        int imgResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getDrawable(imgResource);
        ImageView imgView = (ImageView)view.findViewById(R.id.mie_brand_img);
        imgView.setImageDrawable(res);

        TextView priceView = (TextView)view.findViewById(R.id.mie_brand_price);
        priceView.setText(State.getInstance().addDot("RP "+(getItem(i).price.toString())));

        if (getItem(i).brand.equals(selectedBrand)) {
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
    public MieStock getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
