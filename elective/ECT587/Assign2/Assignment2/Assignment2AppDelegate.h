//
//  Assignment2AppDelegate.h
//  Assignment2
//
//  Created by Raymond Elward on 4/5/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Assignment2ViewController;

@interface Assignment2AppDelegate : NSObject <UIApplicationDelegate> {

}

@property (nonatomic, retain) IBOutlet UIWindow *window;

@property (nonatomic, retain) IBOutlet Assignment2ViewController *viewController;

@end
