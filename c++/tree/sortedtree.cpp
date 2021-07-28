//Lab 6 Laura Webber
// Source code (modified): Carrano and Henry
//(c) 2017 Pearson Education, Hoboken, New Jersey.
#include <iostream>
#include <memory>
#include "BinaryNodeTree.h"
#include "BinarySearchTree.h" 
#include <time.h>

void display(int& anItem)
{
   std::cout << "Displaying item: " << anItem << std::endl;
}  // end display
int main() //MAIN
{
	int n;
   	std::shared_ptr<BinarySearchTree<int>> treePtr;
    treePtr = std::make_shared<BinarySearchTree<int>>();
	srand (time(0));	         		
	for (int count = 0; count < 21; count++)	
	{	
		int num = rand() % 100 + 1;
		treePtr->add(num);
		n = num;
	}	
	treePtr->inorderTraverse(display);
	std::cout <<std::endl;
    treePtr->remove(n);
	std::cout << "Last node added: " << n << ", has been removed. \n";
    std::cout <<std::endl;
   	treePtr->inorderTraverse(display);
   	treePtr->clear();
	std::cout << "\nCleared.\n";
    std::cout << "Exiting.\n";
   return 0;
}  // end main
