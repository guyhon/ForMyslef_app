package com.example.formyself.Object;

import java.util.ArrayList;

public class TextList {

    private ArrayList<Text> texts = new ArrayList<>();

    public TextList() {    }

    public ArrayList<Text> getTexts() {
        return texts;
    }

    public TextList setText(ArrayList<Text> texts) {
        this.texts = texts;
        return this;
    }


    public void addText(Text text){
        this.texts.add(text);
    }

    @Override
    public String toString() {
        return "TextList{" +
                "texts=" + texts.toString() +
                '}';
    }
}
