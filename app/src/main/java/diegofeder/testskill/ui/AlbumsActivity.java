package diegofeder.testskill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import diegofeder.testskill.interfaces.Api;
import diegofeder.testskill.R;
import diegofeder.testskill.adapter.AlbumAdapter;
import diegofeder.testskill.domain.Album;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        ListView albumsListView = findViewById(R.id.list_view_albums);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Album>> call = api.getAlbums();

        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(@NonNull Call<List<Album>> call, @NonNull Response<List<Album>> response) {
                List<Album> listAlbums = response.body();
                AlbumAdapter albumAdapter = new AlbumAdapter(listAlbums, AlbumsActivity.this);
                albumsListView.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Album>> call, @NonNull Throwable t) {
                Toast.makeText(AlbumsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.options_menu_albums:
                // already in albums
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
