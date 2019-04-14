/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.automation;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Automation_SetPIDFVA extends Command {
  public Automation_SetPIDFVA() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.swerve);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      Robot.swerve.getBR().getSpeedMotor().config_kP(0, Robot.prefs.getDouble("P", 0.0), RobotMap.TIMEOUT);
      Robot.swerve.getBR().getSpeedMotor().config_kI(0, Robot.prefs.getDouble("I", 0.0), RobotMap.TIMEOUT);
      Robot.swerve.getBR().getSpeedMotor().config_kD(0, Robot.prefs.getDouble("D", 0.0), RobotMap.TIMEOUT);
      Robot.swerve.getBR().getSpeedMotor().config_kF(0, Robot.prefs.getDouble("F", 0.0), RobotMap.TIMEOUT);
      Robot.swerve.getBR().getSpeedMotor().configMotionCruiseVelocity((int) Robot.prefs.getDouble("V", 0.0), RobotMap.TIMEOUT);
      Robot.swerve.getBR().getSpeedMotor().configMotionAcceleration((int) Robot.prefs.getDouble("A", 0.0), RobotMap.TIMEOUT);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
