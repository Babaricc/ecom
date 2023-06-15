package com.telgo.ecom.models;

public class Category {
    private int id, seq;
    private  String title, image, description, background;

    public Category() {
    }

    public Category(int id, int seq, String title, String image, String description, String background) {
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.image = image;
        this.description = description;
        this.background = background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
