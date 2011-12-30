#include <stdio.h>

char* my_gets(char* buffer) {
  int c;  /* NB: int rather than char because the system I/O function
	     getchar() returns the int value 0xffffffff on end-of-file */
  char* dest = buffer;
  
  /* read until newline or end-of-file (Control-Z for the standard input) */
  while ((c = getchar()) != '\n' && c != EOF)
    *dest++ = c; /* store next character in buffer and move pointer */
  
  if (c == EOF) return NULL; /* NULL is 0, the invalid address */
  
  return buffer; /* now filled with characters from getchar() */
}

int main() {
  char buf[4];                   /* big trouble waiting to happen */
  char* flag = my_gets(buf);     /* flag is NULL or non-NULL */
  if (flag) printf("%s\n", buf); /* print if non-NULL */
  
  return 0;
}
/* output from sample runs:

    $ ./stack_corrupt
    a b 
    a b

    $ ./stack_corrupt
    a b c d e
    a b c d e
    Segmentation fault

   The problem:

                +=======================+ <--- main's call frame
                |        ...            |
                |-----------------------| 
                | return address        | <--- saved %eip for main
                +=======================+ <--- my_gets's call frame
                | saved stack pointer   | <--- saved %ebp for main
                |-----------------------|
                | [3] | [2] | [1] | [0] | <--- buffer in my_gets (LE)
                |-----------------------|
                |        ...            |
                +=======================|

   At the assembler level (assembler for main):

     main:
            pushl %ebp          ;; save main's call frame pointer
            movl %esp, %ebp     ;; create call frame for my_gets
            subl $20, %esp      ;; grow the call frame for my_gets
            pushl %ebx          ;; save %ebx on the stack
            addl $-12, %esp     ;; grow the call frame some more
            leal -4(%ebp), %ebx ;; compute buf as %ebp - 4
            pushl %ebx          ;; save new %ebx == buf on the stack
            call my_gets        ;; transfer control to my_gets
*/
