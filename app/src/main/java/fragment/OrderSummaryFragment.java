package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.MainActivity;
import com.bintang5.supremie.activity.State;

import java.util.ArrayList;

import model.DrinkStock;
import model.MieStock;
import model.OrderSummaryItem;
import model.ToppingStock;

/**
 * Created by rei on 2/09/17.
 */

public class OrderSummaryFragment extends Fragment {

//    Integer pedasLevel;
    ArrayList<OrderSummaryItem> items;
    OrderSummaryGridAdapter gridAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_pedas, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_pedas);
        MieStock chosenMie = ((MainActivity)getActivity()).getAllStock().getMieStocks().get(
                State.getInstance().getMieId());
        items = new ArrayList<>();
        Integer subTotal = 0;

        //set fist row
        OrderSummaryItem item = new OrderSummaryItem(chosenMie.brand+ " - "+
                chosenMie.flavour, "QTY - "+State.getInstance().getQuantityMie().toString(),
                "RP "+String.valueOf(State.getInstance().getQuantityMie()*chosenMie.price));
        items.add(item);
        subTotal += State.getInstance().getQuantityMie()*chosenMie.price;

        //set 2nd row
        OrderSummaryItem pedas = new OrderSummaryItem("LEVEL PEDAS - LEVEL "+State.getInstance().getPedasLevel().toString(),
                "", "RP "+String.valueOf(State.getInstance().getPedasPrice(State.getInstance().getPedasLevel())));
        items.add(pedas);
        subTotal += State.getInstance().getPedasPrice(State.getInstance().getPedasLevel());

        //create topping rows
        int[] toppingQuantities = State.getInstance().getToppingQuantities();
        ArrayList<ToppingStock> toppings = ((MainActivity)getActivity()).getAllStock().getToppingStocks();
        for (int i = 0; i<toppingQuantities.length; i++) {
            if (toppingQuantities[i] > 0) {
                ToppingStock toppingStock = toppings.get(i);
                OrderSummaryItem topping = new OrderSummaryItem("TOPPING - "+toppingStock.name,
                        "QTY - "+String.valueOf(toppingQuantities[i]),
                        "RP "+String.valueOf(toppingQuantities[i]*toppingStock.price));
                items.add(topping);
                subTotal += toppingQuantities[i]*toppingStock.price;
            }
        }

        //create drink rows
        int[] drinkQuantities = State.getInstance().getDrinkQuantities();
        ArrayList<DrinkStock> ds = ((MainActivity)getActivity()).getAllStock().getDrinkStocks();
        for (int i=0; i<drinkQuantities.length; i++) {
            if (drinkQuantities[i] > 0) {
                DrinkStock d = ds.get(i);
                OrderSummaryItem dRow = new OrderSummaryItem("MINUMAN - "+d.brand+" "+d.flavour,
                        "QTY - "+String.valueOf(drinkQuantities[i]),
                        "RP "+String.valueOf(drinkQuantities[i]*d.price));
                items.add(dRow);
                subTotal += drinkQuantities[i]*d.price;
            }
        }

        //create subtotal row
        OrderSummaryItem o = new OrderSummaryItem("", "SUB TOTAL",
                "RP "+subTotal.toString());
        items.add(o);

        //create tax & service charge row
        Double taxCharge = subTotal*0.15;
        OrderSummaryItem taxRow = new OrderSummaryItem("", "TAX & SERVICE CHARGE 15%",
                "RP "+String.valueOf(taxCharge.intValue()));
        items.add(taxRow);

        Double grandTotal = subTotal+ (subTotal*0.15);
        OrderSummaryItem totalRow = new OrderSummaryItem("", "GRAND TOTAL",
                "RP "+String.valueOf(grandTotal.intValue()));
        items.add(totalRow);

        gridAdapter = new OrderSummaryGridAdapter(getActivity(),
                items);
        gridView.setAdapter(gridAdapter);
//        if (pedasLevel != null) {
//            gridAdapter.setSelectedPosition(pedasLevel);
//            gridAdapter.notifyDataSetChanged();
//        }
//        gridAdapter.setSelectedPosition(pedasLevel);

        //TODO: select previously selected selectedBrand
//        if (pedasLevel != null) {
        setListener(this, gridView, items);
//        }
        return view;
    }

    private void setListener(final Fragment f, GridView gridView, final ArrayList<OrderSummaryItem>  items) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (State.getInstance().getPedasLevel() == null ||
                        State.getInstance().getPedasLevel() != i) {
//                    Arrays.fill(items, false);
//                    items[i] = true;
                    //TODO: do this when fragment pauses instead
                    State.getInstance().setPedasLevel(i);
                    gridAdapter.notifyDataSetChanged();
                }

            }
        });
    }
}
