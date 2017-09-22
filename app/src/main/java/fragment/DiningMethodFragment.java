package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.MainActivity;
import com.bintang5.supremie.activity.State;

/**
 * Created by rei on 2/09/17.
 */

public class DiningMethodFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining_method, container, false);

        ImageButton dineInButton = (ImageButton)view.findViewById(R.id.button_dine_in);
        dineInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State.getInstance().setDiningMethod("makan sini");
                setAndChangeTab();
            }
        });

        ImageButton takeawayButton = (ImageButton)view.findViewById(R.id.button_takeaway);
        takeawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State.getInstance().setDiningMethod("bungkus");
                setAndChangeTab();
            }
        });

        return view;
    }

    private void setAndChangeTab() {
        ((MainActivity)getActivity()).enableTab(1);
        TabHost tabHost = (TabHost)getActivity().findViewById(R.id.tab_host);
        tabHost.setCurrentTab(1);
    }
}
