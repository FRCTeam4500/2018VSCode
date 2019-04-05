/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.testing;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utility.Logger;

public class Testing_SetWheelAngle extends Command {

    private int errorSum = 0;
    private int lastError = 0;
    private double x, y, z;
    
    public Testing_SetWheelAngle(double x, double y, double z) {
        requires(Robot.swerve);
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.logger.write(Logger.LogEvent.EVENT, "Initializing Testing_SetWheelAngle", this);
        Preferences prefs = Robot.prefs;
        setTimeout(3);
        // double angleX = prefs.getDouble("Angle X", 0.0);
        // double angleY = prefs.getDouble("Angle Y", 0.0);
        // double angleZ = prefs.getDouble("Angle Z", 0.0);
        Robot.logger.write(Logger.LogEvent.INFO, String.format("aX: %.2f aY: %.2f aZ: %.2f", x, y, z), this);
        Robot.swerve.setAngle(x, y, z);  
        // Timer.delay(1);
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.logger.write(Logger.LogEvent.INFO, String.format("AngleError is %d", Robot.swerve.getFL().getAngleError()), this);
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        errorSum += Robot.swerve.getFL().getAngleError();
        boolean conditionA = errorSum > 0 && Math.abs(Robot.swerve.getFL().getAngleError()) < 5;
        boolean conditionB = this.isTimedOut();
        
        Robot.logger.write(Logger.LogEvent.INFO, String.format("AngleError condition is %b, timedOut condition is %b", conditionA, conditionB), this);
        lastError = Robot.swerve.getFL().getAngleError();
        
        return conditionA || conditionB;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.logger.write(Logger.LogEvent.EVENT, "Testing_SetWheelAngle has ended", this);
        Robot.swerve.cancelLoops();
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
