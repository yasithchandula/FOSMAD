package com.example.fosmad;

public class Offers {

    String offerTitle;
    String offerPrice;
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

    public Offers(String offerTitle, String offerPrice, String offerDescription) {
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

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;

    }
}
