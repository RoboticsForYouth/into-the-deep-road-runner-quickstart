package org.firstinspires.ftc.teamcode.az.itd.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
import org.firstinspires.ftc.teamcode.az.itd.tools.EnhancedClaw;
import org.firstinspires.ftc.teamcode.az.itd.tools.Slides;

@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
public class AdvanceLeftAuto extends BasicLeftAuto {

    TurnConstraints turnConstraints = new TurnConstraints(
            (Math.PI)/3,
            -(Math.PI)/3,
            (Math.PI)/3);


    public static final double HIGH_BASKET_X_POS = 17.75;
    public static final double HIGH_BASKET_Y_POS = 13;
    public static final int HIGH_BASKET_HEADING = -45;

    private Action moveToDropPos1;
    private Action moveToDropPos2;
    private Action moveToDropPos3;
    private Action moveToDropPos4;
    private Action collectPos1;
    private Action collectPos2;
    private Action collectPos3;
    private Action highDropArmSetupAction;
    private Action resetAction;
    private Action collectAction1;
    private Action collectAction2;
    private Action collectAction3;
    private Action collectPos1_1;
    private Action collectPos2_1;
    private Action collectPos3_1;
    private Action waitForArmAction1;
    private Action highLaterDropsArmSetupAction;
    private Action waitForArmAction2;
    private Action waitForArmAction3;
    private Action parkPos;

   int lastActionSeq = 0;

   public static boolean LeftAutoHighDropArmSetupActionDone = false;

    private void updateInit() {
        initAuto();
        addActions();
    }

    private void addActions() {

        TrajectoryActionBuilder moveToDropTraj1 = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(HIGH_BASKET_X_POS-1.5, HIGH_BASKET_Y_POS-2.75), Math.toRadians(HIGH_BASKET_HEADING));
        moveToDropPos1 = moveToDropTraj1.build();

        TrajectoryActionBuilder collectTraj1 = moveToDropTraj1.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(14.25, 12.5))
//                .strafeToLinearHeading(new Vector2d(14.25, 9.5), Math.toRadians(6));
                .turnTo(Math.toRadians(-20), turnConstraints); //6
        collectPos1 = collectTraj1.build();

        TrajectoryActionBuilder collectTraj1_1 = collectTraj1.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(14, 20.5));
//                .turnTo(Math.toRadians(6), turnConstraints);
        collectPos1_1 = collectTraj1_1.build();

        TrajectoryActionBuilder moveToDropTraj2 = collectTraj1_1.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HIGH_BASKET_HEADING));
                .strafeToLinearHeading(new Vector2d(HIGH_BASKET_X_POS-3.25, HIGH_BASKET_Y_POS+1), Math.toRadians(HIGH_BASKET_HEADING));
        moveToDropPos2 = moveToDropTraj2.build();

        TrajectoryActionBuilder collectTraj2 = moveToDropTraj2.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(9.75, 13.5))
//                .strafeToLinearHeading(new Vector2d(10, 8.5), Math.toRadians(10));
                .turnTo(Math.toRadians(10), turnConstraints); //35
        collectPos2 = collectTraj2.build();

        TrajectoryActionBuilder collectTraj2_1 = collectTraj2.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(10, 18.5));
//                .turnTo(Math.toRadians(35), turnConstraints);
        collectPos2_1 = collectTraj2_1.build();

        TrajectoryActionBuilder moveToDropTraj3 = collectTraj2_1.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HIGH_BASKET_HEADING));
                .strafeToLinearHeading(new Vector2d(HIGH_BASKET_X_POS-4, HIGH_BASKET_Y_POS-1), Math.toRadians(HIGH_BASKET_HEADING));
        moveToDropPos3 = moveToDropTraj3.build();

        TrajectoryActionBuilder collectTraj3 = moveToDropTraj3.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(8.75, 14))
