package com.foysaltech.navigationdrawertheme;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.foysaltech.navigationdrawertheme.Fragment.CategoriesFragment;
import com.foysaltech.navigationdrawertheme.Fragment.HomeFragment;
import com.foysaltech.navigationdrawertheme.Fragment.LoginFragment;
import com.foysaltech.navigationdrawertheme.Fragment.MissingPlaceFragment;
import com.foysaltech.navigationdrawertheme.Fragment.ProfileFragment;
import com.foysaltech.navigationdrawertheme.Fragment.SearchFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    Constant constant;
    SharedPreferences.Editor editor;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        constant.color = appColor;

        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setBackgroundColor(Constant.color);


        getSupportFragmentManager().beginTransaction().replace(R.id.containerId,
                new HomeFragment()).commit();


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(Constant.color);

        View header = navigationView.getHeaderView(0);
        RelativeLayout relativeLayout = header.findViewById(R.id.nav_header_id);
        relativeLayout.setBackgroundColor(Constant.color);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Profile) {
            Intent fb = new Intent(Intent.ACTION_VIEW);
            fb.setData(Uri.parse("https://www.Facebook.Com/AndroidAndComputerExpert"));
            startActivity(fb);

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out best News App at: https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] recipients = {"SmictFoysal@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Headline of problem");
            intent.putExtra(Intent.EXTRA_TEXT, "The details");
            intent.putExtra(Intent.EXTRA_CC, "SmictFoysal@gmail.com");
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));

        } else if (id == R.id.nav_rate) {
            Uri uri = Uri.parse("market://details?=" + MainActivity.this.getPackageName());
            Intent gotoMarket = new Intent(Intent.ACTION_VIEW, uri);
            gotoMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            try {
                startActivity(gotoMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            showFragment(fragment, "Home");
        } else if (id == R.id.nav_search) {
            fragment = new SearchFragment();
            showFragment(fragment, "Search");
        } else if (id == R.id.nav_all_categories) {
            fragment = new CategoriesFragment();
            showFragment(fragment, "Categories");
        } else if (id == R.id.addMissingPlace) {
            fragment = new MissingPlaceFragment();
            showFragment(fragment, "MissingPlace");
        } else if (id == R.id.nav_login) {
            fragment = new LoginFragment();
            showFragment(fragment, "Login");
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
            showFragment(fragment, "Profile");
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment, String title) {
        getSupportActionBar().setTitle(title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, fragment)
                .addToBackStack(null).commit();
    }
}