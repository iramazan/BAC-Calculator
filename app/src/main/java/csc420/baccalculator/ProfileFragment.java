package csc420.baccalculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import csc420.baccalculator.data.DatabaseManager;
import csc420.baccalculator.data.User;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    OnFragmentSelectionListener activityCallback;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long userId = sharedPref.getLong(getString(R.string.current_user_key), 0);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        User user;
        try {
            user = executor.submit(() -> DatabaseManager.getInstance(getActivity().getApplicationContext()).userDao().getUserById(userId)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
        TextView nameView = getView().findViewById(R.id.profile_name);
        nameView.setText(user.name);
        TextView dobView = getView().findViewById(R.id.dob_profile);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
        dobView.setText("Date Of Birth: " + formatter.format(user.birthday));
        TextView genderView = getView().findViewById(R.id.gender_profile);
        genderView.setText("Gender: " + user.gender.name().toLowerCase());
        // set button listeners
        Button favoritePage = getView().findViewById(R.id.favorites_profile_button);
        favoritePage.setOnClickListener(v -> activityCallback.onFragmentSelection(new FavoritesPageFragment()));
    }

    public void setOnFragmentSelectionListener(OnFragmentSelectionListener activity) {
        activityCallback = activity;
    }

    public interface OnFragmentSelectionListener {
        void onFragmentSelection(Fragment fragment);
    }
}
