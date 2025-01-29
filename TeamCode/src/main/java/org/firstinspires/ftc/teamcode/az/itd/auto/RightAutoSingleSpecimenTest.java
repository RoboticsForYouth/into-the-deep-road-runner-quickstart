package org.firstinspires.ftc.teamcode.az.itd.auto;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.az.itd.tools.CandyCane;
import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
import org.firstinspires.ftc.teamcode.az.sample.AZUtil;
import org.firstinspires.ftc.teamcode.az.sample.MecanumDrive;


@Config
@Autonomous
public class RightAutoSingleSpecimenTest extends LinearOpMode {

    TurnConstraints turnConstraints = new TurnConstraints(
            Math.PI,
            -Math.PI,
            Math.PI);

    public static final double SPECIMEN_DROP_POS_XXXX = 25.2;
    public static final int SPECIMEN_DROP_POS_YYYY = 15;
    public static final int SPECIMEN_DROP_POS_HEADING = 180;
    public static final int SPIKE_MARK_POS_XXXX = 20;
    public static final int OBS_ZONE_POS_XXXX = 11;
    public static final int OBS_ZONE_POS_YYYY = -33;
    public static final double OBS_ZONE_POS_1_XXXX = 5.5;
    public static final int OBS_ZONE_DROP_HEADING = -140;
    ElapsedTime runtime = new ElapsedTime();



    SpecimenTool specimenTool = null;
    CandyCane candyCane = null;
    private MecanumDrive drive;
    private Pose2d beginPose;
    private Action specimenDropPos1;
    private Action observationZonePos1;
    private Action observationZonePos1_1;
    private Action specimenDropPos2;
    private Action parkPos;
    private Action specimenToolDrop;
    private Action armDrop;
    private Action slidesReset;
    private Action readyToDropSpecimen;
    private Action park;
    private Action armGripperReset;
    private Action releaseSpecimenAction;
    private Action specimenCollectAction;
    private Action spikeMarkPos1;
    private Action lowerCandyCane;
    private Action raiseCandyCane;
    private Action spikeMarkPos3;

    private Action observationZoneDropPos1;
    private Action move;
    private Action drop;
    private Action spikeMarkPos2;
    private Action observationZoneDropPos2;
    private Action observationZoneDropPos3;
    private Action resetCandyCane;
    private Action observationZonePos2;
    private Action observationZonePos2_1;
    private Action specimenDropPos3;
    private Action observationZonePos3;
    private Action observationZonePos3_1;
    private Action specimenDropPos4;
    private Action observationZonePos4;
    private Action observationZonePos4_1;
    private Action specimenDropPos5;
    private Action specimenToolDropAfterPickup;
    private Action lowerCandyCaneAfterDrop;
    private Action afterDropSpecimenCollectAction;



    public class specimenHang implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            specimenTool.specimenCollect();
            sleep(1000);



            return false;
        }
    }

    public Action specimenHang() {
        return new specimenHang();
    }

    public void initAuto() {
        specimenTool = new SpecimenTool(this);
        specimenTool.specimenAutoReset();

        candyCane = new CandyCane((this));

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        runtime.reset();
        beginPose = new Pose2d(0,0,Math.toRadians(SPECIMEN_DROP_POS_HEADING));
        drive = new MecanumDrive(hardwareMap, beginPose);
        telemetry.addData("current position", drive.pose);
        telemetry.update();
        createActions();
    }

    public void createActions() {
        TrajectoryActionBuilder specimenDropTraj1 = drive.actionBuilder(beginPose)
                .strafeToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX, SPECIMEN_DROP_POS_YYYY));
//                .splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
        specimenDropPos1 = specimenDropTraj1.build();





        parkPos = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(3, -36), Math.toRadians(0))
                .build();


        releaseSpecimenAction = new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                AZUtil.runInParallel(new Runnable() {
                    @Override
                    public void run() {
                        sleep(500);

                        specimenTool.gripper.specimenDropPos();

                        specimenTool.specimenDrop();
                    }
                });
                return false;
            }
        };

        specimenCollectAction = new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.autoSpecimenCollect();
                return false;
            }
        };

        afterDropSpecimenCollectAction = new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                AZUtil.runInParallel(new Runnable() {
                    @Override
                    public void run() {
                        sleep(2000);
                        specimenTool.autoSpecimenCollect();
                    }
                });
                return false;
            }
        };



        specimenToolDrop = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                AZUtil.runInParallel(new Runnable() {
                    @Override
                    public void run() {
                        specimenTool.rightAutoSpecimenHangPos();
                    }
                });
                return false;
            }
        };

        specimenToolDropAfterPickup = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.arm.setPosAndWait((int) DoubleArm.DoubleArmPos.SPECIMEN_DROP_INTEMEDIATE_WAIT.getValue());

                AZUtil.runInParallel(new Runnable() {
                    @Override
                    public void run() {
                        sleep(2000);
                        specimenTool.rightAutoSpecimenHangPos();
                    }
                });

                return false;
            }
        };


        lowerCandyCane = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                candyCane.autoLower();
                return false;
            }
        };



        raiseCandyCane = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                candyCane.autoRaise();
                return false;
            }
        };

        resetCandyCane = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                candyCane.reset();
                return false;
            }
        };

        move = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.gripper.autoMoveAfterSpecimenCollect();
                return false;
            }
        };

        drop = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.gripper.drop();
                return false;
            }
        };



//        readyToDropSpecimen = new ParallelAction(specimenToolDrop, specimenDropPos);





        armDrop = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.specimenDrop();
                return false;
            }
        };


        slidesReset = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.slides.reset();
                return false;
            }
        };


        armGripperReset = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.reset();
                specimenTool.gripper.reset();
                return false;
            }
        };


        park = new ParallelAction(armGripperReset, parkPos);
    }


    public void runOpMode() throws InterruptedException {
        initAuto();
        waitForStart();
        Actions.runBlocking(
                new SequentialAction(
                        releaseSpecimenAction,
                        specimenDropPos1
//                        new SleepAction(1),







//                        armDrop,
//                        new SleepAction(1),
//                        slidesReset,
//                        new SleepAction(1),
//                        park
                )
        );
//
        sleep(5000);
        telemetry.addData("current position",drive.pose);
        telemetry.update();
        sleep(10000);



    }

}
