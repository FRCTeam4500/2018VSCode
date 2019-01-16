package frc.robot.utility;

import frc.robot.subsystems.Swerve;

import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.modifiers.SwerveModifier;
import frc.robot.subsystems.WheelModule;
public class PaffinderByRoro{
    Trajectory.Config config;
    double wheelbase_width;
    double wheelbase_depth;
    SwerveModifier.Mode mode = SwerveModifier.Mode.SWERVE_DEFAULT;
    Swerve swerve;
    public PaffinderByRoro(Swerve swerve, double wheelbase_width, double wheelbase_depth){
        this.swerve = swerve;
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        this.wheelbase_width = wheelbase_width;
        this.wheelbase_depth = wheelbase_depth;
    }
    public SwerveModifier CreateSwerveTrajectory(Waypoint[] waypoints){
        Trajectory trajectory = Pathfinder.generate(waypoints,config);
        SwerveModifier modifier = new SwerveModifier(trajectory);
        modifier.modify(wheelbase_width, wheelbase_depth, mode);
        return modifier;
    }
    public void HEXECUTE(SwerveModifier modifier){
        WheelModule br = swerve.getBR();
        Trajectory brTrajectory = modifier.getBackRightTrajectory();
        for(int i = 0;i<brTrajectory.segments.length;i++){
            Trajectory.Segment segment = brTrajectory.segments[i];
            br.drive(segment.velocity,segment.heading);
        }
    }
}