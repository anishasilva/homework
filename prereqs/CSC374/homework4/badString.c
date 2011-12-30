

#include <stdlib.h> 
#include <stdio.h> 
#include <string.h>


#define STRING_LEN  (20)
#define NUMBER_LEN  (3)

void enterName (char* namePtr)
{
	printf("Please enter your name: ");
	gets(namePtr);
}

void enterAge (int* agePtr)
{
	char numberText[NUMBER_LEN];

	printf("Please enter your age: ");
	gets(numberText);
	*agePtr = atoi(numberText);
}

void enterFavoriteColor (char* itemNamePtr, char* entryPtr)
{
	printf("Please enter your favorite color for a %s: ", itemNamePtr);
	gets(entryPtr);
}

void printInfo (char* namePtr, int age, char* carColorPtr, char* houseColorPtr)
{
	char designation[STRING_LEN];
	sprintf(designation, "%s who is %d years old", namePtr, age);
	printf("%s likes the car color %s.\n", designation, carColorPtr);

	if (strcmp(carColorPtr, houseColorPtr) == 0)
		printf("They do like the same color for houses, too. \n");
	else 
		printf("however, they prefer houses colored %s.\n", houseColorPtr);

}

int main()
{
	char name[STRING_LEN];
	int age;
	char carColor[STRING_LEN];
	char houseColor[STRING_LEN];

	enterName(name);
	enterAge(&age);
	enterFavoriteColor("car", carColor);
	enterFavoriteColor("house", houseColor);
	printInfo(name, age, carColor, houseColor);
	return(EXIT_SUCCESS);
}