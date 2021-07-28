import argparse
import os
import sys

class Node():
    def __init__(self, node_name):
        self.ID = node_name
        self.neighbors = []
        self.weights = []
        self.chartNeighbors = []
        self.chartWeights = []
        self.chartNext = []
        self.package = []
        self.incomingPackBuf = [] #list of packages

def printChart(fObj, aNode, round):
    if round == -1 or round == 0:
        round = "initial charts"
    fObj.write("\nChart for Node: " + str(aNode.ID))
    fObj.write("\nRound: " + str(round))
    fObj.write("\n" +'=' * 20)
    fObj.write("\nDest\tCost\tNext\n")
    neib = aNode.chartNeighbors[:] #list of neighbors
    neib.sort()
    for n in neib:
        ind = aNode.chartNeighbors.index(n)
        fObj.write(str(aNode.chartNeighbors[ind]) + "\t" + str(aNode.chartWeights[ind]) + "\t" + str(aNode.chartNext[ind]) + "\n")
    fObj.write('-' * 20 + "\n")

def findIndex(val, aList):
    if not aList:
        return -1
    for i in aList:  #iterate through nodes in main list
        if val == i.ID:
            return aList.index(i) #return index of node

def search(val, aNode):
    if not aNode.neighbors:
        return False
    for i in aNode.neighbors:  #iterate through nodes in main list
        if val == i:
            return True

def addToNode(aNode, listToGetFrom):
    aNode.neighbors.append(listToGetFrom[1]) #add neighbor,
    aNode.weights.append(listToGetFrom[2])
    addToChart(aNode, listToGetFrom)  #assign weight

def addToChart(bNode, blistToGetFrom):
    bNode.chartNeighbors.append(blistToGetFrom[1])
    bNode.chartWeights.append(blistToGetFrom[2])
    bNode.chartNext.append(blistToGetFrom[1])  #assign weight

def fillChart(bNode, blistToGetFrom):
    bNode.chartNeighbors.append(blistToGetFrom[0])
    bNode.chartWeights.append(blistToGetFrom[1])
    bNode.chartNext.append(blistToGetFrom[2])  #assign weight

def createNode(cLine, nList):
    existingNodeIn = findIndex(cLine[0], nList)
    if existingNodeIn>=0: #return index if node exists in list
        if search(cLine[1], nList[existingNodeIn]): #true if neighbors in neighbor list
            pass   #node in list, neighbor exists
        else:  #neighbor not in neighbor list
            addToNode(nList[existingNodeIn], cLine) #add neighbor,
    else: #node not in main
        currNode = Node(cLine[0])
        addToNode(currNode, cLine)
        nList.append(currNode)
    return nList

def retChartNbrIndex(val, aList):
    if not aList:
        return -1
    if val in aList:
        return aList.index(val)
    else:
        return -1 #return index of node

