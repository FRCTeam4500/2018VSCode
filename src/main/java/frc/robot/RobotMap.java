/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	/*===================
	 * Drivetrain
	 *===================*/
	
    public final static int FLSPEEDPORT = 7, FLANGLEPORT = 10;
	public final static int FRSPEEDPORT = 2, FRANGLEPORT = 1;
	public final static int BLSPEEDPORT = 6, BLANGLEPORT = 5;
	public final static int BRSPEEDPORT = 3, BRANGLEPORT = 4;
	
	public final static double L = 29.5;
	public final static double W = 29.5;
	public final static double COUNTPERDEG = 16.2539;

	/*===================
	 * Configuration
	 *===================*/
	
	public final static double DEADZONE_XY = 0.2;
	public final static double DEADZONE_Z = 0.4;
	public final static int TIMEOUT = 0;
}
