package events;

/**
 * Created by Hossein on 12/27/2016.
 */

public class EventPlaySound {

    private final String fileName;

    public EventPlaySound(String fileName) {
        this.fileName = fileName ;
    }

    public String getFileName() {
        return fileName;
    }

}
