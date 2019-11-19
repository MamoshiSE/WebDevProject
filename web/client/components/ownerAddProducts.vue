<template>
<div>
		<h3>Add new product</h3>
	<br>
		<form>
  <div class="form-group">
        <div>
		<label class="control-label text-left" for="productName">Product Name</label>
          <input type="text" class="form-control" id="productName" placeholder="Name" v-model="Product.name">
        </div>
	  <br>
			 <div>
          <label class="control-label" for="productPrice">Product Price</label>
          <input type="number" class="form-control" id="productPrice" placeholder="Price" v-model="Product.price">
        </div>
	  <br>
			 <div>
          <label class="control-label" for="productImage">Product Image</label>
          <input type="text" class="form-control" id="productImage" placeholder="Image link" v-model="Product.image">
        </div>
	  <br>
        <div>
			<label class="control-label" for="productDesc">Product description</label>
    	<textarea class="form-control" id="productDesc" rows="3" v-model="Product.desc"></textarea>
          
        </div>
	  <br>
        <div>
			
          <button type="submit" class="btn btn-primary" data-dismiss="modal" @click="addProduct">Add</button>
        </div>
      </div>
	</form>
	</div>
</template>

<script>
var axios = require('axios');

module.exports = {
  name:"AddProducts",
  data () {
    return {
      Product: {name: '',price: '',image: '',desc: ''}
    }
  },
  methods: {
      //Performs a GET request to /api/products using AXIOS.
        addProduct: function () {
			 let Product = {
					name: this.Product.name,
					 price: this.Product.price,
				     image: this.Product.image,
				     desc: this.Product.desc
                        
                    }
//			 if (name == '' || price == ''  || image == '' || desc == '') {
//				 console.log("Please fill in all fields");
//			 } else {
            axios.post('/api/products', Product)
			.then((response) => {
				return Product;
				
			})
			.catch((error) => {
				console.log(error);
			});
          
          
        }, 
    }, 
};
</script>