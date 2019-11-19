package se.janssondev.group9.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import se.janssondev.group9.R;
import se.janssondev.group9.backend.BasketHandler;
import se.janssondev.group9.models.Product;


/** Used instead of the BasketMapAdapter because of le bug **/


public class ProductListAdapterBasket extends ArrayAdapter<Product> {

    private static String TAG = "ProductListAdapter";
    private Context mContext;
    private int mResource;
    private Handler basketViewHandler;

    public ProductListAdapterBasket(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects, Handler basketViewHandler) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
        this.basketViewHandler = basketViewHandler;
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return super.getItem(position);
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        String name = product.getName();
        int price = product.getPrice();
        String image = product.getImage();
        String desc = product.getDesc();
        int qty = BasketHandler.getQty(product);

        String qtyPriceString = Integer.toString(qty) + " pieces";
        int total = qty * price;

        String totalString = Integer.toString(total) + " kr";



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
            Message messageToActivity = basketViewHandler.obtainMessage(1);
            basketViewHandler.sendMessage(messageToActivity);        });

        tvName.setText(name);
        tvQty.setText(qtyPriceString);
        tvPrice.setText(totalString);
        Ion.with(ivImage).load(image);

        return convertView;

    }

}
