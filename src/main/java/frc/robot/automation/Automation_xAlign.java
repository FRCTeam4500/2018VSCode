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

public class Automation_xAlign extends Command {
    public Automation_xAlign() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerve);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("[INIT] xAlign");
        setTimeout(3);
        double[] data = Robot.vision.getCenter();
        double X = (data[0] - RobotMap.imgW)*RobotMap.cameraHeight / RobotMap.focalLength;
        Timer.delay(0.2);
        System.out.println("[INFO] " + X);
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.swerve.getBR().getDriveError()) < 10 || isTimedOut();
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
