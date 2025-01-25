package org.firstinspires.ftc.teamcode.az.itd.tools;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.az.sample.AZUtil;

//@TeleOp
@TeleOp
public class DoubleArm extends LinearOpMode {

    DcMotorEx doubleArmMotor1;
    DcMotorEx doubleArmMotor2;
    LinearOpMode opMode;
    public static final double POWER = 1.0;
    public static final double LOW_POWER = 0.6;
    public static final int INCREMENT = 50;
    private static final int SLOW_INCREMENT = 50;
    public static final double ARM_TICKS_PER_DEGREE = 19.7924893140647;
    public static final double ARM_CONVERSION_FACTOR = 14.444444444444;

    //PID adjustment
    private static final double kP = 0.0;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kF = 0.0; // Feedforward term, usually not needed for position control
    private static final double GRAVITY_COMPENSATION = 0.2;
    PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kP, kI, kD, kF);



    private int currentPosValue;

    public DoubleArm() {
        super();
    }

    public DoubleArm(LinearOpMode newOpMode) {
        this.opMode = newOpMode;
        setup();
    }

    public void move() {
        setPos(DoubleArmPos.MOVE.value);
    }

    public void lowBasketDrop() {
        setPos(DoubleArmPos.LOW_BASKET_DROP.value);
    }
    public void collect() {
        setPos(DoubleArmPos.COLLECT.value);

    }

    public void specimenCollect() {
        setPos(DoubleArmPos.SPECIMEN_PICKUP_UP.value);
    }

    public String printCurrentPos() {
        return  new StringBuffer().append("doubleArm 1: ")
                .append(doubleArmMotor1.getCurrentPosition())
                .append(",\n doubleArm 2:")
                .append(doubleArmMotor2.getCurrentPosition()).toString();
    }

    public void setCurrentPosValue(int pos) {
        currentPosValue = pos;
    }
    public void setCurrentPosValue(DoubleArmPos pos) {
        currentPosValue = pos.value;
    }

    public void moveToCurrentPos() {
        setPos(currentPosValue);
    }

    public enum DoubleArmPos {
        //multiple 1.39 times when we replace 435 motor with 312 motor
        DROP((int)(90 * ARM_CONVERSION_FACTOR)), //(530),
        RESET(0),
        COLLECT((int)(18 * ARM_CONVERSION_FACTOR)),  //(-785),
        AUTO_COLLECT((int)(0 * ARM_CONVERSION_FACTOR)),
        LOW_BASKET_DROP((int)(60 * ARM_CONVERSION_FACTOR)),
        NEW_SPECIMEN_DROP((int)(90 * ARM_CONVERSION_FACTOR)),
        LEVEL_ONE_ASCENT_PART_ONE((int)(80 * ARM_CONVERSION_FACTOR)), //(520),
        LEVEL_ONE_ASCENT((int)(80 * ARM_CONVERSION_FACTOR)), //(612), //(440),

        SPECIMEN_DROP((int)(70 * ARM_CONVERSION_FACTOR)), //(600),
        SPECIMEN_PICKUP_UP((int)(15*ARM_CONVERSION_FACTOR)),
        SPECIMEN_ARM_CLIP((int)(90 * ARM_CONVERSION_FACTOR)),

        LEVEL_TWO_HANG((int)(90 * ARM_CONVERSION_FACTOR)),


        MOVE((int)(20 * ARM_CONVERSION_FACTOR)), //(-450),
        BASKET_DROP((int)(90 * ARM_CONVERSION_FACTOR)),
        INIT(1250),
        AUTO_BASKET_DROP((int)(90 * ARM_CONVERSION_FACTOR)),

        VERTICAL_TEST(1300);


        private final int value;

        DoubleArmPos(int val) {
            this.value = val;
        }

        public double getValue() {
            return this.value;
        }
    }

    public void setup() {

        doubleArmMotor1 = opMode.hardwareMap.get(DcMotorEx.class, "arm1");
        doubleArmMotor2 = opMode.hardwareMap.get(DcMotorEx.class, "arm2");
        pidfCoefficients = doubleArmMotor1.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);

        doubleArmMotor1.setDirection(DcMotor.Direction.FORWARD);
        doubleArmMotor2.setDirection(DcMotor.Direction.REVERSE);
        resetDoubleArmPos();
    }

    private void resetDoubleArmPos() {
        doubleArmMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        doubleArmMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        doubleArmMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        doubleArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        doubleArmMotor1.setTargetPosition(0);
        doubleArmMotor2.setTargetPosition(0);


        doubleArmMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        doubleArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }

    public void moveUp(){
        int newPos = doubleArmMotor1.getCurrentPosition() + INCREMENT;
        setPos(newPos);
    }

    public void moveDown(){
        int newPos = doubleArmMotor1.getCurrentPosition() - INCREMENT;
        setPos(newPos);
    }

    public void moveDownSlow(){
        int newPos = doubleArmMotor1.getCurrentPosition() - SLOW_INCREMENT;
        setPos(newPos);
    }

    public void moveUpSlow(){
        int newPos = doubleArmMotor1.getCurrentPosition() + SLOW_INCREMENT;
        setPosLowPower(newPos);
    }


    public void quickExtend() {
        int newPos = doubleArmMotor1.getCurrentPosition() + 600;
        setPos(newPos);
    }
    private void setPos(int pos){
        double v = pos/ARM_TICKS_PER_DEGREE;
        double gravityCompensation = 0.01 * Math.cos(Math.toRadians(v));
        double power = 1.0 + gravityCompensation;
        AZUtil.setBothMotorTargetPosition(doubleArmMotor1, doubleArmMotor2, pos, power);
    }

    private void setPosLowPower(int pos){
        double v = pos/ARM_TICKS_PER_DEGREE;
        double gravityCompensation = 0.01 * Math.cos(Math.toRadians(v));
        double power = 1.0 + gravityCompensation;
        AZUtil.setBothMotorTargetPosition(doubleArmMotor1, doubleArmMotor2, pos, LOW_POWER);
    }

    private void setPosAndWait(int pos){
        setPos(pos);
        AZUtil.waitUntilMotorAtPos(this, doubleArmMotor1, pos);
        AZUtil.waitUntilMotorAtPos(this, doubleArmMotor2, pos);
    }

    public void extend(float factor) {
        int position = Math.round(DoubleArmPos.COLLECT.value + factor*900);
        setPos(position);
    }

    public void reset() {
        setPos(DoubleArmPos.RESET.value);
        resetDoubleArmPos();
    }

    public void resetAndWait() {
        setPosAndWait(DoubleArmPos.RESET.value);
    }

    public void halfwayReset() {
        setPos(DoubleArmPos.RESET.value);
    }


    public void moveToPosition(DoubleArmPos DoubleArmPos){
        setPos(DoubleArmPos.value);
    }
    public void moveToPositionLowPower(DoubleArmPos DoubleArmPos){
        setPosLowPower(DoubleArmPos.value);
    }
    public void specimenPickUp() {
        moveToPosition(DoubleArmPos.SPECIMEN_PICKUP_UP);
    }

    public int getCurrentPos(){
        return doubleArmMotor1.getCurrentPosition();
    }
    @Override
    public void runOpMode() {
        this.opMode = this;

        telemetry.addLine("Init");
        telemetry.update();
        setup();

        waitForStart();

        while (opModeIsActive()){

            if (gamepad1.dpad_up) {
                setArmPos(DoubleArmPos.BASKET_DROP);
                //sleep(1000);
            }

            if( gamepad1.dpad_down){
                setArmPos(DoubleArmPos.RESET);
            }

            if(gamepad1.dpad_right){
                setArmPos(DoubleArmPos.COLLECT);
            }

            if(gamepad1.dpad_left){
                setArmPos(DoubleArmPos.LOW_BASKET_DROP);
            }
            telemetry.addLine(this.toString());
            telemetry.update();
        }



    }

    @Override
    public String toString() {
        return "Arm{" +
                "arm1=" + doubleArmMotor1.getCurrentPosition() +
                ", arm2=" + doubleArmMotor2.getCurrentPosition() +
                ", pidfCoefficients=" + pidfCoefficients +
                '}';
    }

    private void teleOp() {
        while (opModeIsActive()){
            if (gamepad1.dpad_up){
                moveUpdoubleArmr();
            }
            else if( gamepad1.dpad_down){
                moveDowndoubleArmr();
            }
        }
    }


    public void moveDowndoubleArmr() {

        if( getCurrentPos() > 800) {
            setPos(getCurrentPos() - 300);
        }
    }

    public void moveUpdoubleArmr() {
        if( getCurrentPos() < 3800) {
            setPos(getCurrentPos() + 300);
        }
    }


    private void autoMode() {

        telemetry.addLine("Init");
        telemetry.update();
        setup();

        waitForStart();

//        teleOpTest();

        setPos(DoubleArmPos.VERTICAL_TEST.value);
        sleep(4000);
        telemetry.addData("Pos1", doubleArmMotor1.getCurrentPosition());
        telemetry.addData("Pos2", doubleArmMotor2.getCurrentPosition());
        telemetry.update();
        sleep(5000);
//        setPos(0);
//        sleep(5000);

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


            telemetry.addData("Pos1", doubleArmMotor1.getCurrentPosition());
            telemetry.addData("Pos2", doubleArmMotor2.getCurrentPosition());
            telemetry.update();
        }
    }





    public void autoCollect() {
        moveToPosition(DoubleArmPos.AUTO_COLLECT);
    }


    public void runWithoutEncoder() {
//        AZUtil.setMotorTargetPosition(arm1, pos, POWER);
        doubleArmMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        doubleArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        doubleArmMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        doubleArmMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


//        AZUtil.setMotorTargetPosition(arm2, pos, power);
    }

    public void slowMoveToPosition(int pos) {
//        AZUtil.setMotorTargetPosition(arm1, pos, POWER);
        double v = pos/ARM_TICKS_PER_DEGREE;
        double gravityCompensation = 0.01 * Math.cos(Math.toRadians(v));
        double power = 1.0 + gravityCompensation;
        AZUtil.setBothMotorTargetPosition(doubleArmMotor1, doubleArmMotor2, pos, LOW_POWER);
    }




    public void setPower(double power){

        doubleArmMotor1.setPower(power);
        doubleArmMotor2.setPower(power);

    }
    public void moveFactor (double factor) {

//        int newPos = arm2.getCurrentPosition() + 20;

        if (factor > 1) {
            factor = 1;
        }
        else if (factor < -1) {
            factor = -1;
        }
//        else if(factor < 0) {
//            factor = 0.25; //fix!!
//            newPos = newPos * -1;
//        }

        else if(factor < 0.25 && factor > 0) {
            factor = 0.25;
        }
        else if (factor > -0.25 && factor < 0) {
            factor = -0.25;
        }

        setPower(factor);
    }


    public void specimenDrop() {
        moveToPosition(DoubleArmPos.SPECIMEN_DROP);
    }

    public void newSpecimenHang() {moveToPosition(DoubleArmPos.SPECIMEN_DROP);}

    public void newSpecimenDrop() {moveToPosition(DoubleArmPos.NEW_SPECIMEN_DROP);}

    public int getCurrentPosition() {
        return doubleArmMotor1.getCurrentPosition();
    }



    public void setArmPos(DoubleArmPos pos) {
        moveToPosition(pos);
    }

}


