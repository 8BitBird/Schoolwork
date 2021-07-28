#include <memory>
#include "BinaryTreeInterface.h"
#include "BinaryNode.h"
#include "BinaryNodeTree.h"
#include "NotFoundException.h"
#include "PrecondViolatedExcep.h"
#include "BinarySearchTree.h"

template<class ItemType>
BinarySearchTree<ItemType>::BinarySearchTree()
{
}

template<class ItemType>
BinarySearchTree<ItemType>::~BinarySearchTree()
{
}

template<class ItemType>
bool BinarySearchTree<ItemType>::add(const ItemType& newData)
{
	auto newNodePtr = std::make_shared<BinaryNode<ItemType>>(newData);
	rootPtr = placeNode(rootPtr, newNodePtr);
	
	return true;
} //end add

//Recursively places node in correct place in binary searchtree
template<class ItemType>
auto BinarySearchTree<ItemType>::placeNode(std::shared_ptr<BinaryNode<ItemType>> subTreePtr,
			   std::shared_ptr<BinaryNode<ItemType>> newNodePtr)
{
	if (subTreePtr == nullptr)
		return newNodePtr;
	else if (subTreePtr->getItem() > newNodePtr->getItem())
	{
		auto tempPtr = placeNode(subTreePtr->getLeftChildPtr(), newNodePtr);
		subTreePtr->setLeftChildPtr(tempPtr);
	}
	else
	{
		auto tempPtr = placeNode(subTreePtr->getRightChildPtr(), newNodePtr);
		subTreePtr->setRightChildPtr(tempPtr);
	}
	return subTreePtr;
}//////end

////Removes the given target from the binary search tree to which subTree points
//Returns a pointer to the node at this tree location after the value is removed.
//Sets isSuccessful to true if the removal is successful, or else false
template<class ItemType>
std::shared_ptr<BinaryNode<ItemType>>  BinarySearchTree<ItemType>::removeValue(std::shared_ptr<BinaryNode<ItemType>> subTreePtr,
				 const ItemType target,
				 bool& success)
{
	bool isSuccessful = false;
	if (subTreePtr == nullptr)
	{
		isSuccessful = false;
	}
	else if (subTreePtr->getItem() == target)
	{
		//Item is in root of subtree
		subTreePtr = removeNode(subTreePtr); // remove item
		isSuccessful = true;	
	}
	else if (subTreePtr->getItem() > target)
	{
		//search left subtree
		 auto tempPtr = removeValue(subTreePtr->getLeftChildPtr(), target, isSuccessful);
		subTreePtr->setLeftChildPtr(tempPtr);
	}
	else
	{
	//search right subtree
	auto tempPtr = removeValue(subTreePtr->getRightChildPtr(), target, isSuccessful);
	subTreePtr->setRightChildPtr(tempPtr);
	}
	return subTreePtr;
}
//removes the data item in the node. N to which nodePtr points.
//retuns a pointer to the node at this tree location after the removal.
template<class ItemType>
auto BinarySearchTree<ItemType>::removeNode(std::shared_ptr<BinaryNode<ItemType>> nodePtr)
{
	if (nodePtr->getRightChildPtr() == nullptr && nodePtr->getLeftChildPtr() == nullptr)
	{
		nodePtr = nullptr;
		//Delete the node to which nodePtr points //done if nodePtr is smart
		return nodePtr;
	}
	else if (nodePtr->getRightChildPtr() == nullptr)
	{
		auto nodeToConnectPtr = nodePtr->getRightChildPtr();
		return nodeToConnectPtr;
	}
	else if (nodePtr->getRightChildPtr() == nullptr)
	{
		auto nodeToConnectPtr = nodePtr->getLeftChildPtr();
		return nodeToConnectPtr;	
	}	
	else //N has 2 children
	{
		//Find the in order successor of the entry in N: it is the left subtree
		//at N's right child
		ItemType newNodeValue;

		auto tempPtr = removeLeftmostNode(nodePtr->getRightChildPtr(), newNodeValue);
		nodePtr->setRightChildPtr(tempPtr);
		nodePtr->setItem(newNodeValue); //put replacement value in node N
		return nodePtr;
	}
}
//removes the leftmost node in the left subtree of the node pointed to by nodePtr
//sets inorderSuccessor to the value in this node.
//return a pointer to the revised subtree.
template<class ItemType>
auto BinarySearchTree<ItemType>::removeLeftmostNode(std::shared_ptr<BinaryNode<ItemType>> nodePtr,
													ItemType& inorderSuccessor)
{
	if (nodePtr->getLeftChildPtr() == nullptr)
	{
		//this is the node you want; it has no left child but may have right subtree
		inorderSuccessor = nodePtr->getItem();
		return removeNode(nodePtr);
	}
	else
	{
		auto tempPtr = removeLeftmostNode(nodePtr->getLeftChildPtr(), inorderSuccessor);
		nodePtr->setLeftChildPtr(tempPtr);
		return nodePtr;
	}
}
//removes given data from binary search tree
template<class ItemType>
bool BinarySearchTree<ItemType>::remove(const ItemType& anEntry)
{
	bool isSuccessful = false;
	rootPtr = removeValue(rootPtr, anEntry, isSuccessful);
	return isSuccessful;
}

