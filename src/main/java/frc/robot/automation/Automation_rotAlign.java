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
import frc.robot.utility.Logger;

public class Automation_rotAlign extends Command {
    
    // private double distanceToMove;
    private int errorSum = 0;
    
    public Automation_rotAlign() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerve);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.logger.write(Logger.LogEvent.EVENT, "Initializing", this);
        setTimeout(5);

        Robot.swerve.getBR().setDriveEncoderPosition(0);
        double angle = Robot.vision.getAngle();
        double distanceToMove = (RobotMap.wheelToRobotCenterDiameterCM * RobotMap.driveTicksPerRotation * angle) / (360 * RobotMap.wheelDiameterCM);
        
        
        double newAngle = (distanceToMove / RobotMap.driveTicksFor360Deg) * 360;
        Robot.logger.write(Logger.LogEvent.INFO, String.format("Angle is %.2f", angle), this);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("Distance to move is %.2f", distanceToMove), this);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("newAngle is %.2f", newAngle), this);
        // System.out.println("[LOG] angle " + angle);
        // System.out.println("[LOG] distanceToMove " + distanceToMove);
        // System.out.println("[LOG] newAngle" + newAngle);
        // this.distanceToMove = distanceToMove;
        Robot.swerve.setDrivePosition(distanceToMove);
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        errorSum += Robot.swerve.getBR().getDriveError();
        boolean conditionA = errorSum > 0 && Math.abs(Robot.swerve.getBR().getDriveError()) < 5;
        boolean conditionB = this.isTimedOut();
        Robot.logger.write(Logger.LogEvent.INFO, String.format("AngleError condition is %b, timedOut condition is %b", conditionA, conditionB), this);
        return conditionA || conditionB;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        double err = Robot.swerve.getBR().getDriveError();
        // System.out.println("[LOG] final drive error as angle " + (360 * err * RobotMap.wheelDiameter) / (RobotMap.wheelToRobotCenterDiameterCM * RobotMap.driveTicksPerRotation));
        Robot.logger.write(Logger.LogEvent.INFO, String.format("final drive error %.2f", err), this);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("final drive error as angle is %.2f", (360*err*RobotMap.wheelDiameter) / (RobotMap.wheelToRobotCenterDiameterCM * RobotMap.driveTicksPerRotation)), this);
        Robot.logger.write(Logger.LogEvent.EVENT, "Finished execution", this);
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}