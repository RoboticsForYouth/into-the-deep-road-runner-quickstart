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


    public static final double HIGH_BASKET_X_POS = 10;
    public static final double HIGH_BASKET_Y_POS = 21;
    public static final int HIGH_BASKET_HEADING = -45;
    private Action samplePos2;
    private Action sampleTwoCollectAction;
    private Action timeOverResetAction;
    private Action moveToCollect2;
    private Action moveToDrop1;
    private TrajectoryActionBuilder specimenDropPosTraj;
    private Action highDropEjectAction;
    private Action waitUntilHighBasket;
    private Action dropAction;
    private Action moveBackToResetAction;
    private Action highDropArmSetupAction;
    private Action samplePos3_1;
    private Action secondSamplePosAction;
    private Action parallelSpecimenHang;
    private Action movePosAction;
    private Action moveToParkAction;
    private Action moveToDrop2;
    private Action moveToDrop2_1;
    private Action moveBackToResetAction2;
    private Action samplePos3;
    private Action moveToCollect3;
    private Action moveToDrop3;
    private Action moveToDrop3_1;
    private Action moveBackToResetAction3;
    private Action samplePos4;
    private Action moveToCollect4;
    private Action moveToDrop4;
    private Action moveToDrop4_1;
    private Action moveBackToResetAction4;
    private Action angledCollectAction;
    private Action resetAction;
    private Action detectColorAction;
    private Action samplePos2_1;
    private Action sampleThreeCollectAction;
    private Action lowerSlidesAction;



    private void updateInit() {
        initAuto();
        addActions();
    }

    private void addActions() {

        parallelSpecimenHang = new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                AZUtil.runInParallel(new Runnable() {
                    @Override
                    public void run() {
                        //specimenTool.specimenHang();
                        sleep(1000);
                    }
                });
                return false;
            }
        };

//        specimenDropPosTraj = drive.actionBuilder(drive.pose)
//                .splineToConstantHeading(new Vector2d(21, 0), Math.toRadians(0));
//        specimenDropPos = specimenDropPosTraj.build();






        TrajectoryActionBuilder moveToDropTraj1 = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)), 0);
        moveToDrop1 = moveToDropTraj1.build();

        highDropArmSetupAction = new Action(){

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.autoDropHighBasket();
                return false;
            }
        };


        highDropEjectAction = new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.gripper.detectColorActionEjectAuto();
                return false;
            }
        };

        waitUntilHighBasket = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while (!specimenTool.isSlidesMovingUpInPos(Slides.SlidesPos.AUTO_BASKET_DROP, 900)) {
                    Thread.yield();
                }
                specimenTool.gripper.autoSampleDrop();

                while (!specimenTool.isSlidesMovingUpInPos(Slides.SlidesPos.AUTO_BASKET_DROP, 50)) {
                    // || specimenTool.isArmMovingUpInPos(Arm.ArmPos.AUTO_BASKET_DROP, 50)
                    Thread.yield();
                }
                return false;
            }
        };

//        TrajectoryActionBuilder resetActionTraj = moveToDropTraj1.endTrajectory().fresh()
//                .lineToX(5);
//        moveBackToResetAction = resetActionTraj
//                .build();

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

        TrajectoryActionBuilder samplePos2Traj = moveToDropTraj1.endTrajectory().fresh()
                .turnTo(Math.toRadians(-15))
//                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(-12)), 0)
                ;
        samplePos2 = samplePos2Traj.build();

        TrajectoryActionBuilder samplePos2_1Traj = samplePos2Traj.endTrajectory().fresh()
                .turnTo(Math.toRadians(-25))
//                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(-20)), 0)
                ;
        samplePos2_1 = samplePos2_1Traj.build();


        sampleTwoCollectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.autoCollect(EnhancedClaw.WRIST_POS.AUTO_PICKUP_SAMPLE_ONE);
                specimenTool.slides.moveToPosition(Slides.SlidesPos.AUTO_SAMPLE_COLLECT);

                while (!specimenTool.isArmMovingDownInPos(Arm.ArmPos.AUTO_COLLECT, 50)) {
                    Thread.yield();
                }


                return false;
            }
        };

        sampleThreeCollectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.autoCollect(EnhancedClaw.WRIST_POS.AUTO_PICKUP_SAMPLE_TWO);
                specimenTool.slides.moveToPosition(Slides.SlidesPos.AUTO_SAMPLE_COLLECT);

                while (!specimenTool.isArmMovingDownInPos(Arm.ArmPos.AUTO_COLLECT, 50)) {
                    Thread.yield();
                }


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
                specimenTool.slides.moveToPosition(Slides.SlidesPos.AUTO_SAMPLE_COLLECT);
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

                while (!specimenTool.isSlidesMovingDownInPos(Slides.SlidesPos.AUTO_SAMPLE_COLLECT, 300)) {
                    Thread.yield();
                }

                return false;
            }
        };




        TrajectoryActionBuilder moveToDropTraj2 = samplePos2_1Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)), 0);
        moveToDrop2 = moveToDropTraj2.build();


        TrajectoryActionBuilder samplePos3Traj = moveToDropTraj2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_X_POS, Math.toRadians(10)), 0);
        samplePos3 = samplePos3Traj.build();

        TrajectoryActionBuilder samplePos3_1Traj = samplePos3Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_X_POS, Math.toRadians(0)), 0);
        samplePos3_1 = samplePos3_1Traj.build();


        TrajectoryActionBuilder moveToDropTraj3 = samplePos3_1Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)),0);
        moveToDrop3 = moveToDropTraj3.build();

        TrajectoryActionBuilder samplePos4Traj = moveToDropTraj3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(23, 24, Math.toRadians(10)), 0);
        samplePos4 = samplePos4Traj.build();


        TrajectoryActionBuilder moveToDropTraj4 = samplePos4Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)),0);
        moveToDrop4 = moveToDropTraj4.build();






        TrajectoryActionBuilder moveToPark = moveToDropTraj3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS+4, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)), 0);
        moveToParkAction = moveToPark
                .build();






//        TrajectoryActionBuilder secondSamplePosTraj = resetActionTraj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(22, 39, Math.toRadians(50)), 0);
//
//        secondSamplePosAction = secondSamplePosTraj.build();

    }

    @Override
    public void runOpMode() throws InterruptedException {
        updateInit();
        Actions.runBlocking(
                new SequentialAction(
                        moveToDrop1, //start here!!!

                        highDropArmSetupAction,
                        waitUntilHighBasket,
                        highDropEjectAction,
                        new SleepAction(0.5),

                        lowerSlidesAction,
                        samplePos2,
                        sampleTwoCollectAction,
                        samplePos2_1,
                        detectColorAction,
                        moveToDrop2,
                        movePosAction,
                        highDropArmSetupAction,
                        waitUntilHighBasket,
                        highDropEjectAction,

                        lowerSlidesAction,
                        samplePos3, //
                        sampleThreeCollectAction,
                        samplePos3_1, //
                        detectColorAction,
                        moveToDrop3, //
                        movePosAction,
                        highDropArmSetupAction,
                        waitUntilHighBasket,
                        highDropEjectAction,

//                        angledCollectAction, //
//                        timeOverResetAction,
//                        highDropArmSetupAction, //
//                        moveToDrop3,
//                        waitUntilHighBasket,
//                        highDropEjectAction,
//
//                        samplePos4,
//                        angledCollectAction, //
//                        timeOverResetAction,
//                        highDropArmSetupAction, //
//                        moveToDrop4,
//                        waitUntilHighBasket, //
//                        highDropEjectAction, //
//                        timeOverResetAction //
                        moveToParkAction,
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
