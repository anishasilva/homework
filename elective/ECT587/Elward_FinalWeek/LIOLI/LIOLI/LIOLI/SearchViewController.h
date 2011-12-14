//
//  SearchViewController.h
//  LIOLI
//
//  Created by Raymond Elward on 4/25/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "StaticFunctions.h"
#import "Story.h"

@interface SearchViewController : UIViewController  {
    UILabel *genderTextLabel;
    UILabel *ageTextLabel;
    UILabel *loveTextLabel;
    UILabel *leaveTextLabel;
    UITextView *statementTextField;
    UITextField *idInputTextField;
    UIActivityIndicatorView *activityIndicator;
    UIButton *submitButtonOutlet;
}

@property (nonatomic, retain) IBOutlet UILabel *genderTextLabel;
@property (nonatomic, retain) IBOutlet UILabel *ageTextLabel;
@property (nonatomic, retain) IBOutlet UILabel *loveTextLabel;
@property (nonatomic, retain) IBOutlet UILabel *leaveTextLabel;
@property (nonatomic, retain) IBOutlet UITextView *statementTextField;
@property (nonatomic, retain) IBOutlet UITextField *idInputTextField;
@property (nonatomic, retain) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (nonatomic, retain) IBOutlet UIButton *submitButtonOutlet;

- (IBAction)submitButton:(id)sender;
- (IBAction)submitButtonTouchDown:(id)sender;
-(void)alertIdNotFound;
@end
