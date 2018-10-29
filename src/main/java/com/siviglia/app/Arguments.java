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

import org.kohsuke.args4j.Option;

public class Arguments {

    @Option(name="-serial",usage="Sensor serial port.")
    public String serialPort = "/dev/serial0";

    @Option(name="-server",usage="SMTP server.",required=true)
    public String smtpServer;

    @Option(name="-port",usage="SMTP port.",required=true)
    public int smtpPort;

    @Option(name="-user",usage="SMTP username.",required=true)
    public String smtpUser;

    @Option(name="-pass",usage="SMTP password.",required=true)
    public String smtpPass;

    @Option(name="-to",usage="Email destination.",required=true)
    public String emailTo;

    @Option(name="-from",usage="Email source.")
    public String emailFrom = "pi@motiondetector.com";

    @Option(name="-subject",usage="Email subject.")
    public String emailSubject = "Motion Detected.";

    @Option(name="-text",usage="Email text body.")
    public String emailText = 
    "Motion has been detected at the time this email was sent.";
}
