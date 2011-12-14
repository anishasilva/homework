//
//  SubmitViewController.h
//  LIOLI
//
//  Created by Raymond Elward on 4/24/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Story.h"
#import "StaticFunctions.h"


@interface SubmitViewController : UIViewController <UITextFieldDelegate> {
   
    UITextView *statementTextField;
    UITextField *genderTextField;
    UITextField *ageTextField;
    
    UITextView *idOutputTextView;
    UIButton *submitButtonOutlet;
    UIActivityIndicatorView *activityIndicator;
}

@property (nonatomic, retain) IBOutlet UIButton *submitButtonOutlet;
@property (nonatomic, retain) IBOutlet UITextView *statementTextField;
@property (nonatomic, retain) IBOutlet UITextField *genderTextField;
@property (nonatomic, retain) IBOutlet UITextField *ageTextField;
-(IBAction)submitButton:(id)sender;
-(void) processSubmissionWithStory:(Story *)story;
@property (nonatomic, retain) IBOutlet UITextView *idOutputTextView;
-(void) alertForBadInput:(NSString *) input;
- (IBAction)submitButtonTouchDown:(id)sender;
@property (nonatomic, retain) IBOutlet UIActivityIndicatorView *activityIndicator;

@end
