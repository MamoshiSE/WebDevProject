package se.janssondev.group9.models;


import java.io.Serializable;

public class Product implements Serializable {
    public String _id;
    public String name;
    public int price;
    public String image;
    public String desc;




    public Product(String name, int price, String image, String desc, String _id)  {
        this.name = name;
        this.price = price;
        this.image = image;
        this.desc = desc;
        this._id = _id;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
