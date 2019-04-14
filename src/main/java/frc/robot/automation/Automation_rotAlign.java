/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.automation;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        setTimeout(3);

        Robot.swerve.getBR().setDriveEncoderPosition(0);
        // double angle = Robot.vision.getAngle();
        double angle = Robot.prefs.getDouble("Angle", 0.0);
        double distanceToMove = (angle * RobotMap.driveTicksFor360Deg) / 360;
        //double distanceToMove = (RobotMap.wheelToRobotCenterDiameterCM * RobotMap.driveTicksPerRotation * angle) / (360 * RobotMap.wheelDiameterCM);
        SmartDashboard.putNumber("deg Start", angle);
        SmartDashboard.putNumber("deg Tick", distanceToMove);

        
        // double newAngle = (distanceToMove / RobotMap.driveTicksFor360Deg) * 360;
        Robot.logger.write(Logger.LogEvent.INFO, String.format("Angle is %.2f", angle), this);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("Distance to move is %.2f", distanceToMove), this);
        // Robot.logger.write(Logger.LogEvent.INFO, String.format("newAngle is %.2f", newAngle), this);
        Robot.swerve.setDrivePosition(distanceToMove);
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        int err = Robot.swerve.getBR().getDriveError();
        errorSum += err;
        boolean conditionA = errorSum > 5 && Math.abs(err) < 5;
        boolean conditionB = this.isTimedOut();
        Robot.logger.write(Logger.LogEvent.INFO, String.format("AngleError is %d ErrorSum is %d", err, errorSum), this);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("AngleError condition is %b, timedOut condition is %b", conditionA, conditionB), this);
        return conditionB;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        double err = Robot.swerve.getBR().getDriveError();
        
        double degMoved = (Robot.swerve.getBR().getDrivePosition() * 360) / RobotMap.driveTicksFor360Deg;
        SmartDashboard.putNumber("deg Err", Robot.swerve.getBR().getDriveError());
        SmartDashboard.putNumber("deg End", degMoved);
        // System.out.println("[LOG] final drive error as angle " + (360 * err * RobotMap.wheelDiameter) / (RobotMap.wheelToRobotCenterDiameterCM * RobotMap.driveTicksPerRotation));
        Robot.logger.write(Logger.LogEvent.INFO, String.format("final drive error %.2f", err), this);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("final drive error as angle is %.2f", ((err * 360) / RobotMap.driveTicksFor360Deg)), this);
        Robot.logger.write(Logger.LogEvent.EVENT, "Finished execution", this);
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}