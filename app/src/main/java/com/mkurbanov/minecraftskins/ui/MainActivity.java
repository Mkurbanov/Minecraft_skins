package com.mkurbanov.minecraftskins.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mkurbanov.minecraftskins.R;
import com.mkurbanov.minecraftskins.ui.favorites.FavFragment;
import com.mkurbanov.minecraftskins.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_mods, R.id.navigation_favs)
                .build();
        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);*/

        addFragment(R.id.fl, FavFragment.getInstance());
        addFragment(R.id.fl, HomeFragment.getInstance());

        navView.setItemIconTintList(null);
        navView.setOnNavigationItemReselectedListener(item -> {
        });
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_mods:
                    changeFragment(R.id.fl, HomeFragment.getInstance());
                    return true;
                case R.id.navigation_favs:
                    changeFragment(R.id.fl, FavFragment.getInstance());
                    return true;
            }
            return false;
        });
    }

    private void addFragment(int frame, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(frame, fragment)
                .commit();
    }

    public void changeFragment(int frame, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(frame, fragment)
                .commit();
    }
}