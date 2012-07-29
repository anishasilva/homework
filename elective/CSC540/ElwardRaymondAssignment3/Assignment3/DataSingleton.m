//
//  DataSingleton.m
//  Assignment3
//
//  Created by Raymond Elward on 1/22/12.
//  Copyright (c) 2012 Raymond Elward. All rights reserved.
//

#import "DataSingleton.h"

@implementation DataSingleton

static NSMutableArray *array;

+(NSMutableArray *) getArray {
	if ([array count] == 0) {
		return [[NSMutableArray alloc] initWithObjects:@"Alfa",
				@"Bravo", @"Charlie", @"Delta",
				@"Echo", @"Foxtrot", @"Golf",
				@"Hotel", @"India", @"Juliet", @"Kilo",
				@"Lima", @"Mike", @"November",@"Oscar",
				@"Papa",@"Quebec",@"Romeo",@"Sierra",@"Tango",
				@"Uniform",@"Victor",@"Whiskey",@"Xray",@"Yankee",
				@"Zulu",nil];
	} else {
		return array;
	}
	
}

+(void) saveArray: (NSMutableArray *) inArray {
	array = inArray;
}
@end
