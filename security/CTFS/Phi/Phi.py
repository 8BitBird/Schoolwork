#!/usr/bin/python
# -*- coding: utf-8 -*-
from __future__ import division
import sys
import os

##Decodes ceasar cypher by calculating the correlation of the letters relating to how often they appear in english

flag = ""
alphList = []
frequency = [0.000]*26
table = []
engFreq = [.08, .015, .03, .04, .130, .02, .015, .060, .065, .005, .005, .035, .030, .070, .08, .02, .002, .065, .060, .090, .03, .01, .015, .005, .02, .002]

for alph in range(0,26):    #populate with english frequency
    alphList.append(chr(alph + 65))
# code= 'TEBKFKQEBZLROPBLCERJXKBSBKQP'
code = 'OLSSVZDLLAPL'
print "\n" + code + "\n"
for letter in code: #get base frequency of each letter doesn't change
    alpInd = alphList.index(letter)
    if frequency[alpInd] == 0:
        x = code.count(letter)
        length = len(code)
        frequency[alpInd] =  round(x/length, 4)
    else:
        pass
allCorr = []
count = 0
for z in range(len(alphList)):
    roundTotal =0
    roundCorrelation = [0.00]*26
    for i in range(len(alphList)):
        roundCorrelation[i] = engFreq[i]*frequency[(i+count)%26]
        roundTotal+=roundCorrelation[i]
    count+=1
    allCorr.append(roundTotal)
maxv = allCorr.index(max(allCorr))
s = "SHIFT"
print s.rjust(10) +"|" + "CORRELATION"
for i in range(len(alphList)):
    print '\t' + str(i).rjust(2) +'|' + str(round(allCorr[i], 4))
    # print '\t|' + str(i).rjust(2) +'|' + str(round(allCorr[i], 4)).rjust(5) + "|"
    # print "\t" + "-"*11
print "\nHighest correlation: " + str(maxv)
print "Key is " + str(maxv)
for char in code:
    m =(ord(char) + 65 - maxv)%26
    dec_char = chr(m + 65)
    flag += dec_char
print "Plaintext is: " + flag
