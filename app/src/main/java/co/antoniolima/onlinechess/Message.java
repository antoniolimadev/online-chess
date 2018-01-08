package co.antoniolima.onlinechess;

import java.io.Serializable;

public class Message implements Serializable{

    String  text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
