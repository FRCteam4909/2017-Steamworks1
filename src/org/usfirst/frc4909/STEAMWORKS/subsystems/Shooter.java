package org.usfirst.frc4909.STEAMWORKS.subsystems;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc4909.STEAMWORKS.RobotMap;
import org.usfirst.frc4909.STEAMWORKS.commands.*;
import org.usfirst.frc4909.STEAMWORKS.PID.PIDController;

public class Shooter extends Subsystem {
    private final Encoder shooterEncoder = RobotMap.shooterShooterEncoder;
    private final SpeedController shooterMotorController = RobotMap.shooterShooterMotorController;

    boolean shooterState = false;
	private double shooterP = 0.00015;
	private double shooterI = 0.00000;
	private double shooterD = 0.00000;
	private double shooterF = 0.00050;
    
    public PIDController shooterPID=RobotMap.shooterPID;

	public boolean getShooterState() {
		return shooterState;
	}
	
	public void setShooterState(boolean state) {
		shooterState = state;
	}
	
	public void setSpeed(double shotVal) {
		shooterMotorController.set(shotVal);
	}
	
	public double getRPM(){
		// RETURN RPM
		return shooterEncoder.getRate();
	}
	
	public void setRPM(double rpm){
		shooterP = SmartDashboard.getNumber("P", 0.0000);
    	shooterI = SmartDashboard.getNumber("I", 0.0000);
    	shooterD = SmartDashboard.getNumber("D", 0.0000);
    	shooterF = SmartDashboard.getNumber("F", 0.0000);

		shooterPID.changePIDGains(shooterP, shooterI, shooterD);
		
		double output = shooterPID.calcPIDVelocity(rpm, getRPM(), 50, 0.0000);
		setSpeed(output+rpm*shooterF);
	}
    
    public void initDefaultCommand() {}
}