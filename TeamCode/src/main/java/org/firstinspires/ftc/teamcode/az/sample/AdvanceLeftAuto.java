package org.firstinspires.ftc.teamcode.az.sample;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
public class AdvanceLeftAuto extends BasicLeftAuto {


    public static final double HIGH_BASKET_X_POS = 3.5;
    public static final double HIGH_BASKET_Y_POS = 20.5;
    public static final int HIGH_BASKET_HEADING = 135;
    private Action samplePos2;
    private Action collectAction;
    private Action timeOverResetAction;
    private Action moveToCollect2;
    private Action moveToDrop1;
    private TrajectoryActionBuilder specimenDropPosTraj;
    private Action highDropEjectAction;
    private Action waitUntilHighBasket;
    private Action dropAction;
    private Action moveBackToResetAction;
    private Action highDropArmSetupAction;
    private Action moveToDrop1_1;
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
    private Action moveAndWaitAction;



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
                while (!specimenTool.isSlidesMovingUpInPos(Slides.SlidesPos.BASKET_DROP, 50)) {
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
                if (specimenTool.slides.getCurrentPos() > 2000) {
                    specimenTool.autoMove();

                }
                else {
                    specimenTool.resetAndWait();

                }

                specimenTool.resetAndWait();
                return false;
            }
        };

        TrajectoryActionBuilder samplePos2Traj = moveToDropTraj1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(19, 16, Math.toRadians(-5)), 0);
        samplePos2 = samplePos2Traj.build();

        moveAndWaitAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.autoMove();
                return false;
            }
        };

        collectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.autoMove();
                specimenTool.autoCollect();
                specimenTool.gripper.detectColorActionAuto();
                return false;
            }
        };

        angledCollectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.autoMove();
                sleep(500);
                specimenTool.autoCollectAngled();
                specimenTool.gripper.detectColorActionAuto();
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




        TrajectoryActionBuilder moveToDropTraj2 = samplePos2Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)), 0);
        moveToDrop2 = moveToDropTraj2.build();


        TrajectoryActionBuilder samplePos3Traj = moveToDropTraj2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(23, 20, Math.toRadians(10)), 0);
        samplePos3 = samplePos3Traj.build();


        TrajectoryActionBuilder moveToDropTraj3 = samplePos3Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)),0);
        moveToDrop3 = moveToDropTraj3.build();

        TrajectoryActionBuilder samplePos4Traj = moveToDropTraj3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(23, 24, Math.toRadians(10)), 0);
        samplePos4 = samplePos4Traj.build();


        TrajectoryActionBuilder moveToDropTraj4 = samplePos4Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS, Math.toRadians(HIGH_BASKET_HEADING)),0);
        moveToDrop4 = moveToDropTraj4.build();






        TrajectoryActionBuilder moveToPark = samplePos4Traj.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(15, -43, Math.toRadians(0)), 0)
                .lineToX(2);
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
                        highDropArmSetupAction,
                        moveToDrop1, //start here!!!
                        waitUntilHighBasket,
                        highDropEjectAction,

                        moveAndWaitAction,
                        samplePos2,
                        collectAction,
                        highDropArmSetupAction,
                        moveToDrop2,
                        waitUntilHighBasket,
                        highDropEjectAction,

                        moveAndWaitAction,
                        samplePos3,
                        angledCollectAction, //
                        highDropArmSetupAction, //
                        moveToDrop3,
                        waitUntilHighBasket,
                        highDropEjectAction,

                        moveAndWaitAction,
                        samplePos4,
                        angledCollectAction, //
                        highDropArmSetupAction, //
                        moveToDrop4,
                        waitUntilHighBasket, //
                        highDropEjectAction, //
                        timeOverResetAction //
//
//                        moveToParkAction
                )
        );
//
        telemetry.addData("current position", drive.pose);
        telemetry.update();


        sleep(10000);
    }
}
