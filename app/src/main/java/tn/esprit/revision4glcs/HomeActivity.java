package tn.esprit.revision4glcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import tn.esprit.revision4glcs.fragment.FavsFragment;
import tn.esprit.revision4glcs.fragment.ProfileFragment;
import tn.esprit.revision4glcs.fragment.RentFragment;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUserName;
    private Button btnLogout, btnRent, btnProfile, btnFavs;

    private SharedPreferences mPref;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tvUserName = findViewById(R.id.tvUserName);
        btnLogout = findViewById(R.id.btnLogout);
        btnRent = findViewById(R.id.btnRent);
        btnProfile = findViewById(R.id.btnProfile);
        btnFavs = findViewById(R.id.btnFavs);

        mPref = getSharedPreferences("tn.esprit.revision4glcs", MODE_PRIVATE);

        tvUserName.setText(mPref.getString("USER",""));

        btnLogout.setOnClickListener(view ->{

            mPref.edit().clear().apply();
            finish();

        });

        btnRent.setOnClickListener(view ->{
            changeFragment(new RentFragment());
        });

        btnProfile.setOnClickListener(view ->{
            changeFragment(new ProfileFragment());
        });

        btnFavs.setOnClickListener(view ->{
            changeFragment(new FavsFragment());
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new RentFragment())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutoolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.info:
                mPref.edit().clear().apply();
                finish();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}