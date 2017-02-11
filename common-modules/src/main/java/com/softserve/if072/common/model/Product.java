package com.softserve.if072.common.model;

import java.util.List;

/**
 * The class contains information about current product and
 * about the stores where this product is available for purchase
 *
 * @author Vitaliy Malisevych
 */

public class Product {
    private int id;
    private String name;
    private String description;
    private Image image;
    private User user;
    private Category category;
    private Unit unit;
    private boolean isEnabled;
    private List<Store> stores;

    public Product() {
    }

    public Product(int id, String name, String description, Image image, User user, Category category, Unit unit, boolean isEnabled, List<Store> stores) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.user = user;
        this.category = category;
        this.unit = unit;
        this.isEnabled = isEnabled;
        this.stores = stores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", user=" + user +
                ", category=" + category +
                ", unit=" + unit +
                ", isEnabled=" + isEnabled +
                ", stores=" + stores + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}