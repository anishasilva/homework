//
//  SdbRequestDelegate.h
//  LIOLI
//
//  Created by Raymond Elward on 5/17/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//

#import <AWSiOSSDK/AmazonServiceResponse.h>

@interface SdbRequestDelegate:NSObject<AmazonServiceRequestDelegate>
{
    UILabel *bytesIn;
    UILabel *bytesOut;
    
@public
    AmazonServiceResponse *response;
    NSException           *exception;
    NSError               *error;
}

@property (nonatomic, readonly) AmazonServiceResponse *response;
@property (nonatomic, readonly) NSError               *error;
@property (nonatomic, readonly) NSException           *exception;

@property (nonatomic, retain) UILabel                 *bytesIn;
@property (nonatomic, retain) UILabel                 *bytesOut;

-(bool)isFinishedOrFailed;
-(void)request:(AmazonServiceRequest *)request didReceiveResponse:(NSURLResponse *)response;
-(void)request:(AmazonServiceRequest *)request didCompleteWithResponse:(AmazonServiceResponse *)response;
-(void)request:(AmazonServiceRequest *)request didReceiveData:(NSData *)data;
-(void)request:(AmazonServiceRequest *)request didSendData:(NSInteger)bytesWritten totalBytesWritten:(NSInteger)totalBytesWritten totalBytesExpectedToWrite:(NSInteger)totalBytesExpectedToWrite;
-(void)request:(AmazonServiceRequest *)request didFailWithError:(NSError *)error;
-(void)request:(AmazonServiceRequest *)request didFailWithServiceException:(NSException *)exception;



@end