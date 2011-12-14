//
//  RandomEntryViewController.h
//  LIOLI
//
//  Created by Raymond Elward on 4/25/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Story.h"
#import "StaticFunctions.h"
#import <iAd/iAd.h>



@interface RandomEntryViewController : UIViewController  <ADBannerViewDelegate>{
    UITextView *statementTextView;
    UILabel *ageLabel;
    UILabel *idLabel;
    UILabel *genderLabel;
    UILabel *lovesLabel;
    UILabel *leaveLabel;
    UIButton *leaveButtonOutlet;
    Story *_story;
    ADBannerView *banner;
    UIActivityIndicatorView *activityWhenLoadingEntry;
    UIButton *nextButton;
    BOOL bannerIsVisible;
    UIButton *loveButtonOutlet;
}
- (IBAction)leaveItButtonTouchDown:(id)sender;
- (IBAction)loveItButtonTouchDown:(id)sender;

- (IBAction)nextButtonTouchDown:(id)sender;
- (IBAction)nextTouchUpInside:(id)sender;


@property (nonatomic, retain) IBOutlet ADBannerView *banner;

@property (nonatomic, retain) IBOutlet UIActivityIndicatorView *activityWhenLoadingEntry;
@property (nonatomic, retain) IBOutlet UIButton *nextButton;
@property (nonatomic, retain) Story *_story;
@property (nonatomic, retain) IBOutlet UITextView *statementTextView;
@property (nonatomic, retain) IBOutlet UILabel *ageLabel;
@property (nonatomic, retain) IBOutlet UILabel *idLabel;
@property (nonatomic, retain) IBOutlet UILabel *genderLabel;
@property (nonatomic, retain) IBOutlet UILabel *lovesLabel;
@property (nonatomic, retain) IBOutlet UILabel *leaveLabel;
@property (nonatomic, retain) IBOutlet UIButton *leaveButtonOutlet;
@property (nonatomic, retain) IBOutlet UIView *contentView;
@property (nonatomic, retain) IBOutlet UIButton *loveButtonOutlet;


- (IBAction)loveItButton:(id)sender;
- (IBAction)leaveItButton:(id)sender;

// Layout the Ad Banner and Content View to match the current orientation.
// The ADBannerView always animates its changes, so generally you should
// pass YES for animated, but it makes sense to pass NO in certain circumstances
// such as inside of -viewDidLoad.
-(void)layoutForCurrentOrientation:(BOOL)animated;

// A simple method that creates an ADBannerView
// Useful if you need to create the banner view in code
// such as when designing a universal binary for iPad
-(void)createADBannerView;



@end
