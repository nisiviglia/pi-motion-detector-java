package com.siviglia.app;

import org.simplejavamail.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.Email;

import java.util.concurrent.TimeUnit;

class TriggerAlarm{
    
    private MotionDetector motionDetector = null;
    private Mailer mailer = null;
    private Email email = null;
    
    private final int SLEEP_TIME_IN_SEC = 5;

    //A constructor that takes in a preconfigured mailer, email and motion detector
    //instances.
    public TriggerAlarm(Mailer mailer, Email email, MotionDetector motionDetector){
        
        this.mailer = mailer;
        this.email = email;
        this.motionDetector = motionDetector;
    }

    //This will run the motion detector. Once motion is detected it will send
    //a notification, sleep for a few seconds, and then reset.
    public void start(){

        while(true){

            //Wait for motion
            this.motionDetector.start();

            //Send notification
            System.out.println("sending notification.");
            this.sendNotification();
            System.out.println("notification sent.");

            //Sleep for set amount of seconds
            System.out.println("sleeping for " + this.SLEEP_TIME_IN_SEC + " seconds.");
            try{
                TimeUnit.SECONDS.sleep( this.SLEEP_TIME_IN_SEC );
            }
            catch( InterruptedException ex ){
                System.out.println(ex);
            }

            //then repeate
            System.out.println("resetting.");
        }
    }
    
    //This sends out a notification. In our case its an email.
    public void sendNotification(){
        
        this.mailer.sendMail( this.email );
    }
}
