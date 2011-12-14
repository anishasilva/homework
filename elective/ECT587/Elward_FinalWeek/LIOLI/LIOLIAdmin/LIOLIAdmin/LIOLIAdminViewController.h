//
//  LIOLIAdminViewController.h
//  LIOLIAdmin
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Story.h"
#import "StaticFunctions.h"
#import "Constants.h"

@interface LIOLIAdminViewController : UIViewController {
    
    UITextView *statementText;
    UILabel *iDLabel;
    UILabel *ageLabel;
    UILabel *genderLabel;
    Story *_story;
}
- (IBAction)skipButton:(id)sender;
@property (nonatomic, retain) IBOutlet UITextView *statementText;
@property (nonatomic, retain) IBOutlet UILabel *iDLabel;
@property (nonatomic, retain) IBOutlet UILabel *ageLabel;
@property (nonatomic, retain) IBOutlet UILabel *genderLabel;
@property (nonatomic, retain) Story *_story;
- (IBAction)rejectButton:(id)sender;
- (IBAction)acceptButton:(id)sender;

@end
