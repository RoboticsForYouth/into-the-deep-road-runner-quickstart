package org.firstinspires.ftc.teamcode.az.itd.tools;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.az.sample.AZUtil;

@TeleOp (name="EnhancedClaw", group = "sample")
public class EnhancedClaw extends LinearOpMode {
    public static final String UNKNOWN = "Unknown";
    public static final String YELLOW = "Yellow";
    public static final String BLUE = "Blue";
    public static final String RED = "Red";
    private ElapsedTime runtime = new ElapsedTime();
    private CRServo roller;
    private Servo wrist;
    private ColorSensor sampleSensor;
    private Servo elbow;
    private LinearOpMode opMode;
    public static final double INCREMENT = 0.1;



    public enum RollerPower {
        PICKUP(-1),
        EJECT(1.0),
        STOP(0);

        public double getPower() {
            return power;
        }

        private double power;

        RollerPower(double power) {
            this.power = power;
        }
    }

    public enum WRIST_POS {
        RESET(0.0),
        PICKUP(0.5),
        AUTO_PICKUP(0.55), //0
        DROP_OFF(0.55),
        PICKUP_90(0),
        PICKUP_SPECIMEN(0.27),
        DROP_OFF_SPECIMEN(0.8), //0.84 //0.27
        AUTO_PICKUP_ANGLED(0.84),
        AUTO_PICKUP_SAMPLE_TWO(0.27),
        AUTO_PICKUP_SAMPLE_THREE(0.27),
        AUTO_PICKUP_SAMPLE_FOUR(0.84)

        ;


        public double getPos() {
            return pos;
        }

        private double pos;

        WRIST_POS(double pos) {
            this.pos = pos;
        }
    }

    public enum ELBOW_POS {
        PICKUP(0.09), //0
        DROP(0.5), //0.2
        AUTO_PICKUP(0.17), //0.2 //0.12
        SPECIMEN_PICKUP(0.35), //0.25
        MOVE(0.12), //0.35
        SPECIMEN_DROP(.70), //0.3, 0.6 before 1/12/25

        RESET(0.75),
        SPECIMEN_RELEASE(0.12),
        AUTO_DROP(0.7); //0



        public double getPos() {
            return pos;
        }

        private double pos;

        ELBOW_POS(double pos) {
            this.pos = pos;
        }
    }

    public EnhancedClaw() {
        super();
        opMode = this;
    }

    public EnhancedClaw(LinearOpMode opMode) {
        this.opMode = opMode;
        setup();
    }

    String detectedColor = "unknown"; // Fixed 'string' to 'String'

    private TimedRunnable currentRunnable = null; // To track scheduled tasks

    @Override
    public void runOpMode() {
        this.opMode = opMode;
        // Initialize hardware
        setup();


        waitForStart();

        while (opModeIsActive()) {

            // Pickup block on pressing A button
            if (gamepad1.a) {
                samplePickup();
            }

            // Eject block on pressing B button
            if (gamepad1.b) {
                sampleDrop();
                drop();
            }

            // Stop the roller on pressing X button
            if (gamepad1.x) {
                reset();
            }

            if(gamepad1.y){
                autoPickup(WRIST_POS.AUTO_PICKUP);
            }

            if( gamepad1.right_bumper){
                samplePickUp90();
            }

            if( gamepad1.left_bumper){
                specimenPickUp();
            }

            if( gamepad1.dpad_up){
                specimenDrop();
            }

            detectColorAction();

            // Display the color sensor readings and detected color
            telemetry.addData("Detected Color", ColorVal.getValues());
            telemetry.addLine(String.valueOf(this));
            telemetry.update();


        }
    }

//    @Override
//    public void runOpMode() {
//        this.opMode = opMode;
//        // Initialize hardware
//        roller = opMode.hardwareMap.get(CRServo.class, "roller");
//        roller.setDirection(CRServo.Direction.REVERSE);
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//
//            // Pickup block on pressing A button
//            if (gamepad1.a) {
//                roller.setPower(0);
//            }
//
//            // Eject block on pressing B button
//            if (gamepad1.b) {
//                drop();
//            }
//
//            // Stop the roller on pressing X button
//            if (gamepad1.x) {
//                roller.setPower(-1);
//            }
//
//
//
//        }
//    }

    private void setup() {
        roller = opMode.hardwareMap.get(CRServo.class, "roller");
        roller.setDirection(CRServo.Direction.REVERSE);

        wrist = opMode.hardwareMap.get(Servo.class, "wrist");
        sampleSensor = opMode.hardwareMap.get(ColorSensor.class, "sampleSensor");
        elbow = opMode.hardwareMap.get(Servo.class, "elbow");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        reset();
    }

    public void detectColorAction() {
        // Continuously detect color
        detectedColor = detectColor();

        // If the color detected is red, eject and schedule task
        if (detectedColor.equals(RED)) {
            roller.setPower(RollerPower.EJECT.getPower());

            // Schedule a task to reverse the roller after 1 second
            AZUtil.runInParallel(new Runnable() {
                @Override
                public void run() {
                    sleep(1000);
                    roller.setPower(RollerPower.PICKUP.getPower());// Reverse roller after 1 second
                }
            });
        }

        // Rumble if Blue or Yellow detected
        else if(detectedColor.equals(BLUE) || detectedColor.equals(YELLOW)) {
            //gamepad1.rumble(15);
            AZUtil.runInParallel( new Runnable() {
                @Override
                public void run() {
                    sleep(750);


                }
            });
        }
    }



