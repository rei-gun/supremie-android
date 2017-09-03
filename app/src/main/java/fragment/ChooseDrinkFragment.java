package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.MainActivity;

import java.util.ArrayList;

import model.DrinkStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseDrinkFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_topping, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_mie_flavour);
        MainActivity activity = (MainActivity)getActivity();

        ArrayList<DrinkStock> drinkStocks = activity.getAllStock().getDrinkStocks();

        final DrinkGridAdapter gridAdapter = new DrinkGridAdapter(activity,
                drinkStocks);
        gridView.setAdapter(gridAdapter);

        return view;
    }

}
