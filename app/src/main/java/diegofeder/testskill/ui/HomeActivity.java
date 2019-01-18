package diegofeder.testskill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import diegofeder.testskill.R;
import diegofeder.testskill.dao.UserDAO;
import diegofeder.testskill.domain.User;
import diegofeder.testskill.utils.SharedPrefsUtil;

public class HomeActivity extends AppCompatActivity {

    private SharedPrefsUtil sharedPrefsUtil;
    private UserDAO dao;

    private ListView listViewUsers;
    private List<User> listUsers;
    private ArrayAdapter<User> usersListAdapter;

    private EditText filterEditText;
    private TextView textViewUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefsUtil = new SharedPrefsUtil();
        dao = new UserDAO(this);

        listViewUsers = findViewById(R.id.list_view_users);
        listUsers = dao.populateUserList();
        usersListAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1, listUsers);
        listViewUsers.setAdapter(usersListAdapter);

        registerForContextMenu(listViewUsers);
        listViewUsers.setOnItemClickListener((parent, view, position, id) -> openContextMenu(view));

        TextView textViewUserNameLabel = findViewById(R.id.text_view_user_name_label);
        textViewUserNameLabel.setText(R.string.label_user_name);
        textViewUserName = findViewById(R.id.text_view_user_name);
        textViewUserName.setText(dao.findById(sharedPrefsUtil.getSignedInUserId(this)).getName());

        filterEditText = findViewById(R.id.filter_edit_text);
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                HomeActivity.this.usersListAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        Button buttonNewUser = findViewById(R.id.button_new_user);
        buttonNewUser.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), RegisterEditActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewUserName.setText(dao.findById(sharedPrefsUtil.getSignedInUserId(this)).getName());
        new Handler().postDelayed(this::updateUserListView, 100);
        filterEditText.clearFocus();
        filterEditText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.options_menu_home:
                // already in home
                break;
            case R.id.options_menu_albums:
                Intent intent = new Intent(this, AlbumsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final User user = (User) listViewUsers.getItemAtPosition(info.position);

        MenuItem edit = menu.add("Edit");
        edit.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(this, RegisterEditActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            return false;
        });

        MenuItem delete = menu.add("Delete");
        delete.setOnMenuItemClickListener(item -> {
            deleteConfirmation(user);
            new Handler().postDelayed(this::updateUserListView, 100);
            return false;
        });
    }

    private void deleteConfirmation(User user) {

        MaterialDialog mDialog;
        if (sharedPrefsUtil.getSignedInUserId(this).equals(user.getId())) {
            mDialog = new MaterialDialog.Builder(this)
                    .title("Error Deleting Item")
                    .content("You cannot delete the logged user.")
                    .cancelable(false)
                    .positiveText("OK")
                    .show();
        } else {
            mDialog = new MaterialDialog.Builder(this)
                    .title("Delete Confirmation")
                    .content("Are you sure you want to delete this user?")
                    .cancelable(false)
                    .positiveText("OK")
                    .negativeText("Cancel")
                    .onPositive((dialog, which) -> {
                        UserDAO dao = new UserDAO(this);
                        dao.delete(user);
                        updateUserListView();
                    })
                    .show();
        }
    }

    private void updateUserListView() {
        listUsers = dao.populateUserList();
        usersListAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1, listUsers);
        listViewUsers.setAdapter(usersListAdapter);
    }

}
