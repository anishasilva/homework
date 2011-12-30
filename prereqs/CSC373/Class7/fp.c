#include <stdio.h>
#include <math.h>
main()
{
	register i;
	double sum=0.0,newsum,f;
	double sumerr=0.0;
	double err;
	double sqrt();
	for(i=1;i<=1000000000;i++)
	{
		f=sqrt( (double) i);
		newsum = sum + f;
		err = (newsum - sum ) - f;
		sumerr += err;
		sum = newsum;
	}
	printf("Finished %20.14f\n",sum);
	printf("Finished %20.14f\n",sum-sumerr);
}        