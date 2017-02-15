package com.softserve.if072.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyndyn on 15.02.2017.
 */
public class FormForCart {
    private List<Cart> carts;
    private List<Integer> checkbox;

    public FormForCart(){
        carts = new ArrayList<Cart>();
        checkbox = new ArrayList<Integer>();
    }

    public FormForCart(int size){
        carts = new ArrayList<Cart>();
        for (int i = 0; i <size ; i++){
            Cart cart = new Cart();
            cart.setProduct(new Product());
            cart.setStore(new Store());
            carts.add(cart);
        }
        checkbox = new ArrayList<Integer>();

        for (int i = 0; i <size ; i++){
            checkbox.add(0);
        }
    }

    public List<Integer> getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(List<Integer> checkbox) {
        this.checkbox = checkbox;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FormForCart{");
        for(Cart cart : carts){
            sb.append("Cart{productId=" + cart.getProduct().getId());
            sb.append(", storeId=" + cart.getStore().getId());
            sb.append(", amount=" + cart.getAmount() + "}");
        }
        return sb.append(", checkbox=" + checkbox +
                '}').toString();
    }
}
