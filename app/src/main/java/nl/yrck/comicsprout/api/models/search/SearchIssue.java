package nl.yrck.comicsprout.api.models.search;

import java.io.Serializable;

import nl.yrck.comicsprout.api.models.IssueResults;

public class SearchIssue extends IssueResults implements Serializable {
    public String resource_type;
}
