//
//  Assignment4ViewController.m
//  Assignment4
//
//  Created by Raymond Elward on 4/19/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "Assignment4ViewController.h"

@implementation Assignment4ViewController
@synthesize imageView;


- (void)dealloc
{
    
    [imageView release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [[self imageView] setImage:[UIImage imageNamed:@"bw.png"]];
    
    
}


- (void)viewDidUnload
{
    
    [self setImageView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark - Action methods.

- (IBAction)buttonPressed:(id)sender {
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:nil 
                                                             delegate:self 
                                                    cancelButtonTitle:@"Cancel" 
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:@"Black & White", @"Color", nil];
    
    [actionSheet showInView:self.view];
    [actionSheet release];
}

#pragma mark - UIActionSheetDelegate

-(void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
   
    
    switch (buttonIndex) {
        case 0:
            //change to black and white. only if its color
            if ([[self imageView] image] == [UIImage imageNamed:@"color.png"]) {
                [self setImageByName:@"bw.png"];
                NSLog(@"Display black and white photo.");
            }
            
            break;
        case 1:
            //change to color. only if its black and white.
            if ([[self imageView] image] == [UIImage imageNamed:@"bw.png"]) {
                [self setImageByName:@"color.png"];
                NSLog(@"Display color photo.");
            }
            break;
            
        default:
            break;
    }
}

#pragma mark - animation method.

-(void) setImageByName:(NSString *) name {
    [UIView transitionWithView:[self imageView] 
                      duration:1.5
                       options:UIViewAnimationOptionTransitionFlipFromRight
                    animations:^{[[self imageView] setImage:[UIImage imageNamed:name]];}
                    completion:NULL];
}






@end
