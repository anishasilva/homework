//
//  Assignment2ViewController.m
//  Assignment2
//
//  Created by Raymond Elward on 4/5/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "Assignment2ViewController.h"

@implementation Assignment2ViewController

@synthesize messageResultLabel;
@synthesize choice;

- (void)veiwDidLoad {
    [super viewDidLoad];
    
    
}

#pragma mark -
#pragma Action methods

-(IBAction)buttonPressed:(id)sender{
    //sets buttonName to figure out which button the user hit by looking at the sender object
    NSString *buttonName = [[sender titleLabel] text];
    
    //sets the choice string to the name of button pressed, this is used so that as a global var choice can be seen in the alertView method and update the label accordingly
    [self setChoice:[NSString stringWithString:buttonName]];
    //creates the array of buttons we'll use
    NSArray *buttons = [NSArray arrayWithObjects:@"Cancel", buttonName, nil];
    //calls the showAlert method so that an alert will pop up for the user.
    [self showAlert:@"Select action" buttons:buttons alertTitle:@"Alert invoked"];
}

#pragma mark -
#pragma UI display methods

-(void)updateLabel:(NSString *) choicePickedByUserAtAlert{
    //updates the label below the buttons of whichever choice the user selected from the alert
    [messageResultLabel setText:[NSString stringWithFormat:@"%@ was selected.", choicePickedByUserAtAlert]];
}

-(void)showAlert:(NSString *)msg buttons:(NSArray *)buttons alertTitle:(NSString *)title{
    //instantiates the alert and sets it up with the variables passed in from the buttonPressed method
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message:msg delegate:self cancelButtonTitle:[buttons objectAtIndex:0] otherButtonTitles:[buttons objectAtIndex:1], nil];
    //shows alert and frees up its memory
    [alert show];
    [alert release];
    
}


#pragma mark -
#pragma mark - UIAlertViewDelegate

- (void)alertView:(UIAlertView *)view clickedButtonAtIndex:(NSInteger)buttonIndex {
    //checks to see if the user hit cancel or the 'choice' button
    switch (buttonIndex) {
        case 1:
            [self updateLabel:[self choice]];
            break;
            
        default:
            [self updateLabel: @"Cancel"];
            break;
    }
}

#pragma mark -
#pragma mark Memory Manegment

- (void)didReceiveMemoryWarning{
    [super didReceiveMemoryWarning];
}

- (void)viewDidUnload {
    [self setMessageResultLabel:nil];
    [self setChoice:nil];
}

- (void)dealloc
{
    [choice release];
    [messageResultLabel release];
    [super dealloc];
}

@end
