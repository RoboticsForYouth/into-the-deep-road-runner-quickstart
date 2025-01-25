package org.firstinspires.ftc.teamcode.az.itd.tools;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CandyCane", group = "sample")
public class CandyCane extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private Servo candyCane;
    private LinearOpMode opMode;


    public enum CandyCanePos {
        EXTEND(1),
        RETRACT(-1),
        STOP(0);

        public double getValue() {
            return value;
        }

        private double value;

        CandyCanePos(double val) {
            this.value = val;
        }
    }

    public CandyCane() {
        super();
        opMode = this;
    }

    public CandyCane(LinearOpMode opMode) {
        this.opMode = opMode;
        setup();
    }

    private void setup() {
        candyCane = opMode.hardwareMap.get(Servo.class, "candyCane");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        reset();
    }

    public void reset() {
        candyCane.setPosition(CandyCanePos.STOP.getValue());
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.opMode = opMode;
        // Initialize hardware
        setup();

        waitForStart();

        candyCane.setPosition(CandyCanePos.EXTEND.getValue());
        sleep(10000);
        candyCane.setPosition(CandyCanePos.STOP.getValue());
        sleep(5000);
        candyCane.setPosition(CandyCanePos.RETRACT.getValue());
        sleep(10000);
    }
}
