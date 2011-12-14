//
//  LocalArrayStore.h
//  LIOLI
//
//  Created by Raymond Elward on 5/28/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LocalEntry.h"
#import "Constants.h"
#import <AWSiOSSDK/SimpleDB/AmazonSimpleDBClient.h>

@interface LocalArrayStore : NSObject {
    NSMutableArray *entryArray;
    NSManagedObjectContext *managedObjectContext;
}
+(void)setManagedObjectContext:(NSManagedObjectContext *) incoming;
+(NSString *) getId;

@end
