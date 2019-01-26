/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Intake_Group_LoadCube extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Intake_Group_LoadCube() {
        requires(Robot.shooter);
        requires(Robot.intake);
        
        addSequential(new Shooter_ShooterRaise());
        addSequential(new Intake_SetPosition(RobotMap.INTAKE_SCALEPOSITION));
        addSequential(new WaitCommand(0.5));
        addSequential(new Intake_ClawOpen());
        addSequential(new WaitCommand(1));
        addSequential(new Intake_ClawClose());
  }
}
