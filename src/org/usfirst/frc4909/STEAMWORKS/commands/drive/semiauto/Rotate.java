package org.usfirst.frc4909.STEAMWORKS.commands.drive.semiauto;

import org.usfirst.frc4909.STEAMWORKS.Robot;
import org.usfirst.frc4909.STEAMWORKS.utils.PID.PIDCommand;
import org.usfirst.frc4909.STEAMWORKS.utils.PID.PIDConstants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotate extends PIDCommand {
	double target;
	boolean useAlliance = false;
    public Rotate(double targetAngle) {
    	this.target = targetAngle; 
    	
    	this.pidController.changePIDGains(new PIDConstants(0.05, 0, 0.2, 0.4));
    }
    //Angles are based on red side, negate if blue
    public Rotate(double targetAngle, boolean usingAlliance) {
    		
    	this.target = targetAngle; 
    	
    	this.pidController.changePIDGains(new PIDConstants(0.05, 0, 0.2, 0.4));
    	useAlliance = usingAlliance;

    }
    
    protected void initialize(){
    	super.initialize();
    	if(useAlliance && Robot.drivetrain.isBlue())
    		this.target = -this.target;
    		
    }
    protected void usePID() {
    	double currentAngle = Robot.drivetrain.navx.getAngle();
    	
    	if(Math.abs(target - currentAngle) > 180)
    		target = target + (currentAngle / Math.abs(currentAngle))*360;
    	
    	SmartDashboard.putNumber("Current Angle", currentAngle);

    	Robot.drivetrain.robotDrive.arcadeDrive(
			0, // Power
			this.pidController.calcPID(target, currentAngle, 1) // Rot.
		);
    }
}
