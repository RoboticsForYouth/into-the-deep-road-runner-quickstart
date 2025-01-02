package org.firstinspires.ftc.teamcode.az.sample;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
public class AdvanceRightAuto extends BasicRightAuto {
    private Action testDropPos;
    private Action firstSamplePos;
    private void updateInit() {
        initAuto();
        addActions();
    }
    private void addActions() {
        TrajectoryActionBuilder testDropPosTraj = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(5, 1), Math.toRadians(0));
        testDropPos = testDropPosTraj.build();
        Action newSpecimenHang = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.newSpecimenHang();
                sleep(1000);
                return false;
            }
        };
//        TrajectoryActionBuilder firstSamplePosTraj = specimenDropPosTraj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d())


    }
    @Override
    public void runOpMode() throws InterruptedException {
        updateInit();
        Actions.runBlocking(testDropPos);
        telemetry.addData("current position", drive.pose);
        telemetry.update();
        sleep(10000);
    }
}
