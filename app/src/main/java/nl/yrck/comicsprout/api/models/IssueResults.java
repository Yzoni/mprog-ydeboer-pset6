/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api.models;


import java.util.List;

import nl.yrck.comicsprout.api.models.small.SmallModelWithApiAndSite;

public class IssueResults extends BasicResults {
    public String cover_date;
    public String has_staff_review;
    public int issue_number;
    public String store_date;
    public List<SmallModelWithApiAndSite> volume;

    public IssueResults() {
        type = "issue";
    }
}
