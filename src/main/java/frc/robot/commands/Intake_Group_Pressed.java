/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Intake_Group_Pressed extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Intake_Group_Pressed(double lSpeed, double rSpeed) {
        requires(Robot.intake);
        
        addSequential(new Intake_SetPosition(RobotMap.INTAKE_DOWNPOSITION));
        addSequential(new Intake_ClawSetMotor(lSpeed, rSpeed));
        addSequential(new Intake_ClawClose());
  }
}
