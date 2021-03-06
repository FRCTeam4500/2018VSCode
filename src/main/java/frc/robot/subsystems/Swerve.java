/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.utility.automation.WheelModuleResolver;

/**
* Add your docs here.
*/
public class Swerve extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private WheelModule fl, fr, bl, br;
    private ArrayList<WheelModule> modules = new ArrayList<>();    
    private AHRS gyro;
    
    /**
    * Constructor that takes each of the four modules that make up swerve drive
    * @param fl front left module
    * @param fr front right module
    * @param bl back left module
    * @param br back right module
    */
    public Swerve(WheelModule fl, WheelModule fr, WheelModule bl, WheelModule br) {
        this.fl = fl;
        this.fr = fr;
        this.br = br;
        this.bl = bl;

        modules.add(fl);
        modules.add(fr);
        modules.add(br);
        modules.add(bl);

        gyro = new AHRS(SPI.Port.kMXP);
    }
    
    
    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new Swerve_Drive());
    }

    public ArrayList<WheelModule> getModules() {
        return modules;
    }
    
    /*
    * ===================== gyro methods =====================
    */
    
    public void resetGyro() {
        gyro.reset();
    }
    
    public double getGyro() {
        return gyro.getAngle();
    }
    
    /*
    * ===================== helper methods =====================
    */
    
    /**
    * Calculates a vector (contains a magnitude (speed) and heading (angle)) for
    * each of the four wheel modules. Then calls the drive method in the four
    * modules to start the desired movement
    * 
    * @param x coordinate of the joystick
    * @param y coordinate of the joystick
    * @param z coordinate of the joystick
    */
    public void calculateVectors(double x, double y, double z) {
        double L = RobotMap.L;
        double W = RobotMap.W;
        double r = Math.sqrt((L * L) + (W * W));
        y *= -1;
        
        double gyro = getGyro() * Math.PI / 180;
        double temp = y * Math.cos(gyro) + x * Math.sin(gyro);
        x = -y * Math.sin(gyro) + x * Math.cos(gyro);
        y = temp;
        
        double a = x - z * (L / r) + 0;
        double b = x + z * (L / r);
        double c = y - z * (W / r) + 0;
        double d = y + z * (W / r);
        
        double brSpeed = Math.sqrt((a * a) + (c * c));
        double blSpeed = Math.sqrt((a * a) + (d * d));
        double frSpeed = Math.sqrt((b * b) + (c * c));
        double flSpeed = Math.sqrt((b * b) + (d * d));
        
        double max = brSpeed;
        if (brSpeed > max) {
            max = brSpeed;
        }
        if (blSpeed > max) {
            max = blSpeed;
        }
        if (frSpeed > max) {
            max = frSpeed;
        }
        if (flSpeed > max) {
            max = flSpeed;
        }
        
        if (max > 1) {
            brSpeed /= max;
            blSpeed /= max;
            frSpeed /= max;
            flSpeed /= max;
        }
        
        double brAngle = (Math.atan2(a, c) * 180 / Math.PI);
        double blAngle = (Math.atan2(a, d) * 180 / Math.PI);
        double frAngle = (Math.atan2(b, c) * 180 / Math.PI);
        double flAngle = (Math.atan2(b, d) * 180 / Math.PI);
        
        br.drive(brSpeed, brAngle);
        bl.drive(blSpeed, blAngle);
        fr.drive(frSpeed, frAngle);
        fl.drive(flSpeed, flAngle);
    }
    
    public void setAngle(double x, double y, double z) {
        double L = RobotMap.L;
        double W = RobotMap.W;
        double r = Math.sqrt((L * L) + (W * W));
        y *= -1;
        
        double gyro = getGyro() * Math.PI / 180;
        double temp = y * Math.cos(gyro) + x * Math.sin(gyro);
        x = -y * Math.sin(gyro) + x * Math.cos(gyro);
        y = temp;
        
        double a = x - z * (L / r) + 0;
        double b = x + z * (L / r);
        double c = y - z * (W / r) + 0;
        double d = y + z * (W / r);
        
        double brAngle = (Math.atan2(a, c) * 180 / Math.PI);
        double blAngle = (Math.atan2(a, d) * 180 / Math.PI);
        double frAngle = (Math.atan2(b, c) * 180 / Math.PI);
        double flAngle = (Math.atan2(b, d) * 180 / Math.PI);
        
        br.setAngle(brAngle);
        bl.setAngle(blAngle);
        fr.setAngle(frAngle);
        fl.setAngle(flAngle);
    }

    public void setDrivePosition(double pos) {
        br.setDrivePosition(pos, "br");
        fl.setDrivePosition(pos, "br");
        fr.setDrivePosition(pos, "br");
        bl.setDrivePosition(pos, "br");
    }

    public void cancelLoops() {
        br.cancelLoop();
        fl.cancelLoop();
        fr.cancelLoop();
        bl.cancelLoop();
    }
    
    /**
    * Returns an array of each module's angle error
    * 
    * @return int array in the form {fl, fr, bl, br}
    */
    public int[] getAngleError() {
        int flError = fl.getAngleError();
        int frError = fr.getAngleError();
        int blError = bl.getAngleError();
        int brError = br.getAngleError();
        return new int[] { flError, frError, blError, brError };
    }
    
    /**
    * Returns an array of each module's angle position
    * 
    * @return int array in the form {fl, fr, bl, br}
    */
    public int[] getAnglePosition() {
        int flPosition = fl.getAnglePosition();
        int frPosition = fr.getAnglePosition();
        int blPosition = bl.getAnglePosition();
        int brPosition = br.getAnglePosition();
        return new int[] { flPosition, frPosition, blPosition, brPosition };
    }
    
    /**
    * Returns an array of each module's drive position
    * 
    * @return int array in the form {fl, fr, bl, br}
    */
    public int[] getDrivePosition() {
        int flQ = fl.getDrivePosition();
        int frQ = fr.getDrivePosition();
        int blQ = bl.getDrivePosition();
        int brQ = br.getDrivePosition();
        
        return new int[] { flQ, frQ, blQ, brQ };
    }
    
    /**
    * Returns an array of each module's drive voltage
    * 
    * @return double array in the form {fl, fr, bl, br}
    */
    public double[] getVoltage() {
        double flV = fl.getVoltage();
        double frV = fr.getVoltage();
        double blV = bl.getVoltage();
        double brV = br.getVoltage();
        return new double[] { flV, frV, blV, brV };
    }
    
    /*
    * ===================== helper methods =====================
    */
    
    public WheelModule getFL() {
        return fl;
    }
    
    public WheelModule getFR() {
        return fr;
    }
    
    public WheelModule getBL() {
        return bl;
    }
    
    public WheelModule getBR() {
        return br;
    }
}
