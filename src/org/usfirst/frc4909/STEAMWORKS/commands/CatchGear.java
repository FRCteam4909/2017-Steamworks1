package org.usfirst.frc4909.STEAMWORKS.commands;

import org.usfirst.frc4909.STEAMWORKS.Robot;
import org.usfirst.frc4909.STEAMWORKS.PID.PIDController;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CatchGear extends Command {
	double targetTime;

	PIDController loaderPID = Robot.loader.loaderPID;
	
    public CatchGear() {
    	requires(Robot.intake);
    }

    protected void initialize() {
    	targetTime=Timer.getFPGATimestamp();
    	loaderPID.resetPID();
    }

    protected void execute() {
    	loaderPID.atTarget = false;
		
		Robot.loader.moveLoader(Robot.config.catchGearAngle, 2);
		
		if(!loaderPID.isDone())
			targetTime=Timer.getFPGATimestamp();
    }

    protected boolean isFinished() {
        return Timer.getFPGATimestamp()-targetTime>.5;
    }
}
