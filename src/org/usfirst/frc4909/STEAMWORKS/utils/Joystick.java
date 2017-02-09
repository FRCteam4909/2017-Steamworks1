package org.usfirst.frc4909.STEAMWORKS.utils;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class Joystick extends edu.wpi.first.wpilibj.Joystick {
	public Joystick(int port) {
		super(port);
	}

	public double getThresholdAxis(int axis, double threshold){
		if(Math.abs(this.getRawAxis(axis)) > Math.abs(threshold))
			return this.getRawAxis(axis);
		else
			return 0.0;
	}
	
	public void buttonPressed(int button, Command command){
		JoystickButton newButton = new JoystickButton(this, button);
		
		newButton.whenPressed(command);
	}
	
	public void buttonHeld(int button, Command command){
		JoystickButton newButton = new JoystickButton(this, button);
		
		newButton.whenPressed(command);
	}
}