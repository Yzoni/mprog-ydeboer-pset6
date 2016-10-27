/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api.services;

import nl.yrck.comicsprout.api.models.search.SearchWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("search")
    Call<SearchWrapper> query(
            @Query("query") String query,
            @Query("offset") String offset,
            @Query("field_list") String field_list,
            @Query("limit") String limit,
            @Query("resources") String resources
    );
}
