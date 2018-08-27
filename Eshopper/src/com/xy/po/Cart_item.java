package com.xy.po;

public class Cart_item {
    private int cart_item_id;
    private int cart_id;
    private int user_id;
    private int product_id;
    private int product_quantity;

    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getUser_id(){
        return user_id;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public Cart_item(){}

    public Cart_item(int cart_item_id, int cart_id, int user_id, int product_id, int product_quantity) {
        this.cart_item_id = cart_item_id;
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_quantity = product_quantity;
    }

    @Override
    public String toString() {
        return "Cart_item{" +
                "cart_item_id=" + cart_item_id +
                ", cart_id=" + cart_id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", product_quantity=" + product_quantity +
                '}';
    }
}
