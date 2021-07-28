//Laura Webber Lab 2

#include <iomanip>
#include <iostream>
#include <string>



class Node 
{
	private:
		char item;
		Node * next;
	public:
    char getItem() const 
	{
        return item;
    }
    void setItem(char item) 
	{
        Node::item = item;
    }
    	Node *getNext() const 
	{
        return next;
    }
	void setNext(Node *next) 
	{
        Node::next = next;
    }

};

class Chain 
{
	private:
		Node * head;
		int itemcount;
	public:
		Chain():head(nullptr),itemcount(0) {
		}
		
        Chain (const std::string s):head(nullptr),itemcount(0) 
		{
            Node * newNode = new Node();
            newNode->setItem( s[0]);
            newNode->setNext ( nullptr);
            head = newNode;
            Node * curr = head;

            for (int i=1; i < s.length(); i++) 
			{
                Node * newNode = new Node();
                newNode->setItem( s[i]);
                newNode->setNext ( nullptr);
                curr->setNext ( newNode);
                curr = curr->getNext();
            }
            itemcount = s.length();
    	}


		void append(const Chain& lc)
		{	///iterate to end	
            Node * newNode = new Node();
			Node * curr = head;
			Node * tmpNode = lc.head;
            while (curr->getNext() != nullptr) 
			{
                curr = curr->getNext();
            }    
            ////once at end of list1
            while (tmpNode !=nullptr)
				{
	                Node * newNode = new Node();
	                newNode->setItem(tmpNode->getItem());
	                newNode->setNext ( nullptr);
	                curr->setNext ( newNode);
	                curr = curr->getNext();
	                itemcount++;
	                tmpNode = tmpNode->getNext();	              
            	}
 		}
		
        ////////////////////////////
        bool substring(const Chain& ls) const
    	{
            // find item
            Node * mainCurr = head;  //main head
            Node * secCurr = ls.head;  //2nd list head
            while (mainCurr != nullptr)
			{
				while (mainCurr->getItem() == secCurr->getItem()) 	
				{	
		        	mainCurr = mainCurr->getNext();
		            secCurr = secCurr->getNext();
					
					if  (secCurr == nullptr)
		            	return true;
					else
						{
							//std::cout << mainCurr->getItem() << std::endl; 
							//std::cout << secCurr->getItem() << std::endl; 	
						}
				}
				secCurr = ls.head;		
				mainCurr = mainCurr->getNext();	    
			}
		}         
		////get length//////////
	    int length() const {
	        return itemcount;
	    }
				
		////////find index///////////////////
		int locate(char item) const
		{
            // find item
            int index = 0;
            Node * curr = head;
            while (curr != nullptr && curr->getItem() != item) 
			{
                curr = curr->getNext();
                index++;
            }    
            if (curr == nullptr)
            {
            	return -1;
        	}
			else   	
            {    
				return index +1;	
        	}
    	}
		/////display///////
		void display() const 
		{
			Node * curr = head;
			std::cout << "Chain:";
			while (curr != nullptr) {
				std::cout << " " <<  curr->getItem();
				curr = curr->getNext();
			}
			std::cout << std::endl;
		}
		/////////////////
    	void clear() 
		{
	        while (head != nullptr) {
	            Node *tmpNode = head;
	            head = head->getNext();
	            delete tmpNode;
        }
    }

	~Chain () { }
};

/////////////////////////////////////////////////////////////////
//////////////////////////MAIN//////////////////////////////////
int main() 
{		
		char choice, charToLocate, charToRemove;
		std::string  stringNew, stringToAdd, subString;

	   	/*Loop till user chooses to exit.*/
	   	
	   	std::cout <<"We will create a new chain.\n";
		std::cout <<"Enter in a string to be converted to a chain:\n";
		std::getline(std::cin, stringNew);
		Chain list1(stringNew);
		list1.display();
		std::cout << std::endl;

		std::cout << "A for append to an existing chain.\n";
		std::cout << "F for finding location of a character in chain.\n";
		std::cout << "S to test if another string is a substring. \n";
		std::cout << "L to get the length of the chain. \n";
		std::cout << "Q to quit program.\n";
		std::cout << std::endl;
		std::cout << "Please enter your choice: ";
		std::cin >> choice;
		choice = tolower(choice);
		std::cout << std::endl;
		while (choice != 'q')
		{
			if (choice == 'l') 
			{
				std::cout <<"The current length of the chain is: \n";
				std::cout << list1.length();
				std::cout << "\nPlease enter another action or Q to quit.\n";
			   	std::cin >> choice;
			   	choice = tolower(choice);
		    }
			else if (choice == 'a') 
			{
		       	std::cout <<"We will append to the chain.\n";
		    	std::cout <<"Enter in a string to be appended to the chain.\n"; 
				std::cin.ignore();
				std::getline(std::cin, stringToAdd);		
		    	Chain list2(stringToAdd);
				std::cout << "Adding: "; 
				list2.display();
				list1.append(list2);
				list1.display();
				std::cout << "Please enter another action or Q to quit.\n";
			   	std::cin >> choice;
			   	choice = tolower(choice);
		    }
		  
			else if (choice == 'f') 
			{	
		   		std::cout <<"We will find the location of a character in the chain.\n";
		   		std::cout << "Enter the character to be located. (case sensitive)\n";
		   		std::cin >> charToLocate;
				if (list1.locate(charToLocate) == -1)
					std::cout << "Character was not found.\n";
				else
					std::cout << "Location:" << (list1.locate(charToLocate));    
		   		std::cout << "\nPlease enter another action or Q to quit.\n";
		    	std::cin >> choice;
		    	choice = tolower(choice);
		   	}	
		   	else if (choice == 's') 
			{	
		   		std::cout <<"We will test if a substring exists in the chain.\n";
		   		std::cout << "Enter the string to be tested. (case sensitive)\n";
		   		std::cin >> subString;
		   		Chain list3(subString);
		   		list3.display();
				if (!list1.substring(list3))
					std::cout << "\nSubstring not found.\n";
				else
					std::cout << "\nSubstring found! \n"; 
				list3.clear();	  
		   		std::cout << "Please enter another action or Q to quit.\n";
		    	std::cin >> choice;
		    	choice = tolower(choice);
		   	}	
		   	else 
			{
				std::cout << "Please input a valid choice.\n";
				std::cin >> choice;
				choice = tolower(choice);
			}
		}
	std::cout << "Now exiting program.\n";
	return 0;
}
