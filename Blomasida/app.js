import express from 'express';
import { join, dirname } from 'path';
import { fileURLToPath } from 'url';
import fs from 'fs';
import { nextTick, send } from 'process';
const app = express();
const path = dirname(fileURLToPath(import.meta.url));

app.use(express.static(join(path, '/public')));
app.set('views', join(path, '/views'));
app.set('view engine', 'ejs');


const hostname = '127.0.0.1';
const port = 3000;

app.listen(port, hostname, () => {
  console.info(`Server running at http://${hostname}:${port}/`);
});




//read json file
let rawData = fs.readFileSync('pictureData.json');
let imageData = JSON.parse(rawData);


//add new images to json file
function addImgToJson(){
  let x = [];
  try {
    x = fs.readdirSync('./public/img/');
  } catch (error) {
    console.log("Couldn't read image folder to update json file");
  }
  let lengdFiles = x.length;
  let lengdJSON = imageData.pictures.length;
  for(let i=0; i<lengdFiles; i++){
    for(let j=0; j<lengdJSON; j++){
      if(x[i]==imageData.pictures[j].title){
        break;
      }
      else if(j==(lengdJSON-1)){
        let name = x[i];
        let today = new Date();
        let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
        imageData.pictures.push({
          title: `${name}`,
          description: "testTest",
          "created": `${date}`
        }
        );
        console.log(imageData);
        fs.writeFile('pictureData.json', JSON.stringify(imageData), function(err){
          if(err) throw err;
        });  
        }
      }
    }
  }



app.use((req, res, next) =>{
  addImgToJson();
  next();
})

app.use('/', (req, res) => {
    res.render('../index', {imageData});
});

