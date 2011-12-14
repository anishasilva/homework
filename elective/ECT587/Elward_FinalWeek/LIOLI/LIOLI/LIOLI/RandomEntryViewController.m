//
//  RandomEntryViewController.m
//  LIOLI
//
//  Created by Raymond Elward on 4/25/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import "RandomEntryViewController.h"


@implementation RandomEntryViewController

#pragma mark - fields
@synthesize statementTextView;
@synthesize ageLabel;
@synthesize idLabel;
@synthesize genderLabel;
@synthesize lovesLabel;
@synthesize leaveLabel;
@synthesize leaveButtonOutlet;
@synthesize banner;
@synthesize activityWhenLoadingEntry;
@synthesize nextButton;
@synthesize _story;
@synthesize contentView;
@synthesize loveButtonOutlet;





#pragma mark - memory management methods

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
    
    [self setBanner:nil];
    [statementTextView release];
    [ageLabel release];
    [idLabel release];
    [genderLabel release];
    [lovesLabel release];
    [leaveLabel release];
    [_story release];
    [banner release];
    [activityWhenLoadingEntry release];
    [nextButton release];
    [loveButtonOutlet release];
    [leaveButtonOutlet release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

-(void) refreshView{
    [_story release];
    [activityWhenLoadingEntry startAnimating];
    self._story = [StaticFunctions getRandomDatabaseEntry];
    [[self statementTextView] setText:[[self _story] statement]];
    [[self ageLabel] setText:[NSString stringWithFormat:@"Age: %@", [[self _story] age]]];
    [[self idLabel] setText:[NSString stringWithFormat:@"ID: %@", [[self _story] iD]]];
    [[self genderLabel] setText:[NSString stringWithFormat:@"Gender: %@", [[self _story] gender]]];
    [[self lovesLabel] setText:[NSString stringWithFormat:@"%@ love it", [[self _story] love]]];
    [[self leaveLabel] setText:[NSString stringWithFormat:@"%@ leave it", [[self _story] leave]]];
    [activityWhenLoadingEntry stopAnimating];
    
    nextButton.hidden = NO;
    lovesLabel.hidden = YES;
    leaveLabel.hidden = YES;
    loveButtonOutlet.hidden = NO;
    leaveButtonOutlet.hidden = NO;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    lovesLabel.hidden = YES;
    leaveLabel.hidden = YES;
    [activityWhenLoadingEntry startAnimating];
    [activityWhenLoadingEntry setHidesWhenStopped:YES];
    if ([StaticFunctions pingServer]){
        
        [self refreshView];
    } else {
        [StaticFunctions alertException];
    }
    
    static NSString * const kADBannerViewClass = @"ADBannerView";
	if (NSClassFromString(kADBannerViewClass) != nil) {
        if (banner == nil) {
            [self createADBannerView];
        }
        [self layoutForCurrentOrientation:NO];
    }
}



- (void)viewDidUnload
{
    [self setStatementTextView:nil];
    [self setAgeLabel:nil];
    [self setIdLabel:nil];
    [self setGenderLabel:nil];
    [self setLovesLabel:nil];
    [self setLeaveLabel:nil];
    [self setBanner:nil];
    [self setActivityWhenLoadingEntry:nil];
    [self setNextButton:nil];
    [self setLoveButtonOutlet:nil];
    [self setLeaveButtonOutlet:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}



#pragma mark - button action methods

- (IBAction)loveItButton:(id)sender {
    if ([StaticFunctions pingServer]){
        
        [StaticFunctions addToLovesId:[[self _story] iD]];
        lovesLabel.hidden = NO;
        leaveLabel.hidden = NO;
        loveButtonOutlet.hidden = YES;
        leaveButtonOutlet.hidden = YES;
        nextButton.hidden = NO;
        [activityWhenLoadingEntry stopAnimating];
        
    } else {
        [StaticFunctions alertException];
    }
}


- (IBAction)leaveItButton:(id)sender {
    if ([StaticFunctions pingServer])  {
        
        [StaticFunctions addToLeavesId:[[self _story] iD]];
        lovesLabel.hidden = NO;
        leaveLabel.hidden = NO;
        loveButtonOutlet.hidden = YES;
        leaveButtonOutlet.hidden = YES;
        nextButton.hidden = NO;
        [activityWhenLoadingEntry stopAnimating];
        
    } else {
        [StaticFunctions alertException];
    }
    
    
}
- (IBAction)leaveItButtonTouchDown:(id)sender {
    [activityWhenLoadingEntry startAnimating];
}

- (IBAction)loveItButtonTouchDown:(id)sender {
    [activityWhenLoadingEntry startAnimating];
}

- (IBAction)nextButtonTouchDown:(id)sender {
    nextButton.hidden = YES;
    [activityWhenLoadingEntry startAnimating];
}

- (IBAction)nextTouchUpInside:(id)sender {
    [self refreshView];
}




#pragma mark - ADBannerView delegate methods.

-(void)bannerViewDidLoadAd:(ADBannerView *)banner
{
    [self layoutForCurrentOrientation:YES];
}

-(void)bannerView:(ADBannerView *)banner didFailToReceiveAdWithError:(NSError *)error
{
    [self layoutForCurrentOrientation:YES];
}

-(BOOL)bannerViewActionShouldBegin:(ADBannerView *)banner willLeaveApplication:(BOOL)willLeave
{
    return YES;
}

-(void)bannerViewActionDidFinish:(ADBannerView *)banner
{
}

-(void)createADBannerView
{
    // --- WARNING ---
    // If you are planning on creating banner views at runtime in order to support iOS targets that don't support the iAd framework
    // then you will need to modify this method to do runtime checks for the symbols provided by the iAd framework
    // and you will need to weaklink iAd.framework in your project's target settings.
    // See the iPad Programming Guide, Creating a Universal Application for more information.
    // http://developer.apple.com/iphone/library/documentation/general/conceptual/iPadProgrammingGuide/Introduction/Introduction.html
    // --- WARNING ---
    
    // Depending on our orientation when this method is called, we set our initial content size.
    // If you only support portrait or landscape orientations, then you can remove this check and
    // select either ADBannerContentSizeIdentifierPortrait (if portrait only) or ADBannerContentSizeIdentifierLandscape (if landscape only).
	NSString *contentSize;
	if (&ADBannerContentSizeIdentifierPortrait != nil)
	{
		contentSize = UIInterfaceOrientationIsPortrait(self.interfaceOrientation) ? ADBannerContentSizeIdentifierPortrait : ADBannerContentSizeIdentifierLandscape;
	}
	else
	{
		// user the older sizes 
		contentSize = UIInterfaceOrientationIsPortrait(self.interfaceOrientation) ? ADBannerContentSizeIdentifier320x50 : ADBannerContentSizeIdentifier480x32;
    }
	
    // Calculate the intial location for the banner.
    // We want this banner to be at the bottom of the view controller, but placed
    // offscreen to ensure that the user won't see the banner until its ready.
    // We'll be informed when we have an ad to show because -bannerViewDidLoadAd: will be called.
    CGRect frame;
    frame.size = [ADBannerView sizeFromBannerContentSizeIdentifier:contentSize];
    frame.origin = CGPointMake(0.0f, CGRectGetMaxY(self.view.bounds));
    
    // Now to create and configure the banner view
    ADBannerView *bannerView = [[ADBannerView alloc] initWithFrame:frame];
    // Set the delegate to self, so that we are notified of ad responses.
    bannerView.delegate = self;
    // Set the autoresizing mask so that the banner is pinned to the bottom
    bannerView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleTopMargin;
    
	// Since we support all orientations in this view controller, support portrait and landscape content sizes.
    // If you only supported landscape or portrait, you could remove the other from this set
	bannerView.requiredContentSizeIdentifiers = (&ADBannerContentSizeIdentifierPortrait != nil) ?
    [NSSet setWithObjects:ADBannerContentSizeIdentifierPortrait, ADBannerContentSizeIdentifierLandscape, nil] : 
    [NSSet setWithObjects:ADBannerContentSizeIdentifier320x50, ADBannerContentSizeIdentifier480x32, nil];
    
    // At this point the ad banner is now be visible and looking for an ad.
    [self.view addSubview:bannerView];
    self.banner = bannerView;
    [bannerView release];
}

-(void)layoutForCurrentOrientation:(BOOL)animated
{
    CGFloat animationDuration = animated ? 0.2f : 0.0f;
    // by default content consumes the entire view area
    CGRect contentFrame = self.view.bounds;
    // the banner still needs to be adjusted further, but this is a reasonable starting point
    // the y value will need to be adjusted by the banner height to get the final position
	CGPoint bannerOrigin = CGPointMake(CGRectGetMinX(contentFrame), CGRectGetMaxY(contentFrame));
    CGFloat bannerHeight = 0.0f;
    
    // First, setup the banner's content size and adjustment based on the current orientation
    if(UIInterfaceOrientationIsLandscape(self.interfaceOrientation))
		banner.currentContentSizeIdentifier = (&ADBannerContentSizeIdentifierLandscape != nil) ? ADBannerContentSizeIdentifierLandscape : ADBannerContentSizeIdentifier480x32;
    else
        banner.currentContentSizeIdentifier = (&ADBannerContentSizeIdentifierPortrait != nil) ? ADBannerContentSizeIdentifierPortrait : ADBannerContentSizeIdentifier320x50; 
    bannerHeight = banner.bounds.size.height; 
	
    // Depending on if the banner has been loaded, we adjust the content frame and banner location
    // to accomodate the ad being on or off screen.
    // This layout is for an ad at the bottom of the view.
    if(banner.bannerLoaded)
    {
        contentFrame.size.height -= bannerHeight;
		bannerOrigin.y -= bannerHeight;
    }
    else
    {
		bannerOrigin.y += bannerHeight;
    }
    
    // And finally animate the changes, running layout for the content view if required.
    [UIView animateWithDuration:animationDuration
                     animations:^{
                         contentView.frame = contentFrame;
                         [contentView layoutIfNeeded];
                         banner.frame = CGRectMake(bannerOrigin.x, bannerOrigin.y, banner.frame.size.width, banner.frame.size.height);
                     }];
}



@end
