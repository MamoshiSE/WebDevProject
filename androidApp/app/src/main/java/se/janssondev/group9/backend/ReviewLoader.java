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
import se.janssondev.group9.models.Review;

public class ReviewLoader extends AsyncTask<Void, Integer, Void> {
    private final String TAG = "ReviewLoader";
    private ArrayList<Review> reviewList = new ArrayList<>();
    private Handler handler;
    private Product product;
    private final WeakReference<Activity> weakActivity;

    public ReviewLoader(Activity mActivity, Handler reviewActivityHandler, Product product) {
        handler = reviewActivityHandler;
        this.weakActivity = new WeakReference<>(mActivity);
        this.product = product;
    }



    @Override
    protected Void doInBackground(Void... voids) {
        String url = weakActivity.get().getString(R.string.server_url) + "/api/reviews/";

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

                        Review[] review = gson.fromJson(dataArray, Review[].class);

                        for (Review current : review) {
                            // Quick fix for a mistake in the backend

                            if(current.getProduct().equals(product.getId())) {
                                Review x = new Review(current.getProduct(), current.getComment());
                                reviewList.add(x);
                                Log.d(TAG, "Added review for: " + current.getProduct());
                                Log.d(TAG, "review:  " + current.getComment());
                            }


                        }

                        // Send the list of products to our MainActivity
                        Message messageToActivity = handler.obtainMessage(1);
                        messageToActivity.obj = reviewList;
                        handler.sendMessage(messageToActivity);


                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error! " + error.toString());

                        // Send an error to our ReviewActivity
                        Message messageToActivity = handler.obtainMessage(2);
                        handler.sendMessage(messageToActivity);

                    }
                });

        //The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
        return null;
    }
}
