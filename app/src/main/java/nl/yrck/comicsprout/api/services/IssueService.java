package nl.yrck.comicsprout.api.services;

import nl.yrck.comicsprout.api.models.CharacterWrapper;
import nl.yrck.comicsprout.api.models.IssueWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IssueService {
    @GET("issue/{id}")
    Call<IssueWrapper> get(
            @Path("id") String id
    );
}
