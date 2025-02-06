package org.firstinspires.ftc.teamcode.az.itd.tools;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name="EnhancedClaw", group = "sample")
public class EnhancedClaw extends LinearOpMode {
    public static final String UNKNOWN = "Unknown";
    public static final String YELLOW = "Yellow";
    public static final String BLUE = "Blue";
    public static final String RED = "Red";
    public static final double INCREMENT = .01;
    private ElapsedTime runtime = new ElapsedTime();
    private CRServo roller;
    private Servo wrist;
//    private ColorSensor sampleSensor;
    private Servo elbow;
    private LinearOpMode linearOpMode;


    public EnhancedClaw() {
        super();
    }

    public void stopPower() {
        roller.setPower(RollerPower.STOP.getPower());
    }

    public void specimenExtradrop() {
        elbow.setPosition(ELBOW_POS.SPECIMEN_DROP_EXTRA.pos);
    }


    /**
     * Testing related methods
     * =================================================
     */
    /**
     * turnWristLeft
     */
    public void turnWristRight() {
        wrist.setPosition(wrist.getPosition() + INCREMENT);
    }

    public void raiseElbow() {
        elbow.setPosition(elbow.getPosition() + INCREMENT);
    }

    public void lowerElbow() {
        elbow.setPosition(elbow.getPosition() - INCREMENT);
    }

    public void turnWristLeft() {
        wrist.setPosition(wrist.getPosition() - INCREMENT);
    }

    public void rollerCollect() {
        roller.setPower(RollerPower.PICKUP.getPower());
    }

    public void rollerDrop() {
        roller.setPower(RollerPower.EJECT.getPower());
    }

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
        RESET(0.2), //0.2 0.77 0.2

        PICKUP(0.5), //0.5
        AUTO_PICKUP(0.55), //0
        RIGHT_AUTO_PICKUP(0.77),
        DROP_OFF(1), //0.55
        PICKUP_90(0.75),
        DROP_OFF_SPECIMEN(0.2), //0.2 0.77
        AUTO_PICKUP_ANGLED(0.84),
        AUTO_PICKUP_SAMPLE_TWO(0.27),
        AUTO_PICKUP_SAMPLE_THREE(0.27),
        AUTO_PICKUP_SAMPLE_FOUR(0.84),
        PICKUP_SPECIMEN(0.75), //0.75
        TELEOP_DROP_OFF_SPECIMEN(0.2), //0.2 0.77




        //--------------------------------------------------------------------------------------------------------------------
        //LEFT AUTO!!!
        LEFT_AUTO_PICKUP_FIRST(0.7),
        LEFT_AUTO_PICKUP_SECOND(0.8), //0.2
        LEFT_AUTO_PICKUP_THIRD(0.85),
        LEFT_AUTO_RESET(0.5),
        LEFT_AUTO_DROP_OFF(0), //0.5
        //--------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------------
        //RIGHT AUTO!!!
        RIGHT_AUTO_PICKUP_SPECIMEN(0.75), //0.75
        RIGHT_AUTO_DROP_OFF_SPECIMEN(0.2), //0.2 0.77
        //--------------------------------------------------------------------------------------------------------------------

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
        PICKUP(0.09), //0.09
        DROP(0.5), //0.2
        AUTO_PICKUP(0.09), //0.2 //0.17
        RIGHT_AUTO_PICKUP(0.2),
        RIGHT_AUTO_MOVE(0.4),
        SPECIMEN_PICKUP(0.37), //0.25
        MOVE(0.12), //0.35
        SPECIMEN_DROP(.46), //0.7
        SPECIMEN_DROP_TEST(0.42), //0.7
        AUTO_PROTECT(0.3),
        SPECIMEN_DROP_EXTRA(1),
        TELEOP_SPECIMEN_DROP(.43),

        RESET(0.75),
        SPECIMEN_RELEASE(0.12),
        AUTO_DROP(0.5), //0



        //--------------------------------------------------------------------------------------------------------------------
        //LEFT AUTO!!!
        LEFT_AUTO_RESET(0.6),
        LEFT_AUTO_DROP(0.5), //0.49
        LEFT_AUTO_PICKUP(0.09), //0.2 //0.17
        LEFT_AUTO_DROP_INTERMEDIATE(0.2),
        //--------------------------------------------------------------------------------------------------------------------

        RIGHT_AUTO_SPECIMEN_DROP_SLIDES_DOWN(0.73), //0.7

        //--------------------------------------------------------------------------------------------------------------------
        //RIGHT AUTO!!!
        RIGHT_AUTO_SPECIMEN_PICKUP(0.37), //0.25
        RIGHT_AUTO_SPECIMEN_DROP(0.45), //0.7
        FIRST_RIGHT_AUTO_SPECIMEN_DROP(0.47) //0.7
            //--------------------------------------------------------------------------------------------------------------------

