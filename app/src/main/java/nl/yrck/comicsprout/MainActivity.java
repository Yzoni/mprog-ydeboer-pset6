package nl.yrck.comicsprout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import nl.yrck.comicsprout.dialogs.AboutDialog;
import nl.yrck.comicsprout.fragments.CharacterListFragment;
import nl.yrck.comicsprout.fragments.IssueListFragment;
import nl.yrck.comicsprout.fragments.ListFragmentInteraction;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ListFragmentInteraction {

    private static String TAG = "MAIN_ACTIVITY";

    Button authButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get the top part of the sidebar
        View header = navigationView.getHeaderView(0);

        authButton = (Button) header.findViewById(R.id.signin_button);
        TextView textView = (TextView) header.findViewById(R.id.username);

        // Handle sign in and out button clicks
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            authButton.setOnClickListener((v) -> launchSignIn());
            authButton.setText("SignIn");
        } else {
            textView.setText(user.getEmail());
            authButton.setText("SignOut");
            authButton.setOnClickListener((v) -> launchSignOut());
        }

        // Sign the user in or load default fragment on app creation
        if (user == null) {
            launchSignIn();
        } else {
            handleFragmentSwap(CharacterListFragment.class);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_about) {
            AboutDialog.get(this);
            return true;
        }
        if (id == R.id.action_search) {
            if (isOnline()) {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            } else {
                    Toast.makeText(this, "No internet",
                            Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_character) {
            handleFragmentSwap(CharacterListFragment.class);
        } else if (id == R.id.nav_issue) {
            handleFragmentSwap(IssueListFragment.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Swaps a the fragmen in the framelayout with a other fragment class
     *
     * @param fragmentClass
     */
    private void handleFragmentSwap(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "Failed to swap fragment");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    private void launchSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
    private void launchSignOut() {
        FirebaseAuth.getInstance().signOut();
    }


    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
