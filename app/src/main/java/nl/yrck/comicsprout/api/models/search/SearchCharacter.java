/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api.models.search;

import java.io.Serializable;

import nl.yrck.comicsprout.api.models.CharacterResultsBasic;

public class SearchCharacter extends CharacterResultsBasic implements Serializable {
    public String resource_type;
}
