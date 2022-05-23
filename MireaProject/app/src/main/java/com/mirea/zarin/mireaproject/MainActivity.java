package com.mirea.zarin.mireaproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mirea.zarin.mireaproject.databinding.ActivityMainBinding;
import com.mirea.zarin.mireaproject.db.App;
import com.mirea.zarin.mireaproject.db.AppDatabase;
import com.mirea.zarin.mireaproject.practice6.Settings;

public class MainActivity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private final String SAVED_NAME = "saved_name";
    private final String SAVED_GROUP = "saved_group";

    private TextView navName;
    private TextView navGroup;

    private NavigationView navigationView;
    private View headerView;

    private SharedPreferences preferences;

    private String TAG = MainActivity.class.getSimpleName();

    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.calculator, R.id.web, R.id.musicFragment, R.id.sensors, R.id.camera, R.id.audioRecorder, R.id.stories, R.id.dogFragment, R.id.photosFragment, R.id.homeFragment, R.id.mapFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        this.headerView = navigationView.getHeaderView(0);

        this.navName = (TextView) this.headerView.findViewById(R.id.headerName);
        this.navGroup = (TextView) this.headerView.findViewById(R.id.headerGroup);

        load();

        db = App.getInstance().getDatabase();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 228 && resultCode == RESULT_OK)
        {
            String name = data.getExtras().getString("name", getStringRes(R.string.nav_header_title));
            String group = data.getExtras().getString("group", getStringRes(R.string.nav_header_subtitle));

            this.navName.setText(name);
            this.navGroup.setText(group);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_settings)
        {
            openSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getStringRes(int id)
    {
        return getResources().getString(id);
    }

    private void load()
    {
        this.preferences = getSharedPreferences("user", MODE_PRIVATE);

        String name = this.preferences.getString(SAVED_NAME, getStringRes(R.string.nav_header_title));
        String group = this.preferences.getString(SAVED_GROUP, getStringRes(R.string.nav_header_subtitle));

        this.navName.setText(name);
        this.navGroup.setText(group);
    }

    private void openSettings()
    {
        Intent intent = new Intent(this, Settings.class);
        startActivityForResult(intent, 228);
    }

}

