package com.example.formyself;

import java.util.ArrayList;
import java.util.Collections;

public class ChatList {
    private String name = "";

    private ArrayList<Chat> chats = new ArrayList<>();

    public ChatList() {    }

    public String getName() {
        return name;
    }

    public ChatList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public ChatList setChats(ArrayList<Chat> chats) {
        this.chats = chats;
        return this;
    }


    public void addChat(Chat chat){
        this.chats.add(chat);
    }

    @Override
    public String toString() {
        return "ChatList{" +
                "name='" + name + '\'' +
                ", chats=" + chats.toString() +
                '}';
    }
}
