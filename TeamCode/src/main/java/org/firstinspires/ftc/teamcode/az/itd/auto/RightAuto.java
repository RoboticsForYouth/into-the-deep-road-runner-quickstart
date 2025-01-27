package org.firstinspires.ftc.teamcode.az.itd.auto;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
import org.firstinspires.ftc.teamcode.az.sample.MecanumDrive;


@Config
@Autonomous
public class RightAuto extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();

    SpecimenTool specimenTool = null;
    private MecanumDrive drive;
    private Pose2d beginPose;
    private Action specimenDropPos;
    private Action parkPos;
    private Action specimenToolDrop;
    private Action armDrop;
    private Action slidesReset;
    private Action readyToDropSpecimen;
    private Action park;
    private Action armGripperReset;
    private Action releaseSpecimenAction;


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

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        runtime.reset();
        beginPose = new Pose2d(0,0,Math.toRadians(180));
        drive = new MecanumDrive(hardwareMap, beginPose);
        telemetry.addData("current position", drive.pose);
        telemetry.update();
        createActions();
    }

    public void createActions() {
        specimenDropPos = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(24.7, 8), Math.toRadians(180))
                .build();



        parkPos = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(3, -36), Math.toRadians(0))
                .build();


        releaseSpecimenAction = new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.specimenDrop();
                return false;
            }
        };


        specimenToolDrop = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.specimenHangPos();
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
                        specimenToolDrop,
                        specimenDropPos,
                        releaseSpecimenAction,
                        new SleepAction(1)
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
