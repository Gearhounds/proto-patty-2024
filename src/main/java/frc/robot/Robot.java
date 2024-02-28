// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Collection;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private TalonFX test_falcon = new TalonFX(5);
  PositionVoltage m_request;
  
  WPI_TalonSRX leftFront = new WPI_TalonSRX(2);
  WPI_TalonSRX leftBack = new WPI_TalonSRX(1);
  WPI_TalonSRX rightFront = new WPI_TalonSRX(3);
  WPI_TalonSRX rightBack = new WPI_TalonSRX(4);

  MotorControllerGroup leftMotors = new MotorControllerGroup(leftFront, leftBack);
  MotorControllerGroup rightMotors = new MotorControllerGroup(rightFront, rightBack);

  DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  Orchestra orc = new Orchestra();

  private Joystick joystick = new Joystick(0);
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    Slot0Configs slot0Configs = new Slot0Configs();
    slot0Configs.kP = 24;
    slot0Configs.kI = 0;
    slot0Configs.kD = 0.1;
    test_falcon.getConfigurator().apply(slot0Configs);
    m_request = new PositionVoltage(0).withSlot(0);
    orc.addInstrument(test_falcon);
    orc.loadMusic("mega.chrp");
    // orc.play();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
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

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // if (joystick.getRawButtonPressed(3)) {
    //   orc.loadMusic("pirates.chrp");
    // }
    if (joystick.getRawButtonPressed(1)) {
      if (orc.isPlaying()) {
        orc.pause();
      } else {
        orc.play();
      }
    }
    if (joystick.getRawButtonPressed(2)) {
      orc.stop();
      orc.play();
    }

    drive.arcadeDrive(joystick.getRawAxis(4), joystick.getRawAxis(1));

    

    if (joystick.getRawButton(3)) {
      m_request.withPosition(10);
    } else {
      m_request.withPosition(0);
    }

    test_falcon.setControl(m_request);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
