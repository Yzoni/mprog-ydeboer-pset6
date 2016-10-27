/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api.models;


import java.io.Serializable;

import nl.yrck.comicsprout.api.models.small.SmallIssue;
import nl.yrck.comicsprout.api.models.small.SmallModelWithApi;

public class CharacterResultsBasic extends BasicResults implements Serializable {
    public String birth;
    public int count_of_issue_appearances;
    public SmallIssue first_appeared_in_issue;
    public int gender;
    public String real_name;
    public SmallModelWithApi origin;
    public SmallModelWithApi publisher;

    public CharacterResultsBasic() {
        type = "character";
    }

}
