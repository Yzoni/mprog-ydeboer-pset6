package nl.yrck.comicsprout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.yrck.comicsprout.dialogs.AboutDialog;
import nl.yrck.comicsprout.fragments.CharacterDetailFragment;
import nl.yrck.comicsprout.fragments.IssueDetailFragment;
import nl.yrck.comicsprout.models.SavedItem;
import nl.yrck.comicsprout.models.User;

public class DetailActivity extends BaseActivity
        implements View.OnClickListener {

    public static String TAG = "DETAIL_ACTIVITY";

    String detailId;
    String detailTitle;
    String detailType;

    public static final String EXTRA_DETAIL_ID = "detail_id";
    public static final String EXTRA_DETAIL_TITLE = "detail_title";
    public static final String EXTRA_DETAIL_TYPE = "detail_type";

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        detailId = getIntent().getStringExtra(EXTRA_DETAIL_ID);
        detailTitle = getIntent().getStringExtra(EXTRA_DETAIL_TITLE);
        detailType = getIntent().getStringExtra(EXTRA_DETAIL_TYPE);

        if (detailId == null) {
            throw new IllegalArgumentException("Must pass EXTRA_DETAIL_ID");
        } else if (detailTitle == null) {
            throw new IllegalArgumentException("Must pass EXTRA_DETAIL_TITLE");
        } else if (detailType == null) {
            throw new IllegalArgumentException("Must pass EXTRA_DETAIL_TYPE");
        }

        getSupportActionBar().setTitle(detailTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment detailFragment = null;
        switch (detailType) {
            case "character":
                detailFragment = CharacterDetailFragment.newInstance(detailId);
                break;
            case "issue":
                detailFragment = IssueDetailFragment.newInstance(detailId);
                break;
        }

        // Swap the details fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, detailFragment).commit();

        // Set the status color and icon on the FAB button depending on the status
        setFabStatus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                onSave();
                break;
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
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_about) {
            AboutDialog.get(this);
            return true;
        }
        if (id == R.id.action_search) {
            if (isOnline()) {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSave() {
        final String userId = getUid();
        DatabaseReference userItemReference = FirebaseDatabase.getInstance().getReference()
                .child(detailType).child(userId);
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child("users").child(userId).getValue(User.class);
                        if (user == null) {
                            Toast.makeText(DetailActivity.this,
                                    "Could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (dataSnapshot.child(detailType).child(userId).hasChild(detailId)) {
                                userItemReference.child(detailId).removeValue();
                            } else {
                                SavedItem savedItem = new SavedItem(detailId, detailTitle);
                                userItemReference.child(detailId).setValue(savedItem);
                            }
                            setFabStatus();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("sl", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    private void setFabStatus() {
        final String userId = getUid();
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child("users").child(userId).getValue(User.class);
                        if (user == null) {
                            Toast.makeText(DetailActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (dataSnapshot.child(detailType).child(userId).hasChild(detailId)) {
                                fab.setBackgroundTintList(
                                        ColorStateList.valueOf(ContextCompat.getColor(
                                                getApplicationContext(), R.color.colorRemove)));
                                fab.setImageDrawable(
                                        ContextCompat.getDrawable(
                                                getApplicationContext(), R.drawable.ic_remove_white_24px));
                            } else {
                                fab.setBackgroundTintList(
                                        ColorStateList.valueOf(ContextCompat.getColor(
                                                getApplicationContext(), R.color.colorAdd)));
                                fab.setImageDrawable(
                                        ContextCompat.getDrawable(
                                                getApplicationContext(), R.drawable.ic_add_white_24px));

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "E " + databaseError.toException());
                    }
                });
    }


}
