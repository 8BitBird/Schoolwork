#!/usr/bin/python
# -*- coding: utf-8 -*-
import re
flag = ""
part = 0
part3 =[]
flags = []

with open("messyaes.txt", 'r') as codeIn:
    code = codeIn.readline().rstrip()
    codeLines = re.findall('.{1,32}', code)
with open("given-maes.txt", 'r') as f:
    messFile = f.read().split('\n')
for cL in codeLines:
    for mL in messFile:
        if cL in mL:
            if part == 0:
                flag += mL[part:part+16]
                part1 = flag
                part+=16
            elif part == 16:
                part2 =  mL[part:part+16]
                part+=16
            else:
                flag = mL[part:part+16]
                part3.append(flag)
for p in part3:
    flags.append(part1 + part2 + p)
for f in flags:
    print f
    if '}' in f:
        correct = f

print "\nFull Flag:\n" + correct
