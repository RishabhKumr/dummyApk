package com.example.theplayschool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.stats.StatsUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import Model.Chats;

public class Student_Dashboard extends AppCompatActivity implements SubjectSelectDialogFragment.SubjectSelectListener{
    public GoogleApiClient googleApiClient;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
   FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       ;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
               R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
               .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

      //  toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        //drawerLayout.addDrawerListener(toggle);
       // toggle.setDrawerIndicatorEnabled(true);
      //  toggle.syncState();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.navigation_drawer_profile, menu);
        getMenuInflater().inflate(R.menu.navigation_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            drawerLayout.openDrawer(Gravity.START);

        }

        else if (id == R.id.logout) {
            if(firebaseUser!=null)
            {
                FirebaseAuth.getInstance().signOut();
            }
            startActivity(new Intent(Student_Dashboard.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        }
        else if(id == R.id.start_Chat)
        {
            startActivity(new Intent(Student_Dashboard.this, ChatActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    String subjectselected="";
    @Override
    public void onPositiveButtonClicked(String[] subject_list, int Position) {
        //get the selected subject name and pass it to an intent.
        subjectselected=subject_list[Position];
        Log.d("main", "onPositiveButtonClicked: "+subjectselected);
        Intent intent=new Intent(Student_Dashboard.this,TestCreateActivity.class);
        intent.putExtra("Subjectname",subjectselected);
        //start the TestCreateActivity to take other details
        startActivity(intent);
    }

    //implement methods from SubjectSelectListener interface negative button clicked
    @Override
    public void onNegativeButtonClicked() {

    }
}

