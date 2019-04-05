/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.automation;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.utility.Logger;

public class Automation_rotAlign extends Command {
    
    private double distanceToMove;
    private int errorSum = 0;
    
    public Automation_rotAlign() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerve);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.logger.write(Logger.LogEvent.EVENT, "Initializing", this);
        setTimeout(3);

        Robot.swerve.getBR().setDriveEncoderPosition(0);
        double angle = Robot.vision.getAngle();
        // double distanceToMove = (1024 * RobotMap.robotRotationalRadiusCM * angle) / (45 * RobotMap.wheelDiameterCM);
        double distanceToMove = (RobotMap.wheelToRobotCenterDiameterCM * RobotMap.driveTicksPerRotation * angle) / (360 * RobotMap.wheelDiameterCM);
        
        
        // double newAngle = (distanceToMove / RobotMap.driveTicksFor360Deg) * 360;
        // System.out.println("[LOG] angle " + angle);
        // System.out.println("[LOG] distanceToMove " + distanceToMove);
        // System.out.println("[LOG] newAngle" + newAngle);
        this.distanceToMove = distanceToMove;
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
        boolean conditionA = errorSum > 0 && Math.abs( Robot.swerve.getBR().getDriveError()) < 5;
        boolean conditionB = this.isTimedOut();

        return conditionA || conditionB;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        // double err = Robot.swerve.getBR().getDriveError();
        // System.out.println("[LOG] final drive error is " + err);
        // System.out.println("[LOG] final drive error as angle " + (360 * err * RobotMap.wheelDiameter) / (RobotMap.wheelToRobotCenterDiameterCM * RobotMap.driveTicksPerRotation));
        // System.out.println("[END] rotAlign");
        Robot.logger.write(Logger.LogEvent.EVENT, "Ended execution", this);

    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}