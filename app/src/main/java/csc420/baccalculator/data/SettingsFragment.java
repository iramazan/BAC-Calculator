package csc420.baccalculator.data;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import csc420.baccalculator.NewUserFragment;
import csc420.baccalculator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    OnFragmentSelectionListener activityCallback;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button newUserButton = view.findViewById(R.id.new_user_button);
        newUserButton.setOnClickListener(v -> {
            NewUserFragment newUserFragment = new NewUserFragment();
            activityCallback.onFragmentSelection(newUserFragment);
        });
    }

    public void setOnFragmentSelectionListener(OnFragmentSelectionListener activity) {
        activityCallback = activity;
    }

    public interface OnFragmentSelectionListener {
        void onFragmentSelection(Fragment fragment);
    }
}
