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
		double shooterVoltage = (this.controller.getAButton()) ? Constants.shooter.VOLTAGE : this.controller.getLeftY();
		double intakeVoltage = (this.controller.getBButton()) ? Constants.intake.VOLTAGE : this.controller.getRightY();
		// double shooterVoltage = this.controller.getLeftY();
		// double intakeVoltage = this.controller.getRightY();

		switch (controller.getPOV()) {
			case Constants.POV.up -> shooterAngle = Constants.shooter.MAX_ANGLE;
			case Constants.POV.right -> shooterAngle = Constants.shooter.MID_ANGLE;
			case Constants.POV.left -> shooterAngle = Constants.shooter.MID_ANGLE;
			case Constants.POV.down -> shooterAngle = Constants.shooter.MIN_ANGLE;
		}
		if (this.controller.getLeftBumperButtonPressed()) {
			shooterAngle -= Constants.shooter.DELTA;
		} else if (getLeftTriggerButtonPressed()) {
			shooterAngle += Constants.shooter.DELTA;
		}
		leftTriggerAxis = this.controller.getLeftTriggerAxis();

		if(this.controller.getXButton()){
			intakeLength = Constants.intake.MAX_LENGTH;
		} else if(this.controller.getYButton()){
			intakeLength = Constants.intake.MIN_LENGTH;
		}
		if (this.controller.getRightBumperButtonPressed()) {
			intakeLength -= Constants.intake.DELTA;
		} else if (getRightTriggerButtonPressed()) {
			intakeLength += Constants.intake.DELTA;
		}
		rightTriggerAxis = this.controller.getRightTriggerAxis();

		this.shooterSubsystem.move(shooterVoltage, shooterAngle);
		this.intakeSubsystem.move(intakeVoltage, intakeLength);
		/*
		 * intake:
		 *  B botton or Right Y
		 *  X++/Y-- botton or Right Trigger+/Bumper-
		 * shooter:
		 *  A botton or Left Y
		 *  POV or Left Trigger+/Bumper-
		*/
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