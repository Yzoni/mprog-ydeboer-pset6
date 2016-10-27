/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api.services;

import nl.yrck.comicsprout.api.models.CharacterWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharacterService {
    @GET("character/{id}")
    Call<CharacterWrapper> get(
            @Path("id") String id
    );
}
