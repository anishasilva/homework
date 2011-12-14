//
//  StaticFunctions.m
//  tabBarTestApp
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "StaticFunctions.h"


@implementation StaticFunctions

static NSMutableArray *arrOfIds = nil;
static NSMutableArray *arrOfIdsAdmin = nil;

+(NSString *) getRandomUniqueNumber{
    BOOL invalid = YES;
    NSString *randomString;
    
    while (invalid) {
        int _myInt = arc4random() % 999999999;
        
        randomString = [NSString stringWithFormat: @"%d" , _myInt];
        //need to check if the number is already present in the database...
        //get all item names in database
        NSString *selectExpression = [NSString stringWithFormat:@"select itemName() from ActiveLIOLI"];
        NSString *selectExpressionAdmin = [NSString stringWithFormat:@"select itemName() from AdminLIOLI"];
        NSMutableArray *items;
        @try {
            SimpleDBSelectRequest  *selectRequest  = [[[SimpleDBSelectRequest alloc] initWithSelectExpression:selectExpression] autorelease];
            SimpleDBSelectResponse *selectResponse = [[Constants dataBase] select:selectRequest];
            SimpleDBSelectRequest  *selectRequestAdmin  = [[[SimpleDBSelectRequest alloc] initWithSelectExpression:selectExpressionAdmin] autorelease];
            SimpleDBSelectResponse *selectResponseAdmin = [[Constants dataBase] select:selectRequestAdmin];
            
            items = [[[NSMutableArray alloc] initWithCapacity:([selectResponse.items count] + [selectResponseAdmin.items count])] autorelease];
            
            for (SimpleDBItem *item in selectResponse.items) {
                [items addObject:item.name];
            }
            for (SimpleDBItem *item in selectResponseAdmin.items) {
                [items addObject:item.name];
            }
            
            [items sortUsingSelector:@selector(compare:)];
            
            //NSLog(@"Array: %@", items);
        }
        @catch (AmazonServiceException *exception) {
            NSLog(@"Exception = %@", exception);
            [StaticFunctions alertException];
        }
        //see if the number is present.
        NSUInteger indexResult = [items indexOfObject:randomString 
                                        inSortedRange:NSMakeRange(0, [items count]) 
                                              options:0 
                                      usingComparator:^(id a, id b) { return [a compare:b]; }];
        if (indexResult > [items count]){
            invalid = NO;
        }
    }
    return randomString;
}

+(Story *) getRandomDatabaseEntry{
    return [StaticFunctions getRandomDatabaseEntryWithDomain: @"ActiveLIOLI"];
}

+(Story *) getRandomAdminDatabaseEntry{
    return [StaticFunctions getRandomDatabaseEntryWithDomain: @"AdminLIOLI"];
}

+(void) addToLovesId:(NSString *) iDNumber{ 
    
    
    @try {
        SimpleDBGetAttributesRequest *attributes = [[[SimpleDBGetAttributesRequest alloc]initWithDomainName:@"ActiveLIOLI" andItemName:iDNumber] autorelease];
        //[attributes setDelegate:[Constants asynchDelegate]];
        SimpleDBGetAttributesResponse *response = [[Constants dataBase] getAttributes:attributes];
        NSString *lLove;
        
        for (SimpleDBAttribute *attribute in [response attributes]){
            if ([[attribute name] isEqual:@"love"])
                lLove = [attribute value];
        }
        
        
        
        int loves = [lLove intValue];
        loves = loves + 1;
        NSString *newLoves = [[NSNumber numberWithInt:loves] stringValue];
        NSLog(@"NEWLOVES::%@", newLoves);
        SimpleDBPutAttributesRequest *request = [[SimpleDBPutAttributesRequest alloc] init];
        SimpleDBReplaceableAttribute *attribute = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"love" andValue:newLoves andReplace:YES] autorelease];
        
        [request setDomainName:@"ActiveLIOLI"];
        [request setItemName:iDNumber];
        [request addAttribute:attribute];
        
        [request setDelegate:[Constants asynchDelegate]];
        [[Constants dataBase] putAttributes:request];
        
        
        [request autorelease];
    }
    @catch (AmazonServiceException *exception) {
        NSLog(@"Exception = %@", exception);
        [StaticFunctions alertException];
    }
    
}

