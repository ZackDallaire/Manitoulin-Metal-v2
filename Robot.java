/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6865.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMSpeedController;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	private Talon shootPower = new Talon (1);
	private edu.wpi.first.wpilibj.Talon intake = new Talon (2);
	private edu.wpi.first.wpilibj.Talon DriverPower = new Talon (4);
	//public Talon Climb = new Talon (4);
	private Talon Polocord = new Talon (5);
	Talon leftDrive = new Talon(1);
	Talon rightDrive = new Talon(2);
	/*
	 * Spark rightDrive1 = new Spark(3);
	 * Spark rightDrive2 = new Spark(4);
	 */
	
	
	// Set Joysticks
	
	private Joystick bigJ = new Joystick(1);
	private Joystick xBox = new Joystick(0);
	
	// Timer
	public Timer time = new Timer();
	
	
	// Set Constants

	private final double deadZone = 0.05;

	// Diffrential Drive
	private  DifferentialDrive move = new DifferentialDrive(new Talon(0),new Talon(1));
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		
		
		SmartDashboard.putData("Auto choices", m_chooser);
    	SmartDashboard.putNumber("DriverPower",0.82);
    	SmartDashboard.putNumber("ShootPower,",0.9);
    	SmartDashboard.putNumber("IntakePower",0.9);
    	SmartDashboard.putNumber("Polycord",0.6);
		
    	// Camera USB
    	CameraServer.getInstance().startAutomaticCapture();
	}
	
	

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		 m_autoSelected = SmartDashboard.getString("Auto Selector",
		 kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	

	
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}


	

	
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
    	
		double DrivePower = SmartDashboard.getNumber("DriverPower", 0.7);

    	double shootPower = SmartDashboard.getNumber("ShootPower", 0.9);
    	double intakePower = SmartDashboard.getNumber("IntakePower",0.9);
    	//double ClimbPower = SmartDashboard.getNumber('ClimbPower',0.9);
    	double polocord = SmartDashboard.getNumber("Polocord",0.6);
    	
		
		// SAFETYS DISABLED OF COURSE
	
		
		
		// Driving Script
		
		
		 if(Math.abs(bigJ.getY()) > deadZone || Math.abs(bigJ.getX()) > deadZone){
		        move.arcadeDrive(bigJ.getY()*DrivePower, bigJ.getX()*DrivePower);
		      } else {
		    	  move.arcadeDrive(0,0);
		      }
		 // Shooter Controls
		/* 
		 if (xBox.getRawButton(5) == true) {
			 shootPower.set( xBox.getRawButton(0)*shootPower);
		 }
		
		 
		 if(xBox.getRawButton(4) == true) {
			 feed.set(intakePower);
		 }
		 if (xBox.getRawButton(0) == true) {
			 polocord.set(Polocord);
		 }
		 */

	}

		

	 /* This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}}

