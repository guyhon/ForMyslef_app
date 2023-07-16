package com.example.formyself.Object;

public class Chat {
    private String title = "";

    private String numInDB;
    private String image;

    private TextList textList;



    public Chat(String name){
        this.title =name;
        this.textList = new TextList();
        this.image ="";
    }

    public void setTextList(TextList textList) {
        this.textList = textList;
    }

    public String getTitle() {
        return title;
    }

    public TextList getTextList() {
        return textList;
    }

    public String getImage() {
        return image;
    }

    public Chat setImage(String image) {
        this.image = image;
        return this;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumInDB() {
        return numInDB;
    }

    public void setNumInDB(String numInDB) {
        this.numInDB = numInDB;
    }

    //    public String toString() {
//        return "Chat{" +
//                "title='" + title + '\'' +
//                ", image='" + image + '\'' +
//                '}';
//    }
}
