/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utility.automation;

import edu.wpi.first.wpilibj.PIDController;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class PID_Controllers {

    public static PID_RotAlignSO rotAlignSO;
    public static PIDController rotAlignController, xAlignController;

    public static void initializeControllers() {
        rotAlignController = new PIDController(0, 0, 0, rotAlignSO, rotAlignSO);
        rotAlignController.setInputRange(-360, 360);
        rotAlignController.setOutputRange(-RobotMap.driveTicksFor360Deg, RobotMap.driveTicksFor360Deg);
        rotAlignController.setPercentTolerance(20);
    }
}
