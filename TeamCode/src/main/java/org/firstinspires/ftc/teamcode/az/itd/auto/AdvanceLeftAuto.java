package org.firstinspires.ftc.teamcode.az.itd.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;

@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
public class AdvanceLeftAuto extends BasicLeftAuto {


    public static final double HIGH_BASKET_X_POS = 16;
    public static final double HIGH_BASKET_Y_POS = 9;
    public static final int HIGH_BASKET_HEADING = -45;

    private Action moveToDropPos1;
    private Action collectPos1;
    private Action highDropArmSetupAction;
    private Action resetAction;
    private Action collectAction;
    private Action collectPos1_1;
    private Action waitForArmAction;



    private void updateInit() {
        initAuto();
        addActions();
    }

    private void addActions() {

        TrajectoryActionBuilder moveToDropTraj1 = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(HIGH_BASKET_X_POS, HIGH_BASKET_Y_POS), Math.toRadians(HIGH_BASKET_HEADING));
        moveToDropPos1 = moveToDropTraj1.build();

        TrajectoryActionBuilder collectTraj1 = moveToDropTraj1.endTrajectory().fresh()
                .turnTo(Math.toRadians(5));
        collectPos1 = collectTraj1.build();

        TrajectoryActionBuilder collectTraj1_1 = collectTraj1.endTrajectory().fresh()
                .turnTo(Math.toRadians(-15));
        collectPos1_1 = collectTraj1_1.build();


        highDropArmSetupAction = new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                specimenTool.leftAutoDropHighBasket();
                return false;
            }
        };

        collectAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.leftAutoCollect();
                return false;
            }
        };

        waitForArmAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.setPosAndWaitLowPower((int) DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP.getValue());
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
    }

    @Override
    public void runOpMode() throws InterruptedException {
        updateInit();
        Actions.runBlocking(
                new SequentialAction(
                        //sample 1
                        highDropArmSetupAction,
                        moveToDropPos1, //start here!!!
                        new SleepAction(2),
                        collectAction,
                        collectPos1,
                        waitForArmAction,
                        collectPos1_1

                )
        );
//
        telemetry.addData("current position", drive.pose);
        telemetry.update();


        sleep(10000);
    }
}
