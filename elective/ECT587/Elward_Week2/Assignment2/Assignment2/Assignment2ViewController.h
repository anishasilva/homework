//
//  Assignment2ViewController.h
//  Assignment2
//
//  Created by Raymond Elward on 4/5/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface Assignment2ViewController : UIViewController <UIAlertViewDelegate> {
    UILabel *messageResultLabel;
    NSString *choice;
}

@property (nonatomic, retain) IBOutlet UILabel *messageResultLabel;
@property (nonatomic, retain) NSString *choice;

#pragma mark -
#pragma Action methods


-(IBAction)buttonPressed:(id)sender;

#pragma mark -
#pragma UI display methods

-(void)updateLabel:(NSString *) choicePickedByUserAtAlert;

-(void)showAlert:(NSString *)msg buttons:(NSArray *)buttons alertTitle:(NSString *)title;

@end
