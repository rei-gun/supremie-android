package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bintang5.supremie.R;

/**
 * Created by rei on 2/09/17.
 */

public class DiningMethodFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining_method, container, false);
//        ButterKnife.bind(this.view);
        return view;
    }
}
