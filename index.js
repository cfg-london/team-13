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
