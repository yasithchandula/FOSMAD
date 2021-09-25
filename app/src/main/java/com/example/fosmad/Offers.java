package com.example.fosmad;

public class Offers {

    String offerTitle;
    Float offerPrice;
    String offerDescription;
    String offerImage;


    public Offers() {

    }

    public Offers(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public Offers(String offerTitle, Float offerPrice, String offerDescription) {
        this.offerTitle = offerTitle;
        this.offerPrice = offerPrice;
        this.offerDescription = offerDescription;

    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public Float getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Float offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;

    }
}
