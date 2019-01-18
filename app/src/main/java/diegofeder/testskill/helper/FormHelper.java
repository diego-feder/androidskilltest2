package diegofeder.testskill.helper;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import diegofeder.testskill.R;
import diegofeder.testskill.domain.User;

public class FormHelper {

    private EditText mLoginView;
    private EditText mNameView;
    private EditText mPasswordView;
    private Activity activity;
    private User user;

    public FormHelper(Activity activity) {
        mLoginView = activity.findViewById(R.id.login_edit_text);
        mNameView = activity.findViewById(R.id.name_edit_text);
        mPasswordView = activity.findViewById(R.id.password_edit_text);
        this.activity = activity;
        this.user = new User();
    }

    public View validateSignInForm() {
        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of login attempt.
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(activity.getString(R.string.error_field_required));
            focusView = mPasswordView;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(activity.getString(R.string.error_invalid_password));
            focusView = mPasswordView;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(activity.getString(R.string.error_field_required));
            focusView = mLoginView;
        } else if (!isEmailValid(login)) {
            mLoginView.setError(activity.getString(R.string.error_invalid_email));
            focusView = mLoginView;
        }

        return focusView;
    }

    public View validateRegisterForm() {
        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String login = mLoginView.getText().toString();
        String name = mNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(activity.getString(R.string.error_field_required));
            focusView = mPasswordView;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(activity.getString(R.string.error_invalid_password));
            focusView = mPasswordView;
        }

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(activity.getString(R.string.error_field_required));
            focusView = mNameView;
        } else if (!isNameValid(name)) {
            mNameView.setError(activity.getString(R.string.error_invalid_name));
            focusView = mNameView;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(activity.getString(R.string.error_field_required));
            focusView = mLoginView;
        } else if (!isEmailValid(login)) {
            mLoginView.setError(activity.getString(R.string.error_invalid_email));
            focusView = mLoginView;
        }

        return focusView;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isNameValid(String name) {
        return name.length() > 2;
    }

    private boolean isEmailValid(String login) {
        return login.contains("@") && login.contains(".");
    }

    public User getUserFromRegisterForm() {
        user.setEmail(mLoginView.getText().toString().toLowerCase());
        user.setPassword(mPasswordView.getText().toString());
        user.setName(mNameView.getText().toString());

        return user;
    }

    public User getUserFromSignInForm() {
        user.setEmail(mLoginView.getText().toString().toLowerCase());
        user.setPassword(mPasswordView.getText().toString());

        return user;
    }

    public void fillOutForm(User user) {
        mLoginView.setText(user.getEmail());
        mNameView.setText(user.getName());
        mPasswordView.setText(user.getPassword());

        this.user = user;
    }

    public void cleanForm() {
        mLoginView.setText("");
        mNameView.setText("");
        mPasswordView.setText("");
    }

    public void toggleEmailInputEditable() {
        mLoginView.setFocusable(!mLoginView.isFocusable());
        mLoginView.setEnabled(false);
    }

}
