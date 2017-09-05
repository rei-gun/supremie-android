package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.PaymentMethodActivity;
import com.bintang5.supremie.activity.State;

import java.util.ArrayList;

import model.Drink;
import model.DrinkStock;
import model.MieStock;
import model.OrderSummaryItem;
import model.Topping;
import model.ToppingStock;

/**
 * Created by rei on 2/09/17.
 */

public class OrderSummaryFragment extends Fragment {

    ArrayList<OrderSummaryItem> items;
    OrderSummaryGridAdapter gridAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_pedas);
        MieStock chosenMie = State.getInstance().getAllStock().getMieStocks().get(State.getInstance().getMieId()-1);
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
        ArrayList<ToppingStock> toppings = State.getInstance().getAllStock().getToppingStocks();
        ArrayList<Topping> ts = new ArrayList<>();
        for (int i = 0; i<toppingQuantities.length; i++) {
            if (toppingQuantities[i] > 0) {
                ToppingStock toppingStock = toppings.get(i);
                OrderSummaryItem topping = new OrderSummaryItem("TOPPING - "+toppingStock.name,
                        "QTY - "+String.valueOf(toppingQuantities[i]),
                        "RP "+String.valueOf(toppingQuantities[i]*toppingStock.price));
                items.add(topping);
                subTotal += toppingQuantities[i]*toppingStock.price;

                //TODO: put below in a new thread
                Topping t = new Topping(toppingStock.id, toppingQuantities[i], null,toppingStock.price);
                ts.add(t);
            }
        }
        State.getInstance().setToppings(ts);

        //create drink rows
        int[] drinkQuantities = State.getInstance().getDrinkQuantities();
        ArrayList<DrinkStock> ds = State.getInstance().getAllStock().getDrinkStocks();
        ArrayList<Drink> drinks = new ArrayList<>();
        for (int i=0; i<drinkQuantities.length; i++) {
            if (drinkQuantities[i] > 0) {
                DrinkStock d = ds.get(i);
                OrderSummaryItem dRow = new OrderSummaryItem("MINUMAN - "+d.brand+" "+d.flavour,
                        "QTY - "+String.valueOf(drinkQuantities[i]),
                        "RP "+String.valueOf(drinkQuantities[i]*d.price));
                items.add(dRow);
                subTotal += drinkQuantities[i]*d.price;

                //TODO: put below in a new thread
                Drink drink = new Drink(d.id, drinkQuantities[i], d.price);
                drinks.add(drink);
            }
        }
        State.getInstance().setDrinks(drinks);

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

        State.getInstance().setGrandTotal(grandTotal.intValue());

        gridAdapter = new OrderSummaryGridAdapter(getActivity(),
                items);
        gridView.setAdapter(gridAdapter);
        setListener(gridView, items);

        FloatingActionButton b = (FloatingActionButton) view.findViewById(R.id.button_go_to_payment_method);
        setBListener(b);
        b.bringToFront();

        return view;
    }

    private void setListener(GridView gridView, final ArrayList<OrderSummaryItem>  items) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (State.getInstance().getPedasLevel() == null ||
                        State.getInstance().getPedasLevel() != i) {
                    //TODO: do this when fragment pauses instead
                    State.getInstance().setPedasLevel(i);
                    gridAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setBListener(FloatingActionButton b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaymentMethodActivity.class);
                startActivity(intent);
            }
        });
    }
}
