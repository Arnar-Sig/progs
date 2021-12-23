//Loads all images after page load

let folder = "img/";
let imagesArray = []; 


/* $.ajax({
    url : folder,
    success: function (data) {
        $(data).find("a").attr("href", function (i, val) {
            if( val.match(/\.(jpe?g|png|gif|JPG)$/) ) { 
                imagesArray.push(val);
                let mynd = `
                <div class="col col-3">
                    <section class="flower">
                        <a href="${folder + val}" data-lightbox="mygallery" data-title="blóm">
                            <div class="image"><img src="${folder + val}" id="${folder + val}" alt="">
                            <span class="tooltiptext">Tooltip text.</span>
                            </div>
                        </a>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Esse doloremque incidunt 
                        blanditiis accusantium qui recusandae nulla cumque temporibus dolores provident 
                        impedit exercitationem voluptatem numquam, delectus illum, at corporis dignissimos natus.</p>
                    </section>
                </div>
                `
                $(galleri).append(mynd);
            } 
        });
    }
}); */



/* let xhr = new XMLHttpRequest();
xhr.open('GET', 'img/listOfPics.txt', true);
xhr.responseType = 'text/plain';
xhr.onload = function(){
    let myndirFylki = xhr.response.split(",");
    let mainGallery = document.getElementById('galleri');
    for(let i = 0; i<myndirFylki.length; i++){
        if(myndirFylki[i]==='listOfPics.txt'){
            continue;
        }
        let mynd = `
        <div class="col col-3">
            <section class="flower">
                <a href="${folder+myndirFylki[i]}" data-lightbox="mygallery" data-title="blóm">
                    <div class="image"><img src="${folder+myndirFylki[i]}" id="${folder+myndirFylki[i]}" alt="">
                    <span class="tooltiptext">Tooltip text.</span>
                    </div>
                </a>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Esse doloremque incidunt 
                blanditiis accusantium qui recusandae nulla cumque temporibus dolores provident 
                impedit exercitationem voluptatem numquam, delectus illum, at corporis dignissimos natus.</p>
            </section>
        </div>
        `
        
        $(galleri).append(mynd);
    }
}
xhr.send(); */



















/*
//Get EXIF-data
"use strict";
function getExif(picture) {
    EXIF.getData(picture, function(){
        let imgComment = EXIF.getTag(this, "Make");
        console.log("exif.gettag: " + imgComment);


        let test = JSON.stringify(imgComment);
        console.log("stringify prufa: " + test);
    });
}
*/




/* $.get('./', function(myndirJSON){
    let listiAfMyndum = JSON.parse(myndirJSON);
    console.log(listiAfMyndum);
});
 */
