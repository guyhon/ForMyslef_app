package com.example.formyself;

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
