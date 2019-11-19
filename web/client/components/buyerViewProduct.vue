<template>
<div>
	
<div class="productView">
  <div class="card-deck col-sm-12">
  <div class="card">
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
	  
    <div class="card-footer">
		<button type="submit" class="btn btn-warning" @click="addToBasket()">Buy</button>
      </div>
    </div>
  </div>


</div>

	
</div>
</template>

<script>
var axios = require("axios");

module.exports = {
  name: "ListProducts",
  data() {
    return {
      product: {}
    };
  },
  methods: {
    //Performs a GET request to /api/products using AXIOS.
    getProducts: function(id) {
        console.log("Function called");
      axios
        .get("/api/products/" + id)
        .then(response => {
          if (response.status !== 200) {
            console.log("Wrong status code: " + response.status);
          }
          //
          this.product = response.data;
          console.log(response.data);
        })
        .catch(error => {
          //In case of error
          console.log(error);
        })
        .then(function() {
            console.log("function ended");
          //This code is always executed, independent of the request being successful or not.
        });
    },
  
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
    }
  },
    mounted() {
    this.getProducts(this.$route.params.id);
    //this.addToBasket();
  }

};
</script>


