package fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import model.OrderSummaryItem;

/**
 * Created by rei on 2/09/17.
 */

public class OrderSummaryGridAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    ArrayList<OrderSummaryItem> items;

    public OrderSummaryGridAdapter(Context context, ArrayList<OrderSummaryItem> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.grid_order_summary_item, null);
        }

        TextView rightView = (TextView)view.findViewById(R.id.left_text);
        rightView.setText(items.get(i).leftText);

        TextView middleView = (TextView)view.findViewById(R.id.middle_text);
        middleView.setText(items.get(i).middleText);

        TextView rightText = (TextView)view.findViewById(R.id.right_text);
        rightText.setText(items.get(i).rightText);

        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public OrderSummaryItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
