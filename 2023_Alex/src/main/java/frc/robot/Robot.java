/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
// more to do - here by flooof

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;

import com.revrobotics.*;


public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;

  private final Timer m_timer = new Timer();

  //Define controllers.
  private XboxController gamepadDrive;
  //private XboxController gamepadOperator;
  //private Joystick m_stick = new Joystick(0);


  private WPI_TalonSRX leftMotorControllerCIM;
  //private WPI_TalonSRX leftMotorControllerCIM2;
  private WPI_TalonSRX rightMotorControllerCIM;
  //private WPI_TalonSRX rightMotorControllerCIM2;
  

  //private SpeedControllerGroup leftMotorGroup;
  //private SpeedControllerGroup rightMotorGroup;

  //private SpeedControllerGroup climbMotorGroup;

  //private ColorWheelSystem colorWheelSystem;

  //Robot navigation 
  //private int leftEncoderReading = 159;
  //private int rightEncoderReading = 314;
  //private PigeonIMU pigeonIMU;
  //private double [] pigeonIMUData;

  //private double robotHeading;
  
  //Pixy variables
  //private Pixy2 pixy;

  @Override
  public void robotInit() {

//The system inputs/outputs are arranged according to the spreadsheet here:  https://docs.google.com/spreadsheets/d/1-l5YZYubWAp52MwDntlmeQ8fC4OWeWa1os5C94XbTL8/edit?usp=sharing
//Set up the drive motor controllers
      leftMotorControllerCIM = new WPI_TalonSRX(0);
      //leftMotorControllerCIM2 = new WPI_TalonSRX(1);
      
      //leftMotorGroup = new SpeedControllerGroup(leftMotorControllerCIM1);//,leftMotorControllerCIM2);

      rightMotorControllerCIM = new WPI_TalonSRX(1);
      //rightMotorControllerCIM2 = new WPI_TalonSRX(3);
      //rightMotorGroup = new SpeedControllerGroup(rightMotorControllerCIM1);//,rightMotorControllerCIM2);

//Create a differential drive system using the left and right motor groups
      m_myRobot = new DifferentialDrive(leftMotorControllerCIM, rightMotorControllerCIM);
      

//Set up the two Xbox controllers. The drive is for driving, the operator is for all conveyor and color wheel controls
      gamepadDrive = new XboxController(0);
      //gamepadOperator = new XboxController(1);

//Set up encoders on the left and right sides of the drive
//
      //leftMotorControllerCIM1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder); 
      //leftMotorControllerCIM1.setSensorPhase(true);
      //leftMotorControllerCIM1.setSelectedSensorPosition(0);
      //rightMotorControllerCIM1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder); 
      //rightMotorControllerCIM1.setSelectedSensorPosition(0);

//Set up the Pigeon
      //pigeonIMU = new PigeonIMU(leftMotorControllerCIM1);
      //pigeonIMUData = new double[3];
      //pigeonIMU.setFusedHeading(70);
    
//Set up the Pixy
/*
    pixy = Pixy2.createInstance(new SPILink()); // Creates a new Pixy2 camera using SPILink
		pixy.init(); // Initializes the camera and prepares to send/receive data
		pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
		pixy.setLED(200, 30, 255); // Sets the RGB LED to purple
   */   
  }

  @Override
  public void teleopPeriodic() {

  //**********DRIVE CONTROL**********//
  //Set the drive motors according to the coordinates of the right joystick on the drive controller


    double leftY = gamepadDrive.getY(Hand.kLeft)*-1.0;

    double rightX = gamepadDrive.getX(Hand.kRight)*0.7;

//**********SMART DASHBOARD CONTROL**********//
//This line sends things to the smart dashboard. We can use this to get any information we might want from the system.
    SmartDashboard.putNumber("leftMotor", leftMotorControllerCIM.get());
    SmartDashboard.putNumber("rightMotor", rightMotorControllerCIM.get());

    //Show robot position data from encoders and Pigeon IMU
    //SmartDashboard.putNumber("leftEncoder",leftEncoderReading);
    //SmartDashboard.putNumber("rightEncoder",rightEncoderReading);
    //Nam - your code goes in the next line. Talk to Leo if you need help.
    //SmartDashboard.putNumber("Robot Heading",robotHeading);


 //**********ROBOT NAVIGATION DATA**********//
  //leftEncoderReading = leftMotorControllerCIM.getSelectedSensorPosition();
  //rightEncoderReading = rightMotorControllerCIM.getSelectedSensorPosition();
  //pigeonIMU.getYawPitchRoll(pigeonIMUData);

  //robotHeading = pigeonIMU.getFusedHeading();  

} //End of robotPeriodicTeleop


   /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    /*
    if (m_timer.get() < 2.0) {
      m_myRobot.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_myRobot.stopMotor(); // stop robot
    }
    */
  }

  public double abs(double number){
    if(number > 0){
      return number;
    }
    else{
      return -number;
    }
  }
}


//on first gamepad:
//left stick is dumper forward, right stick is intake forward
//right joystick currently the one being used
//



///second operator is counting the cells to make sure there aren't more than 5

//top right bumper button rotates color wheel between 3 - 5 times
//bottom right trigger button sets color to value from FMS

//dashboard shows sequence complete to say that stage 1 color wheel is complete
//read color output from FMS
//D pad for hanging deploy sequence
//safety button for deploy

//use rumble feature to show that we are in position against the trench
