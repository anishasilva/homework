#include <stdio.h>
#include <stdlib.h>


int main() {
	char* strings[] = {"foo", 35, "baz", "bar", NULL};
	int i = 0;
	while (strings[i] != NULL){
		printf("%s\n", strings[i++]);
	}
	return 0;
}
