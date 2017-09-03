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

import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseMieBrandFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_brand, container, false);
        final GridView gridView = (GridView) view.findViewById(R.id.grid_mie_brand);
        ArrayList<MieStock> oneOfEachBrand = ((MainActivity)getActivity()).getAllStock().getOneOfEachBrand();

        final MieBrandGridAdapter gridAdapter = new MieBrandGridAdapter(getActivity(),
                oneOfEachBrand);
        gridView.setAdapter(gridAdapter);

        //TODO: select previously selected brand

        setListener(this, gridView, oneOfEachBrand);

        return view;
    }

    private void setListener(final Fragment f, GridView gridView, final ArrayList<MieStock> oneOfEachBrand) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (State.getInstance().getBrand() != oneOfEachBrand.get(i).brand ) {
                    State.getInstance().setBrand(oneOfEachBrand.get(i).brand);
                    //TODO: do this when fragment pauses instead
                    State.getInstance().setMieId(null);
                    State.getInstance().setChooseMieFragmentId(null, null);
                }

            }
        });
    }
}
