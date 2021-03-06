package org.usfirst.frc4909.STEAMWORKS.utils.PID;

import org.usfirst.frc4909.STEAMWORKS.utils.Command;

import edu.wpi.first.wpilibj.Timer;

public abstract class PIDCommand extends Command {
	double targetTime;

	protected PIDController pidController = new PIDController(new PIDConstants(0, 0, 0, 1.0));
	
    protected void initialize() {    
    	targetTime = Timer.getFPGATimestamp();
    	
    	pidController.resetPID();
    }

    protected void execute() {
    	pidController.atTarget = false;
    	
    	this.usePID();
    	
		if(!pidController.isDone())
			targetTime = Timer.getFPGATimestamp(); 
	}

    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - targetTime > .5;
    }
    
    protected abstract void usePID();
}
