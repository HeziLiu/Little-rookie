package com.xy.vo;

import com.xy.po.Product;

// 购物车中的一条记录
public class CartVo{
    private int product_id;
    private String product_name;
    private String description;
    private double price;
    private int product_quantity;
    private double total;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CartVo(){}

    public CartVo(int product_id, String product_name, String description, double price, int product_quantity, double total) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.description = description;
        this.price = price;
        this.product_quantity = product_quantity;
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartVo{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", product_quantity=" + product_quantity +
                ", total=" + total +
                '}';
    }
}