def bufferIntake(node_list, last):
    lastChanged= last
    changesMade = 0
    for node in node_list:
        for pack in node.incomingPackBuf:
            immNeigbors = pack[0]   #1 value per iteration 3 total
            nBrsNbr = pack[1]
            wghts = pack[2]
            nbrNbrInx = 0
            for nbrNbrInx in range(len(nBrsNbr)):
                cNbrIndex = retChartNbrIndex(nBrsNbr[nbrNbrInx], node.chartNeighbors)  #find neighbor neighbors index in their chart
                if cNbrIndex>=0:  #chartneighbor in chart already
                    newWeightIndex = nBrsNbr.index(nBrsNbr[nbrNbrInx]) #compare chartWeights
                    NW = node.chartWeights[node.chartNeighbors.index(immNeigbors)] + wghts[newWeightIndex]
                    if NW < node.chartWeights[cNbrIndex]:
                        node.chartWeights[cNbrIndex] = NW
                        node.chartNext[cNbrIndex] = immNeigbors
                        changesMade +=1
                        lastChanged = node.ID
                    else:
                        pass
                else: #neighbor not in chartNeighbors, add to chartneighbors
                    hopIndex = retChartNbrIndex(nBrsNbr[nbrNbrInx], node.chartNext) #check if exist in hops already, will need to add thst data
                    if hopIndex>=0:     #hop exists in chartx
                        totalWeight = node.chartWeights[hopIndex] + wghts[hopIndex] #add hop weight to old weght
                        #add neighbor with new weight to chartNeighbors
                        NbrWgtHop = [nBrsNbr[nbrNbrInx], totalWeight, immNeigbors]     #add neighbor with new weight to chartNeighbors
                        fillChart(node, NbrWgtNxt)
                        changesMade +=1
                        lastChanged = node.ID
                    else:   #neighbor not in chart, hop does not exist in chart
                        if nBrsNbr[nbrNbrInx] == node.ID:#if neighbor neighbor data is node.ID, then toss
                            pass
                        else:
                            hIndex = retChartNbrIndex(immNeigbors, node.chartNext)
                            tWeight = node.chartWeights[hIndex] + wghts[nbrNbrInx] #add hop weight to old weght
                            node.chartWeights[hIndex]
                            NWH = [nBrsNbr[nbrNbrInx], tWeight, immNeigbors]
                            fillChart(node, NWH)
                            changesMade +=1
                            lastChanged = node.ID
    result = [node_list, changesMade, lastChanged]
    return result

def makePackage(node_list):
    for node in node_list:     #MAKE PACKAGES for all (initial)
        node.package.extend([node.ID, node.chartNeighbors, node.chartWeights])
    return node_list

def sendPackage(node_list, numPackagesSent):
    for node in node_list:  #PUT PACKAGES IN NEIGHBOR BUFFER
        for nbr in node.neighbors:     #for all neighbors in node
            index = findIndex(nbr, node_list)  #find neighbor in node_list
            node_list[index].incomingPackBuf.append(node.package)   #send package to all neighbors' node buffer for intake
            numPackagesSent += 1    #increase num packages sent
    return [node_list, numPackagesSent]

def findDest(node_list, src, dest):
    idx = findIndex(src, node_list)
    nIndx = node_list[idx].chartNeighbors.index(dest)   #get where dest is in chart
    hop = node_list[idx].chartNext[nIndx]       #get the next dest
    return hop
