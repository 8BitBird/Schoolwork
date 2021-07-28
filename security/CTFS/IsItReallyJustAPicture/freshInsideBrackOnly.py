#!/usr/bin/python
# -*- coding: utf-8 -*-
from pwn import *
import base64

flag = ""

bracket = False

with open("fresh.txt", 'r') as f:
    for line in f.read().split("\n"):
        for char in line:
            if (char=='{' or char=='}'):
                flag += char
                bracket = True
            if (bracket):
                if (ord(char)>64 and ord(char)<91):
                    flag += char
        
print flag