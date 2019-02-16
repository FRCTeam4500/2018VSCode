/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    /*
     * =================== Drivetrain ===================
     */

    public final static int FLSPEEDPORT = 7, FLANGLEPORT = 10;
    public final static int FRSPEEDPORT = 2, FRANGLEPORT = 1;
    public final static int BLSPEEDPORT = 6, BLANGLEPORT = 5;
    public final static int BRSPEEDPORT = 3, BRANGLEPORT = 4;

    public final static double L = 29.5;
    public final static double W = 29.5;
    public final static double COUNTPERDEG = 16.2539;

    public final static double angleP = 2, angleI = 0, angleD = 20, angleF = 0.546;
    public final static int angleV = 7488, angleA = 7488;

    /*===================
	 * Shooter
	 *===================*/
	
	public final static int LSHOOTER = 8, RSHOOTER = 9;
	
	public final static int LEVELSHOOTERONE = 2, LEVELSHOOTERTWO = 3, LEVELSHOOTERPCM = 1;
	public final static int LIFTSHOOTERONE = 4, LIFTSHOOTERTWO = 5, LIFTSHOOTERPCM = 1;
	
	public final static double RPMTOTICKSPER100MS = 0.146484;
	
	public final static int HIGHGOALSPEED = 3300;
	public final static int LOWGOALSPEED = 2800;
    public final static int SWITCHSPEED = 1200;
    
    /*===================
	 * Intake
	 *===================*/
	
	public final static int INTAKEPOSITIONMOTOR = 11;
	public final static int INTAKELEFTMOTOR = 3, INTAKERIGHTMOTOR = 1;
	public final static int INTAKECLAWSOLENOIDONE = 6, INTAKECLAWSOLENOIDTWO = 7, INTAKECLAWSOLENOIDPCM = 1;
	
	 public final static double INTAKE_DOWNPOSITION = 4713;
	 public final static double INTAKE_SCALEPOSITION = 0;
     public final static double INTAKE_SWITCHPOSITION = 2053;
     
    /*
     * =================== Configuration ===================
     */

    public final static double DEADZONE_XY = 0.2;
    public final static double DEADZONE_Z = 0.4;
    public final static int TIMEOUT = 0;

    public static double imgW;
    public static double imgH;
    public static double imgCenterW;
    public static double imgCenterH;
    public static double focalLength;
    public static final double FOV = 68.5;

    public final static double wheelDiameterCM = 7.62;
    public final static double robotRotationalRadiusCM = 42;


    
     /*
     * =================== Auto ===================
     */

    public final static double wheelDiameter = 0.08255;
	public final static double wheelBaseWidth = 0.689;
	public final static double wheelBaseDepth = 0.5612;
	
	public final static double flKv = 0.28;
	public final static double flKa = 0;
	
	public final static double frKv = 0.28;
	public final static double frKa = 0;
	
	public final static double blKv = 0.28;
	public final static double blKa = 0;
	
	public final static double brKv = 0.28;
	public final static double brKa = 0;
	public final static int ticksPerRotation = 5280;

     /*
     * =================== Methods ===================
     */

     public static void dashboardDisplay() {
         SmartDashboard.putNumber("gyro", Robot.swerve.getGyro());
     }

     /*===================
	 * Other
	 *===================*/
	
	public final static int COMPRESSOR = 2;

}
