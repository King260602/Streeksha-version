package com.makapps.streeksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makapps.streeksha.Fragments.HomeFragment;
import com.makapps.streeksha.Fragments.ProfileFragment;

import static com.makapps.streeksha.Utilities.Constants.MY_PERMISSIONS_REQUEST_SEND_SMS;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:{
                    HomeFragment homeFragment = HomeFragment.newInstance();
                    loadFragment(homeFragment);
                    return true;
                }
                case R.id.nav_profile:{
                    ProfileFragment profileFragment = ProfileFragment.newInstance();
                    loadFragment(profileFragment);
                    return true;
                }
                case R.id.nav_settings:{
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bot_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.root_home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
