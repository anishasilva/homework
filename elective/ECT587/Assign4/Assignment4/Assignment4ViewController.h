//
//  Assignment4ViewController.h
//  Assignment4
//
//  Created by Raymond Elward on 4/19/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface Assignment4ViewController : UIViewController <UIActionSheetDelegate> {
    
    
    UIImageView *imageView;
}
@property (nonatomic, retain) IBOutlet UIImageView *imageView;

- (IBAction)buttonPressed:(id)sender;

-(void) setImageByName:(NSString *) name;
@end
