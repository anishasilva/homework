//
//  SubmitViewController.m
//  LIOLI
//
//  Created by Raymond Elward on 4/24/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "SubmitViewController.h"


@implementation SubmitViewController
@synthesize activityIndicator;

@synthesize idOutputTextView;
@synthesize submitButtonOutlet;
@synthesize statementTextField;
@synthesize genderTextField;
@synthesize ageTextField;


#pragma mark - memory management
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
    [statementTextField release];
    [genderTextField release];
    [ageTextField release];
    [idOutputTextView release];
    [submitButtonOutlet release];
    [activityIndicator release];
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
    
    [[self statementTextField] setText:@"Details!"];
    [activityIndicator setHidesWhenStopped:YES];
}

- (void)viewDidUnload
{
    [self setStatementTextField:nil];
    [self setGenderTextField:nil];
    [self setAgeTextField:nil];
    [self setIdOutputTextView:nil];
    [self setSubmitButtonOutlet:nil];
    [self setActivityIndicator:nil];
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
    
    [[self genderTextField] resignFirstResponder];
    [[self ageTextField] resignFirstResponder];
    [[self statementTextField] resignFirstResponder];
    NSString *gender = [NSString stringWithFormat:@"%@", [[self genderTextField] text]];
    NSString *statement = [NSString stringWithFormat:@"%@", [[self statementTextField] text]];
    NSInteger age = [[[self ageTextField] text] integerValue];
    
    if ( ![gender isEqual:@"M"] && ![gender isEqual:@"F"]){
        
        NSLog(@"Gender is Wrong!!");
        [self alertForBadInput:@"Gender needs to be 'M' or 'F'" ];
        //add alert view to tell user to change gender.
        
    }else if (age < 1 || age > 125){
        
        NSLog(@"Age is wrong!!");
        [self alertForBadInput:@"Invalid age." ];
        
    }else if ([statement length] > 1020){
        
        NSLog(@"Statement is too long!!");
        [self alertForBadInput:@"Too long.  Keep statment under 1020 characters." ];
        
    }else{
        Story *newStory = [[[Story alloc] initWithStatement:statement age:ageTextField.text gender:gender] autorelease];
        NSString *newId = [newStory iD];
        [self processSubmissionWithStory:newStory];
        [[self idOutputTextView] setText:[NSString stringWithFormat:@"The ID of your submission is: %@.  Please keep this for your records to check on what people think about your relationship later.", newId]];
        [[self statementTextField] setText:@"Details!"];
        [[self genderTextField] setText:@""];
        [[self ageTextField] setText:@""];
    }
    [activityIndicator stopAnimating];
    submitButtonOutlet.hidden = NO;
}

-(void) alertForBadInput:(NSString *) input {
    
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error" 
                                                   message:input 
                                                  delegate:self 
                                         cancelButtonTitle:@"Ok" 
                                         otherButtonTitles: nil];
    
    [alert show];
    [alert release];
}

- (IBAction)submitButtonTouchDown:(id)sender {
    [activityIndicator startAnimating];
    submitButtonOutlet.hidden = YES;
}


-(void) processSubmissionWithStory:(Story *) story {
    
    if ([StaticFunctions pingServer])  {
        [StaticFunctions addEntryToDatabase:story];
    } else {
        [StaticFunctions alertException];
    }
    
}


#pragma mark - Keyboard notification delegate methods.




@end
