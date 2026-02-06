package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.intake;
import frc.robot.Constants;

public class MotorCmd extends Command {

	private final shooter shooterSubsystem;
	private final intake intakeSubsystem;
	private final XboxController controller;

	public MotorCmd(shooter shooterSubsystem, intake intakeSubsystem, XboxController controller) {
		this.shooterSubsystem = shooterSubsystem;
		this.intakeSubsystem = intakeSubsystem;
		this.controller = controller;
		this.addRequirements(this.shooterSubsystem);
		this.addRequirements(this.intakeSubsystem);
	}

	@Override

	public void initialize() {
	}

	private double shooterAngle = 0.0;
	private double intakeLength = 0.0;
	private double leftTriggerAxis = 0.0;
	private double rightTriggerAxis = 0.0;

	private boolean getLeftTriggerButtonPressed() {
		return this.controller.getLeftTriggerAxis() > Constants.TRIGGER_VALUE
				&& leftTriggerAxis < Constants.TRIGGER_VALUE;
	}

	private boolean getRightTriggerButtonPressed() {
		return this.controller.getRightTriggerAxis() > Constants.TRIGGER_VALUE
				&& rightTriggerAxis < Constants.TRIGGER_VALUE;
	}

	@Override
	public void execute() {
		// FOR TESTING
		double rollerVoltage = (this.controller.getAButton()) ? Constants.SHOOTER_VOLTAGE : this.controller.getLeftY();
		double intakeVoltage = (this.controller.getBButton()) ? Constants.INTAKE_VOLTAGE : this.controller.getRightY();
		switch (controller.getPOV()) {
			case 0 -> shooterAngle = 29.0;
			case 270 -> shooterAngle = 14.0;
			case 90 -> shooterAngle = 14.0;
			case 180 -> shooterAngle = 0.5;
		}
		if(this.controller.getXButton()){
			intakeLength = 0.3;
		} else if(this.controller.getYButton()){
			intakeLength = 0.0;
		}
		if (this.controller.getLeftBumperButtonPressed()) {
			shooterAngle -= 1.5;
		} else if (getLeftTriggerButtonPressed()) {
			shooterAngle += 1.5;
		}
		if (this.controller.getRightBumperButtonPressed()) {
			intakeLength -= 0.06;
		} else if (getRightTriggerButtonPressed()) {
			intakeLength += 0.06;
		}
		leftTriggerAxis = this.controller.getLeftTriggerAxis();
		rightTriggerAxis = this.controller.getRightTriggerAxis();

		this.shooterSubsystem.move(rollerVoltage, shooterAngle);
		this.intakeSubsystem.move(intakeVoltage, intakeLength);
	}

	@Override
	public void end(boolean interrupted) {
		this.shooterSubsystem.stop();
		this.intakeSubsystem.stop();
	}

	@Override

	public boolean isFinished() {
		return false;
	}
}