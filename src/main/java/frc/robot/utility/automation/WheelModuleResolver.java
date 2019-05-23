/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utility.automation;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Talon;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class WheelModuleResolver {
    
    private TalonSRX speedMotor, angleMotor; 
    
    private double angle;
    private double currentTick, lastTick;
    private double[] pos;

    public WheelModuleResolver(TalonSRX speedMotor, TalonSRX angleMotor) {
        this.speedMotor = speedMotor;
        this.angleMotor = angleMotor;
        pos = new double[] {0, 0};
        lastTick = 0;
    }

    public void update() {
        currentTick = speedMotor.getSelectedSensorPosition();
        double deltaT = currentTick - lastTick;
        angle = angleMotor.getSelectedSensorPosition() / RobotMap.ticksPerRotation;
        pos[0] += deltaT * Math.cos(angle);
        pos[1] += deltaT * Math.sin(angle);
    }

    public double[] getPos() { return pos; }
}
