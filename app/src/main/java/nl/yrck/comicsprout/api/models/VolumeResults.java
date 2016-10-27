/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api.models;


import java.util.List;

import nl.yrck.comicsprout.api.models.small.SmallIssue;
import nl.yrck.comicsprout.api.models.small.SmallIssueWithSite;
import nl.yrck.comicsprout.api.models.small.SmallModelWithApi;
import nl.yrck.comicsprout.api.models.small.SmallModelWithApiAndSite;

public class VolumeResults extends BasicResults {
    public List<SmallModelWithApiAndSite> characters;
    public List<SmallModelWithApiAndSite> concepts;
    public SmallIssue first_issue;
    public List<SmallIssueWithSite> issues;
    public SmallIssue last_issue;
    public SmallModelWithApi publisher;
    public String locations;
    public String objects;
}
