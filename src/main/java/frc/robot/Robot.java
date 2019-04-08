/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.automation.Automation_Group_RotAlign;
import frc.robot.automation.Automation_Group_Test;
import frc.robot.automation.Automation_Group_XAlign;
import frc.robot.commands.Robot_Group_PreConfigure;
import frc.robot.commands.Swerve_Drive;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.WheelModule;
import frc.robot.utility.Logger;
import frc.robot.utility.automation.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    public static WheelModule fl, fr, bl, br;
    public static Swerve swerve;
    public static Vision vision;
    public static Preferences prefs;
    public static Logger logger;
    
    public static OI oi;

    Command m_autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        bl = new WheelModule(RobotMap.BLANGLEPORT, RobotMap.BLSPEEDPORT, "bl", false); 
		br = new WheelModule(RobotMap.BRANGLEPORT, RobotMap.BRSPEEDPORT, "br", false);
		fl = new WheelModule(RobotMap.FLANGLEPORT, RobotMap.FLSPEEDPORT, "fl", false); 
		fr = new WheelModule(RobotMap.FRANGLEPORT, RobotMap.FRSPEEDPORT, "fr", true);
		
        swerve = new Swerve(fl, fr, bl, br);
        
        vision = new Vision();
        // PID_Controllers.initializeControllers();

        prefs = Preferences.getInstance();
        SmartDashboard.putData("Automation_Group_Test", new Automation_Group_Test());
        SmartDashboard.putData("Automation_RotAlign", new Automation_Group_RotAlign());
        SmartDashboard.putData("Automation_XAlign", new Automation_Group_XAlign());
        // SmartDashboard.putData("Automation_SetWheelAngle", new Automation_SetWheelAngle(prefs.getDouble("Angle X", 0.0), prefs.getDouble("Angle Y", 0.0), prefs.getDouble("Angle Z", 0.0)));
        // SmartDashboard.putData("Automation_rotAlign", new Automation_rotAlign());
        // SmartDashboard.putData("Automation_xAlign", new Automation_xAlign());
        // SmartDashboard.putData("Testing_Group", new Testing_Group());
        prefs.putDouble("A", 0.0);
        // prefs.putDouble("Angle X", 0.0);
        // prefs.putDouble("Angle Y", 0.0);
        // prefs.putDouble("Angle Z", 0.0);
        logger = new Logger();
        oi = new OI();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        RobotMap.dashboardDisplay();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString code to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons to
     * the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        // if (m_autonomousCommand != null) {
        // m_autonomousCommand.start();
        // }
        if (swerve.getDefaultCommand() != null) {
            swerve.setDefaultCommand(null);
        }
        Command cmd = new Automation_Group_Test();
        cmd.start();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // if (m_autonomousCommand != null) {
        // m_autonomousCommand.cancel();
        // }
        Command preconfigure = new Robot_Group_PreConfigure();
        preconfigure.start();
        if (swerve.getDefaultCommand() == null) {
            swerve.setDefaultCommand(new Swerve_Drive());
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        RobotMap.dashboardDisplay();
    }

    @Override
    public void testInit() {
        logger.setDebugMode(true);    
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }

}
