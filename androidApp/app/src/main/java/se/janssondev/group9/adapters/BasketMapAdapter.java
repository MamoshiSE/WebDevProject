package se.janssondev.group9.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.ion.Ion;
import java.util.HashMap;
import se.janssondev.group9.R;
import se.janssondev.group9.backend.BasketHandler;
import se.janssondev.group9.models.Product;

/** Can't get this adapter to work properly ..
 *  It's causing a bug so when the quantity of an item in the basket reaches 0 it don't remove it
 *  from the ListView, even though it updates all the values to 0. (That's why the ArrayList in BasketHandler class)
 */

public class BasketMapAdapter extends BaseAdapter {
    private HashMap<Product, Integer> basket;
    private Product[] mProducts;
    private Context mContext;
    private int mResource;
    private Handler mBasketViewHandler;

    public BasketMapAdapter(@NonNull Context context, int resource, Handler basketViewHandler){
        basket  = BasketHandler.basket;
        mProducts = basket.keySet().toArray(new Product[basket.size()]);
        mContext = context;
        mResource = resource;
        mBasketViewHandler = basketViewHandler;
    }

    @Override
    public int getCount() {
        return basket.size();
    }

    @Override
    public Integer getItem(int position) {
        return basket.get(mProducts[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Product product = mProducts[pos];
        String name = product.getName();
        String image = product.getImage();
        int price = product.getPrice();

        /* Beacause of bug the following line can throw a NullPointer exception */
        //int qty = getItem(pos);
        /* That's why the following function was created in BasketHandler class */
        int qty = BasketHandler.getQty(product);

        int total = qty * price;
        String totalString = Integer.toString(total) + " kr";
        String qtyPriceString = Integer.toString(qty) + " pieces";


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = convertView.findViewById(R.id.productTitle);
        TextView tvQty = convertView.findViewById(R.id.productQtyPrice);
        TextView tvPrice = convertView.findViewById(R.id.productTotalPrice);
        ImageView ivImage = convertView.findViewById(R.id.productImageView);

        TextView deleteBtn = convertView.findViewById(R.id.productDelete);
        deleteBtn.setOnClickListener((View v) -> {
            BasketHandler.removeFromBasket(product);
            //Update list
            this.notifyDataSetChanged();
            // Update Total
            Message messageToActivity = mBasketViewHandler.obtainMessage(1);
            mBasketViewHandler.sendMessage(messageToActivity);
        });

        tvName.setText(name);
        tvQty.setText(qtyPriceString);
        tvPrice.setText(totalString);
        Ion.with(ivImage).load(image);

        return convertView;
    }
}