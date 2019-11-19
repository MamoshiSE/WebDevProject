var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userModel = new Schema({
    name: String, 
    isAdmin: Boolean
});

module.exports = mongoose.model('users', userModel);
