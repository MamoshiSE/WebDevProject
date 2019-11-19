<template>
<div>
  

<div class="text-center">
<div class="row">
<div class="card-deck col-xs-12 col-sm-6 col-md-4" v-for="product in products" v-bind:key="product._id">
  <div class="card">
    <a v-bind:href="'#/viewProduct/'+ product._id" >
	  <div class="text-center">
      <div class="img-container">
    <img class="card-img-top img-responsive" :src="product.image" alt="Card image cap" >
    </div>
		  </div>
    <div class="card-body">
      <h5 class="card-title">{{product.name}}</h5>
	<h3 class="card-text">{{product.price}} SEK</h3>
      <p class="card-text">{{product.desc}}</p>
    </div>
    </a>
	  
    <div class="card-footer">
		<a href="#" class="btn btn-primary" data-toggle="modal" v-bind:data-target="'#product-modal-' + product._id"  @click="getReviews(product._id)" >
      Reviews</a>
		<a href="#" class="btn btn-warning" data-toggle="modal" v-bind:data-target="'#buy-modal-'" >Buy</a>
      
    </div>
  </div>


<div :id="'buy-modal-'" class="modal fade text-center" role="dialog">
  	<div class="modal-dialog modal-lg">
      <!-- Modal content-->
    <div class="modal-content" >
      <div class="modal-header">
        <h6 class="modal-title">Add To Basket</h6>
                <button type="button" class="close" data-dismiss="modal">&times;</button>

      </div>
      <div class="modal-body"> 
		  <h2>Do you want to add this item to your basket?</h2>
		  <br>
          <button type="submit" class="btn btn-warning" @click="addToBasket()">Add To Basket</button>
          <button type="button" class="btn btn-default" data-dismiss="modal" >Confirm</button>
          
				<br>
				<br>
        
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" @click="emptyBasket()">Cancel</button>
      </div>
    </div>
	</div>
</div>
	</div>

	 	<div :id="'product-modal-' + product._id" class="modal fade text-center" role="dialog">
  	<div class="modal-dialog modal-lg">

    <!-- Modal content-->
    <div class="modal-content" >
      <div class="modal-header">
        <h4 class="modal-title">Product Reviews</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>

      </div>
		
      <div class="modal-body"> 
		  <h2>This product has {{reviews.length}} reviews</h2>
		  
		  
		  <br>
		<p v-for="(review, index) in reviews" v-bind:key="review._id">Review {{index + 1}}: {{review.comment}} </p>
		
		  <br>
		  	<form role="form">
                  <div class="form-group">
                    <label for="addReview">Product Review</label>
                     <textarea class="form-control" id="addReview" rows="2" placeholder="Your thoughts about the product..." v-model="comment_product" required></textarea>
                  </div>
					
                  <button type="button" class="btn btn-warning" @click="addReview(product._id)">Submit</button>
				<br>
				<br>
                </form>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
	</div>
</div>
	</div>
		
</div>

	</div>
	
	</div>	
  	<button type="submit" class="btn btn-danger float-right" @click="emptyBasket()">Empty Basket</button>

	</div>
	

	

</template>

<script>
var axios = require("axios");

module.exports = {
  name: "ListProducts",
  data() {
    return {
      products: [],
      reviews: [],
      comment_product: ""
    };
  },
  methods: {
    //Performs a GET request to /api/products using AXIOS.
    getProducts: function() {
      axios
        .get("/api/products")
        .then(response => {
          if (response.status !== 200) {
            console.log("Wrong status code: " + response.status);
          }
          //
          this.products = response.data;
        })
        .catch(error => {
          //In case of error
          console.log(error);
        })
        .then(function() {
          //This code is always executed, independent of the request being successful or not.
        });
    },

    // get review for specific product
    getReviews: function(productID) {
      axios
        .get("/api/reviews/product/" + productID)
        .then(response => {
          if (response.status !== 200) {
            console.log("Wrong status code: " + response.status);
          }
          //
          this.reviews = response.data;
        })
        .catch(error => {
          //In case of error
          console.log(error);
        })
        .then(function() {
          //This code is always executed, independent of the request being successful or not.
        });
    },

    // add review function
    addReview: function(productID) {
      console.log(productID);
      let Review = {
        product: productID,
        comment: this.comment_product
      };

      axios
        .post("/api/reviews", Review)
        .then(response => {
		  this.getReviews(productID);
          return Review;
        })
        .catch(error => {
          console.log(error);
        });
    },

    //basically a click counter that adds a one to the local storage counter ever time buy item is clicked
    addToBasket: function(){
      if(typeof(Storage) !== "undefined") {
        if (localStorage.clickcount) {
            localStorage.clickcount = Number(localStorage.clickcount)+1;
        } else {
            localStorage.clickcount = 1;
        }
        document.getElementById("result").innerHTML = localStorage.clickcount;
    } else {
        document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
    }
    },

    //emptoes the locat storage of the click count and refresh the page
    emptyBasket: function(){
      localStorage.clear();
      location.reload();

    }

    
  },
  mounted() {
    this.getProducts();
    //this.addToBasket();
  }
};
</script>


