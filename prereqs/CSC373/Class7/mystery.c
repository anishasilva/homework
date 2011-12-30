#include <stdio.h>

int mystery(int n1, int n2) {
	unsigned char* ptr1 = (unsigned char*) &n1;
	unsigned char* ptr2 = (unsigned char*) &n2;
	
	printf("%x\n", ptr1);
	
	printf("%x\n", ptr2);
	
	int ret_val;
	unsigned char* ptr3 = (unsigned char*) &ret_val;
	
	printf("%x\n", ptr3);
	ptr2 += sizeof(int) - 1;
	ptr3 += sizeof(int) - 1;
	printf("%x\n", ptr2);
	
	printf("%x\n", ptr3);
	
	int i;
	for (i = 0; i < sizeof(int); i++) {
		*ptr3 = *ptr2;
		ptr3--;
		ptr2--;
	}
	
	ptr3++;
	*ptr3 = *ptr1;
	
	return ret_val;
}

int main(){
	int x1 = 0x12345678, x2 = 0x12345678;
	int ans = mystery(x1, x2);
	printf("the mystery outputs: %x\n", ans);
	return 0;
}