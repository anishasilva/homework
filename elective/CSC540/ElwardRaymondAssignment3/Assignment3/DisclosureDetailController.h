//
//  DisclosureDetailController.h
//  Assignment3
//
//  Created by Raymond Elward on 1/22/12.
//  Copyright (c) 2012 Raymond Elward. All rights reserved.
//


#import <UIKit/UIKit.h>
@interface DisclosureDetailController : UIViewController {
	UILabel    *label;
	NSString   *message;
}

@property (nonatomic, retain) IBOutlet UILabel *label;
@property (nonatomic, copy) NSString *message;
@end