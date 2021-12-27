/* Fastar og imports */
const express = require('express');
const path = require('path');
const app = express();
//const Database = require('better-sqlite3'); //better-sqlite3
const sqlite3 = require('sqlite3').verbose();
const http = require('http');
//const formidable = require('formidable');
const multer  = require('multer')
const upload = multer({ dest: 'public/img/' })

/* Database */
//const db = new Database('myndir.db', {verbose:console.log}); //better-sqlite3
let db = new sqlite3.Database('myndir.db');

/* Basic server */
app.use(express.static('public'))
app.set('view engine', 'ejs');
app.listen(5000);


  

/* Data */
pictureData = [];
getImgData();



/* server gets */
app.get('/', (req, res) =>{
    //console.log(pictureData);
    res.render('index.ejs', {pictureData});

});

app.get('/new', (req, res) =>{
    //console.log(pictureData);
    res.render('new.ejs');
});


/* Getting images from computer and, reading their filename, title and description 
   ATTENTION: MULTIPLE FILES AND MULTIPLE TITLE/DESCRIPTIONS NOT PROPERLY CONFIGURED
*/

app.post('/fileupload', upload.array('filetoupload', 12), function (req, res, next) {
    // req.files is array of `photos` files
    // req.body will contain the text fields, if there were any
    let arrayLength = req.files.length;
    for (let i = 0; i<arrayLength; i++){
        try{
            const stmt = db.prepare('INSERT INTO myndir (fileName, title, description) VALUES (?, ?, ?);');
            stmt.run(`${req.files[i].originalname}`, `${req.body.title}`, `${req.body.description}`);
        }
        catch(e){
            console.log(e);
        }
        finally{
            res.redirect('/');
        }
    }
    getImgData();
    //console.log(pictureInfo);
  })

/* Functions */

/* Update pictureInfo array */

/*  ASYNC GET IMAGE DATA
function getImgData(){
    let arr = []
    let sql = 'SELECT * FROM myndir;';
    let p = new Promise((resolve, reject) => {
        db.all(sql, arr, (err, rows) => {
            if (err) {
              throw err;
            }
            rows.forEach((row) => {
                let theData = {
                    "filename": row.fileName,
                    "title": row.title,
                    "description": row.description
                   }
                   arr.push(theData);
            });
            //console.log(arr);
        });
        resolve('Success');  
    });
    p.then((msg) => {
        console.log("Successfully retrieved image-data");
        return arr;
    });
};
 */


async function getImgData(){
    let sql = 'SELECT * FROM myndir;';
    let pictureFileName =[];
    let pictureTitle = [];
    let pictureDescription = [];
    let temp = db.all(sql, [], (err, rows) => {
        if (err) {
          throw err;
        }
        rows.forEach((row) => {
            pictureFileName.push(row.fileName);
            pictureTitle.push(row.title);
            pictureDescription.push(row.description);
        });
        let INDEXCORRECTOR = ["NOTUSED"];
        pictureData = INDEXCORRECTOR.concat(pictureFileName,pictureTitle, pictureDescription)
        return 1;
    });
    return 1;


}
/* 
async function getImgData(arr){
    let sql = 'SELECT * FROM myndir;';
    let temp = await db.all(sql, arr, (err, rows) => {
        if (err) {
          throw err;
        }
        rows.forEach((row) => {
            let theData = {
                "filename": row.fileName,
                "title": row.title,
                "description": row.description
               }    
               arr.push(theData);
        });
        
        return arr;
    });
    return arr;

}
 */
