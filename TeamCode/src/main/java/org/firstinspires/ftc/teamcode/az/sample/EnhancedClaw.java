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
    private ElapsedTime runtime = new ElapsedTime();
    private CRServo roller;
    private Servo wrist;
    private ColorSensor sampleSensor;
    private Servo elbow;

    public enum RollerPower {
        PICKUP(-1),
        EJECT(1),
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
        RESET(1.0),
        PICKUP(1.0),
        DROP_OFF(1.0),
        PICKUP_90(0.5),
        PICKUP_SPECIMEN(0.5);

        public double getPos() {
            return pos;
        }

        private double pos;

        WRIST_POS(double pos) {
            this.pos = pos;
        }
    }

    public enum ELBOW_POS {
        PICKUP(0.0),
        AUTO_PICKUP(0.2),
        SPECIMEN_PICKUP(0.6);

        public double getPos() {
            return pos;
        }

        private double pos;

        ELBOW_POS(double pos) {
            this.pos = pos;
        }
    }

    private String detectedColor = "unknown"; // Fixed 'string' to 'String'

    private TimedRunnable currentRunnable = null; // To track scheduled tasks

    @Override
    public void runOpMode() {
        // Initialize hardware
        roller = hardwareMap.get(CRServo.class, "roller");
        wrist = hardwareMap.get(Servo.class, "wrist");
        sampleSensor = hardwareMap.get(ColorSensor.class, "sampleSensor");
        elbow = hardwareMap.get(Servo.class, "elbow");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        setPos(RollerPower.STOP, WRIST_POS.RESET, ELBOW_POS.PICKUP);


        waitForStart();

        while (opModeIsActive()) {

            // Pickup block on pressing A button
            if (gamepad1.a) {
                samplePickup();
            }

            // Eject block on pressing B button
            if (gamepad1.b) {
                sampleDrop();
            }

            // Stop the roller on pressing X button
            if (gamepad1.x) {
                reset();
            }

            if(gamepad1.y){
                autoPickup();
            }

            if( gamepad1.right_bumper){
                samplePickUp90();
            }

            if( gamepad1.left_bumper){
                specimenPickUp();
            }

            detectColorAction();

            // Display the color sensor readings and detected color
            telemetry.addData("Detected Color", ColorVal.getValues());
            telemetry.update();

        }
    }

    private void detectColorAction() {
        // Continuously detect color
        detectedColor = detectColor();

        // If the color detected is red, eject and schedule task
        if (detectedColor.equals("Red")) {
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
        else if(detectedColor.equals("Blue") || detectedColor.equals("Yellow")) {
            //gamepad1.rumble(15);
            AZUtil.runInParallel( new Runnable() {
                @Override
                public void run() {
                    sleep(1000);
                    reset();// Reverse roller after 1 second

                }
            });
        }
    }

    private void specimenPickUp() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_90, ELBOW_POS.SPECIMEN_PICKUP);
    }

    private void samplePickUp90() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_90, ELBOW_POS.PICKUP);
    }

    private void autoPickup() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP, ELBOW_POS.AUTO_PICKUP);
    }

    private void reset() {
        roller.setPower(RollerPower.STOP.getPower());
    }

    private void samplePickup() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP, ELBOW_POS.PICKUP);
    }

    private void setPos(RollerPower pickup, WRIST_POS pickup1, ELBOW_POS pickup2) {
        roller.setPower(pickup.getPower());
        wrist.setPosition(pickup1.getPos());
        elbow.setPosition(pickup2.getPos());
    }

    private void sampleDrop() {
        wrist.setPosition(WRIST_POS.DROP_OFF.getPos());
        roller.setPower(RollerPower.EJECT.getPower());
    }

    private String detectColor() {
        int red = sampleSensor.red();
        int green = sampleSensor.green();
        int blue = sampleSensor.blue();

        String val;
        int minPowerVal = 500;
        // Logic to detect Red, Blue, and Yellow based on RGB values
        if (red > blue  && red > green && red > minPowerVal) {
            val = "Red";
        } else if (blue > red && blue > green && blue > minPowerVal) {
            val = "Blue";
        } else if ( green > red && green > blue && green > minPowerVal) { // Yellow detection threshold
            val =  "Yellow";
        } else {
            val = "Unknown";
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
