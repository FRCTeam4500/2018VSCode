package frc.robot.utility.automation;

@FunctionalInterface
public interface PIDOutputInterface<T> {
    public abstract T get(T error, T lastError, T kP, T kI, T kD);
}