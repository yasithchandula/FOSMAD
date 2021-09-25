package com.example.fosmad;

public class CartItems {
    String productName;
    Float productPrice;
    Integer productQty;
    String ItemImage;

    public CartItems() {}
    public CartItems(String productName, Float productPrice, int productQty, String itemImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
        ItemImage = itemImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public String getItemImage() {
        return ItemImage;
    }

    public void setItemImage(String itemImage) {
        ItemImage = itemImage;
    }
}
