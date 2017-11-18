myApp.controller('James', function ($scope, $rootScope, $location,$window,$window,$sce) {

  $scope.init = function () {
    console.log("");
    $scope.apiURL ="";
    getdata($scope.apiURL);
  };

  console.log("proba");
  $scope.data = [
            { title:"Titlu1",
              description:"Description1",
              imageURL:"https://www.nobelprize.org/images/recommend/medicine/medicine-short-facts.jpg",
              link:""
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


  generateRecomandations ($scope.data.length,); 
  function generateRecomandations (nr){
     console.log("is this even called..");
     var recommendationsTags = "";
     for(var i=0; i < nr; i++){
       recommendationsTags +='<div class="recommended-slider-item slick-slide slick-active" data-slick-index="1" aria-hidden="false" tabindex="0" role="option" aria-describedby="slick-slide01" style="width: 144px;"><a href="' 
                           + $scope.data[i].link 
                           + '" tabindex="0"><img class="recommended-slider-img" src="' 
                           + $scope.data[i].imageURL 
                           + '"><a class="recommended-slider-title" href="' 
                           + $scope.data[i].link 
                           +' tabindex="0">'
                           + $scope.data[i].title 
                           + '</a>'
                           + '<p class="recommended-slider-descr">'
                           + $scope.data[i].description
                           +'</p></div>';
       }
       
       console.log(recommendationsTags);
       $scope.recommendations = $sce.trustAsHtml(recommendationsTags);
     }

    /*<div class="recommended-slider-item slick-slide slick-active" data-slick-index="1" aria-hidden="false" tabindex="0" role="option" aria-describedby="slick-slide01" style="width: 144px;">
      <a href="https://www.nobelprize.org/nobel_prizes/facts/medicine/index.html" tabindex="0"><img class="recommended-slider-img" src="./James Watson - Facts_files/medicine-short-facts.jpg"></a>
        <a class="recommended-slider-title" href="https://www.nobelprize.org/nobel_prizes/facts/medicine/index.html" tabindex="0">Facts on the Nobel Prize in Physiology or Medicine</a>
        <p class="recommended-slider-descr">All you want to know about the Medicine Prize!</p>
    </div>
    */

    function getdata (apiURL){
        console.log("getdata");
        $.get(apiURL, function(data, status){
                $scope.data = JSON.parse(data);
                alert("Data: " + data + "\nStatus: " + status);
                $scope.$apply();
            });
    }

    // var jqxhr = $.ajax({

    //         method: 'GET',
    //         url: apiURL,
    //         data: { information: informationstring },
    //         //dataType: 'json',
    //         crossDomain: true,
    //         jsonp: false
    //     })

    //     .done(function () {
    //         document.getElementById('mesaj').innerText = "Success";
    //     })

    //     .fail( function(xhr, textStatus, errorThrown) {
    //         document.getElementById('mesaj').innerText = xhr.status;
    //         eroare();
    //     });

});