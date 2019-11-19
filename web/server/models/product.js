var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var productModel = new Schema({
    name: {
        type: String,
        required: true
    },
	price: {
        type: Number,
        required: true
    }, 
	image: {
        type: String,
        required: true
    },
	desc: String,
	// reviews: {type: mongoose.Schema.Types.ObjectId, ref: 'reviews'}
});

 var Product = mongoose.model('products', productModel);
module.exports = Product;
