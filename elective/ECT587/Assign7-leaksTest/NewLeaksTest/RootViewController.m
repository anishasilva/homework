//
//  RootViewController.m
//  NewLeaksTest
//
//  Created by Gene Backlin on 5/18/11.
//  Copyright 2011 MariZack Consulting. All rights reserved.
//

#import "RootViewController.h"

@implementation RootViewController

@synthesize names;


- (void)viewDidLoad
{
    [super viewDidLoad];
    [self createAddButton];
    [self initializeTableView];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

 // Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	// Return YES for supported orientations.
	return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

// Customize the number of sections in the table view.
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[self names] count];
}

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString *cellText = [[self names] objectAtIndex:[indexPath row]];
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }

    // Configure the cell.
    [[cell textLabel] setText:cellText];
    return cell;
}

- (void)createAddButton {
    UIBarButtonItem *btnAdd = [[[UIBarButtonItem alloc] initWithTitle:@"Add" 
                                                               style:UIBarButtonItemStyleBordered 
                                                              target:self 
                                                              action:@selector(addName)] autorelease];
    [[self navigationItem] setRightBarButtonItem:btnAdd];
}

- (void)addName {
    //NSMutableArray *aArray = [[NSMutableArray alloc] initWithCapacity:[[self names] count]];
    //NSString *aName = [[NSString alloc] init];
    //NSString *aTitle = [[NSString alloc] init];
    
    NSString *aName = [NSString stringWithFormat:@"Line %d", [[self names] count]];
    
    NSMutableArray *aArray = [[self names] mutableCopy];
    [aArray addObject:aName];
    [self setNames:aArray];
    NSString *aTitle = [NSString stringWithFormat:@"Line count %d", [[self names] count]];
    [self setTitle:aTitle];
    
    [[self tableView] reloadData];
    [[self tableView] scrollToRowAtIndexPath:[NSIndexPath indexPathForRow:([[self names] count] - 1) inSection:0] 
                            atScrollPosition:UITableViewScrollPositionBottom animated:NO];
    [aArray release];
}

- (void)initializeTableView {
    NSMutableArray *aNames = [[NSMutableArray alloc] init];
    //NSString *aTitle = [[NSString alloc] init];
    
    for (int i=0;i<30;i++) {
        [aNames addObject:[NSString stringWithFormat:@"Line %d", i]];
    }
    [self setNames:aNames];
    [aNames release];
    NSString *aTitle = [NSString stringWithFormat:@"Line count %d", [[self names] count]];
    [self setTitle:aTitle];
    
    [[self tableView] reloadData];
    [[self tableView] scrollToRowAtIndexPath:[NSIndexPath indexPathForRow:([[self names] count] - 1) inSection:0] 
                            atScrollPosition:UITableViewScrollPositionBottom animated:NO];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    /*
    <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
    // ...
    // Pass the selected object to the new view controller.
    [self.navigationController pushViewController:detailViewController animated:YES];
    [detailViewController release];
	*/
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Relinquish ownership any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload
{
    [super viewDidUnload];

    // Relinquish ownership of anything that can be recreated in viewDidLoad or on demand.
    // For example: self.myOutlet = nil;
}

- (void)dealloc
{
    [names release];
    [super dealloc];
}

@end
