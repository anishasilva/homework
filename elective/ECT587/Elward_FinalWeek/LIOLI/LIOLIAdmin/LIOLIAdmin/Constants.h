//
//  Constants.h
//  tabBarTestApp
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//
#import <Foundation/Foundation.h>

#import <AWSiOSSDK/SimpleDB/AmazonSimpleDBClient.h>
#import "SdbRequestDelegate.h"





@interface Constants : NSObject {
    
}


+(AmazonSimpleDBClient *) dataBase;

+(SdbRequestDelegate *) asynchDelegate;



@end
