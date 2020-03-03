
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PWMTalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  

  // Talon Controls

  private Talon shoot = new Talon(1);
  private Talon intake = new Talon(3);

  private Talon Polocord = new Talon(2);
  /*
   * private Talon leftMotor = new Talon(7); private Talon rightMotor = new
   * Talon(6);
   */
  // Joystick Controls

  private final Joystick bigJ = new Joystick(1);
  private final XboxController xBox = new XboxController(0);
  // Timer
  public Timer time = new Timer();

  // Constraints for the Joystick

  private final double deadZone = 0.05;

  // Diffrential Drive
  Talon m_frontLeft = new Talon(5);
  Talon m_rearLeft = new Talon(7);
  SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

  Talon m_frontRight = new Talon(4);
  Talon m_rearRight = new Talon(6);
  SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

  DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

  // Safety offline
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    // Chooser Code

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);

    m_chooser.addOption("Blue 1", kCustomAuto);

    m_chooser.addOption("Blue 2", kCustomAuto);

    m_chooser.addOption("Blue 3", kCustomAuto);

    m_chooser.addOption("Red 1", kCustomAuto);

    m_chooser.addOption("Red 2", kCustomAuto);

    m_chooser.addOption("Red 3", kCustomAuto);

    SmartDashboard.putData("Auto choices", m_chooser);

    // Camera Server
    CameraServer.getInstance().startAutomaticCapture();

    // Data for motos

    SmartDashboard.putNumber("DrivePower", 0.82);
    SmartDashboard.putNumber("ShootPower", 0.5);
    SmartDashboard.putNumber("IntakePower", .5);
    SmartDashboard.putNumber("PolyCord", 0.6);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   * 
   * 
   */

  @Override
  public void teleopPeriodic() {

    // ALL THE POWER
    double drivePower = SmartDashboard.getNumber("DrivePower", 0.85);
    double intakePower = SmartDashboard.getNumber("IntakePower", 0.9);
    double shootPower = SmartDashboard.getNumber("ShootPower", 0.9);
    double polocordPower = SmartDashboard.getNumber("PolocordPower", 0.6);
    Scheduler.getInstance().run();

    // The Safety Captian should not look at this section

    intake.setSafetyEnabled(true);
    shoot.setSafetyEnabled(true);
    Polocord.setSafetyEnabled(true);
    m_drive.setSafetyEnabled(true);
   

    if (xBox.getAButtonPressed() == true) {
      Polocord.set(polocordPower);
    } else {
      Polocord.set(0);
    }

    if (xBox.getBButton() == true) {
      shoot.set(shootPower);
    } else {
      shoot.set(0);
    }

    if (xBox.getRawButton(1) == true) {
      intake.set(intakePower);
    } else {
      intake.set(intakePower);
    }

    if (Math.abs(bigJ.getY()) > deadZone || Math.abs(bigJ.getX()) > deadZone) {
      m_drive.arcadeDrive(bigJ.getY()*drivePower,bigJ.getX()*drivePower);

   }else{
    m_drive.arcadeDrive(0,0);
   }
// this is old scripts but are no longer current due to us trying a diffrent method
    // Polycord Script 
/*
    if(xBox.getRawButton(0) == true) {
      Polocord.set(polocordPower);
    }else{
      Polocord.set(0);
    }
    
    // Shooting 
    
    if (xBox.getRawButton(5) == true){
      shoot.set(shootPower);
    }else{
      shoot.set(0);
    }
    
    // Intake
    
    if (xBox.getRawButton(4) == true || xBox.getRawButton == true){
      intake.set(intakePower);
    }else{
      intake.set(0);
    }




    if(Math.abs(bigJ.getY()) > deadZone || Math.abs(bigJ.getX()) > deadZone){
      move.arcadeDrive(bigJ.getY()*drivePower,bigJ.getX()*drivePower);
    }
*/



  }

  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public Robot() {
    LiveWindow.disableAllTelemetry();
  }
}
