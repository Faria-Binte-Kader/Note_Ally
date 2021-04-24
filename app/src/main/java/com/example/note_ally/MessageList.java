package com.example.note_ally;

public class MessageList {

    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageList(String id,String name) {
        this.id = id;
        this.name = name;
    }

    public MessageList(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
