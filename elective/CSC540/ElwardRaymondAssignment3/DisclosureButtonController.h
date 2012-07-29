//
//  DisclosureButtonController.h
//  Assignment3
//
//  Created by Raymond Elward on 1/22/12.
//  Copyright (c) 2012 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SecondLevelViewController.h"
@class DisclosureDetailController;

@interface DisclosureButtonController : SecondLevelViewController {
    NSArray    *list;
    DisclosureDetailController *childController;
}
@property (nonatomic, retain) NSArray *list;
@end