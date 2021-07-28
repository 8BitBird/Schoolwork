#!/usr/bin/python
# -*- coding: utf-8 -*-
from pwn import *
import base64

flag = ""

with open("pokemon.txt", 'r') as f:
    for line in f.read().split("\n")[2::8]:
        flag += line[4:8]
        
print flag