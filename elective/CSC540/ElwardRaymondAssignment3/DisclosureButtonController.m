//
//  DisclosureButtonController.m
//  Assignment3
//
//  Created by Raymond Elward on 1/22/12.
//  Copyright (c) 2012 Raymond Elward. All rights reserved.
//

#import "DisclosureButtonController.h"
#import "AppDelegate.h"
#import "DisclosureDetailController.h"
#import "DataSingleton.h"

@implementation DisclosureButtonController
@synthesize list;

- (void)viewDidLoad {
    NSArray *array = [DataSingleton getArray];
    self.list = array;
    [super viewDidLoad];
}

- (void)viewDidUnload {
	self.list = nil;
	childController = nil;
}



#pragma mark -
#pragma mark Table Data Source Methods
- (NSInteger)tableView:(UITableView *)tableView
 numberOfRowsInSection:(NSInteger)section {
    return [list count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	
    static NSString * DisclosureButtonCellIdentifier =
	@"DisclosureButtonCellIdentifier";
	
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:
                             DisclosureButtonCellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc]
				 initWithStyle:UITableViewCellStyleDefault
				 reuseIdentifier:DisclosureButtonCellIdentifier];
    }
    NSUInteger row = [indexPath row];
    NSString *rowString = [list objectAtIndex:row];
    cell.textLabel.text = rowString;
    cell.accessoryType = UITableViewCellAccessoryDetailDisclosureButton;
    return cell;
}

#pragma mark -
#pragma mark Table Delegate Methods
- (void)tableView:(UITableView *)tableView
didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:
						  @"Hey, do you see the disclosure button?"
													message:@"If you're trying to drill down, touch that instead"
												   delegate:nil
						  
										  cancelButtonTitle:@"Won't happen again"
										  otherButtonTitles:nil];
    [alert show];
}
- (void)tableView:(UITableView *)tableView

accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath {
    if (childController == nil) {
        childController = [[DisclosureDetailController alloc]
                           initWithNibName:@"DisclosureDetail" bundle:nil];
    }
    childController.title = @"Disclosure Button Pressed";
    NSUInteger row = [indexPath row];
    NSString *selectedMovie = [list objectAtIndex:row];
    NSString *detailMessage = [[NSString alloc]
							   initWithFormat:@"You pressed the disclosure button for %@.",
							   selectedMovie];
    childController.message = detailMessage;
    childController.title = selectedMovie;
	
    [self.navigationController pushViewController:childController
                                         animated:YES];
}
@end
