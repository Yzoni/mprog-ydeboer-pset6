/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api.models;


import nl.yrck.comicsprout.api.models.small.SmallModelWithApiAndSite;

public class EpisodeResults extends BasicResults {
    public String air_date;
    public String has_staff_review;
    public int episode_number;
    public String site_detail_url;
    public SmallModelWithApiAndSite series;
}
