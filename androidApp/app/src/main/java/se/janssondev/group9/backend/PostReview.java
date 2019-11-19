package se.janssondev.group9.backend;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.ref.WeakReference;

import se.janssondev.group9.R;
import se.janssondev.group9.models.Product;
import se.janssondev.group9.models.Review;

public class PostReview {
    private static final String TAG = "PostReview";
    private JsonObject json;
    private Review review;
    private Context mContext;
    private Handler reviewViewhandler;
    String url;

    public PostReview(Review review, Context mContext, Handler reviewViewHandler) {
        this.review = review;
        json = new JsonObject();
        this.mContext = mContext;
        url = mContext.getString(R.string.server_url) + "/api/reviews";
        this.reviewViewhandler = reviewViewHandler;

    }



    public void postReview() {
        json.addProperty("product", review.getProduct());
        json.addProperty("comment", review.getComment());
        Ion.with(mContext)
                .load(url)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d(TAG, "ADDED REVIEW");

                        reviewViewhandler.sendEmptyMessage(3);


                    }
                });
    }




}
