package frc.robot;

public final class Constants {
    public static final int driverControllerPort = 0;

    public static final class POV {
        public static final int up = 0;
        public static final int right = 90;
        public static final int down = 180;
        public static final int left = 270;
    }

    public static final class shooter {
        public static final double VOLTAGE = 6.0;
        public static final double MIN_ANGLE = 0.5;
        public static final double MID_ANGLE = 14.0;
        public static final double MAX_ANGLE = 29.0;
        public static final double DELTA = (MAX_ANGLE - MIN_ANGLE) / 16.0;
    }
    public static final class intake {
        public static final double VOLTAGE = 2.5;
        public static final double INTAKE_VOLTAGE = 2.5;
        public static final double MIN_LENGTH = 0.0;
        public static final double MAX_LENGTH = 0.3;
        public static final Double DELTA = (MAX_LENGTH-MIN_LENGTH) / 16.0;
    }

    public static final double TRIGGER_VALUE = 0.5;
}