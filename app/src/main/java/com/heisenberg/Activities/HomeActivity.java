package com.heisenberg.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.heisenberg.Fragments.ContestFragment;
import com.heisenberg.Fragments.DiscoverFragment;
import com.heisenberg.Fragments.PracticeFragment;
import com.heisenberg.R;
import com.heisenberg.Fragments.YouFragment;
import com.heisenberg.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Don't add first fragment to backstack
        final Fragment contestFragment=new ContestFragment();
        final Fragment practiceFragment=new PracticeFragment();
        final Fragment youFragment=new YouFragment();
        final Fragment discoverFragment=new DiscoverFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,contestFragment).commit();

         bottomNavigationView=findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment=null;
                if(bottomNavigationView.getSelectedItemId()==menuItem.getItemId())
                    return true;
                String TAG="";
                switch (menuItem.getItemId()){
                    case R.id.contests: fragment=contestFragment;TAG="contest";
                                        break;
                    case R.id.practice: fragment=practiceFragment;TAG="practice";
                                        break;
                    case R.id.you: fragment=youFragment;TAG="you"; break;
                    case R.id.discover: fragment=discoverFragment;TAG="discover"; break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
                return true;
            }
        });

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    public void goToSettings(View view){
        Intent intent=new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
