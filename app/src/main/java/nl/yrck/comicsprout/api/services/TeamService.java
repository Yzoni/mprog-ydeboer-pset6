package nl.yrck.comicsprout.api.services;
import nl.yrck.comicsprout.api.models.TeamResults;
import nl.yrck.comicsprout.api.models.TeamWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TeamService {
    @GET("team/{id}")
    Call<TeamWrapper> get(
            @Path("id") String id
    );
    @GET("teams")
    Call<TeamResults> list(
            @Query("sort") String sort
    );
}
