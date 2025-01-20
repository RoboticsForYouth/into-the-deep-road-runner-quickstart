package org.firstinspires.ftc.teamcode.az.itd.tools;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.az.sample.AZUtil;

//@TeleOp
@Autonomous
public class DoubleArm extends LinearOpMode {

    DcMotorEx doubleArmMotor1;
    DcMotorEx doubleArmMotor2;
    LinearOpMode opMode;
    public static final double POWER = 1.0;
    public static final double LOW_POWER = 0.6;
    public static final int INCREMENT = 150;
    private static final int SLOW_INCREMENT = 50;
    public static final double ARM_TICKS_PER_DEGREE = 19.7924893140647;





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
    public void collect() {
        setPos(DoubleArmPos.COLLECT.value);

    }

    public void specimenCollect() {
        setPos(DoubleArmPos.SPECIMEN_COLLECT.value);
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
        DROP((int)(70 * ARM_TICKS_PER_DEGREE)), //(530),
        RESET(0),
        COLLECT((int)(24 * ARM_TICKS_PER_DEGREE)),  //(-785),
        AUTO_COLLECT((int)(0 * ARM_TICKS_PER_DEGREE)),
        SPECIMEN_COLLECT((int)(54 * ARM_TICKS_PER_DEGREE)), //(700),
        LOW_BASKET_DROP((int)(155 * ARM_TICKS_PER_DEGREE)),
        NEW_SPECIMEN_HANG((int)(70 * ARM_TICKS_PER_DEGREE)),
        NEW_SPECIMEN_DROP((int)(160 * ARM_TICKS_PER_DEGREE)),
        LEVEL_ONE_ASCENT_PART_ONE((int)(41 * ARM_TICKS_PER_DEGREE)), //(520),
        LEVEL_ONE_ASCENT((int)(47 * ARM_TICKS_PER_DEGREE)), //(612), //(440),
        SPECIMEN_DROP((int)(47 * ARM_TICKS_PER_DEGREE)), //(600),

        SPECIMEN_PICKUP_UP((int)(38*ARM_TICKS_PER_DEGREE)),

        SPECIMEN_ARM_CLIP((int)(100 * ARM_TICKS_PER_DEGREE)),

        LEVEL_TWO_HANG((int)(180 * ARM_TICKS_PER_DEGREE)),


        MOVE((int)(20 * ARM_TICKS_PER_DEGREE)), //(-450),
        BASKET_DROP((int)(162 * ARM_TICKS_PER_DEGREE)),
        INIT(1250),
        AUTO_BASKET_DROP((int)(194 * ARM_TICKS_PER_DEGREE));


        private final int value;

        DoubleArmPos(int val) {
            this.value = val;
        }

        public double getValue() {
            return this.value;
        }
    }

    public void setup() {
        doubleArmMotor1 = opMode.hardwareMap.get(DcMotorEx.class, "DoubleArm1");
        doubleArmMotor2 = opMode.hardwareMap.get(DcMotorEx.class, "DoubleArm2");
        doubleArmMotor1.setDirection(DcMotor.Direction.FORWARD);
        doubleArmMotor2.setDirection(DcMotor.Direction.REVERSE);
        resetdoubleArmPos();
    }

    private void resetdoubleArmPos() {
        doubleArmMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        doubleArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        AZUtil.setBothMotorTargetPosition(doubleArmMotor1, doubleArmMotor2, pos, POWER);
    }

    private void setPosLowPower(int pos){
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
        resetdoubleArmPos();
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

//        teleOp();
        autoMode();

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

        setPos(DoubleArmPos.BASKET_DROP.value);
        sleep(8000);
        telemetry.addData("Pos1", doubleArmMotor1.getCurrentPosition());
        telemetry.addData("Pos2", doubleArmMotor2.getCurrentPosition());
        telemetry.update();
        sleep(5000);
        setPos(0);
        sleep(5000);

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
}


