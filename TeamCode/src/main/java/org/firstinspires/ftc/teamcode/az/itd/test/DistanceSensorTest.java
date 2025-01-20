package org.firstinspires.ftc.teamcode.az.itd.test;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.az.itd.tools.LaserRangeFinder;
import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;


@Autonomous
public class DistanceSensorTest extends LinearOpMode {

    LaserRangeFinder lrf = null;
    SpecimenTool specimenTool = null;

    public void runOpMode() throws InterruptedException {

        lrf = new LaserRangeFinder(hardwareMap.get(RevColorSensorV3.class, "Laser"));
        lrf.setDistanceMode(LaserRangeFinder.DistanceMode.LONG);
        specimenTool = new SpecimenTool(this);

        waitForStart();
        while (opModeIsActive()) {

            double distance = lrf.getDistance(DistanceUnit.INCH);

            while (!(distance >= (LaserRangeFinder.height - LaserRangeFinder.tolerance)) || !(distance <= (LaserRangeFinder.height + LaserRangeFinder.tolerance))) {

                if (distance > (LaserRangeFinder.height)) {
                    specimenTool.arm.moveDownSlow();
                }

                else if (distance < (LaserRangeFinder.height)) {
                    specimenTool.arm.moveUpSlow();
                }

                distance = lrf.getDistance(DistanceUnit.INCH); //continuously record distance

                telemetry.addData("Distance", distance);
                telemetry.addData("Status", lrf.getStatus());
                telemetry.update();
            }
        }
    }
}

