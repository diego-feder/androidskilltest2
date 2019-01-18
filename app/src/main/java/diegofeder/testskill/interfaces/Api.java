package diegofeder.testskill.interfaces;

import java.util.List;

import diegofeder.testskill.domain.Album;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("photos")
    Call<List<Album>> getAlbums();
}
