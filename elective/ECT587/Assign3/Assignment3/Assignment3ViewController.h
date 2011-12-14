//
//  Assignment3ViewController.h
//  Assignment3
//
//  Created by Raymond Elward on 4/12/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface Assignment3ViewController : UIViewController  {
    
    UITextField *userInputTextField;
}
@property (nonatomic, retain) IBOutlet UITextField *userInputTextField;
- (IBAction)submitButton:(id)sender;



@end
