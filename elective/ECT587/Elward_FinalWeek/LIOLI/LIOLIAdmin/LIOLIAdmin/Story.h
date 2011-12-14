//
//  Story.h
//  tabBarTestApp
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AWSiOSSDK/SimpleDB/AmazonSimpleDBClient.h>
#import "Constants.h"

#import "StaticFunctions.h"


@interface Story : NSObject {
    NSString *statement;
    NSString *gender;
    NSString *leave;
    NSString *love;
    NSString *age;
    NSString *iD;
}
@property (nonatomic, retain) NSString *iD;

@property (nonatomic, retain) NSString *age;

@property (nonatomic, retain) NSString *love;

@property (nonatomic, retain) NSString *leave;

@property (nonatomic, retain) NSString *gender;

@property (nonatomic, retain) NSString *statement;

-(Story *) initWithStatement:(NSString *)statementIn age: (NSString *) ageIn gender: (NSString *) genderIn;

-(Story *) initWithStatement:(NSString *)statementIn age: (NSString *) ageIn gender: (NSString *) genderIn iD: (NSString *) idIn love: (NSString *) loveIn leave: (NSString *) leaveIn;

-(void) dealloc;

@end