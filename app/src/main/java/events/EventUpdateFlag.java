package events;

/**
 * Created by Hossein on 12/26/2016.
 */

public class EventUpdateFlag {

    private final String vocabulary;
    private final String definition;

    public EventUpdateFlag(String vocabulary , String definition) {
        this.vocabulary = vocabulary ;
        this.definition = definition ;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public String getDefinition() {
        return definition;
    }
}
