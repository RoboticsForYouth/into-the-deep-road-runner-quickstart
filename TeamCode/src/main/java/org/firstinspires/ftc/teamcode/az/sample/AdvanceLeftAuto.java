package org.firstinspires.ftc.teamcode.az.sample;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.az.itd.tools.EnhancedClaw;

@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
public class AdvanceLeftAuto extends BasicLeftAuto {


    public static final double HIGH_BASKET_X_POS = 13;
    public static final double HIGH_BASKET_Y_POS = 20;
    public static final int HIGH_BASKET_HEADING = -45;
    private Action samplePos2;
    private Action sampleTwoCollectAction;
    private Action timeOverResetAction;
    private Action moveToDropPos1;
    private TrajectoryActionBuilder specimenDropPosTraj;
    private Action highDropEjectAction;
    private Action waitUntilHighBasketAction;
    private Action highDropArmSetupAction;
    private Action samplePos3_1;
    private Action parallelSpecimenHang;
    private Action movePosAction;
    private Action moveToParkAction;
    private Action moveToDropPos2;
    private Action samplePos3;
    private Action moveToDropPos3;
    private Action samplePos4;
    private Action moveToDrop4;
    private Action angledCollectAction;
    private Action resetAction;
    private Action detectColorAction;
    private Action samplePos2_1;
    private Action sampleThreeCollectAction;
    private Action lowerSlidesAction;
    private Action sampleFourCollectAction;
    private Action moveToDropPos4;
    private Action justEjectAction;


    private void updateInit() {
        initAuto();
        addActions();
    }

    private void addActions() {

        TrajectoryActionBuilder moveToDropTraj1 = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS+1, HIGH_BASKET_Y_POS+2, Math.toRadians(HIGH_BASKET_HEADING)), 0);
        moveToDropPos1 = moveToDropTraj1.build();


        TrajectoryActionBuilder samplePos2Traj = moveToDropTraj1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS+0.5, HIGH_BASKET_Y_POS, Math.toRadians(-15)), 0);
//                .turnTo(Math.toRadians(-15), turnConstraints);
        samplePos2 = samplePos2Traj.build();


//        TrajectoryActionBuilder samplePos2_1Traj = samplePos2Traj.endTrajectory().fresh()
//                .turnTo(Math.toRadians(-25), turnConstraints);
//        samplePos2_1 = samplePos2_1Traj.build();


        TrajectoryActionBuilder moveToDropTraj2 = samplePos2Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS-2.5, HIGH_BASKET_Y_POS-3, Math.toRadians(HIGH_BASKET_HEADING+6)), 0);
        moveToDropPos2 = moveToDropTraj2.build();


        TrajectoryActionBuilder samplePos3Traj = moveToDropTraj2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS-3, HIGH_BASKET_Y_POS-2.5, Math.toRadians(-3)), 0);
        samplePos3 = samplePos3Traj.build();

//        TrajectoryActionBuilder samplePos3_1Traj = samplePos3Traj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_X_POS, Math.toRadians(0)), 0);
//        samplePos3_1 = samplePos3_1Traj.build();


        TrajectoryActionBuilder moveToDropTraj3 = samplePos3Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS-4.5, HIGH_BASKET_Y_POS-8, Math.toRadians(HIGH_BASKET_HEADING+12)),0);
        moveToDropPos3 = moveToDropTraj3.build();


        TrajectoryActionBuilder samplePos4Traj = moveToDropTraj3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS-7, Math.toRadians(12)), 0);
        samplePos4 = samplePos4Traj.build();

        TrajectoryActionBuilder moveToDropTraj4 = samplePos4Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS-10, 0, Math.toRadians(90)),0);
        moveToDropPos4 = moveToDropTraj4.build();

//
//
//        TrajectoryActionBuilder moveToDropTraj4 = samplePos4Traj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)),0);
//        moveToDrop4 = moveToDropTraj4.build();





//        TrajectoryActionBuilder moveToPark = moveToDropTraj3.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS+4, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)), 0);
//        moveToParkAction = moveToPark
//                .build();




        highDropArmSetupAction = new Action(){

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.autoDropHighBasket();
                return false;
            }
        };

        justEjectAction = new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.gripper.drop();
                return false;
            }
        };

        highDropEjectAction = new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                telemetry.addLine("ejectAction entered");
//                telemetry.update();

//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        specimenTool.gripper.drop();
//                        sleep(3000);
//                        specimenTool.gripper.samplePickUp90();
//                    }
//                });

                specimenTool.gripper.drop();
                specimenTool.gripper.drop();
                sleep(1500);
                AZUtil.runInParallel(new Runnable() {
                    @Override
                    public void run() {
                        sleep(1000);
                        specimenTool.gripper.samplePickUp90();
                    }
                });

