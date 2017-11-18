const express = require('express')
var mysql = require('mysql')
const app = express()

var con = mysql.createConnection({
  host: '54.77.29.175',
  user: 'root',
  password: 'toor'
})

app.get('/', async function(req, res) {
  let hasFailed = await con.connect()
  if (hasFailed) {
    res.send('Oh no')
  } else {
    res.send('Connected')
  }
})

app.listen(3000)
