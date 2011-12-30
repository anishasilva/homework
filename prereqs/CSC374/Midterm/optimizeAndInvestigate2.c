/*
 *  optimizeAndInvestigate2.c
 *  
 *
 *  Created by Raymond Elward on 10/12/10.
 *  Copyright 2010 DePaul University. All rights reserved.
 *
 */


int limit;

int sum(int sumLimit){
	int i;
	int total = 0;
	for ( i = 1; i <= sumLimit; i++ ) {
		total += i;
	}
	return total;
}