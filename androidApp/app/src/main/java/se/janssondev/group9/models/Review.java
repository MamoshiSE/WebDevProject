package se.janssondev.group9.models;

public class Review {
    private String product;
    private String comment;

    public Review(String productId, String comment) {
        this.product= productId;
        this.comment = comment;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String productId) {
        this.product = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
