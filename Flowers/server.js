/* Fastar og imports */
const express = require('express');
const path = require('path');
const app = express();
const Database = require('better-sqlite3');
const http = require('http');
//const formidable = require('formidable');
const multer  = require('multer')
const upload = multer({ dest: 'public/img/' })

/* Database */
const db = new Database('myndir.db', {verbose:console.log});

/* Data */





/* Basic server */
app.use(express.static('public'))
app.set('view engine', 'ejs');
app.listen(5000);

/* server gets */
app.get('/', (req, res) =>{
    res.render('index.ejs');

});

app.get('/new', (req, res) =>{
    res.render('new.ejs');
});


/* Getting images from computer and, reading their filename, title and description 
   ATTENTION: MULTIPLE FILES AND MULTIPLE TITLE/DESCRIPTIONS NOT PROPERLY CONFIGURED
*/

app.post('/fileupload', upload.array('filetoupload', 12), function (req, res, next) {
    // req.files is array of `photos` files
    // req.body will contain the text fields, if there were any

    //console.log(req.body[0].title);
    //console.log(req.body.title);
    //console.log(req.files[0].filename);

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
            res.redirect('/');
        }

    }
  })

/* Functions */

/* function getImgNameFromDB(){
    const stmt = db.get("SELECT * FROM myndir;").all();
   */  
/*     const stmt = db.each("SELECT * FROM myndir", function(err, rows) {  
        rows.forEach(function (row) {  
            console.log(row.filename, row.title, row.description);    // and other columns, if desired
        })  
    }); */

/* }
 */

//let myndirfragagnagrunn = 
//getImgNameFromDB();
//console.log(myndirfragagnagrunn);