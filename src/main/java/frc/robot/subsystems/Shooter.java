/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {
    private TalonSRX leftMotor;
    private TalonSRX rightMotor;
    private DoubleSolenoid raisePiston;
    private DoubleSolenoid liftPiston;
    
    public Shooter() {
    	raisePiston = new DoubleSolenoid(RobotMap.LEVELSHOOTERPCM, RobotMap.LEVELSHOOTERONE, RobotMap.LEVELSHOOTERTWO);
    	liftPiston = new DoubleSolenoid(RobotMap.LIFTSHOOTERPCM, RobotMap.LIFTSHOOTERONE, RobotMap.LIFTSHOOTERTWO);
    	
    	leftMotor = new TalonSRX(RobotMap.LSHOOTER);
    	leftMotor.setInverted(false);
    	
    	rightMotor = new TalonSRX(RobotMap.RSHOOTER);
    	rightMotor.setSensorPhase(true);
    	rightMotor.setInverted(true);
    	
    	rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.TIMEOUT);
    	rightMotor.config_kP(0, 0.15, RobotMap.TIMEOUT);
    	rightMotor.config_kI(0, 0.0005, RobotMap.TIMEOUT);
    	rightMotor.config_kD(0, 0, RobotMap.TIMEOUT);
    	rightMotor.config_kF(0, 0.035, RobotMap.TIMEOUT);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /*=====================
	 * liftPiston methods
	 *=====================*/
    
    public void raiseLift() {
    	liftPiston.set(Value.kForward);
    }
    
    public void lowerLift() {
    	liftPiston.set(Value.kReverse);
    }
    
    public void toggleLift() {
    	if(liftPiston.get().equals(Value.kForward)) {
    		liftPiston.set(Value.kReverse);
    	} else {
    		liftPiston.set(Value.kForward);
    	}
    }
    
    /*=====================
   	 * positionPiston methods
   	 *=====================*/
    
    public void raiseShooter() {
    	raisePiston.set(Value.kReverse);
    }
    
    public void lowerShooter() {
    	raisePiston.set(Value.kForward);
    }
    
    public void toggleShooterLevel() {
    	if(raisePiston.get().equals(Value.kForward)) {
    		raisePiston.set(Value.kReverse);
    	} else {
    		raisePiston.set(Value.kForward);
    	}
    }
    
    /*=====================
   	 * motor methods
   	 *=====================*/
    
    public void shoot(double speed) {
    	speed /= RobotMap.RPMTOTICKSPER100MS;
    	rightMotor.set(ControlMode.Velocity, speed);
    	leftMotor.set(ControlMode.Follower, RobotMap.RSHOOTER);
    }
    
    /*=====================
   	 * helper methods
   	 *=====================*/
    
    public int getVelocity() {
    	return rightMotor.getSelectedSensorVelocity(0);
    }
    
    public int getVelocityError() {
    	return rightMotor.getClosedLoopError(0);
    }
}
