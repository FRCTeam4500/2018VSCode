/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.automation;

import java.util.stream.IntStream;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Automation_SetWheelAngle extends Command {
    
    private double x, y, z;
    
    public Automation_SetWheelAngle(double x, double y, double z) {
        requires(Robot.swerve);
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        setTimeout(3);
        System.out.println("[INIT] SetWheelAngle");
        System.out.println("[LOG] anglePos " + Robot.swerve.getFL().getAnglePosition());
        Robot.swerve.setAngle(x, y, z);
        Timer.delay(0.2); // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.swerve.setAngle(x, y, z);
        System.out.println("[LOG] anglePos " + Robot.swerve.getFL().getAnglePosition());
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // double sumOfError = IntStream.of(Robot.swerve.getAngleError()).sum();
        double sumOfError = Robot.swerve.getFL().getAngleError();
        System.out.println("Checking if " + sumOfError + " <= 10");
        return Math.abs(sumOfError) <= 10 || isTimedOut();
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("[END] SetWheelAngle");
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}