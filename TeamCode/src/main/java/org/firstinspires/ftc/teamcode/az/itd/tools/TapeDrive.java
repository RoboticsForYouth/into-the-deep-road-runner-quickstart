package org.firstinspires.ftc.teamcode.az.itd.tools;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TapeDrive", group = "sample")
public class TapeDrive extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private CRServo tapeDrive;
    private LinearOpMode opMode;


    public enum TapeDrivePower {
        EXTEND(1),
        RETRACT(-1),
        STOP(0);

        public double getPower() {
            return power;
        }

        private double power;

        TapeDrivePower(double power) {
            this.power = power;
        }
    }

    public TapeDrive() {
        super();
        opMode = this;
    }

    public TapeDrive(LinearOpMode opMode) {
        this.opMode = opMode;
        setup();
    }

    private void setup() {
        tapeDrive = opMode.hardwareMap.get(CRServo.class, "tapeDrive");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        reset();
    }

    public void reset() {
        tapeDrive.setPower(TapeDrivePower.STOP.getPower());
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.opMode = opMode;
        // Initialize hardware
        setup();

        waitForStart();

        tapeDrive.setPower(TapeDrivePower.EXTEND.getPower());
        sleep(10000);
        tapeDrive.setPower(TapeDrivePower.STOP.getPower());
        sleep(5000);
        tapeDrive.setPower(TapeDrivePower.RETRACT.getPower());
        sleep(10000);
    }
}
