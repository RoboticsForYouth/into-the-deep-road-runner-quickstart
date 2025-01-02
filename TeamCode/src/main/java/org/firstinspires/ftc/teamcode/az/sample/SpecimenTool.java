package org.firstinspires.ftc.teamcode.az.sample;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class SpecimenTool extends LinearOpMode {
    private LinearOpMode opMode;
    Arm arm;
    Gripper gripper;
    Slides slides;

    public enum State {
        //Specimen State
        SPECIMEN_READY_TO_PICKUP,
        SPECIMEN_PICKED_UP,
        SPECIMEN_READY_TO_HANG,
        SPECIMEN_COMPLETED_HANGING,

        //Sample State
        SAMPLE_READY_TO_COLLECT,
        SAMPLE_COLLECTED,
        SAMPLE_READY_TO_DROP,
        SAMPLE_DROPPED;

        //implement methods


    }

//    State specimenState = State.SPECIMEN_READY_TO_PICKUP;
//    State sampleState = State.SAMPLE_READY_TO_COLLECT;

    public void setState(){
        if ( arm.getCurrentPosValue() == Arm.ArmPos.SPECIMEN_PICKUP_UP.getValue() &&
                slides.getCurrentPosValue() == Slides.SlidesPos.SPECIMEN_PICKUP.getValue() &&
                specimenClaw.getCurrentPosValue() == SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT.getValue()){
//            specimenState = State.SPECIMEN_PICKED_UP;
        }
    }
    SpecimenClaw specimenClaw;

    public SpecimenTool(){
        super();
    }
    public SpecimenTool(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode);
    }

    public void grabSpecimen() {
        specimenClaw.grab();
    }

    private void init(LinearOpMode opMode){
        arm = new Arm(opMode);
        gripper = new Gripper(opMode);
        slides = new Slides(opMode);
        specimenClaw = new SpecimenClaw(opMode);
    }

    public void eject() {
        gripper.sampleDrop();
        //sleep(500);
    }

    public void ejectPos(){
        //gripper.setCurrentPos(Gripper.GripperPos.);
    }

    public void dropHighBasket() {
        arm.moveToPosition(Arm.ArmPos.BASKET_DROP);
        sleep(2000);
        slides.moveToPosition(Slides.SlidesPos.BASKET_DROP);
        gripper.dropPos();
    }

    public void pickUpSpecimen(){
        slides.moveToPosition(Slides.SlidesPos.SPECIMEN_PICKUP);
        arm.moveToPosition(Arm.ArmPos.SPECIMEN_PICKUP_UP);


    }

    public void levelOneAscent() {
        arm.moveToPosition(Arm.ArmPos.LEVEL_ONE_ASCENT_PART_ONE);
        sleep(1200);
        slides.moveToPosition(Slides.SlidesPos.LEVEL_ONE_ASCENT);

        sleep(1000);
        arm.moveToPosition(Arm.ArmPos.LEVEL_ONE_ASCENT);
    }


    public void printPos(Telemetry telemetry){
        telemetry.addData("Slide Pos", slides.printCurrentPos());
        telemetry.addData("Arm Pos:", arm.getCurrentPosition());
        telemetry.update();
    }

    public void gripper_drop() {
        gripper.sampleDrop();
    }

    public void gripper_reset() {
        gripper.reset();
    }

    public void collect() {
        slides.collect();
//        sleep(500);
        gripper.collect();
//        sleep(500);
        arm.collect();
//        sleep(1000);
    }

    public void setCollectPos() {
//        slides.setCurrentPosValue(Slides.SlidesPos.COLLECT);
//       gripper.setCurrentPos(Gripper.GripperPos.WRISTCOLLECT.getValue(),
//               Gripper.RollerPower.COLLECT.getValue());
//        arm.setArmPos(Arm.ArmPos.COLLECT.getValue());
    }

    public void autoCollect()    {
        slides.collect();
//        sleep(500);
        gripper.autoCollect();
//        sleep(500);
        arm.autoCollect();
//        gripper.moveAround();

//        sleep(1000);
    }
 public void collectVertical() {
        slides.collect();
//        sleep(500);
        gripper.collectVertical();
//        sleep(500);
        arm.collect();
//        sleep(1000);
    }

    public void specimenHang() {
        arm.specimenHang();
        sleep(1000);
        slides.specimenHang();
        sleep(1000);
        gripper.specimenHang();
        sleep(500);

        arm.specimenDrop();
        sleep(500);
    }

    public void newSpecimenHang() {
        arm.newSpecimenHang();
        sleep(1000);
        slides.specimenHang();
        sleep(1000);
        specimenClaw.reset();
        sleep(500);

        arm.newSpecimenDrop();
        sleep(500);
    }
 public void specimenLowBasket() {
        arm.specimenHang();
        sleep(1000);
        slides.specimenHang();
        sleep(1000);
        gripper.dropPos();
        sleep(500);
    }


    public void move() {
        gripper.move();
        arm.move();
        sleep(1000);
        slides.move();
        sleep(500);

    }

    public void teleOpMove() {
        gripper.move();
        arm.move();
        sleep(1000);
        slides.move();
        sleep(500);

    }

    public void specimenToolInit() {
        slides.move();
        sleep(500);
        arm.move();
        sleep(1000);
        gripper.move();


    }

    public void reset() {
        slides.reset();
        sleep(1000);
        arm.reset();
        sleep(1000);
        gripper.reset();
        sleep(500);
    }

    public void resetAndWait() {
        slides.resetAndWait();
        sleep(1000);
        arm.reset();
//        sleep(1000);
        gripper.sampleDrop();
        sleep(1000);
        gripper.reset();
    }

    public void highReset () {
        slides.move();
        gripper.move();
        sleep(1000);
        arm.move();
        sleep(1000);

    }

    public void setArmCurrentPos (int pos) {
        arm.setCurrentPosValue(pos);
    }

    public void setSlidesCurrentPos (int pos) {
        slides.setCurrentPosValue(pos);
    }

    public void setWristCurrentPos (int pos) {
        gripper.setWristCurrentPosValue(pos);
    }

    public void setRollerCurrentPos (int pos) {
        gripper.setRollerCurrentPower(pos);
    }


    public void moveToCurrentPos() {
        arm.moveToCurrentPos();
    }



    //extend by a factor between 0 and 1
    public void extend(float factor) {slides.extend(factor);}

    public void slides_reset() {slides_reset();}

    @Override
    public void runOpMode() throws InterruptedException {
        this.opMode = this;
        init(opMode);
        gripper.dropPos();


        waitForStart();

//        autoTest();
        while(opModeIsActive()){

            if(gamepad1.dpad_up){
                slides.moveUpSlow();
            }

            if(gamepad1.dpad_down){
                slides.moveDown();
            }

            if( gamepad1.dpad_left){
                arm.moveDown();
            }

            if( gamepad1.dpad_right){
                arm.moveUp();
            }
            if( gamepad1.b){
                arm.setupPos();
                arm.moveToPosition(1160); //changed for 435 rpm motor to 312rpm
            }
        }


    }

    private void autoTest() {
        specimenToolInit();
        sleep(5000);

        collect();
        sleep(5000);

        move();
        sleep(5000);


        specimenHang();
        sleep(5000);

        highReset();
        sleep(5000);


        dropHighBasket();
        sleep(5000);

        highReset();
        sleep(5000);


        reset();
        sleep(5000);
    }

    public void level2Hang() {
        arm.moveToPosition(Arm.ArmPos.LEVEL_TWO_HANG);
        sleep(500);
        slides.moveToPositionLowPower(Slides.SlidesPos.LEVEL_2_HANG);
        sleep(1500);
        slides.moveToPosition(Slides.SlidesPos.RESET);
        sleep(1000);
        arm.moveToPosition(Arm.ArmPos.RESET);
        sleep(1000);
    }

    public void setGrabSpecimenPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_PICKUP);
        arm.setCurrentPosValue(Arm.ArmPos.SPECIMEN_PICKUP_UP);
        specimenClaw.reset();
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_RESET);
    }

    public void setCurrentPos() {
        slides.moveToCurrentPos();
        arm.moveToCurrentPos();
//        specimenClaw.setToCurrentPos();
    }

    public boolean hasGrabbedSpecimen() {
        //check if specimenClaw is in grab position
        return specimenClaw.isGrabbedSpecimen();
    }

    public void setSpecimenDropPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_DROP);
        arm.setCurrentPosValue(Arm.ArmPos.SPECIMEN_ARM_CLIP);
        specimenClaw.collect();
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT);
    }

    public void setSpecimenClipPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_CLIP);
        arm.setCurrentPosValue(Arm.ArmPos.SPECIMEN_PICKUP_UP);
        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                sleep(250);
                specimenClaw.reset();
            }
        });
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT);
    }

    public boolean isSpecimenDropPos() {
        return slides.getCurrentPosValue() == Slides.SlidesPos.SPECIMEN_DROP.getValue()
                && arm.getCurrentPosValue() == Arm.ArmPos.SPECIMEN_DROP.getValue()
                && specimenClaw.isGrabbedSpecimen();
    }

    public void setAfterDropSpecimenPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_CLIP);
        specimenClaw.reset();
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_RESET);
    }

    public void setResetPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.RESET);
        arm.setCurrentPosValue(Arm.ArmPos.RESET);
    }

    public void setGrabAndLiftSpecimenPos() {
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT);
        specimenClaw.collect();
        sleep(500);
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_LIFT);
        arm.setCurrentPosValue(Arm.ArmPos.SPECIMEN_PICKUP_UP);
    }

    //sets the current state
    public void setCurrentState(){
//        //set Specimen State
//        if ( slides.getCurrentPos() == Slides.SlidesPos.RESET &&
//        arm.getCurrentPosValue() == Arm.ArmPos.RESET){
//
//        }

    }
}
