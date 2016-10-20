package nl.yrck.comicsprout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.yrck.comicsprout.api.ComicVine;


public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private static ComicVine comicVine;
    private boolean isOnline;
    static boolean firebaseDatabaseInitialized = false;

    public BaseActivity() {

        // Only try to enable persistence once on app start
        if (!firebaseDatabaseInitialized) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            firebaseDatabaseInitialized = true;
        }
        setConnectedStatusListner();
    }


    /**
     * Gets the comicvine api instance
     *
     * @return ComicVine instance
     */
    public static ComicVine getComicVine() {
        if (comicVine == null) {
            comicVine = new ComicVine("2c8dc0c72fcc6e2fb02e1c7cca94b3df4a59cf5e");
        }
        return comicVine;
    }

    /**
     * Show app wide progress dialog
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        progressDialog.show();
    }

    /**
     * Hide the app wide progress dialog
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private void setConnectedStatusListner() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                isOnline = snapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("ComicSprout", "Connected status listner was cancelled");
            }
        });
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
