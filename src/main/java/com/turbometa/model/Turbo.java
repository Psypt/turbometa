package com.turbometa.model;

public class Turbo {

    private String ref;
    private String brand;
    private String model;
    private String geo;

    public Turbo() {
    }

    public Turbo(String ref, String brand, String model, String geo) {
        this.ref = ref;
        this.brand = brand;
        this.model = model;
        this.geo = geo;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }
}
