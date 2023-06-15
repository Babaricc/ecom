package com.telgo.ecom.models;

public class Sliders {
    int id, isDeleted;
    String title, caption, image;

    public Sliders() {
    }

    public Sliders(int id, String title, String caption, String image, int isDeleted) {
        this.id = id;
        this.title = title;
        this.caption = caption;
        this.image = image;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int isDeleted() {
        return isDeleted;
    }

    public void setDeleted(int deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Sliders{" +
                "id=" + id +
                ", isDeleted=" + isDeleted +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
