package se.janssondev.group9.adapters;

import android.content.Context;
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
import se.janssondev.group9.models.Product;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private static String TAG = "ProductListAdapter";
    private Context mContext;
    private int mResource;

    public ProductListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;

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

        String name = getItem(position).name;
        int price = getItem(position).price;
        String image = getItem(position).image;
        String desc = getItem(position).desc;
        String _id = getItem(position)._id;


        String priceString = Integer.toString(price) + " kr";


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = convertView.findViewById(R.id.productTitle);
        TextView tvDesc = convertView.findViewById(R.id.productDesc);
        TextView tvPrice = convertView.findViewById(R.id.productPrice);
        ImageView ivImage = convertView.findViewById(R.id.productImageView);

        tvName.setText(name);
        tvDesc.setText(desc);
        tvPrice.setText(priceString);
        Ion.with(ivImage).load(image);

        return convertView;

    }
}
