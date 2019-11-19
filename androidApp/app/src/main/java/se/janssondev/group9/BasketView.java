package se.janssondev.group9;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import se.janssondev.group9.adapters.BasketMapAdapter;
import se.janssondev.group9.adapters.ProductListAdapter;
import se.janssondev.group9.adapters.ProductListAdapterBasket;
import se.janssondev.group9.backend.BasketHandler;
import se.janssondev.group9.models.Product;

import static se.janssondev.group9.backend.BasketHandler.basket;
import static se.janssondev.group9.backend.BasketHandler.basketArray;

public class BasketView extends AppCompatActivity {
    private static String TAG = "BasketView";
    private ListView basketListView;
    private TextView homeText;
    private TextView totalTextView;
    private static Handler basketViewHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_view);
        // Load UI
        loadUI();

        loadUIHandler();

        // Load Basket
        loadBasket();


    }

    private void loadUIHandler() {
        basketViewHandler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg){
                updateTotal();
                return true;
            }


        });
    }


    private void loadUI() {
        basketListView = findViewById(R.id.basketListView);
        homeText = findViewById(R.id.homeText);
        homeText.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        totalTextView = findViewById(R.id.totalTextView);
        updateTotal();
    }

    private void updateTotal(){
        totalTextView.setText("Total: " + Double.toString(BasketHandler.getTotal()) + " kr");
    }


    private void loadBasket() {
        final ProductListAdapterBasket productAdapter = new ProductListAdapterBasket(BasketView.this, R.layout.adapter_view_layout2, basketArray, basketViewHandler);
        //final BasketMapAdapter productAdapter = new BasketMapAdapter(BasketView.this, R.layout.adapter_view_layout2, basketViewHandler);

        basketListView.setAdapter(productAdapter);



    }




}
