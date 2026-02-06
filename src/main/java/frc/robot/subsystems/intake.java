package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DeviceId;

public class intake extends SubsystemBase {
    private final TalonFX lifter;
    private final TalonFX intake;
    private final TalonFX hopper;
    private final TalonFX center;

    public intake() {
        this.lifter = new TalonFX(DeviceId.intake.LIFTER);
        this.intake = new TalonFX(DeviceId.intake.INTAKE);
        this.hopper = new TalonFX(DeviceId.intake.HOPPER);
        this.center = new TalonFX(DeviceId.intake.CENTER);

        TalonFXConfiguration rollerConfig = new TalonFXConfiguration();
        rollerConfig.MotorOutput
                .withNeutralMode(NeutralModeValue.Coast)
                .withInverted(InvertedValue.Clockwise_Positive);
        TalonFXConfiguration lifterConfig = new TalonFXConfiguration();
        lifterConfig.MotorOutput
                .withInverted(InvertedValue.Clockwise_Positive)
                .withNeutralMode(NeutralModeValue.Coast);
        lifterConfig.Feedback
                .withSensorToMechanismRatio(60.15);
        lifterConfig.MotionMagic
                .withMotionMagicJerk(2000.0)
                .withMotionMagicAcceleration(10.0)
                .withMotionMagicCruiseVelocity(1.0);
        lifterConfig.withSlot0(
                new Slot0Configs()
                            .withKP(10.0)
                            .withKS(0.0)
                            .withKV(0.0)
                            .withKG(0.0)
                            .withKA(0.0));
        this.lifter.getConfigurator().apply(lifterConfig);
        this.intake.getConfigurator().apply(rollerConfig);
        this.hopper.getConfigurator().apply(rollerConfig);
        this.center.getConfigurator().apply(rollerConfig);
    }

    public void move(Double rollerVoltage, double lifterLength) {
        this.lifter.setControl(new MotionMagicVoltage(lifterLength));
        this.intake.setVoltage(rollerVoltage);
        this.hopper.setVoltage(rollerVoltage);
        this.center.setVoltage(rollerVoltage);
        SmartDashboard.putNumber("intakeVoltage", rollerVoltage);
        SmartDashboard.putNumber("intakeLength", lifterLength);
    }

    public void stop() {
        this.lifter.stopMotor();
        this.intake.stopMotor();
        this.hopper.stopMotor();
        this.center.stopMotor();
    }
}