/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.automation;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.utility.Logger;

public class Automation_xAlign extends Command {

    private int errorSum = 0;

    public Automation_xAlign() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerve);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.logger.write(Logger.LogEvent.EVENT, "Initialized", this);
        setTimeout(3);

        
        Robot.swerve.getBR().setDriveEncoderPosition(0);
        
        double[] data = Robot.vision.getCenter();
        double XCM = ((data[0] - (RobotMap.imgW/2))*RobotMap.cameraHeight) / RobotMap.focalLength;
        double XTick = (XCM / (Math.PI * RobotMap.wheelDiameterCM)) * RobotMap.driveTicksPerRotation;
        
        // Robot.swerve.setDrivePosition(XTick);
        
        SmartDashboard.putNumber("X Data", data[0]);
        SmartDashboard.putNumber("X Start", XCM);
        SmartDashboard.putNumber("XTick", XTick);
        SmartDashboard.putNumber("FL", RobotMap.focalLength);
        
        Robot.logger.write(Logger.LogEvent.INFO, String.format("X: %.3f Y: %.3f", data[0], data[1]), this);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("X: %.3f", XCM), this);
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
        return conditionA || conditionB;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.logger.write(Logger.LogEvent.EVENT, "Finished execution", this);
        double currentPos = Robot.swerve.getBR().getDrivePosition();
        double XCM = (RobotMap.wheelDiameterCM * Math.PI * currentPos) / (RobotMap.driveTicksPerRotation);
        SmartDashboard.putNumber("X End", XCM);

    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
