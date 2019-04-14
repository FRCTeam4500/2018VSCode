/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.automation;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;
import frc.robot.commands.Swerve_ToggleDefault;

public class Automation_Group_RotAlign extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Automation_Group_RotAlign() {
    requires(Robot.swerve);
    
    addSequential(new Swerve_ToggleDefault());
    addSequential(new Automation_SetPIDFVA());
    addSequential(new Automation_SetWheelAngle(0, 0, 1));
    addSequential(new TimedCommand(0.1));
    addSequential(new Automation_rotAlign());
    addSequential(new Swerve_ToggleDefault());

  }
}
