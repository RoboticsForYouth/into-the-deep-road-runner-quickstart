package org.firstinspires.ftc.teamcode.az.itd.tools;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.az.sample.AZUtil;

//@TeleOp
@Autonomous
public class Slides extends LinearOpMode {

    DcMotorEx slideMotor1;
    DcMotorEx slideMotor2;
    LinearOpMode opMode;
    public static final double POWER = 1;
    public static final double EXTEND_POWER = 0.3;

    public static final int INCREMENT = 150;
    public static final int INCREMENT_SLOW = 50;


    private int currentPosValue;

    public Slides() {
        super();
    }

    public Slides(LinearOpMode newOpMode) {
        this.opMode = newOpMode;
        setup();
    }

    public String printCurrentPos() {
       return  new StringBuffer().append("Slide 1: ")
                .append(slideMotor1.getCurrentPosition())
                .append(",\n Slide 2:")
                .append(slideMotor2.getCurrentPosition()).toString();
    }

    public void lowBasket() {
        setPos(SlidesPos.LOW_BASKET_DROP.value);
    }

    public enum SlidesPos {

        LEVEL_2_HANG_START(1400),
        LEVEL_2_HANG_END(400),
        COLLECT(500),
        RIGHT_AUTO_COLLECT(1500),

        MOVE(400),
        SPECIMEN_COLLECT(100),
        BASKET_DROP(2350),
        LOW_BASKET_DROP(750),

        SPECIMEN_PICKUP(300),

        RESET(0),
        SPECIMEN_DROP(0), //650
        SPECIMEN_CLIP(700),

        TEST_HEIGHT(1800),
        TELEOP_SPECIMEN_DROP(0), //650





        //--------------------------------------------------------------------------------------------------------------------
        //LEFT AUTO!!!
        LEFT_AUTO_PICKUP_FIRST(1050),
        LEFT_AUTO_PICKUP_SECOND(1250),
        LEFT_AUTO_PICKUP_THIRD(1050),
         LEFT_AUTO_BASKET_DROP(2500),
        LEFT_AUTO_INTERMEDIATE_PICKUP(700),

        //--------------------------------------------------------------------------------------------------------------------

        RIGHT_AUTO_SPECIMEN_DROP_SLIDES_DOWN_INITIAL(950), //650
        RIGHT_AUTO_SPECIMEN_DROP_SLIDES_DOWN(450), //650


        //--------------------------------------------------------------------------------------------------------------------
        //RIGHT AUTO!!!
        RIGHT_AUTO_SPECIMEN_DROP(0) //650
        //--------------------------------------------------------------------------------------------------------------------


        ;





        private int value;

        SlidesPos(int val) {
            this.value = val;
        }

        public double getValue() {
            return this.value;
        }
    }

    public void setup() {
        slideMotor1 = opMode.hardwareMap.get(DcMotorEx.class, "slides1");
        slideMotor2 = opMode.hardwareMap.get(DcMotorEx.class, "slides2");
        slideMotor1.setDirection(DcMotor.Direction.FORWARD);
        slideMotor2.setDirection(DcMotor.Direction.REVERSE);
        resetSlidePos();
    }

    private void setPos(int pos){

        AZUtil.setBothMotorTargetPosition(slideMotor1, slideMotor2, pos, POWER);
//        AZUtil.setMotorTargetPosition(slideMotor2, pos, POWER);
    }

    private void setPosLowPower(int pos){
        AZUtil.setBothMotorTargetPosition(slideMotor1, slideMotor2, pos, EXTEND_POWER);
//        AZUtil.setMotorTargetPosition(slideMotor2, pos, EXTEND_POWER);
    }

    public void setPosAndWait(int pos){
        setPos(pos);
        AZUtil.waitUntilMotorAtPos(this, slideMotor1, pos);
        AZUtil.waitUntilMotorAtPos(this, slideMotor2, pos);
    }

    public void setPosAndWaitLowPower(int pos){
        setPosLowPower(pos);
        AZUtil.waitUntilMotorAtPos(this, slideMotor1, pos);
        AZUtil.waitUntilMotorAtPos(this, slideMotor2, pos);
    }

