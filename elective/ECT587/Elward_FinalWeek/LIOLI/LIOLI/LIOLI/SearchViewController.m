//
//  SearchViewController.m
//  LIOLI
//
//  Created by Raymond Elward on 4/25/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "SearchViewController.h"


@implementation SearchViewController

@synthesize genderTextLabel;
@synthesize ageTextLabel;
@synthesize loveTextLabel;
@synthesize leaveTextLabel;
@synthesize statementTextField;
@synthesize idInputTextField;
@synthesize activityIndicator;
@synthesize submitButtonOutlet;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)dealloc
{
    [genderTextLabel release];
    [ageTextLabel release];
    [loveTextLabel release];
    [leaveTextLabel release];
    [statementTextField release];
    [idInputTextField release];
    [activityIndicator release];
    [submitButtonOutlet release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [activityIndicator setHidesWhenStopped:YES];
}

- (void)viewDidUnload
{
    [self setGenderTextLabel:nil];
    [self setAgeTextLabel:nil];
    [self setLoveTextLabel:nil];
    [self setLeaveTextLabel:nil];
    [self setStatementTextField:nil];
    [self setIdInputTextField:nil];
    [self setActivityIndicator:nil];
    [self setSubmitButtonOutlet:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark - action methods

- (IBAction)submitButton:(id)sender {
    [[self idInputTextField] resignFirstResponder];
    
    if ([[[self idInputTextField] text] isEqual:@""]){
        [self alertIdNotFound];
        [activityIndicator stopAnimating];
        submitButtonOutlet.hidden = NO;
        [[self statementTextField] setText:@""];
        [[self ageTextLabel] setText:@""];
        [[self genderTextLabel] setText:@""];
        [[self loveTextLabel] setText:@""];
        [[self leaveTextLabel] setText:@""];
        return;
    }
    

    if ([StaticFunctions pingServer]){
        NSString *idInput = [[self idInputTextField] text];
        Story *_story = [[StaticFunctions getEntryByID:idInput andDomain:@"ActiveLIOLI"] retain];
        
        
        if (_story == nil || [idInput integerValue] < 1){
            [self alertIdNotFound];
            [[self statementTextField] setText:@""];
            [[self ageTextLabel] setText:@""];
            [[self genderTextLabel] setText:@""];
            [[self loveTextLabel] setText:@""];
            [[self leaveTextLabel] setText:@""];
            [_story release];
        }else{
        
            [[self statementTextField] setText:[_story statement]];
            [[self ageTextLabel] setText:[NSString stringWithFormat:@"Age: %@", [_story age]]];
            [[self genderTextLabel] setText:[NSString stringWithFormat:@"Gender: %@", [_story gender]]];
            [[self loveTextLabel] setText:[NSString stringWithFormat:@"%@ love it", [_story love]]];
            [[self leaveTextLabel] setText:[NSString stringWithFormat:@"%@ leave it", [_story leave]]];
            
            //decrement reference count because it is not autoreleased from caller.
            [_story release];
        }
    
    } else {
        [StaticFunctions alertException];
    }
    [activityIndicator stopAnimating];
    submitButtonOutlet.hidden = NO;
    
}

- (IBAction)submitButtonTouchDown:(id)sender {
    submitButtonOutlet.hidden = YES;
    [activityIndicator startAnimating];
}


-(void)alertIdNotFound {
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error" 
                                                   message:@"Not a valid entry.  Either you typed incorrectly or this entry has not been approved yet.  try again later." 
                                                  delegate:self 
                                         cancelButtonTitle:@"Ok" 
                                         otherButtonTitles: nil];
    
    [alert show];
    [alert release];
}

@end
