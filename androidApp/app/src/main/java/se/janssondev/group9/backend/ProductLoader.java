package se.janssondev.group9.backend;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import se.janssondev.group9.R;
import se.janssondev.group9.models.Product;



public class ProductLoader extends AsyncTask<Void, Integer, Void> {
    private final String TAG = "ProductLoader";
    private ArrayList<Product> productList = new ArrayList<>();
    private Handler handler;
    private final WeakReference<Activity> weakActivity;

    public ProductLoader(Activity mActivity, Handler mainActivityHandler) {
        handler = mainActivityHandler;
        this.weakActivity = new WeakReference<>(mActivity);
    }



    @Override
    protected Void doInBackground(Void... voids) {
        String url = weakActivity.get().getString(R.string.server_url) + "/api/products";

        //This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(weakActivity.get());
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;

                        try {
                            dataArray = response.toString();
                        } catch (Exception e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        StringBuilder productString = new StringBuilder();
                        productString.append("This is the list of my camels: \n");

                        Product[] product = gson.fromJson(dataArray, Product[].class);

                        for (Product current : product) {
                            Product x = new Product(current.name, current.price, current.image, current.desc, current._id);
                            productList.add(x);
                            Log.d(TAG, "Added: " + current.name);
                        }

                        // Send the list of products to our MainActivity
                        Message messageToActivity = handler.obtainMessage(1);
                        messageToActivity.obj = productList;
                        handler.sendMessage(messageToActivity);


                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error! " + error.toString());

                        // Send an error to our MainActivity
                        handler.sendEmptyMessage(2);

                    }
                });

        //The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
        return null;
    }

}
