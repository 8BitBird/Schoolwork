import argparse
import os
import sys
import random
from operator import attrgetter
import math

class Node():
    def __init__(self, xAx, yAx, team):
        self.xAxis = xAx
        self.yAxis = yAx
        self.centerNode = self
        self.collectiveDist = 0.0
        self.clusterTeam = team

def printChart(fObj, aNode):
    fObj.write(str(aNode.xAxis) + "\t" + str(aNode.yAxis) + "\t" + str(aNode.clusterTeam) + "\n")

def findCenter(nList, numOfTeams):  #assign center for each team
    changeMade = False
    for i in range(1, numOfTeams+1):   #iterate through teams
        teamList = []
        for node in nList:      #iterate through list for each team
            if node.clusterTeam == i:   #if on team
                teamList.append(node)   #add all nodes in team to a new list before figuring out center
        centNode = assignCenter(teamList)
        for z in teamList:  #reassign center for all nodes in team
            if centNode != z.centerNode: #if ANY change of center, will be flagged
                changeMade = True
            z.centerNode = centNode
    return changeMade

def assignCenter(TList):   #fixes center node in memory for all in team
    TmList = list(TList)
    minDist = 0.0
    for node in TList:  #for each node in team, get other's distance to it
        node.collectiveDist = 0
        for n in TmList:  #get all distances for that one node each time
            yDist = abs(node.yAxis - n.yAxis)
            xDist = abs(node.xAxis - n.xAxis)
            node.collectiveDist += math.sqrt(pow(yDist, 2) + pow(xDist, 2))  #store total distances in each node to other nodes (if center)
    minDist = min(TList,key=attrgetter('collectiveDist'))
    return minDist

def reassignTeam(mainList, numTeams):
    teamChange = False
    cList = getCenters(mainList, numTeams)  #all center nodes, one for each team
    for node in mainList:   #for every node
        distList = []
        for c in cList:     #compare distance to each center
            yDist = abs(node.yAxis - c.yAxis)
            xDist = abs(node.xAxis - c.xAxis)
            distList.append(math.sqrt(pow(yDist, 2) + pow(xDist, 2)))  #store total distances in each node to other nodes (if center)
        newDist = min(distList) #get min distance #
        newCIndex = distList.index(newDist)
        if node.clusterTeam != cList[newCIndex].clusterTeam:
            teamChange = True
            node.clusterTeam = cList[newCIndex].clusterTeam
    return teamChange

def getCenters(nList, numTeams):
    cList = []
    for id in range(1, numTeams+1):#for each diferent center:
        tempCent = getNode(nList, id)
        cList.append(tempCent.centerNode)
    return cList

def getNode(nList, num):
    for n in nList:
        if n.clusterTeam == num:
            return n
#######################################
def main():     #commanline is <program> <#clusters <input.txt>
    fin = sys.argv[2]
    fout = "output.txt"
    numClusters = int(sys.argv[1])
    if numClusters < 1:
        print("ERROR: K Cannot be smaller than 1.  Exiting.")
        sys.exit()
    node_list = []
    backupNList = []
    file_out = open(fout, 'w') ## create an out file.txt
    with open(fin, 'r') as file_in:
        for line in file_in:      #per line:
            curr_line = line.split()    # curr_line = [a, b]
            currNode = Node(int(curr_line[0]), int(curr_line[1]), random.randint(1, numClusters))
            currNode2 = Node(int(curr_line[0]), int(curr_line[1]), random.randint(1, numClusters))  #cluster twice, keep most accurate data
            node_list.append(currNode)
            backupNList.append(currNode2)
############^^ Create node for all entries, put in list
    centerChange = True
    teamChange = True
    if numClusters <=3:
        while centerChange == True or teamChange == True: #while still changes, keep going
            centerChange = findCenter(node_list, numClusters)
            teamChange = reassignTeam(node_list, numClusters)
        for node in node_list: #print out results
            printChart(file_out, node)
    else:
        while centerChange == True or teamChange == True: #while still changes, keep going
            centerChange = findCenter(node_list, numClusters)
            teamChange = reassignTeam(node_list, numClusters)
        centerChange = True
        teamChange = True
        while centerChange == True or teamChange == True: #while still changes, keep going
            centerChange = findCenter(backupNList, numClusters)
            teamChange = reassignTeam(backupNList, numClusters)

        centers1 = getCenters(node_list, numClusters)
        centers2 = getCenters(backupNList, numClusters)

        distances = [0,0]   # get center distance data
        for c in centers1:
            distances[0] += c.collectiveDist
        for f in centers2:
            distances[1] += f.collectiveDist
        newDist = min(distances) #get min distance to center
        if newDist == distances[0]:
            for node in node_list: #print out results with best result
                 printChart(file_out, node)
        if newDist == distances[1]:
            for node in backupNList: #print out results with best result
                 printChart(file_out, node)
####################
    file_in.close()
    file_out.close()
if __name__ == '__main__':
    main()