//                specimenTool.gripper.detectColorActionEjectAuto();


//                telemetry.addLine("ejectAction done");
//                telemetry.update();
                return false;
            }
        };

        waitUntilHighBasketAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                telemetry.addLine("waitUntilHighBasketAction entered");
                telemetry.update();

                while (!specimenTool.isSlidesMovingUpInPos(Slides.SlidesPos.AUTO_BASKET_DROP, 900)) {
                    Thread.yield();
                }
                specimenTool.gripper.autoSampleDrop();

                while (!specimenTool.isSlidesMovingUpInPos(Slides.SlidesPos.AUTO_BASKET_DROP, 50)) {
                    // || specimenTool.isArmMovingUpInPos(Arm.ArmPos.AUTO_BASKET_DROP, 50)
                    Thread.yield();
                }
//                sleep(500);
//
//                telemetry.addLine("waitUntilHighBasketAction done");
//                telemetry.update();

                return false;
            }
        };

        timeOverResetAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (runtime.seconds() >= 27) {
                    specimenTool.autoMove();
                    specimenTool.resetAndWait();
                }
                return false;
            }
        };

        resetAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.resetAndWait();

                return false;
            }
        };

        sampleTwoCollectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.autoCollectAndWait(EnhancedClaw.WRIST_POS.AUTO_PICKUP_SAMPLE_TWO, Slides.SlidesPos.AUTO_SAMPLE_TWO_COLLECT);
                telemetryPacket.addLine(specimenTool.toString());
                return false;
            }
        };

        sampleThreeCollectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.autoCollectAndWait(EnhancedClaw.WRIST_POS.AUTO_PICKUP_SAMPLE_THREE, Slides.SlidesPos.AUTO_SAMPLE_THREE_COLLECT);


                return false;
            }
        };

        sampleFourCollectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.autoCollectAndWait(EnhancedClaw.WRIST_POS.AUTO_PICKUP_SAMPLE_FOUR, Slides.SlidesPos.AUTO_SAMPLE_FOUR_COLLECT);


                return false;
            }
        };

        detectColorAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.detectColorActionAuto();

                return false;
            }
        };

        angledCollectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                sleep(500);
                specimenTool.autoCollectAngled();
                specimenTool.slides.moveToPosition(Slides.SlidesPos.AUTO_SAMPLE_TWO_COLLECT);
                specimenTool.detectColorActionAuto();
                return false;
            }
        };

        movePosAction = new Action(){

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.move();
                return false;
            }
        };

        lowerSlidesAction = new Action(){

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.slides.collect();
//                specimenTool.arm.slowInitPos();

                while (!specimenTool.isSlidesMovingDownInPos(Slides.SlidesPos.AUTO_SAMPLE_TWO_COLLECT, 300)) {
                    //|| !specimenTool.isArmMovingDownInPos(Arm.ArmPos.INIT, 500)) {
                    Thread.yield();
                }

                return false;
            }
        };

    }

    @Override
    public void runOpMode() throws InterruptedException {
        updateInit();
        Actions.runBlocking(
                new SequentialAction(
                        //sample 1
                        highDropArmSetupAction,
                        moveToDropPos1, //start here!!!

                        waitUntilHighBasketAction,

                        highDropEjectAction,
                        new SleepAction(0.8),

                        //sample 2
                        lowerSlidesAction,
                        samplePos2,
                        sampleTwoCollectAction,
                        detectColorAction,
                        moveToDropPos2,
                        highDropArmSetupAction,
                        waitUntilHighBasketAction,
                        highDropEjectAction,

                        //sample 3
                        lowerSlidesAction,
                        samplePos3, //
                        sampleThreeCollectAction,
                        detectColorAction,
                        moveToDropPos3, //
                        movePosAction,
                        highDropArmSetupAction,
                        waitUntilHighBasketAction,
                        highDropEjectAction,

                        //sample 4
                        samplePos4,
                        sampleFourCollectAction,
                        moveToDropPos4,
                        justEjectAction,


//                        angledCollectAction, //
//                        timeOverResetAction,
//                        highDropArmSetupAction, //
//                        moveToDrop4,
//                        waitUntilHighBasket, //
//                        highDropEjectAction, //
//                        timeOverResetAction //
//                        moveToParkAction,
                        resetAction
//
                )
        );
//
        telemetry.addData("current position", drive.pose);
        telemetry.update();


        sleep(10000);
    }
}
