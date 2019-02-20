/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Swerve_ToggleDefault extends Command {
    
    
    public Swerve_ToggleDefault() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerve);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.print("[INIT] Toggling default to ");
        if (Robot.swerve.getDefaultCommand() == null) {
            Robot.swerve.setDefaultCommand(new Swerve_Drive());
            System.out.print("enabled");
        } else {
            Robot.swerve.setDefaultCommand(null);
            System.out.print("disabled");
        }
        System.out.println("");
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
        System.out.println("[END] ToggleDefault");
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
