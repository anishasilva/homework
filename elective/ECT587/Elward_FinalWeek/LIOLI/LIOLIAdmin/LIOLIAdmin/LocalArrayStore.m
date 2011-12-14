//
//  LocalArrayStore.m
//  LIOLI
//
//  Created by Raymond Elward on 5/28/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "LocalArrayStore.h"


@implementation LocalArrayStore

static NSMutableArray *entryArray = nil;
static NSManagedObjectContext *managedObjectContext = nil;

+(void)fillArray {
    [entryArray release];
    entryArray = [[NSMutableArray alloc] initWithCapacity:0];
    SimpleDBSelectRequest *selectRequest = [[[SimpleDBSelectRequest alloc] initWithSelectExpression:@"select itemName() from ActiveLIOLI"] autorelease];
    SimpleDBSelectResponse *selectResponse = [[Constants dataBase] select:selectRequest];
    NSArray *items = [selectResponse items];
    
    for (SimpleDBItem *item in items){
        
        LocalEntry *entry = (LocalEntry *) [NSEntityDescription insertNewObjectForEntityForName:@"LocalEntry" inManagedObjectContext:managedObjectContext];
        [entry setEntryId:[item name]];
        NSError *error = nil;
        if (![managedObjectContext save:&error]){
            NSLog(@"loca database error");
        }
        [entryArray addObject:entry];
    }
}

+(void)setManagedObjectContext:(NSManagedObjectContext *)incoming{
    managedObjectContext = incoming;
}

+(NSString *) getId{
    if (entryArray == nil || [entryArray count] == 0){
        [LocalArrayStore fillArray];
    }
    
    NSNumber *indexToGrab = [NSNumber numberWithInt:(arc4random() % [entryArray count])];
    
    LocalEntry *entry = (LocalEntry *) [entryArray objectAtIndex:[indexToGrab intValue]];
    NSString *entryToShow = [NSString stringWithFormat:@"%@", [entry entryId]];
    
    NSManagedObject *eventToDelete = [entryArray objectAtIndex:[indexToGrab intValue]];
    [managedObjectContext deleteObject:eventToDelete];
    NSError *error = nil;
    if (![managedObjectContext save:&error]){
        NSLog(@"Local database save error");
    }
    [entryArray removeObjectAtIndex:[indexToGrab intValue]];
    return entryToShow;
    
}

         
         
@end
