This program clusters data by making n numbers of medoids and slowly adjusting it's center and collecting new nodes to classify
all data according to proximity.  This program was implemented in python 2.7.  It needs to be in the folder with its input files and it will 
automatically produce an output file named "output.txt".

It can be executed in windows (it's a .exe) but the raw sourcecode can be executed if the python 2.7 
environment is loaded on the executing computer. 

The commanline format is <program> <k> <input.txt>
Ex: "kmedoid.exe 4 input4.txt"