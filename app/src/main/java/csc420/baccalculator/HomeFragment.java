package csc420.baccalculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private OnFragmentSelectionListener activity;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = getView().findViewById(R.id.calc_button);
        fab.setOnClickListener(v -> {
            CalculatorFragment fragment = new CalculatorFragment();
            activity.onFragmentSelection(fragment);
        });
        TextView bacTextView = getView().findViewById(R.id.bac_text);
        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        double bac = Double.parseDouble(sharedPref.getString(getString(R.string.bac_key), "0"));
        bacTextView.setText(String.format("%f", bac));
    }

    public void setOnFragmentSelection(OnFragmentSelectionListener activity) {
        this.activity = activity;
    }

    public interface OnFragmentSelectionListener {
        void onFragmentSelection(Fragment fragment);
    }
}