        //wrist 0.2, elbow 0.65
        ;

        public double getPos() {
            return pos;
        }

        private double pos;

        ELBOW_POS(double pos) {
            this.pos = pos;
        }
    }

//    public EnhancedClaw() {
//        super();
//        opMode = this;
//    }

    String detectedColor = "unknown"; // Fixed 'string' to 'String'

    private TimedRunnable currentRunnable = null; // To track scheduled tasks

    public EnhancedClaw(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;
        setup();
    }

    @Override
    public String toString() {
        return "EnhancedClaw{" +
                "roller=" + roller.getPower() +
                ", wrist=" + wrist.getPosition() +
                ", elbow=" + elbow.getPosition() +
                '}';
    }

    private void setup() {
        roller = linearOpMode.hardwareMap.get(CRServo.class, "roller");
        roller.setDirection(CRServo.Direction.FORWARD);

        wrist = linearOpMode.hardwareMap.get(Servo.class, "wrist");
//        sampleSensor = linearOpMode.hardwareMap.get(ColorSensor.class, "sampleSensor");
        elbow = linearOpMode.hardwareMap.get(Servo.class, "elbow");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        reset();
    }

    private void setPos(RollerPower pickup, WRIST_POS pickup1, ELBOW_POS pickup2) {
        roller.setPower(pickup.getPower());
        wrist.setPosition(pickup1.getPos());
        elbow.setPosition(pickup2.getPos());
    }

    public void moveUp() {
        double newPos = elbow.getPosition() + INCREMENT;
        elbow.setPosition(newPos);
    }

    public void moveDown() {
        double newPos = elbow.getPosition() - INCREMENT;
        elbow.setPosition(newPos);
    }

//    public void detectColorAction() {
//        // Continuously detect color
//        detectedColor = detectColor();
//
//        // If the color detected is red, eject and schedule task
//        if (detectedColor.equals(RED)) {
//            roller.setPower(RollerPower.EJECT.getPower());
//
//            // Schedule a task to reverse the roller after 1 second
//            AZUtil.runInParallel(new Runnable() {
//                @Override
//                public void run() {
//                    sleep(1000);
//                    roller.setPower(RollerPower.PICKUP.getPower());// Reverse roller after 1 second
//                }
//            });
//        }
//
//        // Rumble if Blue or Yellow detected
//        else if(detectedColor.equals(BLUE) || detectedColor.equals(YELLOW)) {
//            //gamepad1.rumble(15);
//            AZUtil.runInParallel( new Runnable() {
//                @Override
//                public void run() {
//                    sleep(750);
//                }
//            });
//        }
//    }

//    public void detectColorActionEjectAuto() {
//        drop();
//        // Continuously detect color
//        detectedColor = detectColor();
//
//        // If the color detected is red, eject and schedule task
//        while (!detectedColor.equals(UNKNOWN)) {
//            Thread.yield();
//            detectedColor = detectColor();
//            };
//
//        sleep(2000);
//
//        roller.setPower(RollerPower.STOP.getPower());
//        setPos(RollerPower.STOP, WRIST_POS.PICKUP, ELBOW_POS.PICKUP);
//    }











    public void specimenRelease() {
        setPos(RollerPower.STOP, WRIST_POS.DROP_OFF_SPECIMEN, ELBOW_POS.SPECIMEN_RELEASE);
    }

    public void specimenDrop() {
        roller.setPower(RollerPower.EJECT.getPower());
    }

    public void specimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.DROP_OFF_SPECIMEN.getPos());
        sleep(500);
        elbow.setPosition(ELBOW_POS.SPECIMEN_DROP.getPos());
    }

    public void specimenDropPosTest() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.DROP_OFF_SPECIMEN.getPos());
        sleep(500);
        elbow.setPosition(ELBOW_POS.RESET.getPos());
    }

    public void teleOpspecimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.DROP_OFF_SPECIMEN.getPos());
        sleep(500);
        elbow.setPosition(ELBOW_POS.TELEOP_SPECIMEN_DROP.getPos());
    }



    public void samplePickUp90() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_90, ELBOW_POS.PICKUP);
    }

    public void autoPickup(WRIST_POS autoPickup) {
        setPos(RollerPower.PICKUP, autoPickup, ELBOW_POS.AUTO_PICKUP);
    }

    public void rightAutoPickup() {
        setPos(RollerPower.PICKUP, WRIST_POS.RIGHT_AUTO_PICKUP, ELBOW_POS.RIGHT_AUTO_PICKUP);
    }

    public void autoMoveAfterSpecimenCollect() {
        setPos(RollerPower.PICKUP, WRIST_POS.RIGHT_AUTO_PICKUP, ELBOW_POS.RIGHT_AUTO_MOVE);
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

    public void  specimenAutoReset() {
        elbow.setPosition(ELBOW_POS.RESET.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.RESET.getPos());
    }


    public void samplePickup() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP, ELBOW_POS.PICKUP);
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

    public void drop(){
        roller.setPower(RollerPower.EJECT.getPower());
    }

    public void specimenHang(){
        setPos(RollerPower.STOP, WRIST_POS.DROP_OFF_SPECIMEN, ELBOW_POS.SPECIMEN_DROP);
    }

    public void autoProtect() {
        setPos(RollerPower.PICKUP, WRIST_POS.DROP_OFF, ELBOW_POS.AUTO_PROTECT);
    }

    public void specimenPickUp() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_SPECIMEN, ELBOW_POS.SPECIMEN_PICKUP);
    }

    public void teleOpSpecimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.TELEOP_DROP_OFF_SPECIMEN.getPos());
