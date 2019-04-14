/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.automation.Automation_Group_Test;
import frc.robot.automation.DriveTune;
import frc.robot.commands.Swerve_GyroReset;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    Joystick driveStick;
    
    Button driveResetGyro, driveTuneOne, driveTuneTwo, driveTuneThree, driveTuneFour;
    Button intakeGrabCube, intakeLoadCube, intakeClawOpen;
    Button automationOne, automationTwo;
    
    public OI() {
        driveStick = new Joystick(0);
        
        driveResetGyro = new JoystickButton(driveStick, 1);
        driveResetGyro.whenPressed(new Swerve_GyroReset());

        // driveTuneOne = new JoystickButton(driveStick, 7);
        // driveTuneOne.whenPressed(new DriveTune(2000));
        
        // driveTuneTwo = new JoystickButton(driveStick, 9);
        // driveTuneTwo.whenPressed(new DriveTune(-2000));

        // driveTuneThree = new JoystickButton(driveStick, 8);
        // driveTuneThree.whenPressed(new DriveTune(8000));

        // driveTuneFour = new JoystickButton(driveStick, 10);
        // driveTuneFour.whenPressed(new DriveTune(-8000));

        // automationOne = new JoystickButton(driveStick, 4);
        // automationOne.whenPressed(new Automation_Group_Test());
    }

    public double getX() {
		return Math.abs(driveStick.getX()) > RobotMap.DEADZONE_XY ? driveStick.getX() / Robot.prefs.getDouble("XYSpeed", 1.0) : 0;
	}
	
	public double getY() {
		return Math.abs(driveStick.getY()) > RobotMap.DEADZONE_XY ? driveStick.getY() / Robot.prefs.getDouble("XYSpeed", 1.0) : 0;
	}
	
	public double getZ() {
		return Math.abs(driveStick.getZ()) > RobotMap.DEADZONE_Z ? driveStick.getZ() / Robot.prefs.getDouble("ZSpeed", 1.0) : 0;
	}
	
	public double getSlider() {
		return driveStick.getThrottle();
	}
}
