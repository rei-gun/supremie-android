package fragment;

import android.content.Context;
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

    public PedasGridAdapter(Context context, boolean[] items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.grid_pedas_item, null);
        }
        ImageView imgView = (ImageView)view.findViewById(R.id.pedas_img);
//        imgView.setImageAlpha();

        TextView textView = (TextView)view.findViewById(R.id.pedas_text);
        textView.setText(String.valueOf(i+1));

        if(items[i])
        {
            view.setBackgroundColor(context.getColor(R.color.colorPrimaryDark));
        } else {
            view.setBackgroundColor(context.getColor(R.color.white));
        }

        return view;
    }

    @Override
    public int getCount() {
        return items.length;
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
