<!-- baserouter.vue -->
<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="#">Group 9 </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarColor01">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="#/">Home</a>
      </li>
    </ul>
  </div>
  <div class="basket">
  <button type="chip" class="btn btn-primary">Basket: <span class="chip chip-placeholder"><div id="result"></div></span></button>
  </div>
		</nav>

  
    <!-- This is our footer -->
    <div class="row">
      <!-- The empty col-2 here are just used as a placeholder on the right and left -->
      <div class="col-sm-2">
      </div>
    
      <!-- Router view is where we will mount our dynamic content -->
      <div class="col-sm-8 text-center">
        <router-view></router-view>
      </div>
    
      <div class="col-sm-2">
      </div>
    </div>
  
  <div class="footer">
<footer class="page-footer bg-primary text-white ">
  <div class="footer-copyright text-center py-3">
	  Group 9, September 2018.
  </div>
</footer> 
</div>
</div>
</template>

<script>
  module.exports = {
    name:"BaseRouter"
  };
</script>