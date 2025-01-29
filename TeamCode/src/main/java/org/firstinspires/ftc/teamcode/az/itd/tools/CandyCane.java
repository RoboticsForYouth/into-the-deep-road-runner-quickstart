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
        LOWER(0.0),
        RAISE(0.1),
        RESET(0.65);

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
        candyCane.setPosition(CandyCanePos.RESET.getValue());
    }

    public void autoLower() {
        candyCane.setPosition(CandyCanePos.LOWER.getValue());
    }

    public void autoRaise() {
        candyCane.setPosition(CandyCanePos.RAISE.getValue());
    }


    @Override
    public void runOpMode() throws InterruptedException {
        this.opMode = this;
        // Initialize hardware
        setup();

        waitForStart();

        autoLower();
        sleep(3000);
        autoRaise();
        sleep(3000);
        reset();
        sleep(3000);
    }
}
