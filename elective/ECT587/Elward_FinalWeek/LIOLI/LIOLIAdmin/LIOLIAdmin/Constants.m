//
//  Constants.m
//  tabBarTestApp
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Constants.h"


//please ignore the next two lines if you're trying to be malicious and access my database without going through my app.
#define ACCESS_KEY @"AKIAIHHGNE3VHJD4XZVA"
#define OTHER_KEY @"mBo+vzGz00Xj8DyaDS5FeYQ+N0q6qgaYYZ2JCN/e"
@implementation Constants

static AmazonSimpleDBClient *dataBase = nil;
static SdbRequestDelegate *sdbRequest = nil;

+(AmazonSimpleDBClient *) dataBase {
    if (dataBase == nil) {
        dataBase = [[AmazonSimpleDBClient alloc] initWithAccessKey:ACCESS_KEY withSecretKey:OTHER_KEY];
    }
    return dataBase;
}

+(SdbRequestDelegate *) asynchDelegate{
    
    if (sdbRequest == nil) {
        sdbRequest = [[SdbRequestDelegate alloc] init];
    }
    return sdbRequest;
}


@end