+(void) addToLeavesId:(NSString *) iDNumber  {
    @try {
        SimpleDBGetAttributesRequest *attributes = [[[SimpleDBGetAttributesRequest alloc]initWithDomainName:@"ActiveLIOLI" andItemName:iDNumber] autorelease];
        //[attributes setDelegate:[Constants asynchDelegate]];
        SimpleDBGetAttributesResponse *response = [[Constants dataBase] getAttributes:attributes];
        NSString *lLeave;
        
        for (SimpleDBAttribute *attribute in [response attributes]){
            if ([[attribute name] isEqual:@"leave"])
                lLeave = [attribute value];
        }
        
        
        
        
        int leaves = [lLeave intValue];
        leaves = leaves + 1;
        NSString *newLeaves = [[NSNumber numberWithInt:leaves] stringValue];
        
        SimpleDBPutAttributesRequest *request = [[SimpleDBPutAttributesRequest alloc] init];
        SimpleDBReplaceableAttribute *attribute = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"leave" andValue:newLeaves andReplace:YES] autorelease];
        
        [request setDomainName:@"ActiveLIOLI"];
        [request setItemName:iDNumber];
        [request addAttribute:attribute];
        
        [request setDelegate:[Constants asynchDelegate]];
        [[Constants dataBase] putAttributes:request];
        
        
        [request release];
    }
    @catch (AmazonServiceException *exception) {
        NSLog(@"Exception = %@", exception);
        [StaticFunctions alertException];
    }
    
}

+(void) addEntryToDatabase: (Story *) data{
    @try{
        SimpleDBPutAttributesRequest *request = [[SimpleDBPutAttributesRequest alloc] init];
        SimpleDBReplaceableAttribute *attLeave = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"leave" andValue:[data leave] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attStatement = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"statement" andValue:[data statement] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attLove = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"love" andValue:[data love] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attAge = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"age" andValue:[data age] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attGender = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"gender" andValue:[data gender] andReplace:YES] autorelease];
        
        [request setDomainName:@"AdminLIOLI"];
        [request setItemName:[data iD]];
        [request addAttribute:attLeave];
        [request addAttribute:attLove];
        [request addAttribute:attAge];
        [request addAttribute:attStatement];
        [request addAttribute:attGender];
        
        SimpleDBPutAttributesResponse *putResponse = [[Constants dataBase] putAttributes:request];
        
        NSLog(@"put RESPONSE::%@", putResponse);
        
        
        [request release];
    }
    @catch (AmazonServiceException *exception) {
        NSLog(@"Exception = %@", exception);
        [StaticFunctions alertException];
    }
}

+(Story *) getEntryByID: (NSString *) ID andDomain:(NSString *) domain{
    
    Story *story;
    @try{
        
        NSString *requestId = ID;
        
        
        SimpleDBGetAttributesRequest *attributes = [[[SimpleDBGetAttributesRequest alloc]initWithDomainName:domain andItemName:requestId] autorelease];
        SimpleDBGetAttributesResponse *response = [[Constants dataBase] getAttributes:attributes];
        
        if([[response attributes] count] < 4) {
            story = nil;
        }else {
            NSString *lGender;
            NSString *lStatement;
            NSString *lAge;
            NSString *lLeave;
            NSString *lLove;
            
            
            for (SimpleDBAttribute *attribute in [response attributes]){
                if ([[attribute name] isEqual:@"statement"])
                    lStatement = [attribute value];
                else if ([[attribute name] isEqual:@"gender"])
                    lGender = [attribute value];
                else if ([[attribute name] isEqual:@"age"])
                    lAge = [attribute value];
                else if ([[attribute name] isEqual:@"leave"])
                    lLeave = [attribute value];
                else if ([[attribute name] isEqual:@"love"])
                    lLove = [attribute value];
            }
            
            story = [[Story alloc] initWithStatement:lStatement age:lAge gender:lGender iD:requestId love:lLove leave:lLeave];
            
        }
    }
    @catch (AmazonServiceException *exception) {
        NSLog(@"Exception = %@", exception);
        [StaticFunctions alertException];
    }
    
    return story;
}

+(void) alertException{
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error" 
                                                   message:@"Internet Connection not found" 
                                                  delegate:self 
                                         cancelButtonTitle:@"Ok" 
                                         otherButtonTitles: nil];
    
    [alert show];
    [alert release];
}

+(BOOL) pingServer{
    NSURL *url = [NSURL URLWithString:@"http://sdb.amazonaws.com/"];
    NSURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    NSHTTPURLResponse *response = nil;
    [NSURLConnection sendSynchronousRequest:request
                          returningResponse:&response error:NULL];
    return (response != nil);
}

+(void) adminRejectEntry:(Story *) data{

    @try {
        SimpleDBDeleteAttributesRequest *request = [[SimpleDBDeleteAttributesRequest alloc] initWithDomainName:@"AdminLIOLI" andItemName:[data iD]];
        SimpleDBAttribute *atLeave = [[[SimpleDBAttribute alloc] initWithName:@"leave" andValue:[data leave]] autorelease];
        SimpleDBAttribute *atStatement = [[[SimpleDBAttribute alloc] initWithName:@"statement" andValue:[data statement]] autorelease];
        SimpleDBAttribute *atLove = [[[SimpleDBAttribute alloc] initWithName:@"love" andValue:[data love]] autorelease];
        SimpleDBAttribute *atAge = [[[SimpleDBAttribute alloc] initWithName:@"age" andValue:[data age]] autorelease];
        SimpleDBAttribute *atGender = [[[SimpleDBAttribute alloc] initWithName:@"gender" andValue:[data gender]] autorelease];
        
        [request addAttribute:atLeave];
        [request addAttribute:atLove];
        [request addAttribute:atAge];
        [request addAttribute:atStatement];
        [request addAttribute:atGender];
        
        
        
        SimpleDBDeleteAttributesResponse *deleteResponse = [[Constants dataBase] deleteAttributes:request];
        
        
        NSLog(@"delete Response::%@", deleteResponse);
        
        [request release];
    }
    @catch (AmazonServiceException *exception){
        NSLog(@"Exception = %@", exception);
        [StaticFunctions alertException];
    }
}

