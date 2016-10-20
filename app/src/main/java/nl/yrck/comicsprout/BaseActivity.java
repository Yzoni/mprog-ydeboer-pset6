package nl.yrck.comicsprout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
        if (!firebaseDatabaseInitialized) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            firebaseDatabaseInitialized = true;
        }
        setConnectedStatusListner();
    }

    public static ComicVine getComicVine() {
        if (comicVine == null) {
            ////
            SET COMICVINE API KEY HERE
                    ////
            comicVine = new ComicVine("HERE");
        }
        return comicVine;
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        progressDialog.show();
    }

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
                System.err.println("Online listener was cancelled");
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
