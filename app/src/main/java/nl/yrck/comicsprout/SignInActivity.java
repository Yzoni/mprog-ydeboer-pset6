/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.yrck.comicsprout.models.User;


/**
 * Handles signin and signup
 * <p>
 * Boilerplate code taken from
 * <p>
 * https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/
 * google/firebase/quickstart/database/SignInActivity.java
 */
public class SignInActivity extends BaseActivity {

    private static final String TAG = "SIGNIN_ACTIVITY";

    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.field_email);
        password = (EditText) findViewById(R.id.field_password);
        Button siginIn = (Button) findViewById(R.id.button_sign_in);
        Button signUp = (Button) findViewById(R.id.button_sign_up);

        siginIn.setOnClickListener((v) -> signIn());
        signUp.setOnClickListener((v) -> signUp());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            onAuthSuccess(firebaseAuth.getCurrentUser());
        }
    }

    @Override
    public void onBackPressed() {
        // Disallow back presses
    }

    /**
     * Handle sign in
     */
    private void signIn() {
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (task) -> {
                    hideProgressDialog();
                    if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                    } else {
                        Toast.makeText(SignInActivity.this, R.string.sign_in_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * handle sign up
     */
    private void signUp() {
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (task) -> {
                    hideProgressDialog();

                    if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                        startMainActivity();
                    } else {
                        Toast.makeText(SignInActivity.this, R.string.sign_up_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Write the new user to the database
     *
     * @param user
     */
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        writeNewUser(user.getUid(), username, user.getEmail());
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError(getString(R.string.signup_error_required));
            result = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError(getString(R.string.signup_error_required));
            result = false;
        } else {
            password.setError(null);
        }

        return result;
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        database.child("users").child(userId).setValue(user);
    }
}
