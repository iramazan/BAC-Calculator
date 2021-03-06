package csc420.baccalculator;


import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import csc420.baccalculator.data.AppDatabase;
import csc420.baccalculator.data.DatabaseManager;
import csc420.baccalculator.data.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserFragment extends Fragment {

    OnFragmentSelectionListener activityCallback;

    public NewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Selecting date of birth
        final Calendar calendar = Calendar.getInstance();
        final EditText editDob = getView().findViewById(R.id.edit_dob);
        final DatePickerDialog.OnDateSetListener dobPicker = (view1, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
            editDob.setText(formatter.format(calendar.getTime()));
        };
        editDob.setOnClickListener(v -> new DatePickerDialog(getContext(), dobPicker, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());

        // selecting gender
        final Spinner spinner = getView().findViewById(R.id.edit_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genders, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Handle new user submission
        final Button submitButton = getView().findViewById(R.id.new_user_button);
        submitButton.setOnClickListener(v -> {
            AppDatabase db = DatabaseManager.getInstance(getContext().getApplicationContext());
            EditText editName = getView().findViewById(R.id.edit_name);
            EditText editWeight = getView().findViewById(R.id.edit_weight);
            String userName = editName.getText().toString();
            int weight = Integer.parseInt(editWeight.getText().toString());
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
            Date dob;
            try {
                dob = formatter.parse(editDob.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            User.Gender gender = User.Gender.valueOf(spinner.getSelectedItem().toString().toUpperCase());
            User user = new User(userName, dob, gender, weight);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Long> genUid = executor.submit(() -> db.userDao().insert(user));
            SharedPreferences sharedPref = getActivity().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            try {
                editor.putLong(getString(R.string.current_user_key), genUid.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return;
            }
            editor.commit();
            activityCallback.onFragmentSelectionNoStack(new SuccessFragment());
        });
    }

    public void setOnFragmentSelectionListener(OnFragmentSelectionListener activity) {
        activityCallback = activity;
    }

    public interface OnFragmentSelectionListener {
        void onFragmentSelectionNoStack(Fragment fragment);
    }
}
