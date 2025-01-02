package org.firstinspires.ftc.teamcode.az.sample;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//@Autonomous

@Autonomous
public class Arm extends LinearOpMode {

    public static final double POWER = 1.0;
    public static final int INCREMENT = 25;
    private DcMotor arm1;
    private DcMotor arm2;
    LinearOpMode opMode;
    private int currentPosValue;

    private ArmPos currentPos;

    public static final double ARM_TICKS_PER_DEGREE = 19.7924893140647;

    public void moveToPosition(ArmPos pos) {
        moveToPosition(pos.value);
    }

    public void moveToCurrentPos() {
        moveToPosition(currentPosValue);
    }

    public int getCurrentPosValue() {
        return currentPosValue;
    }

    public void setCurrentPosValue(ArmPos armPos) {
        currentPosValue = (int) armPos.value;
        currentPos = armPos;
    }

    public ArmPos getCurrentArmPos(){
        return currentPos;
    }
//    public static int toDegrees(int ticks) {
//        int degree = ticks * 9826 / 194481;
//        return degree;
//    }

    public enum ArmPos {
        //multiple 1.39 times when we replace 435 motor with 312 motor
        DROP((int)(42 * ARM_TICKS_PER_DEGREE)), //(530),
        RESET(0),
        COLLECT((int)(-56 * ARM_TICKS_PER_DEGREE)),  //(-785),
        AUTO_COLLECT((int)(-56 * ARM_TICKS_PER_DEGREE)),
        SPECIMEN_HANG((int)(54 * ARM_TICKS_PER_DEGREE)), //(700),
        NEW_SPECIMEN_HANG((int)(156 * ARM_TICKS_PER_DEGREE)),
        NEW_SPECIMEN_DROP((int)(160 * ARM_TICKS_PER_DEGREE)),
        LEVEL_ONE_ASCENT_PART_ONE((int)(41 * ARM_TICKS_PER_DEGREE)), //(520),
        LEVEL_ONE_ASCENT((int)(47 * ARM_TICKS_PER_DEGREE)), //(612), //(440),
        SPECIMEN_DROP((int)(47 * ARM_TICKS_PER_DEGREE)), //(600),

        SPECIMEN_PICKUP_UP((int)(115*ARM_TICKS_PER_DEGREE)),

        SPECIMEN_ARM_CLIP((int)(100 * ARM_TICKS_PER_DEGREE)),

        LEVEL_TWO_HANG((int)(126 * ARM_TICKS_PER_DEGREE)),


        MOVE((int)(-30 * ARM_TICKS_PER_DEGREE)), //(-450),
        BASKET_DROP((int)(101 * ARM_TICKS_PER_DEGREE));
        //(1415);


        private final int value;

        ArmPos(int val) {
            this.value = val;
        }

        public double getValue() {
            return this.value;
        }
    }


    public Arm() {
        super();
    }

    public Arm(LinearOpMode newOpMode) {
        this.opMode = newOpMode;
        setup();
    }

    public void setup() {
//        arm1 = getArm("arm1");
        arm2 = getArm("arm2");
        setupPos();
    }

    private DcMotor getArm(String arm11) {
        return opMode.hardwareMap.get(DcMotor.class, arm11);
    }

    public void setupPos() {
//
//        setupArm(arm1);
        setupArm(arm2);
    }

     private void setupArm(DcMotor arm11) {
        arm11.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm11.setDirection(DcMotor.Direction.FORWARD);
        arm11.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm11.setTargetPosition(0);
        arm11.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void move() {
        moveToPosition(ArmPos.MOVE.value);
    }

    public void autoCollect() {
        moveToPosition(ArmPos.AUTO_COLLECT.value);
    }

    public void moveToPosition(int pos) {
//        AZUtil.setMotorTargetPosition(arm1, pos, POWER);
        AZUtil.setMotorTargetPosition(arm2, pos, POWER);
    }


    public void setCurrentPosValue(int pos) {
        currentPosValue = pos;
    }


    public void collect() {
        moveToPosition(ArmPos.COLLECT.value);
    }



    public void reset() {

        moveToPosition( ArmPos.RESET.value);
    }

    public void moveUp() {
        int newPos = arm2.getCurrentPosition() + INCREMENT;
        moveToPosition(newPos);
    }

    public void specimenHang() {
        moveToPosition(ArmPos.SPECIMEN_HANG.value);
    }

    public void specimenDrop() {
        moveToPosition(ArmPos.SPECIMEN_DROP.value);
    }

    public void newSpecimenHang() {moveToPosition(ArmPos.NEW_SPECIMEN_HANG);}

    public void newSpecimenDrop() {moveToPosition(ArmPos.NEW_SPECIMEN_DROP);}

    public int getCurrentPosition() {
        return arm2.getCurrentPosition();
    }

    public void specimenPicup(){moveToPosition(ArmPos.SPECIMEN_PICKUP_UP);}
    public void moveDown() {
        int newPos = arm2.getCurrentPosition() - INCREMENT;
        moveToPosition(newPos);
    }

    @Override
    public void runOpMode() {
        this.opMode = this;

        telemetry.addLine("Init");
        telemetry.update();
        setup();
        waitForStart();
        //AZUtil.setMotorTargetPosition(arm1, -1000, 1.0);
//        AZUtil.setMotorTargetPosition(arm2, -1000, 1.0);
//        AZUtil.setBothMotorTargetPosition(arm1, arm2, 100, .5);
//        moveToPosition(100);
//        sleep(5000);
//        AZUtil.setMotorTargetPosition(arm1, 0, 1.0);
//        sleep(2000);


//        teleOpTest();
        autoTest();

    }

    private void teleOpTest() {
        while (opModeIsActive()) {


            if (gamepad1.dpad_up) {
                moveUp();
                //sleep(1000);
            }
            if (gamepad1.dpad_down) {
                moveDown();
                //sleep(1000);
            }


            telemetry.addData("Pos", arm2.getCurrentPosition());
            telemetry.update();
        }
    }

    private void autoTest() {
        setArmPos(ArmPos.RESET);
        sleep(3000);
        setArmPos(ArmPos.MOVE);
        sleep(3000);

        setArmPos(ArmPos.COLLECT);
        sleep(3000);

        setArmPos(ArmPos.SPECIMEN_HANG);
        sleep(3000);

        setArmPos(ArmPos.BASKET_DROP);
        sleep(3000);

        setArmPos(ArmPos.NEW_SPECIMEN_HANG);
        sleep(3000);

        reset();
        sleep(10000);
    }

    public void setArmPos(ArmPos pos) {
        moveToPosition(pos);
    }

}