myApp.controller('James', function ($scope, $rootScope, $location,$window,$window,$sce) {

  $scope.test = "de ce dracu nu merge..";
  $scope.init = function () {
    console.log("");
  };
  //TOBE REPLACED WITH DATA REQUEST
  console.log("proba");
  $scope.testData = [
            { title:"Titlu1",
              description:"Description1",
              imageURL:"pic/pic1",
              link:"/"
              },
            { 
              title:"Titlu2",
              description:"Description2",
              imageURL:"pic/pic2",
              link:"/"
              },
            { 
              title:"Titlu3",
              description:"Description3",
              imageURL:"pic/pic3",
              link:"/"
            }];

  //$scope.recommendations = $sce.trustAsHtml(recommendationsTags);
 console.log($scope.testData.length);
  $scope.recommendationsTags = $scope.generateRecomandations ($scope.testData.length); 
  
  $scope.generateRecomandations= function(nr){
     recommendationsTags = "";
     for(var i=0; i < nr; i++){
       // recommendationsTags += '<div class="recommended-slider-item slick-slide slick-active" data-slick-index="1" aria-hidden="false" tabindex="0" role="option" aria-describedby="slick-slide01" style="width: 144px;"><a href="' 
       //                     + testData[i].link 
       //                     + '" tabindex="0"><img class="recommended-slider-img" src="' 
       //                     + testData[i].imageURL 
       //                     + ' tabindex="0">'
       //                     + testData[i].title 
       //                     + '</a>'
       //                     + '<p class="recommended-slider-descr">'
       //                     + testData[i].description
       //                     +'</p></div>';
       
          recommendationsTags= recommendationsTags + i;
       }
       console.log(recommendationsTags);
       return recommendationsTags;
     }

    /*<div class="recommended-slider-item slick-slide slick-active" data-slick-index="1" aria-hidden="false" tabindex="0" role="option" aria-describedby="slick-slide01" style="width: 144px;">
      <a href="https://www.nobelprize.org/nobel_prizes/facts/medicine/index.html" tabindex="0"><img class="recommended-slider-img" src="./James Watson - Facts_files/medicine-short-facts.jpg"></a>
        <a class="recommended-slider-title" href="https://www.nobelprize.org/nobel_prizes/facts/medicine/index.html" tabindex="0">Facts on the Nobel Prize in Physiology or Medicine</a>
        <p class="recommended-slider-descr">All you want to know about the Medicine Prize!</p>
    </div>
    */


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