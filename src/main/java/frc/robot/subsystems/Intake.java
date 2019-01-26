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
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
    private TalonSRX positionMotor;
	private Talon leftClawMotor;
	private Talon rightClawMotor;
	private DoubleSolenoid clawPiston;
	
	public Intake() {
		leftClawMotor = new Talon(RobotMap.INTAKELEFTMOTOR);
		leftClawMotor.setInverted(true);
		rightClawMotor = new Talon(RobotMap.INTAKERIGHTMOTOR);
		clawPiston = new DoubleSolenoid(RobotMap.INTAKECLAWSOLENOIDPCM, RobotMap.INTAKECLAWSOLENOIDONE, RobotMap.INTAKECLAWSOLENOIDTWO);
		
		positionMotor = new TalonSRX(RobotMap.INTAKEPOSITIONMOTOR);
		
		int absolutePosition = positionMotor.getSelectedSensorPosition(0);
		positionMotor.setSelectedSensorPosition(absolutePosition, 0, RobotMap.TIMEOUT);
		positionMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.TIMEOUT);
		
		positionMotor.configAllowableClosedloopError(0, 0, RobotMap.TIMEOUT);
		
		positionMotor.configForwardSoftLimitThreshold(4800, RobotMap.TIMEOUT);
		positionMotor.configReverseSoftLimitThreshold(-10, RobotMap.TIMEOUT);
		positionMotor.configForwardSoftLimitEnable(true, RobotMap.TIMEOUT);
		positionMotor.configReverseSoftLimitEnable(true, RobotMap.TIMEOUT);
		
		positionMotor.config_kP(0, 0.8, RobotMap.TIMEOUT);
		positionMotor.config_kI(0, 0.008, RobotMap.TIMEOUT);
		positionMotor.config_kD(0, 8, RobotMap.TIMEOUT);
		positionMotor.config_IntegralZone(0, 60, RobotMap.TIMEOUT);
		positionMotor.config_kF(0, 0, RobotMap.TIMEOUT);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /*=====================
   	 * claw piston methods
   	 *=====================*/
    
    public void toggleClaw() {
    	if (clawPiston.get().equals(Value.kForward)) {
    		clawPiston.set(Value.kReverse);
    	} else {
    		clawPiston.set(Value.kForward);
    	}
    }
    
    public void openClaw() {
    	clawPiston.set(Value.kForward);
    }
    
    public void closeClaw() {
    	clawPiston.set(Value.kReverse);
    }
    
    /*=====================
   	 * motor methods
   	 *=====================*/
    
    public void setClawMotor(double lSpeed, double rSpeed) {
    	leftClawMotor.set(lSpeed);
    	rightClawMotor.set(rSpeed);
    }
    
    /*=====================
   	 * PID motor methods
   	 *=====================*/
    
    public void setPosition(double pos) {
    	positionMotor.set(ControlMode.Position, pos);
    }
    
    public boolean positionFinished() {
    	return Math.abs(getError()) < 100;
    }
    
    public void zero() {
    	positionMotor.setSelectedSensorPosition(0, 0, RobotMap.TIMEOUT);
    }
    
    public double getError() {
    	return positionMotor.getClosedLoopError(0);
    }
    
    public double getPosition() {
    	return positionMotor.getSelectedSensorPosition(0);
    }

}
