package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.DeviceId;

public class shooter extends SubsystemBase {
    private final TalonFX lifter;
    private final TalonFX flyWheelMain;
    private final TalonFX flyWheelFollower;
    private final Follower followerCtrl = new Follower(DeviceId.shooter.FLYWHEEL_MAIN, MotorAlignmentValue.Opposed);
    private final TalonFX feeder;

    public shooter() {
        this.lifter = new TalonFX(DeviceId.shooter.LIFTER);
        this.flyWheelMain = new TalonFX(DeviceId.shooter.FLYWHEEL_MAIN);
        this.flyWheelFollower = new TalonFX(DeviceId.shooter.FLYWHEEL_FOLLOWER);
        this.feeder = new TalonFX(DeviceId.shooter.FEEDER);

        TalonFXConfiguration rollerConfig = new TalonFXConfiguration();
        rollerConfig.MotorOutput
                .withNeutralMode(NeutralModeValue.Coast)
                .withInverted(InvertedValue.Clockwise_Positive);

        TalonFXConfiguration lifterConfig = new TalonFXConfiguration();
        lifterConfig.MotorOutput
                .withInverted(InvertedValue.CounterClockwise_Positive)
                .withNeutralMode(NeutralModeValue.Coast);
        lifterConfig.Feedback
                .withSensorToMechanismRatio(294.0);
        lifterConfig.MotionMagic
                .withMotionMagicJerk(1000.0)
                .withMotionMagicAcceleration(200.0)
                .withMotionMagicCruiseVelocity(1.0);
        lifterConfig.withSlot0(
                new Slot0Configs()
                        .withKS(0.3)
                        .withKV(30.0)
                        .withKA(0.0)
                        .withKG(0.0)
                        .withKP(150.0)
                        .withKD(0.0));
        this.feeder.getConfigurator().apply(rollerConfig);
        this.flyWheelMain.getConfigurator().apply(rollerConfig);
        this.flyWheelFollower.setControl(this.followerCtrl);
        this.lifter.getConfigurator().apply(lifterConfig);
        this.lifter.setPosition(0.0);
    }

    public void move(double rollerVoltage, double lifterAngle) {
        this.flyWheelMain.setVoltage(rollerVoltage);
        if(this.flyWheelMain.get()>(Constants.SHOOTER_VOLTAGE/12.0)*0.8){
            this.feeder.setVoltage(rollerVoltage);
        }
        this.lifter.setControl(new MotionMagicVoltage(Units.degreesToRotations(lifterAngle)));
        SmartDashboard.putNumber("shooterVoltage", rollerVoltage);
        SmartDashboard.putNumber("shooterAngle", lifterAngle);
    }

    public void stop() {
        this.lifter.stopMotor();
        this.flyWheelMain.stopMotor();
        this.flyWheelFollower.stopMotor();
        this.feeder.stopMotor();
    }
}