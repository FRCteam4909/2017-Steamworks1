package org.usfirst.frc4909.STEAMWORKS.utils.PID;

import org.usfirst.frc4909.STEAMWORKS.utils.PID.Position.PotentiometerPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Simple PID Controller that assumes regular loop intervals .
 *
 * Modified from Base by Team 1241
 */
public class PIDController {

	/** The p gain. */
	double pGain;
	double iGain;
	double dGain;

	double pOut;
	double iOut;
	double dOut;
	double maxOut;

	double error;
	double errorSum = 0;
	double lastError = 0;

	double dProcessVar;

	double output = 0;

	double previousValue = 0;

	double previousAverage = 0;

	double currentAverage;

	/** The average. */
	double average;

	/** The at target. */
	public boolean atTarget = false;

	/**
	 * Instantiates a new PID controller.
	 *
	 * @param p
	 *            the p
	 * @param i
	 *            the i
	 * @param d
	 *            the d
	 */
	public PIDController(double p, double i, double d, double max) {
		errorSum = 0; // initialize errorSum to 0
		lastError = 0; // initialize lastError to 0
		pGain = p;
		iGain = i;
		dGain = d;
		maxOut = max;
	}
	
	public PIDController(PIDConstants constants){
		this(constants.p, constants.i, constants.d, constants.max);
	}

	public PIDController(PotentiometerPIDController potentiometerPIDController) {
		this(potentiometerPIDController.getPID().p, potentiometerPIDController.getPID().i, potentiometerPIDController.getPID().d, potentiometerPIDController.getPID().max);
	}

	/**
	 * Reset integral.
	 */
	public void resetIntegral() {
		errorSum = 0.0;
	}

	/**
	 * Reset derivative.
	 */
	public void resetDerivative() {
		lastError = 0.0;
	}

	/**
	 * Reset PID.
	 */
	public void resetPID() {
		resetIntegral();
		resetDerivative();
		atTarget = false;
	}

	/**
	 * Change PID gains.
	 *
	 * @param kP
	 *            the k P
	 * @param kI
	 *            the k I
	 * @param kD
	 *            the k D
	 */
	public void changePIDGains(double kP, double kI, double kD) {
		pGain = kP;
		iGain = kI;
		dGain = kD;
	}
	
	public void changePIDGains(PIDConstants consts) {
		pGain = consts.p;
		iGain = consts.i;
		dGain = consts.d;
		maxOut = consts.max;
	}

	/**
	 * Calc PID.
	 *
	 * @param setPoint
	 *            the set point
	 * @param currentValue
	 *            the current value
	 * @param epsilon
	 *            the epsilon
	 * @return the double
	 */
	public double calcPID(double setPoint, double currentValue, double epsilon) {
		error = setPoint - currentValue;

		if (Math.abs(error) <= epsilon) {
			atTarget = true;
		} else {
			atTarget = false;
		}

		// P
		pOut = pGain * error;

		// I
		errorSum += error;
		iOut = iGain * errorSum;

		// D
		dProcessVar = (error - lastError);
		dOut = dGain * dProcessVar;

		lastError = error;

		// PID Output
		output = pOut + iOut + dOut;

		// Scale output to be between 1 and -1
		if (output != 0.0)
			output = (output / Math.abs(output) * (1 - Math.pow(.1, (Math.abs(output)))))*maxOut;
		return output;
	}

	/**
	 * Calc PID drive.
	 *
	 * @param setPoint
	 *            the set point
	 * @param currentValue
	 *            the current value
	 * @param epsilon
	 *            the epsilon
	 * @return the double
	 */
	public double calcPIDDrive(double setPoint, double currentValue, double epsilon) {
		error = setPoint - currentValue;

		if (Math.abs(error) <= epsilon) {
			atTarget = true;
		} else {
			atTarget = false;
		}

		// P
		pOut = pGain * error;

		// I
		errorSum += error;
		iOut = iGain * errorSum;

		// D
		dProcessVar = (error - lastError);
		dOut = dGain * dProcessVar;

		lastError = error;

		// PID Output
		output = pOut + iOut + dOut;

		// Scale output to be between 1 and -1
		if (output != 0.0)
			output = output / Math.abs(output) * (1.0 - Math.pow(0.6, (Math.abs(output))));

		return output;
	}

	/**
	 * Calc PID velocity.
	 *
	 * @param setPoint
	 *            the set point
	 * @param currentValue
	 *            the current value
	 * @param epsilon
	 *            the epsilon
	 * @param iStart
	 *            the i start
	 * @return the double
	 */
	public double calcPIDVelocity(double setPoint, double currentValue, double epsilon, double iStart) {
		error = setPoint - currentValue;

		if (Math.abs(error) <= epsilon) {
			atTarget = true;
		} else {
			atTarget = false;
		}

		// P
		pOut = pGain * error;
		if(pOut<0)
			pOut=0;

		// I

		// 0.60
		if (currentValue >= setPoint * iStart) {
			errorSum += error;
			iOut = iGain * errorSum;

			currentAverage = (previousValue + currentValue) / 2;
			average = (currentAverage + previousAverage) / 2;
			SmartDashboard.putNumber("average", average);
		} else {
			iOut = 0;
		}

		// D
		dProcessVar = (error - lastError);
		dOut = dGain * dProcessVar;

		lastError = error;
		previousValue = currentValue;
		previousAverage = currentAverage;

		// PID Output
		output = pOut + iOut + dOut;

		// Scale output to be between 1 and -1
		if (output != 0.0)
			output = output / Math.abs(output) * (1.0 - Math.pow(0.1, (Math.abs(output))));

		return output;
	}

	/**
	 * Checks if is done.
	 *
	 * @return true, if is done
	 */
	public boolean isDone() {
		return atTarget;
	}

	/**
	 * Gets the p gain.
	 *
	 * @return the p gain
	 */
	public double getPGain() {
		return pGain;
	}

	/**
	 * Gets the i gain.
	 *
	 * @return the i gain
	 */
	public double getIGain() {
		return iGain;
	}

	/**
	 * Gets the d gain.
	 *
	 * @return the d gain
	 */
	public double getDGain() {
		return dGain;
	}
}