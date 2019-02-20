package frc.robot.utility.automation;

public class PIDController {
    
    private double kP, kI, kD;
    private double allowableError, lastError = 0;
    private PIDInputInterface<Double> pidInput;
    private PIDOutputInterface<Double> pidOutput;

    public PIDController(double kP, double kI, double kD, double allowableError, PIDInputInterface<Double> pidInput, PIDOutputInterface<Double> pidOutput) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.allowableError = allowableError;
        this.pidInput = pidInput;
        this.pidOutput = pidOutput;
    }

    public double getPIDInput() {
        return pidInput.get();
    }

    public double getPIDOutput(double target) {
        double error = target - getPIDInput();
        // double output = kP * error +
        //                 kD * ((error - lastError) / 0.05);
        double output = pidOutput.get(error, lastError, kP, kI, kD);
        lastError = error;
        return output;
    }

    public boolean targetReached(double target) {
        return Math.abs(target - getPIDInput()) < allowableError ? true : false;
    }
}