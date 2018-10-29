// Copyright (C) 2018 Nicholas Siviglia.
// 
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// 

package com.siviglia.app;

import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.CmdLineException;

public class App{

    public static void main( String[] args ){

        //Parse the arguments.
        Arguments in = new Arguments();
        CmdLineParser parser = new CmdLineParser(in);
        try {
            parser.parseArgument(args);
        }
        catch(CmdLineException ex){
            System.err.println(ex.getMessage());
            parser.printUsage(System.err);
            System.exit(-1);
        }

        //Setup the motion detector and email system.
        MotionDetector motionDetector = new MotionDetector(in.serialPort); 

		Mailer mailer = MailerBuilder
			.withSMTPServer(in.smtpServer, in.smtpPort, in.smtpUser, in.smtpPass)
			.withTransportStrategy(TransportStrategy.SMTP_TLS)
			.buildMailer();
        
        Email email = EmailBuilder.startingBlank()
            .to(in.emailTo)
            .from(in.emailFrom)
            .withSubject(in.emailSubject)
            .withPlainText(in.emailText)
            .buildEmail();

        //Inject the dependencies and start
        TriggerAlarm triggerAlarm = new TriggerAlarm(mailer, email, motionDetector);
        triggerAlarm.start();
    }

}
