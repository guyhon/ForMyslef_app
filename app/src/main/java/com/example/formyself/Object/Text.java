package com.example.formyself.Object;

public class Text {

    private String text ="";


    public Text(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return "text{" +
                "title='" + text + '\'' +
                '}';
    }
}
