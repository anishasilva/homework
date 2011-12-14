//
//  SdbRequestDelegate.m
//  LIOLI
//
//  Created by Raymond Elward on 5/17/11.
//  Copyright 2011 Raymond Elward. All rights reserved.
//
#import "SdbRequestDelegate.h"

@implementation SdbRequestDelegate

@synthesize response;
@synthesize error;
@synthesize exception;
@synthesize bytesIn, bytesOut;

-(id)init
{
    self = [super init];
    if (self)
    {
        response  = nil;
        exception = nil;
        error     = nil;
        bytesIn   = nil;
        bytesOut  = nil;
    }
    return self;
}

-(bool)isFinishedOrFailed
{
    return (response != nil || error != nil || exception != nil);
}

-(void)request:(AmazonServiceRequest *)request didReceiveResponse:(NSURLResponse *)aResponse
{
    NSLog(@"didReceiveResponse");
}

-(void)request:(AmazonServiceRequest *)request didCompleteWithResponse:(AmazonServiceResponse *)aResponse
{
    NSLog(@"didCompleteWithResponse : %@", aResponse);
    [response release];
    response = [aResponse retain];
}

-(void)request:(AmazonServiceRequest *)request didReceiveData:(NSData *)data
{
    NSLog(@"didReceiveData");
    int total = [bytesIn.text intValue];
    total       += [data length];
    bytesIn.text = [NSString stringWithFormat:@"%d", total];
}

-(void)request:(AmazonServiceRequest *)request didSendData:(NSInteger)bytesWritten totalBytesWritten:(NSInteger)totalBytesWritten totalBytesExpectedToWrite:(NSInteger)totalBytesExpectedToWrite
{
    NSLog(@"didSendData");
    int total = [bytesOut.text intValue];
    total        += bytesWritten;
    bytesOut.text = [NSString stringWithFormat:@"%d", total];
}

-(void)request:(AmazonServiceRequest *)request didFailWithError:(NSError *)theError
{
    NSLog(@"didFailWithError : %@", theError);
    [error release];
    error = [theError retain];
}

-(void)request:(AmazonServiceRequest *)request didFailWithServiceException:(NSException *)theException
{
    NSLog(@"didFailWithServiceException : %@", theException);
    [exception release];
    exception = [theException retain];
}

-(void)dealloc
{
    [error release];
    [exception release];
    [response release];
    
    [super dealloc];
}

@end

