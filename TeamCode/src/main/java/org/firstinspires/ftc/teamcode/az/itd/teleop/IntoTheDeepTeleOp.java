package org.firstinspires.ftc.teamcode.az.itd.teleop;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.az.itd.tools.CandyCane;
import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
import org.firstinspires.ftc.teamcode.az.sample.AZUtil;

@TeleOp
public class IntoTheDeepTeleOp extends LinearOpMode {

    static final boolean FIELD_CENTRIC = false;
    SpecimenTool specimenTool = null;

    DoubleArm arm = null;
    CandyCane candyCane = null;
//    Slides slides = null;
    private boolean gamepad2DpadUpProcessing;
    private boolean gamepad2dpadDownProcessing;
    private boolean dpadUpProcessing;
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

    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;



//    public void setup() {
//        specimenTool.resetPos();
//    }

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

        arm = new DoubleArm(this);
        specimenTool = new SpecimenTool(this);
        candyCane = new CandyCane(this);
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        GamepadEx driverOp = new GamepadEx(gamepad1);

        // the extended gamepad object

        //specimenTool.arm.initPos(); //set arm to init position

        waitForStart();

        specimenTool.teleOpSpecimenToolInit();

        while (!isStopRequested()) {


//            slides = new Motor(hardwareMap, "slides");
            //collect
            if (gamepad1.a) { //x
               if(!buttonAProcessing ){
                   AZUtil.runInParallel(new Runnable() {
                       @Override
                       public void run() {
                           buttonAProcessing = true;
                           if(arm.getCurrentPosition() < 500) {

                               specimenTool.teleOpCollect();
                           }

                           else {
                               specimenTool.teleOpHighReset();
                           }


                           buttonAProcessing = false;
                       }
                   });
               }
            }


            //drop the specimen
            if (gamepad1.b) { //circle
                if(!buttonBProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonBProcessing = true;
                            specimenTool.teleOpEject();
                            buttonBProcessing = false;
                        }
                    });

                }
            }

            //set to move position
            if(gamepad1.x){ //square
                if(!buttonXProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonXProcessing = true;

                            if(arm.getCurrentPosition() < 500) {
                                specimenTool.collectVertical();

                            }
                            else {
                                //change order of resetPos to ensure that slides do not hit the basket
                                specimenTool.teleOpHighResetVertical();
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
                            specimenTool.specimenLowBasket();
                            dpadUpProcessing = false;
                        }
                    });

                }

            }

            //Level 2 hang
            if(gamepad1.dpad_down){
                if( !dpadDownProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            dpadDownProcessing = true;
                            specimenTool.level2Hang();
                            dpadDownProcessing = false;
                        }
                    });
                }

            }

            //resetPos to initialize position
            if(gamepad2.dpad_down){
                if( !gamepad2dpadDownProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2dpadDownProcessing = true;
                            specimenTool.reset();
                            gamepad2dpadDownProcessing = false;
                        }
                    });
                }

            }

            //set to high basket
            if( gamepad1.dpad_right){
                if( !dpadRightProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            dpadRightProcessing = true;
                            specimenTool.dropHighBasket();
                            dpadRightProcessing = false;
                        }
                    });
                }

            }

            //extend the slides
            if( gamepad1.right_trigger > 0){
                //if not processing then perform this operation
                if( !rightTriggerProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            rightTriggerProcessing = true;
                            specimenTool.extend(gamepad1.right_trigger);
                            rightTriggerProcessing = false;
                        }
                    });
                }
            }


            if(gamepad1.right_bumper){
                if( !rightBumperProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            rightBumperProcessing = true;
                            specimenTool.teleOpSpecimenHangPos();
                            rightBumperProcessing = false;
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
                            arm.moveDown();
                            leftBumperProcessing = false;
                        }
                    });
                }

            }


            //resetPos the slides
            if (gamepad2.a) { //circle
                if(!gamepad2ButtonAProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2ButtonAProcessing = true;
                            specimenTool.reset();
                            gamepad2ButtonAProcessing = false;
                        }
                    });

                }
            }

            if(gamepad1.y){
                if( !buttonYProcessing){
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonYProcessing = true;
                            specimenTool.specimenCollect();
                            buttonYProcessing = false;
                        }
                    });
                }

            }


                drive.driveRobotCentric(
                        -driverOp.getLeftX(),
                        -driverOp.getLeftY(),
                        -driverOp.getRightX(),
                        false
                );

//            specimenTool.printPos(telemetry);
        }
    }

}