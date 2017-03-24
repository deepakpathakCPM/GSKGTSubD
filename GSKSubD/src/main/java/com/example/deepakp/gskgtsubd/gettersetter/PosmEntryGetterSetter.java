package com.example.deepakp.gskgtsubd.gettersetter;

/**
 * Created by deepakp on 2/20/2017.
 */

public class PosmEntryGetterSetter {

    String id ;
    String brand_cd = "";
    String brand = "";
    String posm_cd = "";
    String posm = "";
    String quantity = "";
    String image = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPosm_cd() {
        return posm_cd;
    }

    public void setPosm_cd(String posm_cd) {
        this.posm_cd = posm_cd;
    }

    public String getBrand_cd() {
        return brand_cd;
    }

    public void setBrand_cd(String brand_cd) {
        this.brand_cd = brand_cd;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPosm() {
        return posm;
    }

    public void setPosm(String posm) {
        this.posm = posm;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
