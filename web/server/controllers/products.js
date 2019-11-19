var express = require('express');
var router = express.Router();
var Product = require('../models/product');


// Get products with sorting query
router.get('/', function (req, res, next) {
	var sort_by = req.query.sort_by;
	Product.find({}).sort({price: sort_by}).exec(function (err, product) {
		console.log(sort_by)
		if (err) {
			return next(err);
		}
		res.status(200).json(product);
	});
});

// Create a new product
router.post('/', function (req, res, next) {
	var product = new Product(req.body);
	product.save(function (err) {
		if (err) {
			return next(err);
		}
		res.status(201).json(product);
	});
});
// Get specific product by id
router.get('/:id', function (req, res, next) {
	Product.findById(req.params.id, function (err, product) {
		if (err) {
			return next(err);
		}
		if (product == null) {
			return res.status(404).json({
				"message": "Product not found"
			});
		}
		res.status(200).json(product);
	});
});
// Update product with PUT 
router.put('/:id', function (req, res, next) {
	Product.findById(req.params.id, function (err, product) {
		if (err) {
			return next(err);
		}
		if (product == null) {
			return res.status(404).json({
				"message": "Product not found"
			});
		}
		product.name = req.body.name;
		product.price = req.body.price;
		product.image = req.body.image;
		product.desc = req.body.desc;
		product.save();
		res.status(200).json(product);
	});
});
// Update product with PATCH
router.patch('/:id', function (req, res, next) {
	Product.findById(req.params.id, function (err, product) {
		if (err) {
			return next(err);
		}
		if (product == null) {
			return res.status(404).json({
				"message": "Product not found"
			});
		}
		product.name = (req.body.name || product.name);
		product.price = (req.body.price || product.price);
		product.image = (req.body.image || product.image);
		product.desc = (req.body.desc || product.desc);
		product.save();
		res.status(200).json(product);
	});
});

router.delete('/', function (req, res, next) {
    Product.deleteMany({},
	function (err, product) {
		if (err) {
			return next(err);
		}
		if (product == null) {
			return res.status(404).json({
				"message": "Products not found"
			});
		}
		res.status(200).json(product);
	});				  
					  
					  
})

// Delete product by id
router.delete('/:id', function (req, res, next) {
	Product.findOneAndDelete({
		_id: req.params.id
	}, function (err, product) {
		if (err) {
			return next(err);
		}
		if (product == null) {
			return res.status(404).json({
				"message": "Product not found"
			});
		}
		res.status(200).json(product);
	});
});
module.exports = router