    public void detectColorActionEjectAuto() {
        drop();
        // Continuously detect color
        detectedColor = detectColor();

        // If the color detected is red, eject and schedule task
        while (!detectedColor.equals(UNKNOWN)) {
            Thread.yield();
            detectedColor = detectColor();
            };

        sleep(2000);

        roller.setPower(RollerPower.STOP.getPower());
        setPos(RollerPower.STOP, WRIST_POS.PICKUP, ELBOW_POS.PICKUP);

    }

    @Override
    public String toString() {
        return "EnhancedClaw{" +
                "roller=" + roller.getPower() +
                ", wrist=" + wrist.getPosition() +
                ", elbow=" + elbow.getPosition() +
                '}';
    }

    public void specimenPickUp() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_SPECIMEN, ELBOW_POS.SPECIMEN_PICKUP);
    }

    public void specimenRelease() {
        setPos(RollerPower.STOP, WRIST_POS.DROP_OFF_SPECIMEN, ELBOW_POS.SPECIMEN_RELEASE);

    }

    public void specimenDrop() {
        elbow.setPosition(ELBOW_POS.SPECIMEN_DROP.getPos());
        sleep(1000);
    }
    public void specimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.DROP_OFF_SPECIMEN.getPos());
        sleep(500);
        elbow.setPosition(ELBOW_POS.SPECIMEN_DROP.getPos());
    }


    public void samplePickUp90() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_90, ELBOW_POS.PICKUP);
    }

    public void autoPickup(WRIST_POS autoPickup) {
        setPos(RollerPower.PICKUP, autoPickup, ELBOW_POS.AUTO_PICKUP);


    }

    public void autoPickupAngled() {
        setPos(RollerPower.PICKUP, WRIST_POS.AUTO_PICKUP_ANGLED, ELBOW_POS.AUTO_PICKUP);

    }

    public void rightAutoDrop() {
        setPos(RollerPower.EJECT, WRIST_POS.AUTO_PICKUP, ELBOW_POS.AUTO_PICKUP);


    }

    public void  reset() {
        elbow.setPosition(ELBOW_POS.RESET.getPos());
        roller.setPower(RollerPower.STOP.getPower());
        wrist.setPosition(WRIST_POS.RESET.getPos());
    }

    public void specimenReset(){
        roller.setPower(RollerPower.STOP.getPower());
    }

    public void samplePickup() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP, ELBOW_POS.PICKUP);

    }

    private void setPos(RollerPower pickup, WRIST_POS pickup1, ELBOW_POS pickup2) {
        roller.setPower(pickup.getPower());
        wrist.setPosition(pickup1.getPos());
        elbow.setPosition(pickup2.getPos());
    }

    public void move(){
        setPos(RollerPower.STOP, WRIST_POS.PICKUP, ELBOW_POS.MOVE);
    }
    public void sampleDrop() {
        wrist.setPosition(WRIST_POS.DROP_OFF.getPos());
        elbow.setPosition(ELBOW_POS.DROP.getPos());
        roller.setPower(RollerPower.STOP.getPower());
    }

    public void autoSampleDrop() {
        wrist.setPosition(WRIST_POS.DROP_OFF.getPos());
        elbow.setPosition(ELBOW_POS.AUTO_DROP.getPos());
        roller.setPower(RollerPower.STOP.getPower());
    }
    public void moveUp() {
        double newPos = elbow.getPosition() + INCREMENT;
        elbow.setPosition(newPos);
    }
    public void moveDown() {
        double newPos = elbow.getPosition() - INCREMENT;
        elbow.setPosition(newPos);
    }
    public void drop(){
        roller.setPower(RollerPower.EJECT.getPower());
    }

    public String detectColor() {
        int red = sampleSensor.red();
        int green = sampleSensor.green();
        int blue = sampleSensor.blue();

        String val;
        int minPowerVal = 500;
        // Logic to detect Red, Blue, and Yellow based on RGB values
        if (red > blue  && red > green && red > minPowerVal) {
            val = RED;
        } else if (blue > red && blue > green && blue > minPowerVal) {
            val = BLUE;
        } else if ( green > red && green > blue && green > minPowerVal) { // Yellow detection threshold
            val = YELLOW;
        } else {
            val = UNKNOWN;
        }

        ColorVal.red = red;
        ColorVal.green = green;
        ColorVal.blue = blue;
        ColorVal.val = val;
        return val;
    }

    // Abstract class for timed runnable tasks
    abstract class TimedRunnable implements Runnable {
        public double whenToRun; // Time when to run (in seconds)
    }
}

class ColorVal{
    public static int red, green, blue;
    public static String val;

    public static String getValues(){
        return new StringBuffer("Red: ")
                .append(red)
                .append(", green: ")
                .append(green)
                .append(", blue: ")
                .append(blue)
                .append("; ")
                .append(val).toString();
    }
}

class ColorMode {

    Color collectColor;
    Color excludeColor;

    public void setCollectColor(Color color){
        if (Color.valueOf(Color.RED) == color){
            collectColor = color;
            excludeColor = Color.valueOf(Color.BLUE);
        }
    }

}
