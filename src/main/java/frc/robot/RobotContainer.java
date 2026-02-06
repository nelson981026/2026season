package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.MotorCmd;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.intake;

public class RobotContainer {
    private final GamepadJoystick joystick = new GamepadJoystick(Constants.OperatorConstants.kDriverControllerPort);

    private final shooter shooterSubsystem = new shooter();
    private final intake intakeSubsystem = new intake();

    private final MotorCmd motorCmd = new MotorCmd(shooterSubsystem, intakeSubsystem, joystick);

    public RobotContainer() {
        this.shooterSubsystem.setDefaultCommand(this.motorCmd);
        this.intakeSubsystem.setDefaultCommand(this.motorCmd);
        this.configureBindings();
    }

    private void configureBindings() {
    }

    public Command getAutonomousCommand() {
        return null;
    }
}