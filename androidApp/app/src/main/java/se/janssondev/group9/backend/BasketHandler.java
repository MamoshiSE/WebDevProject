package se.janssondev.group9.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.janssondev.group9.models.Product;

public class BasketHandler {
    public static HashMap<Product, Integer> basket = new HashMap<>();
    // ** QuickFix for the bug in BasketMapAdapter**
    public static ArrayList<Product> basketArray = new ArrayList<>();


    public static void addToBasket(Product product) {
        for(Product x : basketArray) {
            if(x.getId().equals(product._id)) {
                int count = basket.get(x);
                basket.put(x, count +1);
                return;
            }
        }
        basket.put(product, 1);
        basketArray.add(product);
    }

    /*
    public static void addToBasket(Product product) {
        if(basket.containsKey(product)) {
            int count = basket.get(product);
            basket.put(product, count +1);
            return;
        }
        basket.put(product, 1);
        basketArray.add(product);
    }
    */

    public static void removeFromBasket(Product product) {
        if(basket.containsKey(product)){
            int count = basket.get(product);
            if(count > 1) {
                basket.put(product, --count);
                return;
            }
            basket.remove(product);
            basketArray.remove(product);
        }
    }

    public static int getQty(Product product) {
        if(basket.containsKey(product)) {
            return basket.get(product);
        }
        return 0;
    }

    public static void clearBasket(){
        basket = new HashMap<>();
        basketArray = new ArrayList<>();
    }


    public static double getTotal(){
        double total = 0;
        for(Map.Entry<Product, Integer> item : basket.entrySet()) {
            double price = item.getKey().getPrice();
            int qty = item.getValue();
            total += (price * qty);
        }
        return total;
    }

}
