package com.telgo.ecom.models;


import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Product implements Serializable, Item {
    private int catId, id, stock, sold, reviews, quantity;
    private double price, rating, discount, total;
    private String title, description, image;

    String colors;

    public Product() {
    }

    public Product(int catId, int id, int stock, int sold, double price, double rating, double discount, String title, String description, String image, String colors) {
        this.catId = catId;
        this.id = id;
        this.stock = stock;
        this.sold = sold;
        this.reviews = reviews;
        this.price = price;
        this.rating = rating;
        this.discount = discount;
        this.title = title;
        this.description = description;
        this.image = image;
        this.colors = colors;
    }


    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "Product{" + "catId11=" + catId + ", id=" + id + ", stock=" + stock + ", sold=" + sold + ", reviews=" + reviews + ", price=" + price + ", rating=" + rating + ", discount=" + discount + ", title='" + title + '\'' + ", description='" + description + '\'' + ", image='" + image + '\'' + ", colors=" + colors + '}';
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getItemPrice() {
        return BigDecimal.valueOf(getPrice());
    }

    @Override
    public String getItemName() {
        return getItemName();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
