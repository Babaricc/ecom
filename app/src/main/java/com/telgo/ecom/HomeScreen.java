package com.telgo.ecom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import com.telgo.ecom.databinding.ActivityHomeScreenBinding;
import com.telgo.ecom.session.UserSession;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeScreenBinding binding;

    UserSession userSession;

    ImageView userImageView;
    TextView nameView, emailView;
    MaterialSearchBar searchBar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSession = new UserSession();
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);


        searchBar = binding.appBarHomeScreen.searchBar;
        searchBar.setOnSearchActionListener(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_orders, R.id.nav_slideshow).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_screen);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_logout) {
                    CustomDialog dialog = new CustomDialog(HomeScreen.this, "Logout", "Do you want to logout?", R.raw.logout, false);
                    dialog.show();
                    dialog.yeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userSession.clearSession();
                            finish();
                            startActivity(new Intent(HomeScreen.this, OTP_Screen.class));
                        }
                    });
                }

                NavigationUI.onNavDestinationSelected(item, navController);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen, menu);
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        userImageView = findViewById(R.id.user_image_view);
        nameView = findViewById(R.id.user_name_view);
        emailView = findViewById(R.id.user_email_view);

        if (userSession.getContact() != null && !userSession.getContact().isEmpty())
            emailView.setText("Contact:" + userSession.getContact());

        if (userSession.getName() != null && !userSession.getName().isEmpty())
            nameView.setText(userSession.getName());

        try {
            Picasso.get().load(userSession.getImageUri()).fit().centerCrop().into(userImageView);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.cropped).into(userImageView);
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_screen);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("item!!", "1:" + item.getItemId());

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        Toast.makeText(this, "asdfadsfdsf", Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d("item!!", "onNavigationItemSelected:" + item.getItemId());
        Log.d("item!!", "logout:" + R.id.nav_logout);

        int id = item.getItemId();
        Toast.makeText(getApplicationContext(), "asdfasfdsfds", Toast.LENGTH_LONG).show();
        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_slideshow) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(GravityCompat.START);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.closeSearch();
                break;
        }
    }
}