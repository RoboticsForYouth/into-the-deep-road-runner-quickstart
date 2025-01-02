package org.firstinspires.ftc.teamcode.az.sample;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class SpecimenPickupTeleOp extends LinearOpMode {

    // This variable determines whether the following program
    // uses field-centric or robot-centric driving styles. The
    // differences between them can be read here in the docs:
    // https://docs.ftclib.org/ftclib/features/drivebases#control-scheme
    static final boolean FIELD_CENTRIC = false;
    SpecimenTool specimenTool = null;

    Arm arm = null;
    //    Slides slides = null;
    private boolean dpadUpProcessing;
    private boolean gamepad2DpadUpProcessing;
    private boolean gamepad2dpadDownProcessing;
    private boolean gamepad2DpadDownProcessing;
    private boolean dpadRightProcessing;
    private boolean dpadLeftProcessing;

    private boolean buttonAProcessing;
    private boolean gamepad2ButtonAProcessing;

    private boolean buttonBProcessing;
    private boolean buttonXProcessing;
    private boolean buttonYProcessing;
    private boolean rightTriggerProcessing;
    private boolean leftTriggerProcessing;
    private boolean rightBumperProcessing;
    private boolean leftBumperProcessing;
    private boolean dpadDownProcessing;


    public void setup() {
        specimenTool.reset();

    }

    @Override
    public void runOpMode() throws InterruptedException {
        // constructor takes in frontLeft, frontRight, backLeft, backRight motors
        // IN THAT ORDER
        MecanumDrive drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_435)
        );

        arm = new Arm(this);
        specimenTool = new SpecimenTool(this);


        RevIMU imu = new RevIMU(hardwareMap);
        imu.init();

        // the extended gamepad object
        GamepadEx driverOp = new GamepadEx(gamepad1);

        waitForStart();

        specimenTool.specimenToolInit();

        while (!isStopRequested()) {


//            slides = new Motor(hardwareMap, "slides");
            //collec

            //drop the specimen


            //set to move position
            if(gamepad1.x){ //square
                if(!buttonXProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonXProcessing = true;

                            if(arm.getCurrentPosition() < 0) {
                                specimenTool.move();
                            }
                            else {
                                //change order of reset to ensure that slides do not hit the basket
                                specimenTool.reset();
                            }
                            buttonXProcessing = false;
                        }
                    });
                }
            }

            //low basket
            if(gamepad1.dpad_up){
                if( !dpadUpProcessing) {
                    dpadUpProcessing = true;
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            specimenTool.pickUpSpecimen();
                            dpadUpProcessing = false;
                        }
                    });

                }

            }


            if(gamepad1.left_bumper){
                if( !leftBumperProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            leftBumperProcessing = true;
                            specimenTool.grabSpecimen();
                            leftBumperProcessing = false;
                        }
                    });
                }

            }

            if (!FIELD_CENTRIC) {
                drive.driveRobotCentric(
                        -driverOp.getLeftX(),
                        -driverOp.getLeftY(),
                        -driverOp.getRightX(),
                        false
                );
            } else {
                drive.driveFieldCentric(
                        driverOp.getLeftX(),
                        driverOp.getLeftY(),
                        driverOp.getRightX(),
                        imu.getRotation2d().getDegrees(),   // gyro value passed in here must be in degrees
                        false
                );
            }
            specimenTool.printPos(telemetry);
        }
    }

}