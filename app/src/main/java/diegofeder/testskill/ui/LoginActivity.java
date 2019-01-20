package diegofeder.testskill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import diegofeder.testskill.R;
import diegofeder.testskill.dao.UserDAO;
import diegofeder.testskill.domain.User;
import diegofeder.testskill.helper.FormHelper;
import diegofeder.testskill.utils.SharedPrefsUtil;

/**
 * A login screen that offers login via email/password and intent to register.
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPrefsUtil sharedPrefsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefsUtil = new SharedPrefsUtil();

        Button mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener((View v) -> {
            attemptLogin();
        });

        Button mRegisterButton = findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, RegisterEditActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Attempts to login the account specified by the form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        TextView wrongLoginTextView = findViewById(R.id.text_view_wrong_login);
        wrongLoginTextView.setVisibility(View.INVISIBLE);

        FormHelper formHelper = new FormHelper(this);
        View focusFaultView = formHelper.validateSignInForm();

        if (focusFaultView != null) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusFaultView.requestFocus();
        } else {
            // Perform the user login attempt.
            User user = formHelper.getUserFromSignInForm();
            UserDAO dao = new UserDAO(this);

            if (dao.findByEmailAndPassword(user.getEmail(), user.getPassword()) != null) {
                // User Email and Password found on database. Login successful
                sharedPrefsUtil.setSignedInUserId(this, dao.findByEmail(user.getEmail()).getId());
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);

            } else {
                // Email and/or password incorrect...
                wrongLoginTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}