//                .strafeToLinearHeading(new Vector2d(9, 6.5), Math.toRadians(36));
                .turnTo(Math.toRadians(27), turnConstraints); //31
        collectPos3 = collectTraj3.build();

        TrajectoryActionBuilder collectTraj3_1 = collectTraj3.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(9, 18.5));
//                .turnTo(Math.toRadians(45), turnConstraints);
        collectPos3_1 = collectTraj3_1.build();

        TrajectoryActionBuilder moveToDropTraj4 = collectTraj3_1.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HIGH_BASKET_HEADING));
                .strafeToLinearHeading(new Vector2d(HIGH_BASKET_X_POS-5.25, HIGH_BASKET_Y_POS-0.25), Math.toRadians(HIGH_BASKET_HEADING+5));
        moveToDropPos4 = moveToDropTraj4.build();

        TrajectoryActionBuilder parkTraj = moveToDropTraj4.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(52, 0), Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(52, -12));
        parkPos = parkTraj.build();




        highDropArmSetupAction = new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                LeftAutoHighDropArmSetupActionDone = false;
                specimenTool.leftAutoDropHighBasket();
                return false;
            }
        };

        highLaterDropsArmSetupAction = new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                LeftAutoHighDropArmSetupActionDone = false;

                specimenTool.leftAutoLaterDropsHighBasket();
                return false;
            }
        };

        collectAction1 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone){
                    Thread.yield();
                }
                specimenTool.leftAutoCollect(Slides.SlidesPos.LEFT_AUTO_PICKUP_FIRST, EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_FIRST, DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_FIRST);
                return false;
            }
        };

        collectAction2 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone){
                    Thread.yield();
                }
                specimenTool.leftAutoCollect(Slides.SlidesPos.LEFT_AUTO_PICKUP_SECOND, EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_SECOND, DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_SECOND);
                return false;
            }
        };

        collectAction3 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone){
                    Thread.yield();
                }
                specimenTool.leftAutoCollect(Slides.SlidesPos.LEFT_AUTO_PICKUP_THIRD, EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_THIRD, DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_THIRD);
                return false;
            }
        };



        waitForArmAction1 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.setPosAndWaitLowPowerLeftAuto((int) DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_FIRST.getValue());
                specimenTool.slides.setPosAndWaitLeftAuto((int) Slides.SlidesPos.LEFT_AUTO_PICKUP_FIRST.getValue());
                return false;
            }
        };

        waitForArmAction2 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.setPosAndWaitLowPowerLeftAuto((int) DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_SECOND.getValue());
                specimenTool.slides.setPosAndWaitLeftAuto((int) Slides.SlidesPos.LEFT_AUTO_PICKUP_SECOND.getValue());
                return false;
            }
        };

        waitForArmAction3 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.setPosAndWaitLowPowerLeftAuto((int) DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_THIRD.getValue());
                specimenTool.slides.setPosAndWaitLeftAuto((int) Slides.SlidesPos.LEFT_AUTO_PICKUP_THIRD.getValue());
                return false;
            }
        };



        resetAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone) {
                    Thread.yield();
                }


                specimenTool.leftAutoResetEnd();
                candyCane.preLeftAutoLevelOneAscent();

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
                        collectAction1,
                        collectPos1,
                        waitForArmAction1,
                        collectPos1_1,

                        highLaterDropsArmSetupAction,
                        moveToDropPos2,
                        collectAction2,
                        collectPos2,
                        waitForArmAction2,
                        collectPos2_1,

                        highLaterDropsArmSetupAction,
                        moveToDropPos3,
                        collectAction3,
                        collectPos3,
                        waitForArmAction3,
                        collectPos3_1,

                        highLaterDropsArmSetupAction,
                        moveToDropPos4,
                        resetAction,
                        parkPos,
                        levelOneAscent


                        )
        );
//
        telemetry.addData("current position", drive.pose);
        telemetry.update();


        sleep(10000);
    }
}

interface StatusCallback{
    public void done();
}