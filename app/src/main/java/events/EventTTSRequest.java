package events;

public class EventTTSRequest{

    private String text;
    public EventTTSRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
