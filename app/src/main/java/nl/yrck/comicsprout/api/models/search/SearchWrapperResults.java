package nl.yrck.comicsprout.api.models.search;

import org.simpleframework.xml.ElementList;

import java.util.List;

import nl.yrck.comicsprout.api.models.Response;

public class SearchWrapperResults extends Response {

    @ElementList(entry = "character", inline = true, required = false)
    public List<SearchCharacter> characters;

    @ElementList(entry = "issue", inline = true, required = false)
    public List<SearchIssue> issues;

    @ElementList(entry = "episode", inline = true, required = false)
    public List<SearchEpisode> episodes;

    @ElementList(entry = "team", inline = true, required = false)
    public List<SearchTeam> teams;
}
