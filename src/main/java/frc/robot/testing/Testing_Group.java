/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.testing;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class Testing_Group extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Testing_Group() {
    addSequential(new Testing_SetWheelAngle(Robot.prefs.getDouble("Angle X", 0.0), 0, 0));
    addSequential(new Testing_SetWheelAngle(0, 0, Robot.prefs.getDouble("Angle X", 0.0)));
    addSequential(new Testing_SetWheelAngle(-Robot.prefs.getDouble("Angle X", 0.0), 0, 0));
  }
}
