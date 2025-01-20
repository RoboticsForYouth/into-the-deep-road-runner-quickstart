package org.firstinspires.ftc.teamcode.az.itd.teleop;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;


@TeleOp
public class SpecimenTeleOp extends LinearOpMode {
    static final boolean FIELD_CENTRIC = false;
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();


    private MecanumDrive drive;
    private SpecimenTool specimenTool;

    private SpecimenTool.SpecimenState currentState;
    private boolean previousButtonState = false;

    //I want to be able to execute commands after a specified delay. the commands
    @Override
    public void runOpMode() throws InterruptedException {
        // Store the gamepad values from the previous loop iteration in
        // previousGamepad1/2 to be used in this loop iteration.
        // This is equivalent to doing this at the end of the previous
        // loop iteration, as it will run in the same order except for
        // the first/last iteration of the loop.
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);

        // Store the gamepad values from this loop iteration in
        // currentGamepad1/2 to be used for the entirety of this loop iteration.
        // This prevents the gamepad values from changing between being
        // used and stored in previousGamepad1/2.
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);

        GamepadEx driverOp = new GamepadEx(gamepad1);

        MecanumDrive drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_435)
        );

        currentState = SpecimenTool.SpecimenState.MOVE;
        specimenTool = new SpecimenTool(this);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();
        currentState.execute(specimenTool);


        while (opModeIsActive()) {

            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);



           /* if (!FIELD_CENTRIC) {
                double turnSpeed = -rightX;
                drive.driveRobotCentric(
                        -leftX,
                        -leftY,
                        turnSpeed,
                        false
                );
            } else {
                double degrees = imu.getRotation2d().getDegrees();
                drive.driveFieldCentric(
                        leftX,
                        leftY,
                        rightX,
                        degrees,   // gyro value passed in here must be in degrees
                        false
                );
            }
*/
            /*if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw();

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            */

            //if game pad a is pressed then get ready to grab the specimen
            if (currentGamepad1.a && !previousGamepad1.a) {
                cycleToNextState();
                currentState.execute(specimenTool);
//
            }

            if(gamepad1.b){
                currentState = SpecimenTool.SpecimenState.MOVE;
                currentState.execute(specimenTool);
            }

            if(gamepad1.x){
                specimenTool.reset();
            }


            /* if(gamepad1.b){
                specimenTool.setGrabAndLiftSpecimenPos();
            }
            if(gamepad1.x){
                specimenTool.setResetPos();
            }
            if( gamepad1.y){
                specimenTool.setSpecimenDropPos();
            }

            if( gamepad1.right_bumper){
                specimenTool.setSpecimenClipPos();
            }

            //set specimen tool to current position
            specimenTool.setCurrentPos();
            */

            drive.driveRobotCentric(
                    -driverOp.getLeftX(),
                    -driverOp.getLeftY(),
                    -driverOp.getRightX(),
                    false
            );
            specimenTool.printPos(telemetry);
            telemetry.addLine("State:" + currentState);
            telemetry.update();
        }

    }

    private void cycleToNextState() {
        // Cycle to the next state in the SpecimenState enum
        SpecimenTool.SpecimenState[] states = SpecimenTool.SpecimenState.values();
        int nextStateOrdinal = (currentState.ordinal() + 1) % states.length;  // Loop back to the first state
        currentState = states[nextStateOrdinal];
    }
}
