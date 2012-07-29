//
//  DataSingleton.h
//  Assignment3
//
//  Created by Raymond Elward on 1/22/12.
//  Copyright (c) 2012 Raymond Elward. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DataSingleton : NSObject {
}
+(NSMutableArray *) getArray;
+(void) saveArray: (NSMutableArray *) inArray;
@end
