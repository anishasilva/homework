//
//  Assignment3ViewController.m
//  Assignment3
//
//  Created by Raymond Elward on 4/12/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "Assignment3ViewController.h"

@implementation Assignment3ViewController
@synthesize userInputTextField;

- (void)dealloc
{
    [userInputTextField release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}


#pragma mark - View lifecycle

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
}
*/

- (void)viewDidUnload
{
    [self setUserInputTextField:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Action methods
- (IBAction)submitButton:(id)sender {
    NSString *userInput = [[self userInputTextField] text];
    UIAlertView *alert;
    if ([userInput isEqualToString:@""]){
        alert = [[UIAlertView alloc] initWithTitle:@"Alert Invoked" 
                                           message:@"Please enter some text" 
                                          delegate:self 
                                 cancelButtonTitle:@"Ok"
                                 otherButtonTitles:nil];
    }else {
        alert = [[UIAlertView alloc] initWithTitle:@"Alert Invoked" 
                                           message:userInput
                                          delegate:self 
                                 cancelButtonTitle:@"Cancel"
                                 otherButtonTitles:@"Ok", nil];
    }
    [alert show];
    [alert release];
    
}

#pragma mark - UITextField delegation methods

-(BOOL) textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

@end
