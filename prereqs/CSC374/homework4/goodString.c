

#include <stdlib.h> 
#include <stdio.h> 
#include <string.h>


const int STRING_LEN = 128;
const int NUMBER_LEN = 5;

void removeNewline (char* input) {
	int i;
	for ( i = 0; i < STRING_LEN; i++){
		if ( input[i] == '\n'){
			input[i] = '\0';
			break;
		}
	}
}

void enterName (char* namePtr)
{
	printf("Please enter your name: ");
	fgets(namePtr, STRING_LEN, stdin);
}

void enterAge (int* agePtr)
{
	char numberText[NUMBER_LEN];
	printf("Please enter your age: ");
	fgets(numberText, NUMBER_LEN, stdin);
	*agePtr = atoi(numberText);
}

void enterFavoriteColor (char* itemNamePtr, char* entryPtr)
{
	printf("Please enter your favorite color for a %s: ", itemNamePtr);
	fgets(entryPtr, STRING_LEN, stdin);
}

void printInfo (char* namePtr, int age, char* carColorPtr, char* houseColorPtr)
{
	char designation[STRING_LEN];

	snprintf(designation, STRING_LEN, "%s who is %d years old", namePtr, age);
	printf("%s likes the car color %s.\n", designation, carColorPtr);

	if (strncmp(carColorPtr, houseColorPtr, STRING_LEN) == 0)
		printf("He/She likes the same color for houses, too.\n");
	else
		printf("However, he/she prefers houses colored %s. \n", houseColorPtr);

}

int main()
{
	char name[STRING_LEN];
	int age;
	char carColor[STRING_LEN];
	char houseColor[STRING_LEN];

	enterName(name);
	removeNewline(name);
	
	enterAge(&age);
	
	enterFavoriteColor("car", carColor);
	removeNewline(carColor);
	
	
	enterFavoriteColor("house", houseColor);
	removeNewline(houseColor);
	
	printInfo(name, age, carColor, houseColor);
	return(EXIT_SUCCESS);
}