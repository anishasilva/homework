//
//  AboutViewController.h
//  LIOLI
//
//  Created by Raymond Elward on 4/24/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>

#import <MessageUI/MessageUI.h>

@interface AboutViewController : UIViewController < MFMailComposeViewControllerDelegate> {
    
}

- (IBAction)emailButton:(id)sender;
- (void) showEmailModalView;

@end
