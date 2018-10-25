package com.siviglia.app;

import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.Email;

public class App{

    public static void main( String[] args ){

        MotionDetector motionDetector = new MotionDetector("/dev/serial0"); 
        motionDetector.start();
    }

}
