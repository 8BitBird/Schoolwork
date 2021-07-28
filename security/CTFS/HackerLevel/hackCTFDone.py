#!/usr/bin/python
# -*- coding: utf-8 -*-
from pwn import *
import base64

host= '127.0.0.1'
port = 11111
r = remote(host, port)

while True:
    d = r.recv(2048)
    print d
    if d[-3:-1] == '==':
        s = base64.b64decode(d)
        print s
        r.send(s +'\n')
    else:
        d = r.recv(2048)
        print d
        exit()



    
