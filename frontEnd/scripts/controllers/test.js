myApp.controller('Test', function ($scope, $rootScope, $location,$window) {

  //$scope.test = "de ce dracu nu merge..";
  $scope.init = function () {
    console.log("de ce dracu nu mergi..");
  };

  var testDataTitles = ["Titlu1","Titlu2","Titlu3"];
  var testDataDescriptions = ["Titlu1","Titlu2","Titlu3"];
  var testDataPicsURL = ["pics/pic1","pics/pic2","pics/pic3"];

   // var jqxhr = $.ajax({

   //          method: 'POST',
   //          url: "http://oppdev01.cloudapp.net:49003/api/tracker/SendAllInformation",
   //          data: { information: informationstring },
   //          //dataType: 'json',
   //          crossDomain: true,
   //          jsonp: false
   //      })

   //      .done(function () {
   //          document.getElementById('mesaj').innerText = "Success";
   //      })

   //      .fail( function(xhr, textStatus, errorThrown) {
   //          document.getElementById('mesaj').innerText = xhr.status;
   //          eroare();
   //      });

});