package diegofeder.testskill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import diegofeder.testskill.R;
import diegofeder.testskill.dao.UserDAO;
import diegofeder.testskill.domain.User;
import diegofeder.testskill.helper.FormHelper;
import diegofeder.testskill.utils.SharedPrefsUtil;


public class RegisterEditActivity extends AppCompatActivity {

    private FormHelper formHelper;
    private UserDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        formHelper = new FormHelper(this);
        dao = new UserDAO(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        Button mRegisterSaveButton = findViewById(R.id.register_button);
        mRegisterSaveButton.setOnClickListener((View v) -> {
            // Attempt to Edit or Register an user
            if (user != null) {
                attemptEdit();
            } else {
                attemptRegister();
            }
        });

        formHelper = new FormHelper(this);

        // Update Form Edit Texts and Button Text
        if (user != null) {
            // Edit
            formHelper.fillOutForm(user);
            formHelper.toggleEmailInputEditable();
            setTitle(R.string.activity_edit);
            mRegisterSaveButton.setText(R.string.action_save);
        } else {
            // Register
            formHelper.cleanForm();
            mRegisterSaveButton.setText(R.string.action_register);
        }
    }

    private void attemptRegister() {

        View focusFaultView = formHelper.validateRegisterForm();

        if (focusFaultView != null) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusFaultView.requestFocus();
        } else {
            // Perform the user login attempt.
            User user = formHelper.getUserFromRegisterForm();
            dao.insert(user);

            // User was successfully registered
            Toast.makeText(this, "User: " + user.getName() + " registered.", Toast.LENGTH_SHORT).show();
            // Save new user id to Shared Preferences
            SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil();
            sharedPrefsUtil.setSignedInUserId(this, dao.findByEmail(user.getEmail()).getId());
            // Skip login, go directly to Home as a new account
            Intent intent = new Intent(RegisterEditActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void attemptEdit() {
        View focusFaultView = formHelper.validateRegisterForm();
        if (focusFaultView != null) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusFaultView.requestFocus();
        } else {
            User user = formHelper.getUserFromRegisterForm();
            dao.update(user);

            Toast.makeText(this, "User: " + user.getName() + " saved.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}