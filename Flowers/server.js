/* Fastar og imports */
const express = require('express');
const path = require('path');
const app = express();
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
dataBeingAdded = [];


/* Server gets */
app.get('/', (req, res) =>{
    //console.log(pictureData[1]);
    res.render('index.ejs', {pictureData});

});

app.get('/new', (req, res) =>{
    //console.log(pictureData);
    res.render('new.ejs', dataBeingAdded);
});

// //TEST!!!
// app.post('/fileupload', upload.array('filetoupload', 30), function (req, res, next) {
//     // req.files is array of `photos` files
//     // req.body will contain the text fields, if there were any
//     //let arrayLength = req.files.length;
//     dataBeingAdded = req.files;
//     try{
//         res.render('new.ejs', {dataBeingAdded});
//     }
//     catch(e){
//         console.log(e);
//     }
// });



/* Getting images from computer and, reading their filename, title and description 
   ATTENTION: MULTIPLE FILES AND MULTIPLE TITLE/DESCRIPTIONS NOT PROPERLY CONFIGURED
*/
/*
app.post('/fileupload', upload.array('filetoupload', 30), function (req, res, next) {
    // req.files is array of `photos` files
    // req.body will contain the text fields, if there were any
    let arrayLength = req.files.length;
    for (let i = 0; i<arrayLength; i++){
        try{
            const stmt = db.prepare('INSERT INTO myndir (fileName, title, description) VALUES (?, ?, ?);');
            stmt.run(`${req.files[i].filename}`, `${req.body.title}`, `${req.body.description}`);
        }
        catch(e){
            console.log(e);
        }
        finally{
            getImgData();
            res.redirect('/');
        }
    }
    //console.log(pictureInfo);
  })
*/

/* Functions */

/* Update pictureInfo array */
/* Gets filename, title and description from the database and adds them to
   the file pictureData in this order: [filename_1, title_1, description_1, 
   filename_2, title_2, description_2,...,filename_n, title_n, description_n]
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
        let INDEXCORRECTOR = [pictureFileName.length];
        pictureData = INDEXCORRECTOR.concat(pictureFileName,pictureTitle, pictureDescription)
        return 1;
    });
    return 1;


}
