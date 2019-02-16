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

public class Automation_rotAlign extends Command {
  
  double distanceToMove;

  public Automation_rotAlign() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.swerve);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Controllers.rotationalAlignmentController.reset();
    Robot.swerve.getFR().setDriveEncoderPosition(0);
    double angle = Robot.vision.getAngle();
    double distanceToMove = (1024 * RobotMap.robotRotationalRadiusCM * angle) / (45 * RobotMap.wheelDiameterCM);
    this.distanceToMove = distanceToMove;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.swerve.setDrivePosition(distanceToMove);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.swerve.getFL().getDriveError()) < 10;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
      System.out.println("Target reached");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}