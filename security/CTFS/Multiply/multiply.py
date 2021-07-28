#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
import os

flag = []
args = sys.argv[1]


z = open(args,'r').read().encode('hex')

    # c = int(z,16)
    # n = 83107041701747003548951619916073267657052136454079830436578267127977699952641
    # e = 65537
    # P1 = 282238357022244718977290902746061069487
    # P2 = 294456935544012154828625255162223768143
    # r = (P1-1)*(P2-1)
    # d = int(gmpy.invert(e, r).digits())
    # p = pow(c,d,n)
    # pt = hex(p).strip('0x').strip('L').decode('hex')
    # print pt
    # flag.append(pt)
print z
