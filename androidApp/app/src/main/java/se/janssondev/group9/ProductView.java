package se.janssondev.group9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.io.Serializable;

import se.janssondev.group9.backend.BasketHandler;
import se.janssondev.group9.models.Product;

public class ProductView extends AppCompatActivity implements Serializable {
    ImageView imageView;
    TextView textView;
    Button buyBtn;
    Button reviewBtn;
    Product product;
    TextView homeText;
    TextView basketText;
    TextView tvDesc;
    TextView tvPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Intent i = getIntent();
        // Get selected product
        product = (Product) i.getSerializableExtra("product");

        // Load UI
        loadUI();








    }

    private void loadUI() {
        textView = findViewById(R.id.productTitle);
        imageView = findViewById(R.id.productImage);
        tvDesc = findViewById(R.id.productDesc);
        tvPrice = findViewById(R.id.productPrice);
        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, ReviewView.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        buyBtn = findViewById(R.id.buyBtn);
        buyBtn.setOnClickListener((View v) -> {
            BasketHandler.addToBasket(product);
        });

        basketText = findViewById(R.id.basketText);
        homeText = findViewById(R.id.homeText);

        basketText.setOnClickListener((View v) -> {
            Intent intent = new Intent(this,BasketView.class);
            startActivity(intent);
        });
        homeText.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        textView.setText(product.name);
        Ion.with(imageView).load(product.image);
        tvDesc.setText(product.desc);
        tvPrice.setText(Integer.toString(product.price)+"kr");



    }
}
