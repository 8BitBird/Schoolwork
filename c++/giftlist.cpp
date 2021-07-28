//Laura Webber

#include <iomanip>
#include <iostream>
#include <string>
#include <bits/stdc++.h>

class Kid
{
	public:
		std::string name;
		std::vector<std::string> gifts;
}; 
/////////////////////////////////////		
int main()
{
	std::vector<Kid> niceList;
	std::string gift, name;
	std::cout <<"Add a name to the nice list: \n";
	std::getline(std::cin, name);
	//kid loop
	while (!name.empty())
	{	
		Kid k;
		k.name = name;
		std::cout << "Enter gift for " << k.name << ": ";
		std::getline(std::cin, gift);
		//gift loop
		while (!gift.empty())	
		{
			k.gifts.push_back(gift);
			std::cout << "Enter gift for " << k.name << ": ";
			std::getline(std::cin, gift);
		}
		niceList.push_back(k);
		std::cout <<"\nAdd a name to the nice list: \n";
		std::getline(std::cin, name);
	}
//////////Display list of kids and gifts on list///////////
		std::cout << "Kids on nice list with gifts:\n" ;
		//display 1 name, then all gifts for that kid
	    for (int i = 0; i < niceList.size(); i++) 
		{
			Kid k = niceList.at(i);
			std::cout << k.name << ": ";
	        for (int j = 0; j < k.gifts.size(); j++)
	            std::cout << k.gifts.at(j) << ", ";
	        std::cout << std::endl;
	    }
	
	return 0;
}
