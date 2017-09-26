package fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang5.supremie.R;

/**
 * Created by rei on 2/09/17.
 */

public class PedasGridAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    boolean[] items;
    String[] pedasDescriptions;

    public PedasGridAdapter(Context context, boolean[] items, String[] s) {
        this.context = context;
        this.items = items;
        this.pedasDescriptions = s;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        i += 1;//remove this if you want level 0 back
        if (view == null) {
            view = inflater.inflate(R.layout.grid_item_pedas, null);
        }
        String uri = "@drawable/level"+String.valueOf(i);
        int imgResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getDrawable(imgResource);
        ImageView imgView = (ImageView)view.findViewById(R.id.pedas_img);
        imgView.setImageDrawable(res);

        TextView textView = (TextView)view.findViewById(R.id.pedas_text);
        textView.setText(pedasDescriptions[i]);

        /*
        if(items[i])
        {
            view.setBackgroundColor(context.getColor(R.color.lightGrey));
        } else {
            view.setBackgroundColor(context.getColor(R.color.white));
        }*/

        return view;
    }

    @Override
    public int getCount() {
        return items.length-1;//remove -1 if you want level 0 back
    }

    @Override
    public Boolean getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
