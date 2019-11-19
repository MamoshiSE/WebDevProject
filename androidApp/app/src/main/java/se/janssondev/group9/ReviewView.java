package se.janssondev.group9;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import se.janssondev.group9.backend.PostReview;
import se.janssondev.group9.backend.ReviewLoader;
import se.janssondev.group9.models.Product;
import se.janssondev.group9.models.Review;

public class ReviewView extends AppCompatActivity {
    private static final String TAG = "ReviewViewActivity";
    Product product;
    ArrayList<Review> reviews;
    TextView topText, loadingReviewText, reviewsTextView,basketText, homeText ;
    Button postReview;
    EditText postReviewTextField;
    Handler reviewViewHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_view);
        Intent i = getIntent();
        // Get selected product
        product = (Product) i.getSerializableExtra("product");


        loadUI();


        loadHandler();
        loadReviews();


    }

    private void loadReviews(){
        reviews = new ArrayList<>();
        ReviewLoader loader = new ReviewLoader(this,reviewViewHandler, product);
        loader.execute();

    }

    // Initialize UI handler
    private void loadHandler() {
        reviewViewHandler = new Handler(new Handler.Callback(){

            @SuppressWarnings("unchecked")
            @Override
            public boolean handleMessage(Message msg){
                if(msg.obj != null) {
                    loadingReviewText.setVisibility(View.INVISIBLE);
                    reviews = (ArrayList<Review>) msg.obj;
                    presentList();
                    Log.d(TAG, "Present list called");
                }
                else if(msg.what == 3) {
                    loadReviews();
                    Log.d(TAG, "LoadReviews() called with msg 3");
                }else {
                    Log.d(TAG, "No reviews");
                    loadingReviewText.setVisibility(View.INVISIBLE);
                    reviewsTextView.setText(getResources().getString(R.string.noReviewsText));

                }
                return true;
            }


        });

    }

    private void presentList() {
        if(reviews != null) {
            String string = "";
            int counter = 1;
            for(Review x : reviews) {
                string += counter + ":  ";
                string += x.getComment();
                string += "\n\n";
                counter++;
            }
            reviewsTextView.setVisibility(View.VISIBLE);
            reviewsTextView.setText(string);
        }

    }

    private void loadUI() {
        topText = findViewById(R.id.reviewTopText);
        topText.setText(String.format(getResources().getString(R.string.reviewTopText), product.getName()));
        postReviewTextField = findViewById(R.id.postReviewTextField);

        reviewsTextView = findViewById(R.id.reviewsTextView);
        reviewsTextView.setVisibility(View.INVISIBLE);

        loadingReviewText = findViewById(R.id.loadingReviewsText);
        loadingReviewText.setText(R.string.loadingReviews);
        loadingReviewText.setVisibility(View.VISIBLE);

        postReview = findViewById(R.id.postRevBtn);

        postReview.setOnClickListener((View v) -> {
            String comment =  postReviewTextField.getText().toString();
            if(comment.length() > 0 ) {
                Review review = new Review(product.getId(), comment);
                PostReview poster = new PostReview(review, this, reviewViewHandler);
                poster.postReview();
                loadingReviewText.setText("Posting review..");
                loadingReviewText.setVisibility(View.VISIBLE);
                reviewsTextView.setVisibility(View.INVISIBLE);
            }
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


    }



}
