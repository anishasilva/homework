//
//  StaticFunctions.h
//  tabBarTestApp
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Story.h"
#import <AWSiOSSDK/SimpleDB/AmazonSimpleDBClient.h>
#import "Constants.h"
#import "StaticFunctions.h"
#import "LocalArrayStore.h"

@class Story;

@interface StaticFunctions : NSObject {
    
}

+(NSString *) getRandomUniqueNumber;

+(Story *) getRandomDatabaseEntry;

+(Story *) getRandomAdminDatabaseEntry;

+(void) addToLovesId:(NSString *) iDNumber;

+(void) addToLeavesId:(NSString *) iDNumber ;

+(void) addEntryToDatabase: (Story *) data;
/*
 Returns nil if the entry ID is not found.
 
 */
+(Story *) getEntryByID: (NSString *) ID andDomain:(NSString *) domain;

+(void) alertException;

+(BOOL) pingServer;

+(void) adminRejectEntry:(Story *) data;

+(void) adminAcceptEntry:(Story *) data;

+(Story *) getRandomDatabaseEntryWithDomain: (NSString *) domainName;

+(NSMutableArray *) getArrayOfIds;

+(NSMutableArray *) getArrayOfIdsAdmin;

@end
