/* Problem 1: What does the function mystery do? 
   You should answer at two levels. First, explain in general what it 
   does by giving some sample inputs and outputs. For example, the 
   code as written generates this output

            305419896 ===> 313218680

   How did the function mystery map 305419896 to 313218680?
   If you know how the function works, you might also suggest a better
   name for it.
 
 ANSWER: The most lucid(it should be noted that i'm not having the best time lucidly explaining the concepts i believe i've grasped so far in this course) 
 way I think for me to explain what the mystery does is that it goes to a at its address at b
 (that is b + ptr, which is 2 bytes over from the position of a(or any number of bytes over as long as its a number with in bounds, which the if statement in the function will change an out of bounds b argument to 0 and in that case the function will just replace the byte the pointer specifically points to which will be different on little endian machinces vs big endian(side note:i had a powerpc g5 that i just sold a few weeks ago that i wish i woulda kept to play around with now that i'm experimenting and learning all this vs my new intel machine.)) and replaces what is in that byte.  The better name might be
 "put_c_in_a_at_position_b".  In a small endian machine this will 

   Second, document the function's parameters. If you know what the parameters are, 
   you know what the function does. 
*/

#include <stdio.h>

unsigned int mystery(unsigned int  a,       
		     unsigned int  b,      
		     unsigned char c) {    /* a char is 8 bits */
  unsigned char* ptr = (unsigned char*) &a; /* make ptr point to a */
  unsigned int ret_val;

  if (b >= sizeof(unsigned int)) b = 0;

  *(ptr + b) = c;  /* In general, if ptr is a pointer, then *ptr is the
                      value pointed at. So, for example, if 

                        int n = 27;
                        int* ptr = &n;

                                    0x1234
                                    +----+
                          ptr ----->| 27 |
                                    +----+
                                      n
                      
                      with 0x1234 as the address of n, then *ptr is 27.
		   */

  /* ptr is of type unsigned char*, that is, pointer to unsigned char; but
     the function is supposed to return an int. So, first, we cast ptr's
     value to unsigned int* (pointer to unsigned int):

              (unsigned int*) ptr

     However, we need to return an unsigned int, not a pointer to an 
     unsigned int. So we dereference the pointer:

             *((unsigned int*) ptr)

     If we just did

             *ptr

     we'd get an unsigned char rather than the unsigned int that we want.
  */
  ret_val = *((unsigned int*) ptr);
  return ret_val; /* return an unsigned int */
}
  
int main() {
  /* sample run */
  unsigned int n = 305419896;
  unsigned char c = 171;
	printf("%x\n", c);
  unsigned result = mystery(n, 2, c);
	/*printf("%u is n and %u is c and %u is result\n", n, c, result);*/
  printf("%x ===> %x--------%x\n", n, result,c );
  return 0;
}

