const express = require('express')
var redis = require('redis')
const app = express()

app.get('/', (req, res) => res.send('Hello World!'))

app.listen(3000)
