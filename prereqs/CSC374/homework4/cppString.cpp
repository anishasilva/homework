

#include <string>
#include <iostream>
#include <sstream>

using namespace std;



string enterName ()
{
	string name;
	cout << "Please enter your name: ";
	getline (cin, name);
	return name;
}

int enterAge ()
{
	int age;
	string numberText;
	cout << "Please enter your age: ";
	getline (cin, numberText);
	stringstream(numberText) >> age;
	return age;

}

string enterFavoriteColor (string itemName)
{
	string entry;
	cout << "Please enter your favorite color for a " << itemName << ": ";
	getline (cin, entry);
	return entry;
}

void printInfo (string name, int age, string carColor, string houseColor )
{

	cout << name << " who is " << age << " years old likes the car color " << carColor << ".\n";

	if (carColor == houseColor)
		cout << "He/She likes the same color for houses, too.\n";
	else
		cout << "However, he/she prefers houses colored " + houseColor + ".\n";

}


int main()
{
	string name;
	int age;
	string carColor;
	string houseColor;
	
	name = enterName();
	age = enterAge();
	carColor = enterFavoriteColor("car");
	houseColor = enterFavoriteColor("house");
	printInfo(name, age, carColor, houseColor);
	return(EXIT_SUCCESS);
}