    public void setPosAndWaitLeftAuto(int pos){
        setPos(pos);
        AZUtil.waitUntilMotorAtPos(this, slideMotor1, pos, 10, 2000);
        AZUtil.waitUntilMotorAtPos(this, slideMotor2, pos, 10, 2000);
    }

    public void setPosAndWaitWithTolerance(int pos, int tolerance){
        setPos(pos);
        AZUtil.waitUntilMotorAtPos(this, slideMotor1, pos, tolerance, 4000);
        AZUtil.waitUntilMotorAtPos(this, slideMotor2, pos, tolerance, 4000);
    }

    public void setCurrentPosValue(SlidesPos pos) {
        currentPosValue = pos.value;
    }

    public void moveUp(){
        int newPos = slideMotor1.getCurrentPosition() + INCREMENT;
        setPos(newPos);
    }

    public void moveDown(){
        int newPos = slideMotor1.getCurrentPosition() - INCREMENT;
        setPos(newPos);
    }

    public void moveUpSlow(){
        int newPos = slideMotor1.getCurrentPosition() + INCREMENT_SLOW;
        setPosLowPower(newPos);
    }

    public void moveDownSlow(){
        int newPos = slideMotor1.getCurrentPosition() - INCREMENT_SLOW;
        setPos(newPos);
    }

    public void moveUpSlider() {
        if( getCurrentPos() < 3800) {
            setPos(getCurrentPos() + 300);
        }
    }

    public void moveDownSlider() {
        if( getCurrentPos() > 800) {
            setPos(getCurrentPos() - 300);
        }
    }

    public int getCurrentPos(){
        return slideMotor1.getCurrentPosition();
    }

    public void extend(float factor) {
        int position = Math.round(SlidesPos.COLLECT.value + factor*1200);
        setPos(position);
    }

    public void resetSlidePos() {
        slideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void resetPos() {
        setPos(SlidesPos.RESET.value);
        resetSlidePos();
    }

    public void reset() {
        setPos(SlidesPos.RESET.value);
    }






    public void resetAndWait() {
        setPosAndWait(SlidesPos.RESET.value);
    }

    public void moveToPosition(SlidesPos slidesPos){
        setPos(slidesPos.value);
    }

    public void moveToPositionLowPower(SlidesPos slidesPos){
        setPosLowPower(slidesPos.value);
    }

    public void specimenPickUp() {
        moveToPosition(SlidesPos.SPECIMEN_PICKUP);
    }

    public void move() {
        setPos(SlidesPos.MOVE.value);
    }

    public void collect() {
        setPos(SlidesPos.COLLECT.value);
    }

    public void specimenCollect() {
        setPos(SlidesPos.SPECIMEN_COLLECT.value);
    }



    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!
    public void leftAutoPickup(SlidesPos slidesPos) {
        setPosAndWait(slidesPos.value);
    }
    //--------------------------------------------------------------------------------------------------------------------










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

    private void autoMode() {

        ElapsedTime runtime = new ElapsedTime();

        telemetry.addLine("Init");
        telemetry.update();
        setup();

        waitForStart();

//        teleOpTest();


        double seconds = runtime.seconds();

        telemetry.addData("start time: ", String.valueOf(seconds));
        telemetry.update();

            setPosAndWaitWithTolerance((int) Slides.SlidesPos.LEFT_AUTO_BASKET_DROP.getValue(), 5);

        double end_seconds = runtime.seconds();

        telemetry.addData("Pos1: ", slideMotor1.getCurrentPosition());
        telemetry.addData("Pos2: ", slideMotor2.getCurrentPosition());
        telemetry.addData("difference time: ", String.valueOf(end_seconds - seconds));
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

            telemetry.addData("Pos1", slideMotor1.getCurrentPosition());
            telemetry.addData("Pos2", slideMotor2.getCurrentPosition());
            telemetry.update();
        }
    }

    private void teleOp() {
        while (opModeIsActive()){
            if (gamepad1.dpad_up){
                moveUpSlider();
            }
            else if( gamepad1.dpad_down){
                moveDownSlider();
            }
        }
    }
}


