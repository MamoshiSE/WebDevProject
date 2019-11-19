var express = require('express');
var router = express.Router();
var User = require('../models/user');



// Get all the users
router.get('/', function(req, res, next) {
    User.find(function(err, user) {
        if (err) { return next(err); }
        res.status(200).json(user);
    });
});


// Create new user
router.post('/', function(req, res, next) {
    let user = new User(
        {
            name: req.body.name,
            isAdmin: req.body.isAdmin
        }
    );
    user.save(function(err) {
        if (err) {
            return next(err);
        }
        res.status(201).json(user);
    })
});


// Get user by id
router.get('/:id', function(req, res, next) {
    User.findById(req.params.id, function(err, user) {
        if (err) { return next(err); }
        if (user == null) {
            return res.status(404).json({"message": "User not found"});
        }
        res.status(200).json(user);
    });
});

// Update user by id
router.put('/:id', function(req, res, next) {
    User.findById(req.params.id, function(err, user) {
        if (err) { return next(err); }
        if (user == null) {
            return res.status(404).json({"message": "User not found"});
        } 
        user.name = req.body.name;
        user.isAdmin = req.body.isAdmin;
        user.save();
        res.status(200).json(user);
    });
});

// Patch user
router.patch('/:id', function(req, res, next) {
    User.findById(req.params.id, function(err, user) {
        if (err) { return next(err); }
        if (user == null) {
            return res.status(404).json({"message": "User not found"});
        } 
        user.name = (req.body.name || user.name);
        let isAdmin = req.body.isAdmin;
        if (typeof isAdmin !== "undefined") user.isAdmin = isAdmin;
        user.save();
        res.status(200).json(user);
    });
});

// Delete user by id
router.delete('/:id', function(req, res, next) {
    User.findOneAndDelete({_id: req.params.id}, function(err, user) {
      if (err) { return next(err); }
      if (user == null) {
          return res.status(404).json({"message": "User not found"});
      }
      res.status(200).json(user);
  });
});


module.exports = router;