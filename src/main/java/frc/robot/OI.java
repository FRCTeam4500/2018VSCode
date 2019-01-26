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
import frc.robot.commands.Intake_ClawClose;
import frc.robot.commands.Intake_ClawOpen;
import frc.robot.commands.Intake_Group_LoadCube;
import frc.robot.commands.Intake_Group_Pressed;
import frc.robot.commands.Intake_Group_Released;
import frc.robot.commands.Swerve_GyroReset;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    Joystick driveStick;
    
    Button driveResetGyro;
    Button intakeGrabCube, intakeLoadCube, intakeClawOpen;
    
    public OI() {
        if (!driveStick.getName().equals("")) {
			driveResetGyro = new JoystickButton(driveStick, 7);
            driveResetGyro.whenPressed(new Swerve_GyroReset());

            intakeGrabCube = new JoystickButton(driveStick, 1);
            intakeGrabCube.whenPressed(new Intake_Group_Pressed(0.6, 0.6));
            intakeGrabCube.whenReleased(new Intake_Group_Released());
            
            intakeLoadCube = new JoystickButton(driveStick, 3);
            intakeLoadCube.whenPressed(new Intake_Group_LoadCube());
            
            intakeClawOpen = new JoystickButton(driveStick, 2);
            intakeClawOpen.whenPressed(new Intake_ClawOpen());
            intakeClawOpen.whenReleased(new Intake_ClawClose());
        }
    }

    public double getX() {
		return Math.abs(driveStick.getX()) > RobotMap.DEADZONE_XY ? driveStick.getX() : 0;
	}
	
	public double getY() {
		return Math.abs(driveStick.getY()) > RobotMap.DEADZONE_XY ? driveStick.getY() : 0;
	}
	
	public double getZ() {
		return Math.abs(driveStick.getZ()) > RobotMap.DEADZONE_Z ? driveStick.getZ() : 0;
	}
	
	public double getSlider() {
		return driveStick.getThrottle();
	}
}
