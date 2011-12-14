//
//  AboutViewController.m
//  LIOLI
//
//  Created by Raymond Elward on 4/24/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "AboutViewController.h"


@implementation AboutViewController



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
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}




#pragma mark - action button methods
- (IBAction)emailButton:(id)sender {
    if ([MFMailComposeViewController canSendMail]) {
        [self showEmailModalView];
    } else {
        [[UIApplication sharedApplication] openURL: [NSURL URLWithString:@"mailto:feedback.LIOLI@gmail.com"]];
    }
}

-(void) showEmailModalView {
    MFMailComposeViewController *mailComposeView = [[MFMailComposeViewController alloc] init];
    [mailComposeView setMailComposeDelegate:self];
    
    [mailComposeView setSubject:@"LIOLI iPhone app feedback."];
    [mailComposeView setToRecipients:[NSArray arrayWithObject:@"feedback.LIOLI@gmail.com"]];
    // Fill out the email body text
    NSString *emailBody = [NSString stringWithFormat:@"IPhone app feedback:"];
    
    [mailComposeView setMessageBody:emailBody isHTML:YES]; 
    mailComposeView.navigationBar.barStyle = UIBarStyleBlack; 
    
    [self presentModalViewController:mailComposeView animated:YES];
    [mailComposeView release];
    
    
}


#pragma mark - Mail compose delegate methods

// Dismisses the email composition interface when users tap Cancel or Send. Proceeds to update the message field with the result of the operation.

- (void)mailComposeController:(MFMailComposeViewController*)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error
{ 
    // Notifies users about errors associated with the interface
    switch (result)
    {
        case MFMailComposeResultCancelled:
            break;
        case MFMailComposeResultSaved:
            break;
        case MFMailComposeResultSent:
            break;
        case MFMailComposeResultFailed:
            break;
            
        default:
        {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Email" 
                                                            message:@"Sending Failed"
                                                           delegate:self 
                                                  cancelButtonTitle:@"OK" 
                                                  otherButtonTitles: nil];
            [alert show];
            [alert release];
        }
            
            break;
    }
    [self dismissModalViewControllerAnimated:YES];
}


@end