//        sleep(500);
        elbow.setPosition(ELBOW_POS.TELEOP_SPECIMEN_DROP.getPos());
    }





    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!
    public void  leftAutoReset() {
        elbow.setPosition(ELBOW_POS.LEFT_AUTO_RESET.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.LEFT_AUTO_RESET.getPos());
    }

    public void leftAutoSampleDrop() {
        elbow.setPosition(ELBOW_POS.LEFT_AUTO_DROP.getPos());
        wrist.setPosition(WRIST_POS.LEFT_AUTO_DROP_OFF.getPos());
        sleep(700);
        roller.setPower(RollerPower.EJECT.getPower());
        sleep(700);
    }

    public void leftAutoPickup(WRIST_POS wristPos) {
        setPos(RollerPower.PICKUP, wristPos, ELBOW_POS.LEFT_AUTO_PICKUP);
    }

    public void leftAutoIntermediate() {
        setPos(RollerPower.PICKUP, WRIST_POS.LEFT_AUTO_DROP_OFF, ELBOW_POS.LEFT_AUTO_DROP_INTERMEDIATE);
    }

    //--------------------------------------------------------------------------------------------------------------------


    public void rightAutoSpecimenDropPosSlidesDown() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.RIGHT_AUTO_DROP_OFF_SPECIMEN.getPos());
//        sleep(500);
        elbow.setPosition(ELBOW_POS.RIGHT_AUTO_SPECIMEN_DROP_SLIDES_DOWN.getPos());
    }
    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!
    public void rightAutoSpecimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.RIGHT_AUTO_DROP_OFF_SPECIMEN.getPos());
//        sleep(500);
        elbow.setPosition(ELBOW_POS.RIGHT_AUTO_SPECIMEN_DROP.getPos());
    }

    public void firstRightAutoSpecimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        wrist.setPosition(WRIST_POS.RIGHT_AUTO_DROP_OFF_SPECIMEN.getPos());
//        sleep(500);
        elbow.setPosition(ELBOW_POS.FIRST_RIGHT_AUTO_SPECIMEN_DROP.getPos());
    }

    public void rightAutoSpecimenPickUp() {
        setPos(RollerPower.PICKUP, WRIST_POS.RIGHT_AUTO_PICKUP_SPECIMEN, ELBOW_POS.RIGHT_AUTO_SPECIMEN_PICKUP);
    }
    //--------------------------------------------------------------------------------------------------------------------






//    public String detectColor() {
////        int red = sampleSensor.red();
////        int green = sampleSensor.green();
////        int blue = sampleSensor.blue();
//
//        String val;
//        int minPowerVal = 500;
//        // Logic to detect Red, Blue, and Yellow based on RGB values
//        if (red > blue  && red > green && red > minPowerVal) {
//            val = RED;
//        } else if (blue > red && blue > green && blue > minPowerVal) {
//            val = BLUE;
//        } else if ( green > red && green > blue && green > minPowerVal) { // Yellow detection threshold
//            val = YELLOW;
//        } else {
//            val = UNKNOWN;
//        }
//
//        ColorVal.red = red;
//        ColorVal.green = green;
//        ColorVal.blue = blue;
//        ColorVal.val = val;
//        return val;
//    }

    // Abstract class for timed runnable tasks
    abstract class TimedRunnable implements Runnable {
        public double whenToRun; // Time when to run (in seconds)
    }

    @Override
    public void runOpMode() {
        this.linearOpMode = this;
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

            if(gamepad1.dpad_down) {
                roller.setPower(RollerPower.PICKUP.getPower());
            }

//            detectColorAction();

            // Display the color sensor readings and detected color
//            telemetry.addData("Detected Color", ColorVal.getValues());
//            telemetry.addLine(String.valueOf(this));
//            telemetry.update();


        }
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
