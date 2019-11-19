package se.janssondev.group9;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import se.janssondev.group9.adapters.ProductListAdapter;
import se.janssondev.group9.backend.BasketHandler;
import se.janssondev.group9.backend.ProductLoader;
import se.janssondev.group9.models.Product;

public class MainActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "MainActivity";
    private ListView mainListView;
    private TextView textView1;
    private TextView basketText;
    private TextView homeText;
    private static Handler mainActivityHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load UI
        loadUI();

        // Load our UI handler
        loadHandler();

        // Fetch our products from the server
        ProductLoader productLoader = new ProductLoader(this, mainActivityHandler);
        productLoader.execute();





    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadUI(){
        mainListView = findViewById(R.id.mainListView);
        textView1 = findViewById(R.id.textView1);
        basketText = findViewById(R.id.basketText);
        homeText = findViewById(R.id.homeText);

        basketText.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this,BasketView.class);
            startActivity(intent);
        });
    }



    // Initialize UI handler
    private void loadHandler() {
        mainActivityHandler = new Handler(new Handler.Callback(){

            @SuppressWarnings("unchecked")
            @Override
            public boolean handleMessage(Message msg){
                if(msg.obj != null) {
                        textView1.setVisibility(View.INVISIBLE);
                        ArrayList<Product> passedObject = (ArrayList<Product>) msg.obj;
                        loadList(passedObject);
                }else {
                    Log.d(TAG, "EMPTY LIST");
                    textView1.setText(getResources().getText(R.string.emptylist));
                    textView1.setTextColor(getResources().getColor(R.color.colorError));
                }
                return true;
            }


        });

    }

    private void loadList(ArrayList<Product> productList) {
        final ProductListAdapter productAdapter = new ProductListAdapter(MainActivity.this, R.layout.adapter_view_layout, productList);

        mainListView.setAdapter(productAdapter);

        // On item clicked - Start ProductView activity
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Product item = productAdapter.getItem(i);

                Intent intent = new Intent(MainActivity.this,ProductView.class);
                intent.putExtra("product", item);
                startActivity(intent);
            }

        });

        Log.d(TAG, "DONE SETTING ADAPTER WITH " + Integer.toString(productList.size()) + " ITEMS");
    }

}
