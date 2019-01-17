package frc.robot.utility;

import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.WheelModule;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.SwerveModifier;

public class Paffinder2 {
    /* THe pathfinder class! Made by roro
        In order to run, you need to make the class, then run every single configuration method lmfao.        

    */
    
    //Robot Properties
    private Swerve swerve; //The robot's swerve module.
    private WheelModule fl;
    private WheelModule fr;
    private WheelModule bl;
    private WheelModule br;
    private double wheelDepth; //The depth between the wheels of the swerve drive.
    private double wheelWidth; //The width between the wheels of the swerve drive.
    private int ticksPerRevolution;
    private double wheelDiameter;
    //Robot Properties but like PIDVA properties
    private double proportionalGain;
    private double integralGain;
    private double derivativeGain;
    private double velocityRatio;
    private double accelerationGain;
    //Trajectory Properties
    private Trajectory.Config config;
    private SwerveModifier currentModifier;
    private EncoderFollower frFollower;
    private EncoderFollower flFollower;
    private EncoderFollower brFollower;
    private EncoderFollower blFollower;
    private SwerveModifier.Mode mode;
    //Set up the thing and pass in everything because DECOUPLING
    public Paffinder2(Swerve swerve, double wheelDepth, double wheelWidth, double wheelDiameter, int ticksPerRevolution){
        this.swerve = swerve;
        this.wheelDepth = wheelDepth;
        this.wheelWidth = wheelWidth;
        this.wheelDiameter = wheelDiameter;
        this.ticksPerRevolution = ticksPerRevolution;

        this.mode = SwerveModifier.Mode.SWERVE_DEFAULT;
        fr = swerve.getFR();
        fl = swerve.getFL();
        br = swerve.getBR();
        bl = swerve.getBL();
    }

    private void fullyConfigureEncoder(EncoderFollower encoderFollower, int initialPosition){
        encoderFollower.configurePIDVA(proportionalGain, integralGain, derivativeGain, velocityRatio, accelerationGain);
        encoderFollower.configureEncoder(initialPosition, ticksPerRevolution, wheelDiameter);
    }

    //Pass in the parameters to configure the trajectory, modifier, and encoder followers        
    public void ConfigureTrajectory(Trajectory trajectory){
        currentModifier = new SwerveModifier(trajectory).modify(wheelWidth,wheelDepth,mode);
        brFollower = new EncoderFollower(currentModifier.getBackRightTrajectory());
        blFollower = new EncoderFollower(currentModifier.getBackLeftTrajectory());
        frFollower = new EncoderFollower(currentModifier.getFrontRightTrajectory());
        flFollower = new EncoderFollower(currentModifier.getFrontLeftTrajectory());
        fullyConfigureEncoder(brFollower, br.getDrivePosition());
        fullyConfigureEncoder(blFollower, bl.getDrivePosition());
        fullyConfigureEncoder(frFollower, fr.getDrivePosition());
        fullyConfigureEncoder(flFollower, fr.getDrivePosition());       
    }
    public void ConfigureTrajectory(Waypoint[] waypoints){
        Trajectory trajectory = Pathfinder.generate(waypoints,config);
        ConfigureTrajectory(trajectory);
    }

    //Run this function every iteration in the main loop.
    public void RunNextDriveIteration(){
        double frOutput = frFollower.calculate(fr.getDrivePosition());
        double flOutput = flFollower.calculate(fl.getDrivePosition());
        double brOutput = brFollower.calculate(br.getDrivePosition());
        double blOutput = blFollower.calculate(bl.getDrivePosition());
        double frHeading = frFollower.getHeading(); //In the wiki, there is some conversion process involved. I don't know what we use
        double flHeading = flFollower.getHeading();
        double brHeading = brFollower.getHeading();
        double blHeading = blFollower.getHeading();
        fr.drive(frOutput,frHeading);
        fl.drive(flOutput,flHeading);
        br.drive(brOutput,brHeading);
        bl.drive(blOutput,blHeading);
    }
    //Put in the PID/VA values.
    public void ConfigurePIDVA(double proportionalGain,double integralGain, double derivativeGain, double velocityRatio, double accelerationGain){
        this.proportionalGain = proportionalGain;
        this.integralGain = integralGain;
        this.derivativeGain = derivativeGain;
        this.velocityRatio = velocityRatio;
        this.accelerationGain = accelerationGain;
    }

    //Configure Trajectory.config
    public void ConfigureTrajectoryConfig(FitMethod fitMethod, int samples, double deltaTime, double maxVelocity, double maxAcceleration, double maxJerk){
        config = new Trajectory.Config(fitMethod,samples,deltaTime,maxVelocity,maxAcceleration,maxJerk);
    }
}