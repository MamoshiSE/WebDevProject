var express = require('express');
var router = express.Router();
var Review = require('../models/review');
var Product = require('../models/product');
var User = require('../models/user');
var mongoose = require('mongoose');


// Get all the reviews
router.get('/', function(req, res, next) {
    Review.find(function(err, review) {
        if (err) { return next(err); }
        res.status(200).json(review);
    });
});

router.get('/product/:id', function(req, res, next) {
    Review.find({product: req.params.id})
	.exec(function(err, review) {
        if (err) { return next(err); }
        res.status(200).json(review);
    });
});


// Create new review
router.post('/', function (req, res, next) {
	var review = new Review(req.body);
	review.save(function (err) {
		if (err) {
			return next(err);
		}
		res.status(201).json(review);
	});
});


// Get review by id
router.get('/:id', function(req, res, next) {
    Review.findById(req.params.id, function(err, review) {
        if (err) { return next(err); }
        if (review == null) {
            return res.status(404).json({"message": "Review not found"});
        } 
        res.status(200).json(review);
    });
});

// Update review by id with put
router.put('/:id', function(req, res, next) {
    let product = req.body.product;
    let comment = req.body.comment;
    Review.findById(req.params.id, function(err, review) {
        if (err) { return next(err); }
        if (review == null) {
            return res.status(404).json({"message": "Review not found"});
        } 
        // Validate the format of the id's
        if(!mongoose.Types.ObjectId.isValid(user)) {
            return res.status(404).json({"Error": "User id has wrong format"});
        }
        if(!mongoose.Types.ObjectId.isValid(product)) {
            return res.status(404).json({"Error": "Product id has wrong format"});
        }

        // Check if both the user and the product exists
        Promise 
        .all([checkProduct(product)])
        .then(result => {
            review.product = product;
            review.comment =  comment;
            review.save();
            res.status(200).json(review);
        }) 
        .catch(err => {
            res.status(404).json({"Error": err});
        });
    });
});

// Patch review
router.patch('/:id', function(req, res, next) {
    Review.findById(req.params.id, function(err, review) {
        if (err) { return next(err); }
        if (review == null) {
            return res.status(404).json({"message": "Review not found"});
        }
       
        let product = review.product;
        let comment = review.comment; 
        
        var checkList = [];

        
        if(req.body.product !== undefined){
            if(!mongoose.Types.ObjectId.isValid(req.body.product)) {
                return res.status(404).json({"Error": "Product id has wrong format"});
            }
            product = req.body.product;
        }
        if(req.body.comment !== undefined) {
            comment = req.body.comment;
        }

        // Check if both the user and the product exists
        Promise 
        .all([checkProduct(product)])
        .then(result => {
            review.product = product;
            review.comment =  comment;
            review.save();
            res.status(200).json(review);
        }) 
        .catch(err => {
            res.status(404).json({"Error": err});
        });
    });
});


// Delete review by id
router.delete('/:id', function(req, res, next) {
    Review.findOneAndDelete({_id: req.params.id}, function(err, review) {
      if (err) { return next(err); }
      if (review == null) {
          return res.status(404).json({"message": "Review not found"});
      }
      res.status(200).json(review);
  });
});



// Check if product exists
var checkProduct = function(product) {    
    return new Promise(function(resolve, reject) {
        Product.findById(product)
        .then(result =>  {
            if(result == null) {
                reject('Product not found');
            }
            resolve(true);
        })
        .catch(err => {
            console.log(err);
            reject('Product not found');
        });
    });
};

module.exports = router;