/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private final Timer m_timer = new Timer();
  private XboxController gamepadDrive;
  private XboxController gamepadOperator;
  private WPI_TalonSRX leftMotorControllerCIM1;
  private WPI_TalonSRX leftMotorControllerCIM2;
  private WPI_TalonSRX rightMotorControllerCIM1;
  private WPI_TalonSRX rightMotorControllerCIM2;
  private MotorControllerGroup leftMotorGroup;
  private MotorControllerGroup rightMotorGroup;
  //Robot navigation 
  private int leftEncoderReading = 159;
  private int rightEncoderReading = 314;
  private PigeonIMU pigeonIMU;
  private double [] pigeonIMUData;
  private double robotHeading;
  @Override
  public void robotInit() {
      leftMotorControllerCIM1 = new WPI_TalonSRX(0);
      leftMotorControllerCIM2 = new WPI_TalonSRX(1);
      leftMotorGroup = new MotorControllerGroup(leftMotorControllerCIM1,leftMotorControllerCIM2);
      rightMotorControllerCIM1 = new WPI_TalonSRX(2);
      rightMotorControllerCIM2 = new WPI_TalonSRX(3);
      rightMotorGroup = new MotorControllerGroup(rightMotorControllerCIM1,rightMotorControllerCIM2);
      m_myRobot = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
      gamepadDrive = new XboxController(0);
      gamepadOperator = new XboxController(1);
      pigeonIMU = new PigeonIMU(rightMotorControllerCIM2);
      pigeonIMUData = new double[3];
      pigeonIMU.setFusedHeading(70);
  }
  @Override
  public void teleopPeriodic() {
    double leftY = gamepadDrive.getLeftX()*1.0;
    double rightX = gamepadDrive.getLeftY()*1.0;
    m_myRobot.arcadeDrive(leftY,rightX);
    SmartDashboard.putNumber("leftMotor", leftMotorControllerCIM1.get());
    SmartDashboard.putNumber("rightMotor", rightMotorControllerCIM1.get());
    SmartDashboard.putNumber("Robot Heading",robotHeading);
    pigeonIMU.getYawPitchRoll(pigeonIMUData);
    robotHeading = pigeonIMU.getFusedHeading();  
  } //End of robotPeriodicTeleop
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }
  @Override
  public void autonomousPeriodic() {
    if (m_timer.get() < 2.0) {
      m_myRobot.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_myRobot.stopMotor(); // stop robot
    }
  }
  public double abs(double number){
    if(number > 0){
      return number;
    } else{
      return -number;
    }
  }
}
