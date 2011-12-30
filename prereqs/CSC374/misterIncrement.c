/*
 *  Compile with:
 *  linux> gcc misterIncrement.c -o misterIncrement -lpthread
 */
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_THREADS	2

int count = 0;

sem_t semaphore;

void*	increment (void* ptr)
{
  int incAmount = *(int*)ptr;
  int i;

  for (i = 0;  i < incAmount;  i++)
  {
    sem_wait(&semaphore);
    count++;
    sem_post(&semaphore);
  }

  return(NULL);
}


int	main (int argc, const char* argv[])
{
  pthread_t	threadArray[NUM_THREADS];
  int           i;
  int		incAmount = -1;

  if  (argc >= 2)
    incAmount = atoi(argv[1]);

  while  (incAmount < 0)
  {
    char text[10];

    printf("Please enter an increment amount for each thread: ");
    fgets(text,10,stdin);
    incAmount = atoi(text);
  }

  sem_init(&semaphore,0,1);
  for (i = 0;  i < NUM_THREADS;  i++)
    pthread_create(&threadArray[i],NULL,increment,(void*)&incAmount);

  for (i = 0;  i < NUM_THREADS;  i++)
    pthread_join(threadArray[i],NULL);

  sem_destroy(&semaphore);
  printf("count == %d while 2*incAmount == %d\n",count,2*incAmount);
  return(EXIT_SUCCESS);
}
