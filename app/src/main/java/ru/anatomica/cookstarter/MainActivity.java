package ru.anatomica.cookstarter;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private HttpClients httpClients;
    private ButtonsCreate buttonsCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(() -> {
            if(getFragmentManager().getBackStackEntryCount() == 0)
                finish();
        });
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.navigation_home, R.id.navigation_restaurants, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        buttonsCreate = new ButtonsCreate(this);
        httpClients = new HttpClients(this, buttonsCreate);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            // additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void getRequest(String type, Long id) {
        httpClients.getRequest(type, id);
    }

    public void postRequest(String request)  {
        httpClients.postRequest(request);
    }

    public void setStatus()  {
        httpClients.setStatus();
    }

    public void getFilter(String query) {
        buttonsCreate.restaurantsAdapter.getFilter().filter(query);
    }

    public void reloadCartTable() {
        buttonsCreate.reloadCartTable();
    }

    public void serviceMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void shutdown() {
        System.exit(0);
    }
}