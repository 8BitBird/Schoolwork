#!/usr/bin/python
# -*- coding: utf-8 -*-
from pwn import *
import base64

flag = flag2 = ""

with open("file.txt", 'r') as f:
    code = f.readline()
    s = base64.b64decode(code)
    flag += s
print flag
flag = flag.rstrip()
for char in flag:
    if char == '{' or char == '}' or char == '_':
       flag2 += char
    else:
        if (ord(char)>64 and ord(char)<91):#uppercase
            m =(ord(char) + 65 -24)%26
            dec_char = chr(m + 65)
            flag2 += dec_char
        elif (ord(char)>96 and ord(char)<123):
            m =(ord(char) - ord('a') -24)%26
            dec_char = chr(m + ord('a'))
            flag2 += dec_char
        else:
            print('error')
print "--decoded--\n\n" + flag2
        