//
//  LIOLIAdminViewController.m
//  LIOLIAdmin
//
//  Created by Raymond Elward on 4/23/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "LIOLIAdminViewController.h"

@implementation LIOLIAdminViewController
@synthesize statementText;
@synthesize iDLabel;
@synthesize ageLabel;
@synthesize genderLabel;
@synthesize _story;

- (void)dealloc
{
    [statementText release];
    [iDLabel release];
    [ageLabel release];
    [genderLabel release];
    [_story release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

-(void) refreshView {
    [_story release];
    
    _story = [StaticFunctions getRandomAdminDatabaseEntry];
    
    [[self statementText] setText:[_story statement]];
    [[self iDLabel] setText:[_story iD]];
    [[self ageLabel] setText:[_story age]];
    [[self genderLabel] setText:[_story gender]];
    
    
}
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    
    
    
    
    if ([StaticFunctions pingServer]){
        [self refreshView];
    }else {
        [StaticFunctions alertException];
    }
}


- (void)viewDidUnload
{
    [self setStatementText:nil];
    [self setIDLabel:nil];
    [self setAgeLabel:nil];
    [self setGenderLabel:nil];
    [self set_story:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)rejectButton:(id)sender {
    
    if ([StaticFunctions pingServer]){
        
        [StaticFunctions adminRejectEntry:_story];
        [self refreshView];
    }else {
        [StaticFunctions alertException];
    }
}

- (IBAction)acceptButton:(id)sender {
    
    if ([StaticFunctions pingServer]){
        [StaticFunctions adminAcceptEntry:_story];
        [self refreshView];
    }else {
        [StaticFunctions alertException];
    }
}
- (IBAction)skipButton:(id)sender {
    
    if ([StaticFunctions pingServer]){
        [self refreshView];
    }else {
        [StaticFunctions alertException];
    }
}
@end