+(void) adminAcceptEntry:(Story *) data{
    @try{
        SimpleDBPutAttributesRequest *request = [[SimpleDBPutAttributesRequest alloc] init];
        SimpleDBReplaceableAttribute *attLeave = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"leave" andValue:[data leave] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attStatement = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"statement" andValue:[data statement] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attLove = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"love" andValue:[data love] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attAge = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"age" andValue:[data age] andReplace:YES] autorelease];
        SimpleDBReplaceableAttribute *attGender = [[[SimpleDBReplaceableAttribute alloc] initWithName:@"gender" andValue:[data gender] andReplace:YES] autorelease];
        
        [request setDomainName:@"ActiveLIOLI"];
        [request setItemName:[data iD]];
        [request addAttribute:attLeave];
        [request addAttribute:attLove];
        [request addAttribute:attAge];
        [request addAttribute:attStatement];
        [request addAttribute:attGender];
        
        SimpleDBPutAttributesResponse *putResponse = [[Constants dataBase] putAttributes:request];
        
        NSLog(@"put RESPONSE::%@", putResponse);
        
        
        [request release];
    }
    @catch (AmazonServiceException *exception) {
        NSLog(@"Exception = %@", exception);
        [StaticFunctions alertException];
    }
    
    
    
    [StaticFunctions adminRejectEntry:data];
    
}

+(Story *) getRandomDatabaseEntryWithDomain: (NSString *) domainName{
    Story *story;
    NSNumber *indexToGrab;
    NSMutableArray *localArray;
    NSString *requestId;
    //NSString *selectExpression = [NSString stringWithFormat:@"select itemName() from %@", domainName];
   
    @try {
        if ([domainName isEqual:@"ActiveLIOLI"]){
//            localArray = [StaticFunctions getArrayOfIds];
//            indexToGrab = [NSNumber numberWithInt:arc4random() % [localArray count]];
//            SimpleDBItem *item = [localArray objectAtIndex:[indexToGrab intValue]];
//            requestId = [item name];
//            story = [StaticFunctions getEntryByID:requestId andDomain:domainName];
            
            
            requestId = [LocalArrayStore getId];
            story = [StaticFunctions getEntryByID:requestId andDomain:domainName];
        } else if ([domainName isEqual:@"AdminLIOLI"]){
            localArray = [StaticFunctions getArrayOfIdsAdmin];
            indexToGrab = [NSNumber numberWithInt:arc4random() % [localArray count]];
            SimpleDBItem *item = [localArray objectAtIndex:[indexToGrab intValue]];
            requestId = [item name];
            story = [StaticFunctions getEntryByID:requestId andDomain:domainName];
            [localArray removeObjectAtIndex:[indexToGrab intValue]];
        }
        
        
        
    }
    @catch (AmazonServiceException *exception) {
        NSLog(@"Exception = %@", exception);
        [StaticFunctions alertException];
    }
    //Story is alloced here but released in view controller.  I kept getting a invalid access error when auto releasing it here and trying to use it in the view controller.
    return story;
}
+(NSMutableArray *) getArrayOfIds {
    
    if (arrOfIds == nil || [arrOfIds count] == 0) {
        [arrOfIds release];
        arrOfIds = [NSMutableArray alloc];
        SimpleDBSelectRequest  *selectRequest  = [[[SimpleDBSelectRequest alloc] initWithSelectExpression:@"select itemName() from ActiveLIOLI"] autorelease];
        SimpleDBSelectResponse *selectResponse = [[Constants dataBase] select:selectRequest];
        arrOfIds = [[selectResponse items] mutableCopy];
    }
    
    return arrOfIds;
}
+(NSMutableArray *) getArrayOfIdsAdmin {
    if (arrOfIdsAdmin == nil || [arrOfIdsAdmin count] == 0) {
        [arrOfIdsAdmin release];
        arrOfIdsAdmin = [NSMutableArray alloc];
        SimpleDBSelectRequest  *selectRequest  = [[[SimpleDBSelectRequest alloc] initWithSelectExpression:@"select itemName() from AdminLIOLI"] autorelease];
        SimpleDBSelectResponse *selectResponse = [[Constants dataBase] select:selectRequest];
        arrOfIdsAdmin = [[selectResponse items] mutableCopy];
    }
    
    return arrOfIdsAdmin;
}

@end
