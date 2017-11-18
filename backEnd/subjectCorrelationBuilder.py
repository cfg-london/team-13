import redis
import requests
import json

database = redis.StrictRedis(host='104.46.51.71', port=6379, db=0)
#database.set('foo', 'bar')

#r = requests.get('http://api.nobelprize.org/v1/prize.json?', auth=('user', 'pass'))
r = requests.get('http://api.nobelprize.org/v1/prize.json?', auth=('user', 'pass'))
data = r.json()['prizes']

for x in data:
    for y in data:
        if x['subject'] == y['subject']:
            print "Update DB"
