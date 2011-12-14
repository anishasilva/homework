//
//  RootViewController.h
//  NewLeaksTest
//
//  Created by Gene Backlin on 5/18/11.
//  Copyright 2011 MariZack Consulting. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RootViewController : UITableViewController {
    NSArray *names;
}

@property (nonatomic, retain) NSArray *names;


- (void)createAddButton;
- (void)initializeTableView;



@end
