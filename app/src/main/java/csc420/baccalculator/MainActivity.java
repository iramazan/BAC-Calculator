package csc420.baccalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements SettingsFragment.OnFragmentSelectionListener,
                                                               NewUserFragment.OnFragmentSelectionListener,
                                                               ProfileFragment.OnFragmentSelectionListener,
                                                               FavoritesPageFragment.OnFragmentSelectionListener,
                                                               HomeFragment.OnFragmentSelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create actionbar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        // load initial fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment hf = new HomeFragment();
        ft.replace(R.id.frag_holder, hf);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.menu_home:
                HomeFragment hf = new HomeFragment();
                ft.replace(R.id.frag_holder, hf);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            case R.id.menu_profile:
                ProfileFragment pf = new ProfileFragment();
                ft.replace(R.id.frag_holder, pf);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            case R.id.menu_contacts:
                ContactsFragment cf = new ContactsFragment();
                ft.replace(R.id.frag_holder, cf);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            case R.id.menu_settings:
                SettingsFragment sf = new SettingsFragment();
                ft.replace(R.id.frag_holder, sf);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            default:
                // Not a recognized id
                ft.commit();
                return false;
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof SettingsFragment) {
            ((SettingsFragment) fragment).setOnFragmentSelectionListener(this);
        } else if (fragment instanceof NewUserFragment) {
            ((NewUserFragment) fragment).setOnFragmentSelectionListener(this);
        } else if (fragment instanceof ProfileFragment) {
            ((ProfileFragment) fragment).setOnFragmentSelectionListener(this);
        } else if (fragment instanceof FavoritesPageFragment) {
            ((FavoritesPageFragment) fragment).setOnFragmentSelectionListener(this);
        } else if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).setOnFragmentSelection(this);
        }
    }

    @Override
    public void onFragmentSelection(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_holder, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFragmentSelectionNoStack(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_holder, fragment);
        ft.commit();
    }
}
