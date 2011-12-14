//
//  Assignment1AppDelegate.h
//  Assignment1
//
//  Created by Raymond Elward on 3/29/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Assignment1ViewController;

@interface Assignment1AppDelegate : NSObject <UIApplicationDelegate> {

}

@property (nonatomic, retain) IBOutlet UIWindow *window;

@property (nonatomic, retain) IBOutlet Assignment1ViewController *viewController;

@end