#######################################
def main():     #commandline is <program> <input.txt> <output.txt> <rounds to run> <start node> <end node>
    fin = sys.argv[1]
    fout = sys.argv[2]
    roundsToRun = int(sys.argv[3])
    node_list = []
    lineCount = 0
    file_out = open(fout, 'w') ## create an out file.txt
    with open(fin, 'r') as file_in:
        for line in file_in:      #per line:
            curr_line = line.split()    # curr_line = [a, b, 22]
            for i in range(0, len(curr_line)):
                curr_line[i] = int(curr_line[i])
            node_list = createNode(curr_line, node_list)
        file_in.seek(0)###check all neighbors for some not listed in first
        for line in file_in:      #per line:
            curr_line = line.split()    # curr_line = [a, b, 22]
            for i in range(0, len(curr_line)):
                curr_line[i] = int(curr_line[i])
            temp = curr_line[0]
            curr_line[0] = curr_line[1]
            curr_line[1] = temp
            node_list = createNode(curr_line, node_list)

    OL = [] #sort the nodes in the list by ID
    nodesInOrder = []
    for node in node_list:
        OL.append(node.ID)
    OL.sort()
    for id in OL:
        for nD in node_list:
            if nD.ID == id:
                nodesInOrder.append(nD)

    numPackagesSent = 0
    roundCount = 0
    lastChanged = -1
    converge_flag = False
    nodesInOrder = makePackage(nodesInOrder)     #MAKE PACKAGES for all (initial)
    pkSent = sendPackage(nodesInOrder, numPackagesSent)
    nodesInOrder = pkSent[0]
    numPackagesSent = 0

    if roundsToRun < 1: #round number invalid, 0 or negative
        file_out.write("TOPOLOGY REPORT FOR ROUND: " + str(roundsToRun) + "\n")
        for node in nodesInOrder:
            printChart(file_out, node, roundsToRun)
        file_out.write("\nDV PACKETS SENT UP TO ROUND " + str(roundsToRun) + ": NONE")
        file_out.write("\nLAST NODE TO UPDATE FOR ROUND " + str(roundsToRun) + ": NONE")
    while converge_flag==False: #while still changes, keep going
        roundCount +=1
        nodesInOrder = makePackage(nodesInOrder)     #MAKE PACKAGES for all (initial)
        pkSent = sendPackage(nodesInOrder, numPackagesSent)
        nodesInOrder = pkSent[0]
        numPackagesSent = pkSent[1]
        bufResult= bufferIntake(nodesInOrder, lastChanged) #does only 1 round of updates, checks
        nodesInOrder = bufResult[0]
        lastChanged = bufResult[2]
        if bufResult[1] == 0:  #if no changes were made
            converge_flag = True
            break

        for node in nodesInOrder: #package and buffer flush
            del node.package[:]
            del node.incomingPackBuf[:]
        lastChangedReal = lastChanged   #want to save numbers before convergence round overwrites
        numPackagesSentReal = numPackagesSent #want to save numbers before convergence round overwrites
        roundReal= roundCount #want to save numbers before convergence round overwrites
        if  roundCount == roundsToRun:
            file_out.write("TOPOLOGY REPORT FOR ROUND: " + "\n")
            for node in nodesInOrder:
                printChart(file_out, node, roundsToRun)
            file_out.write("\nDV PACKETS SENT UP TO ROUND " + str(roundCount) + ": " + str(numPackagesSentReal))
            file_out.write("\nLAST NODE TO UPDATE FOR ROUND " + str(roundCount) + ": " + str(lastChangedReal))

    if  roundCount <= roundsToRun:
        file_out.write("TOPOLOGY REPORT FOR ROUND: " + str(roundCount) + "\n")
        for node in nodesInOrder:
            printChart(file_out, node, roundsToRun)
        file_out.write("PROGRAM HAS REACHED CONVERGENCE\n" + "-"*20)
        file_out.write("\nDV PACKETS SENT UP TO ROUND " + str(roundsToRun) + ": " + str(numPackagesSentReal))
        file_out.write("\nLAST NODE TO UPDATE FOR ROUND " + str(roundsToRun) + ": " + str(lastChangedReal))
    file_out.write("\n\nTOTAL ROUNDS UNTIL COMPLETE CONVERGENCE: "+ str(roundReal))       #count rounds, track last changes
    file_out.write("\nDV PACKETS SENT UP TO CONVERGENCE: "+ str(numPackagesSentReal))
    file_out.write("\nLAST NODE TO UPDATE BEFORE CONVERGENCE: "+ str(lastChangedReal))

    ########Routing Packets  Can be un-commented for command line (beginning and ending node for routing after round entry)
    src = A = int(sys.argv[4])
    dest = B = int(sys.argv[5])
    file_out.write("\nRouting package from  " + str(src) + " to "+ str(dest) + ":")

    sIndx = findIndex(src, node_list)
    dIndx = findIndex(dest, node_list)
    if sIndx==None or dIndx ==None: #node not in file
        file_out.write("\nStarting/ending node not in file.\nNo travel can be taken.")
    elif src == dest:#nodes entered are same
        file_out.write("\nSource and destination are the same.  No travel taken.")
    elif dest in node_list[sIndx].neighbors: #check if immediate neighbor
        file_out.write("\n" + str(src) + " to "+ str(dest))
    else:   #find path
        A = findDest(nodesInOrder, src, dest)
        file_out.write("\n" + str(src) + " to "+ str(A))
        C = -1
        while C != dest:
            C = findDest(nodesInOrder, A, dest)
            file_out.write("\n" + str(A) + " to "+ str(C))
            A = C
    #########################

    file_in.close()
    file_out.close()

if __name__ == '__main__':
    main()