//////////////////////////////////////////////////////////////
//      Public Traversals Section
//////////////////////////////////////////////////////////////

template<class ItemType>
void BinarySearchTree<ItemType>::preorderTraverse(void visit(ItemType&)) const
{
   this->preorder(visit, rootPtr);
}  // end preorderTraverse

template<class ItemType>
void BinarySearchTree<ItemType>::inorderTraverse(void visit(ItemType&)) const
{
   this->inorder(visit, rootPtr);
}  // end inorderTraverse

template<class ItemType>
void BinarySearchTree<ItemType>::postorderTraverse(void visit(ItemType&)) const
{
   this->postorder(visit, rootPtr);
}  // end postorderTraverse

/////////////////////////////////
template<class ItemType>
bool BinarySearchTree<ItemType>::isEmpty() const
{
   return rootPtr == nullptr;
}  // end isEmpty

template<class ItemType>
int BinarySearchTree<ItemType>::getHeight() const
{
   return this->getHeightHelper(rootPtr);
}  // end getHeight

template<class ItemType>
int BinarySearchTree<ItemType>::getNumberOfNodes() const
{
   return this->getNumberOfNodesHelper(rootPtr);
}  // end getNumberOfNodes

template<class ItemType>
void BinarySearchTree<ItemType>::clear()
{
   this->destroyTree(rootPtr);
   rootPtr.reset();
}  // end clear

template<class ItemType>
ItemType BinarySearchTree<ItemType>::getRootData() const throw(PrecondViolatedExcep)
{
   if (this->isEmpty())
      throw PrecondViolatedExcep("getRootData() called with empty tree."); 
      
   return rootPtr->getItem();
}  // end getRootData

template<class ItemType>
void BinarySearchTree<ItemType>::setRootData(const ItemType& newData) const throw(PrecondViolatedExcep)
{
	throw PrecondViolatedExcep("getRootData() called with empty tree."); 
}  // end setRootData

template<class ItemType>
ItemType BinarySearchTree<ItemType>::getEntry(const ItemType& anEntry) const throw(NotFoundException)
{
   bool isSuccessful = false;
   auto binaryNodePtr = this->findNode(rootPtr, anEntry);
   
   if (isSuccessful)
      return binaryNodePtr->getItem(); 
   else 
      throw NotFoundException("Entry not found in tree!");
}  // end getEntry

template<class ItemType>
bool BinarySearchTree<ItemType>:: contains(const ItemType& anEntry) const
{
   bool isSuccessful = false;
   this->findNode(rootPtr, anEntry);
   return isSuccessful;   
}  // end contains

template<class ItemType>
auto BinarySearchTree<ItemType>::findNode(std::shared_ptr<BinaryNode<ItemType>> treePtr, const ItemType& target) const
{
   if (treePtr == nullptr) // not found here
      return treePtr;
   
   if (treePtr->getItem() == target) // found it
   {
      return treePtr;
   }
   else 
   {
      std::shared_ptr<BinaryNode<ItemType>> targetNodePtr = this->findNode(treePtr->getLeftChildPtr(), target);
      if (targetNodePtr == nullptr) // no need to search right subTree
      {
         targetNodePtr = this->findNode(treePtr->getRightChildPtr(), target);
      }  // end if
      
      return targetNodePtr;
   }  // end if 
}  // end findNode

