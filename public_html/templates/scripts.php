<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/materialize.min.js"></script>
<script>
  $(".button-collapse").sideNav();
  $('.collapsible').collapsible();
</script>
<script>
  $(".button-collapse").sideNav( {
    menuWidth: 300, // Default is 300
    closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
    draggable: true, // Choose whether you can drag to open on touch screens,
  });
  // main dropdown initialization
  $('.dropdown-button.main-menu-item').dropdown({
      inDuration: 300,
      outDuration: 225,
      constrain_width: false, // Does not change width of dropdown to that of the activator
      hover: true, // Activate on hover
      belowOrigin: true, // Displays dropdown below the button
      alignment: 'left' // Displays dropdown with edge aligned to the left of button
  });
  // nested dropdown initialization
  $('.dropdown-button.sub-menu-item').dropdown({
      inDuration: 300,
      outDuration: 225,
      constrain_width: false, // Does not change width of dropdown to that of the activator
      hover: true, // Activate on hover
      gutter: 0, // Spacing from edge
      belowOrigin: false, // Displays dropdown below the button
      alignment: 'left' // Displays dropdown with edge aligned to the left of button
  });
</script>
<!--<script>
$( window ).load(function() {
  var images = document.getElementsByClassName("image");
  for(var i = 0; i < images.length; i++) {

    if (images[i].naturalWidth < 200) {
      console.log('width = ' + images[i].naturalWidth);
      images[i].setAttribute("style","image-rendering: pixelated;;");
    }
  };
});
</script>-->
