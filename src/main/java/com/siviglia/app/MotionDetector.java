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

import jssc.SerialPortList;
import jssc.SerialPort;
import jssc.SerialPortException;

class MotionDetector{

    //Serial port information
    private SerialPort serialPort = null;
    private final int BAUDRATE = 9600;
    private final int DATABITS = 8;
    private final int STOPBITS = 1;
    private final int PARITY = 0;

    //Motion detection information
    private final int ARRAYSIZE = 12;
    private final int SENSITIVITY = 2; //lower the more sensitive

    //This constructor will open the serial connection or kill the 
    //program if it is unable to.
    public MotionDetector(String serialPortName){

        this.serialPort = new SerialPort(serialPortName); 
        try{
            System.out.println("Opening serial port.");
            serialPort.openPort();
            serialPort.setParams(BAUDRATE, DATABITS, STOPBITS, PARITY);
        }
        catch (SerialPortException ex){
            System.out.println(ex);
            System.exit(-1);
        }
    
    }

    //This will grab a list of available serial ports and print them
    //to the console.
    public static void printListOfSerialPorts(){

    	String[] portNames = SerialPortList.getPortNames();
		for(int i = 0; i < portNames.length; i++){
			System.out.println(portNames[i]);
		}  
    
    }

    //This will get a complete reading from the sensor, convert it to an 
    //integer, and then return it.
    public int getCurrentDistance(){

        String str = "";
        byte[] buf = {0};
        int distance = 0;

        while(true){

            //Get next byte
            try{
                buf = serialPort.readBytes(1);
            }
            catch(SerialPortException ex){
                System.out.println(ex); 
                System.exit(-1);
            }

            //If we have a complete reading then try to break
            if( buf[0] == '\r' && str.length()  > 0){

                //if its a reading of 6 then reset and try again.
                distance = Integer.parseInt( str.substring(2) );
                if(distance == 6){
                    str = "";
                    continue;
                }

                break; 
            }
            
            //add to existing reading
            else if( str.length() != 0){
                str += buf[0] - '0';
            }

            //start of a new reading
            else if( buf[0] == 'R'){
                str += buf[0] - '0';
            }
        }
        
        return distance; 
    }

    // This is the main function to utilize the motion detector.
    // It will first do all the initial calculations and then loop until
    // motion is detected then return.
    public int start(){
    
        System.out.println("calibrating sensor.");
        
        //get the average 
        double avg = this.getAverage();

        //calculate the low and high thresholds
        double low = this.getLowThreshold(avg);
        double high = this.getHighThreshold(avg);
        
        System.out.println("sensor calibrated. \ndetecting motion.");

        //start detecting motion
        int motionDetected = this.detectMotion(low, high);

        System.out.println("motion detected.");

        return motionDetected;
    }

    // This function will constantly check the distance to see
    // if it falls between the thresholds. If it doesn't it returns.
    private int detectMotion(double low, double high){
        
        //loop to get average and test against thresholds.
        double avg = 0.0;
        do{

            double sum = 0.0;
            for(int i=0; i < ARRAYSIZE; i++){
                sum += this.getCurrentDistance();  
            }

            avg = sum / ARRAYSIZE;
        }while(avg > low && avg < high);

        return 1; 
    }

    //Calculates an average distance from the sensor.
    private double getAverage(){
    
        //get array of distances
        int size = ARRAYSIZE * 5;
        double sum = 0;
        for(int i=0; i < size; i++){
            sum += this.getCurrentDistance();  
        }

        double avg = sum / size;
        return avg;
    }

    // This calculates the low threshold.
    private double getLowThreshold(double avg){

        return avg - this.SENSITIVITY;
    }

    // This calculates the high threshold.
    private double getHighThreshold(double avg){
    
        return avg + this.SENSITIVITY;
    }
}
