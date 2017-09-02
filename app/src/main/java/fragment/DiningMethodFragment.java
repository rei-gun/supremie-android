package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.MainActivity;

/**
 * Created by rei on 2/09/17.
 */

public class DiningMethodFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining_method, container, false);

        Button dineInButton = (Button)view.findViewById(R.id.button_dine_in);
        dineInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).getOrder().diningMethod = "makan sini";
            }
        });

        Button takeawayButton = (Button)view.findViewById(R.id.button_takeaway);
        takeawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).getOrder().diningMethod = "bungkus";
            }
        });

        return view;
    }
}
