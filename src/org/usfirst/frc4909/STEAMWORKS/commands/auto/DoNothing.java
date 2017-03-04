package org.usfirst.frc4909.STEAMWORKS.commands.auto;

import org.usfirst.frc4909.STEAMWORKS.commands.drive.semiauto.InvertToState;
import org.usfirst.frc4909.STEAMWORKS.commands.drive.semiauto.ShiftToState;
import org.usfirst.frc4909.STEAMWORKS.commands.loader.HoldGear;
import org.usfirst.frc4909.STEAMWORKS.utils.devices.drivetrain.ShiftingRobotDrive.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DoNothing extends CommandGroup {
    public DoNothing (){
    	addParallel(new HoldGear());
    	addSequential(new InvertToState(true));

    	addSequential(new ShiftToState(Gear.High));
    }
}
