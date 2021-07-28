// Author: Laura Webber
// Assignment Number: Lab 5
// File Name: L5_webber.cpp
// Course/Section: COSC 1337 Section 009
// Date: 10/22/17
// Instructor: Dr. B. Hohlt
//
/*
		This program sorts and searches objects, then 
		displays array data.
*/


// This program demonstrates how an overloaded constructor 
// that accepts an argument can be invoked for multiple objects 
// when an array of objects is created.
#include <iostream>
#include <iomanip>
#include <cmath>		// pow function
using namespace std;

// class declaration
class Circle
{	private:
		double radius;                    // Circle radius
		
	public:
		Circle();                          // Default constructor 		
		Circle(double r);                  // Constructor 2	
		void setRadius(double r);
		double getRadius() const;			
		double findArea() const;		
};
// function prototypes

void sortArray(Circle[], int);
void displayArray(const Circle[], int);
int binarySearch(const Circle[], int size, double value);

/////////////////////////MAIN FN////////////////////////////////////////////	
	int main()
{
	// Define an array of 7 Circle objects. Use an initialization list
	// to call the 1-argument constructor for the first 3 objects.
	// The default constructor will be called for the final object.
	const int NUM_CIRCLES = 7;				//Size of array, # of circles
	double rad_search;						// search key term
	int	results;							//returned position of found value
	
	//initialize objects
	Circle circle[NUM_CIRCLES] = {2.5, 4.0, 1.0, 3.0, 6.0, 5.5, 2.0};       

	//display current array for user
	cout << "The following radii are collected in an array.\n";
	cout << fixed << setprecision(2);
	for (int index = 0; index < NUM_CIRCLES; index++)
	{
		cout << circle[index].getRadius() << " ";
	}
	
	//display sorted array for user, assign to circles
	sortArray(circle, NUM_CIRCLES);     
	     
	// Display the area of each object
	cout << fixed << showpoint << setprecision(2);
	cout << "\n\nThe radii of the " << NUM_CIRCLES << " circles"
	     << " have been sorted by size \n"
		 << "and assigned to a circle corresponding to their order:\n";   	 
	displayArray(circle, NUM_CIRCLES);
	cout << endl;       	     
	cout << "Here are the areas of those " << NUM_CIRCLES 
	     << " circles.\n";
		  
	for (int index = 0; index < NUM_CIRCLES; index++)
	{	cout << "circle " << (index+1) << setw(8)
		     << circle[index].findArea() << endl;
	}
	
	// Prompt user for intput to serach for
	cout << "\nPlease enter a number between 1 and 6\n"
		 << "to search for a circle with that radius: ";
	cin >> rad_search;
	
	//position of found value (-1 if not found)		 
	results = binarySearch(circle, NUM_CIRCLES, rad_search);
	
	//if not found
	if (results ==-1)
		cout << "\nThat radius was not found in the array.\n";
		
	// display results for user when found
	else
	{
		cout << "\nThe radius " << rad_search << " was found as radius\n"
			 << "for circle number: " << results+1 << ".\n";
		cout << "The area for this circle is: "
			 << circle[results].findArea() << ".";
		
	}
	return 0;
}
///////////////////////////////END MAIN//////////////////////////////////////


// class member function definitions

Circle::Circle()       			// Default constructor 
{  radius = 1.0;      			// accepts no arguments

}

Circle::Circle(double r)		// Constructor 2
{  radius = r;         			// accepts 1 argument

}
void Circle::setRadius(double r)  // Setter for radius
{  radius = r;
}

double Circle::getRadius() const  // Getter for radius
{  return radius;
}

double Circle::findArea() const   // Calc area member function
{  return 3.14 * pow(radius, 2);
}
// function definitions

/****************************************************
* 	Bubble Sort  - Sort array in ascending order	*
*	size is # of elements in array elements			*
*	are compared, and swapped if lesser	# found.	*
****************************************************/
void sortArray(Circle array[], int size)
{
	Circle temp;   //holding space needed if swap
	bool swapMade;   	//flag for if swap occurs
	
	do  //binary search for entered term
	{   swapMade = false;
		for (int count = 0; count < (size-1); count++)
		{
			if (array[count].getRadius() > array[count+1].getRadius())
			{
				temp = array[count];
				array[count] = array[count + 1];
				array[count + 1] = temp;
				swapMade = true;
			}		
		}
		
	 //while loop to check for more swaps after one is made	
	} while (swapMade); 
}

/****************************************************
*			Display array contents					*
*		size is # elements in array.				*
*		Step through array, displaying each element.*
****************************************************/
void displayArray(const Circle object[], int size)
{
	for (int index = 0; index < size; index++)
	{
		cout << "circle " << (index+1) << setw(8);
		cout << object[index].getRadius()<< endl;
	}
}

/****************************************************
*				Binary Search						*
*	Search for user input, return -1 if not found   *
****************************************************/
int binarySearch (const Circle array[], int size, double value)
{
	int first = 0,
		last = size - 1,
		middle,
		position = - 1;
	bool found = false;

	while (!found && first <=last)
	{ 
		middle = (first + last) / 2;
		if (array[middle].getRadius() == value)
		{
			found = true;
			position = middle;			
		}
		else if (array[middle].getRadius() > value)
			last = middle - 1;
		else
			first = middle + 1;
	}
	return position; //return element # of found value
}

/******************

The following radii are collected in an array.
2.50 4.00 1.00 3.00 6.00 5.50 2.00 

The radii of the 7 circles have been sorted by size 
and assigned to a circle corresponding to their order:
circle 1    1.00
circle 2    2.00
circle 3    2.50
circle 4    3.00
circle 5    4.00
circle 6    5.50
circle 7    6.00

Here are the areas of those 7 circles.
circle 1    3.14
circle 2   12.56
circle 3   19.62
circle 4   28.26
circle 5   50.24
circle 6   94.98
circle 7  113.04

Please enter a number between 1 and 6
to search for a circle with that radius: 3

The radius 3.00 was found as radius
for circle number: 4.
The area for this circle is: 28.26. 

****************************/

