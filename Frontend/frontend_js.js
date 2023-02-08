// Main
//This runs when the page is fully loaded

$(document).ready(function() {
    //START OF DOCUMENT.READY
    //SWITCHING SCREENS (Connects the buttons to the specific screens)
    $("#menu-addTicket").click(function() {
      showMenuScreen("#addTicket");
    });
  
    $("#menu-inbox").click(function() {
      showMenuScreen("#inbox");
    });
  }); //END OF DOCUMENT.READY
  
  
  
  // Functions
  function showScreen(screen) {
    //Screen holds the name of the screen passed to the function
    $("#menu").hide(); //Hides the menu
    $(".screen").hide(); //Hides all screens
    $(screen).show(); //Shows the specific screen that was passed to the function
  }
  function showMenuScreen(screen) {
    $(".menuScreen").hide();
    $(screen).show();
  }
  