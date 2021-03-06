/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.automation;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveTune extends Command {
    
    private int position;
    
    public DriveTune(int position) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerve);
        this.position = position;
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        setTimeout(2);
        System.out.println("[INIT] DriveTune");
        System.out.println("[LOG] Before reset drivePos is " + Robot.swerve.getBR().getSpeedMotor().getSelectedSensorPosition());
        Robot.swerve.getBR().getSpeedMotor().setSelectedSensorPosition(0);
        System.out.println("[LOG] After reset drivePos is " + Robot.swerve.getBR().getSpeedMotor().getSelectedSensorPosition());
        //   Robot.swerve.setAngle(0, 1, 0);
        Robot.swerve.setDrivePosition(position);
        Timer.delay(0.2);
        //   Robot.swerve.getBR().getSpeedMotor().set(ControlMode.MotionMagic, position);
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        System.out.println("Checking if " + Robot.swerve.getBR().getDriveError() + " <= 200");
        return Math.abs(Robot.swerve.getBR().getDriveError()) <= 50 || isTimedOut();
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("[END] DriveTune");
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
