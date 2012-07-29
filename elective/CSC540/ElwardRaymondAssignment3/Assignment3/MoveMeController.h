//
//  MoveMeController.h
//  Assignment3
//
//  Created by Raymond Elward on 1/22/12.
//  Copyright (c) 2012 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SecondLevelViewController.h"

@interface MoveMeController : SecondLevelViewController {
    NSMutableArray *list;
}
@property (nonatomic, retain) NSMutableArray *list;
- (IBAction)toggleMove;

@end
