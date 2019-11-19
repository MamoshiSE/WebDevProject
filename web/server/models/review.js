var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var reviewShema = new Schema({
	
    product: {type: mongoose.Schema.Types.ObjectId, ref: 'product'},
	comment: String
	
	
	
	
	
});

module.exports = mongoose.model('reviews', reviewShema);
