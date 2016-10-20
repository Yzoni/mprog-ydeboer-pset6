package nl.yrck.comicsprout.api.models;


import java.io.Serializable;
import java.util.List;

import nl.yrck.comicsprout.api.models.small.SmallModelWithApi;
import nl.yrck.comicsprout.api.models.small.SmallModelWithApiAndSite;

public class CharacterResults extends CharacterResultsBasic implements Serializable {
    public List<SmallModelWithApiAndSite> character_enemies;
    public List<SmallModelWithApiAndSite> character_friends;
    public List<SmallModelWithApiAndSite> creators;
    public List<SmallModelWithApiAndSite> issue_credits;
    public List<SmallModelWithApiAndSite> issues_died_in;
    public List<SmallModelWithApiAndSite> movies;
    public List<SmallModelWithApi> powers;
    public List<SmallModelWithApiAndSite> story_arc_credits;
    public List<SmallModelWithApiAndSite> team_enemies;
    public List<SmallModelWithApiAndSite> team_friends;
    public List<SmallModelWithApiAndSite> teams;
    public List<SmallModelWithApiAndSite> volume_credits;
}
