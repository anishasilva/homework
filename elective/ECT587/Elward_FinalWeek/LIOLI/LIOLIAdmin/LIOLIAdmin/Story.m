//
//  Story.m
//  tabBarTestApp
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Story.h"



@implementation Story

@synthesize iD = iD;
@synthesize love = love;
@synthesize leave = leave;
@synthesize statement = statement;
@synthesize age = age;
@synthesize gender = gender;

-(Story *) initWithStatement:(NSString *)statementIn age: (NSString *) ageIn gender: (NSString *) genderIn{
    self = [super init];
    
    if (self){
        [self setStatement:statementIn];
        [self setAge:ageIn];
        [self setGender:genderIn];
        [self setLove:@"0"];
        [self setLeave:@"0"];
        [self setID:[StaticFunctions getRandomUniqueNumber]];
    }
    
    return self;
    
}

-(Story *) initWithStatement:(NSString *)statementIn age: (NSString *) ageIn gender: (NSString *) genderIn iD: (NSString *) idIn love: (NSString *) loveIn leave: (NSString *) leaveIn {
    self = [super init];
    
    if (self) {
        [self setStatement:statementIn];
        [self setAge:ageIn];
        [self setGender:genderIn];
        [self setID:idIn];
        [self setLove:loveIn];
        [self setLeave:leaveIn];
    }
    
    return self;
}

-(void)dealloc{
    [self setStatement:nil];
    [self setAge:nil];
    [self setGender:nil];
    [self setID:nil];
    [self setLove:nil];
    [self setLeave:nil];
    
    [super dealloc];
}




@end
