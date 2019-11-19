<template>
<div>
    <div class="top"> 

    <button type="button" class="btn btn-danger" @click="deleteAll()">Delete All</button>

</div>
    <div class="text-center">
        <div v-for="product in products" v-bind:key="product._id" class="media border p-3 mt-3 mb-3">
			<img class="img-responsive" :src="product.image" style="width: 120px;">
            <div class="media-body">
				 <button type="button" class="close" data-toggle="modal" v-bind:data-target="'#product-delete-' + product._id" >&times;</button>
                <h4>{{product.name}}</h4>
                <h4>{{product.price}} SEK</h4>
                <p>{{product.desc}}</p>   
				<a href="#" class="btn btn-warning float-right" data-toggle="modal" v-bind:data-target="'#product-modal-' + product._id" >Edit Product</a>
            </div>
			
<!--	Delete product confirmation-->
			<div :id="'product-delete-' + product._id" class="modal" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Product deletion</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		  <p>Are you sure you want to delete <br> <b>{{product.name}}?</b></p>
      </div>
      <div class="modal-footer">
		  <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-danger" @click="deleteProduct(product._id)" data-dismiss="modal">Delete</button>
      </div>
    </div>
  </div>
</div>
			
<!--			Edit product form-->
	<div :id="'product-modal-' + product._id" class="modal fade text-center" role="dialog">
  	<div class="modal-dialog modal-lg">

    <!-- Modal content-->
    <div class="modal-content" >
      <div class="modal-header">
        <h4 class="modal-title">Product Reviews</h4>
        		<button type="button" class="close" data-dismiss="modal">&times;</button>

      </div>

      <div class="modal-body"> 
		  <h2>Update product information</h2>
		  
		  <br>
		  <br>
		  	<form>
  <div class="form-group">
        <div>
		<label class="control-label text-left" for="productName">Product Name</label>
          <input type="text" class="form-control" id="productName" v-bind:placeholder="product.name" v-model="products.name">
        </div>
	  <br>
			 <div>
          <label class="control-label" for="productPrice">Product Price</label>
          <input type="number" class="form-control" id="productPrice" v-bind:placeholder="product.price" v-model="products.price">
        </div>
	  <br>
			 <div>
          <label class="control-label" for="productImage">Product Image</label>
          <input type="text" class="form-control" id="productImage" v-bind:placeholder="product.image" v-model="products.image">
        </div>
	  <br>
        <div>
			<label class="control-label" for="productDesc">Product description</label>
    	<textarea class="form-control" id="productDesc" rows="3" v-bind:placeholder="product.desc" v-model="products.desc"></textarea>
          
        </div>
	  <br>
        <div>
			
          <button type="button" class="btn btn-primary" @click="editProduct(product._id)" aria-label="Close" data-dismiss="modal">Update</button>
        </div>
      </div>
	</form>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" @click="reload" >Close</button>
      </div>
    </div>
	</div>
</div>
	</div>
	</div>
        </div>
        
   </div>

</template>

<script>
var axios = require('axios');

module.exports = {
  name:"ListProducts",
  data () {
    return {
      products: {name: '',price: '',image: '',desc: ''}
     
    }
  },
    methods: {
      //Performs a GET request to /api/products using AXIOS.
        getProducts: function () {
            axios.get('/api/products')
            .then(
                response => {
                    if (response.status!==200) {
                        console.log("Wrong status code: " + response.status);
                    }
                    //
                    this.products = response.data;

            })
            .catch(error => {
                //In case of error
                console.log(error);
            })
            .then(function () {
                //This code is always executed, independent of the request being successful or not.
            });
        }, 
        //Performs a DELETE request to the product with the provided ID.
        deleteProduct: function (productID) {
            console.log("Deleting Product with ID " + productID);
            axios.delete('/api/products/'+productID)
            .then(
                response => {
                    //This code basically finds the product under question in the array and deletes it (using splice)
                    var localIndex = -1;
                    for (var i=0; i < this.products.length; i++) {
                        if (this.products[i]._id === productID) {
                            localIndex = i;
                            i = this.products.length;
                        }
                    }
                    if (localIndex !== -1) {
                        //Vue reacts to splice. So removing the element here causes the DOM to update.
                        this.products.splice(localIndex, 1); 
						
                    }
					this.getProducts();
					location.reload();
                    console.log("Success: " + response.status);
            })
            .catch(error => {
                console.log(error);
            })
            .then(function () {
            });

        },
		
		editProduct: function (productID) {
			 let Product = {
					name: this.products.name,
					price: this.products.price,
				    image: this.products.image,
				    desc: this.products.desc
                        
                    }
			 
            axios.patch('/api/products/'+productID, Product)
			.then(
				response => {		
				this.getProducts();
				location.reload();
				console.log("status code: " + response.status);
					
					
				
			})
			.catch((error) => {
				console.log(error);
			})
			.then(function() {
        });
          
          
        },
    
    
    deleteAll: function() {
        axios.delete('/api/products/')
        .then(
			response => {
				
				this.getProducts();
				console.log("status code: " + response.status);
					
			
            
        })
        .catch((error) => {
            console.log(error);
        })
        .then(function() {
        });
    },

    
    },
    mounted () {
        this.getProducts();
    }
};
</script>