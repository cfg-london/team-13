const express = require('express')
var mysql = require('mysql')
const app = express()

var con = mysql.createConnection({
  host: '54.77.29.175',
  user: 'root',
  password: 'toor',
  database: 'recommendations'
})

con.connect(function(err) {
  app.get('/', async function(req, res) {
    if (err) {
      res.send('Oh no')
    } else {
      res.send('Connected')
    }
  })

  // Add headers
  app.use(function (req, res, next) {

      // Website you wish to allow to connect
      res.setHeader('Access-Control-Allow-Origin', 'http://10.241.61.133:8085');

      // Request methods you wish to allow
      res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

      // Request headers you wish to allow
      res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

      // Set to true if you need the website to include cookies in the requests sent
      // to the API (e.g. in case you use sessions)
      res.setHeader('Access-Control-Allow-Credentials', true);

      // Pass to next layer of middleware
      next();
  });

  app.get('/similar', async function(req, res) {
    if (err) {
      res.send('Oh no')
    } else {
      con.query("SELECT u2.url FROM urls \
                  u1 JOIN ranks r ON r.from=u1.id \
                     JOIN urls u2 ON r.to=u2.id \
                  WHERE u1.url='"+ req.query.url +"'", function(err, result, field) {



        res.send(result);
        console.log(err + "|"+ field);
      })
    }
  })
})




app.listen(3000)
