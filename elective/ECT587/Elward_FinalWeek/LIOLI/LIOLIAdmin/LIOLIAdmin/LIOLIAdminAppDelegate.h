//
//  LIOLIAdminAppDelegate.h
//  LIOLIAdmin
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>

@class LIOLIAdminViewController;

@interface LIOLIAdminAppDelegate : NSObject <UIApplicationDelegate> {

}

@property (nonatomic, retain) IBOutlet UIWindow *window;

@property (nonatomic, retain) IBOutlet LIOLIAdminViewController *viewController;

@end
