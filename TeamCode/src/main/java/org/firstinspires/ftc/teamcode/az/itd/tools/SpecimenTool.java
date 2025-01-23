package org.firstinspires.ftc.teamcode.az.itd.tools;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.az.sample.AZUtil;

@TeleOp
public class SpecimenTool extends LinearOpMode {
    public  LinearOpMode opMode;
    public DoubleArm arm;
    public EnhancedClaw gripper;
    public Slides slides;
    public LaserRangeFinder lrf;


    public boolean isSlidesMovingUpInPos(Slides.SlidesPos slidesPos, int tolerance) {
        return slides.getCurrentPos() > (slidesPos.getValue() - tolerance);
    }

    public boolean isArmMovingDownInPos(Arm.ArmPos armPos, int tolerance) {
        return arm.getCurrentPosition() < (armPos.getValue() + tolerance);
    }

    public boolean isArmMovingUpInPos(Arm.ArmPos armPos, int tolerance) {
        return arm.getCurrentPosition() > (armPos.getValue() - tolerance);
    }
    public boolean isSlidesMovingDownInPos(Slides.SlidesPos slidesPos, int tolerance) {
        return slides.getCurrentPos() < (slidesPos.getValue() + tolerance);
    }

    public void specimenHangPos() {
        arm.setArmPos(DoubleArm.DoubleArmPos.NEW_SPECIMEN_HANG);
        slides.moveToPosition(Slides.SlidesPos.SPECIMEN_DROP);
        gripper.specimenDrop();
    }


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
        arm = new DoubleArm(opMode);
        gripper = new EnhancedClaw(opMode);
        slides = new Slides(opMode);
//        lrf = new LaserRangeFinder(hardwareMap.get(RevColorSensorV3.class, "Laser"));
//        lrf.setDistanceMode(LaserRangeFinder.DistanceMode.SHORT);

    }

    public void eject() {
        gripper.drop();
        //sleep(500);
    }

    public void ejectPos(){
        //gripper.setCurrentPos(Gripper.GripperPos.);
    }

    public void dropHighBasket() {
        arm.moveToPosition(DoubleArm.DoubleArmPos.BASKET_DROP);
        sleep(1000);
        slides.moveToPosition(Slides.SlidesPos.BASKET_DROP);
        gripper.sampleDrop();
    }

    public void autoDropHighBasket() {
        arm.moveToPosition(DoubleArm.DoubleArmPos.AUTO_BASKET_DROP);
        sleep(700);

        while (!isArmMovingUpInPos(Arm.ArmPos.AUTO_BASKET_DROP, 70)) {
            Thread.yield();
        }

        slides.moveToPosition(Slides.SlidesPos.AUTO_BASKET_DROP);
//        gripper.autoSampleDrop();
    }

    public void pickUpSpecimen(){
        slides.moveToPosition(Slides.SlidesPos.SPECIMEN_PICKUP);
        arm.moveToPosition(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP);

    }

    public void levelOneAscent() {
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_ONE_ASCENT_PART_ONE);
        sleep(1200);
        slides.moveToPosition(Slides.SlidesPos.LEVEL_ONE_ASCENT);

        sleep(1000);
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_ONE_ASCENT);
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
        gripper.samplePickup();
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

    public void autoCollect(EnhancedClaw.WRIST_POS autoPickup)    {

        slides.collect();

//        sleep(500);
        gripper.autoPickup(autoPickup);
//        sleep(500);
        arm.autoCollect();
//        gripper.moveAround();

//        sleep(1000);
    }

    public void detectColorActionAuto() {

        // Continuously detect color
//        gripper.detectedColor = gripper.detectColor();
//
//        while(gripper.detectedColor.equals(gripper.UNKNOWN)) {
//            Thread.yield();
//            gripper.detectedColor = gripper.detectColor();
//        }
//        sleep(1000);
        autoMove();
    }

    public void autoCollectAngled() {
        slides.collect();

//        sleep(500);
        gripper.autoPickupAngled();
//        sleep(500);
        arm.autoCollect();
    }

    public void rightAutoDrop()    {
        slides.collect();
//        sleep(500);
        gripper.rightAutoDrop();
//        sleep(500);
        arm.autoCollect();
//        gripper.moveAround();

//        sleep(1000);
    }

 public void collectVertical() {
        slides.collect();
//        sleep(500);
        gripper.samplePickUp90();
//        sleep(500);
        arm.collect();
//        sleep(1000);
    }

    public void specimenCollect() {
        arm.specimenCollect();
        sleep(1000);
        slides.specimenCollect();
        sleep(1000);
        gripper.specimenPickUp();
        sleep(500);

        arm.specimenDrop();
        sleep(500);
    }

    public void newSpecimenHang() {
        arm.newSpecimenHang();
        sleep(1000);
        slides.specimenCollect();
        sleep(1000);
        specimenClaw.reset();
        sleep(500);

        arm.newSpecimenDrop();
        sleep(500);
    }
 public void specimenLowBasket() {
        arm.lowBasketDrop();
        sleep(1000);
        slides.specimenCollect();
        sleep(1000);
        gripper.sampleDrop();
        sleep(500);
    }


    public void move() {
        arm.move();
        sleep(1000);
        gripper.move();
        sleep(500);
        slides.move();


    }

    public void autoMove() {
        slides.move();
        while(!isSlidesMovingDownInPos(Slides.SlidesPos.MOVE,50)) {
            Thread.yield();
        }
        arm.move();
//        gripper.move();
        gripper.autoSampleDrop();

    }


//    public void initArmDistanceSensor() {
//
//        arm.runWithoutEncoder();
//
//
//        double distance = lrf.getDistance(DistanceUnit.INCH);
//
//        while (!(distance >= (LaserRangeFinder.height - LaserRangeFinder.tolerance)) || !(distance <= (LaserRangeFinder.height + LaserRangeFinder.tolerance))) {
//
//            double factor = (LaserRangeFinder.height - distance) / 7;
//
//            arm.moveFactor(factor);
//
//            distance = lrf.getDistance(DistanceUnit.INCH); //continuously record distance
//
//            telemetry.addData("Factor", factor);
//            telemetry.addData("Distance", distance);
//            telemetry.addData("Status", lrf.getStatus());
//            telemetry.update();
//        }
//        arm.moveFactor(0);
//        telemetry.addLine("Done");
//        telemetry.addData("Distance", distance);
//        telemetry.update();
//
//        }

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
        sleep(2000);
        arm.reset();
        sleep(2000);
        gripper.reset();
        sleep(500);
    }



    public void resetAndWait() {
        slides.resetAndWait();
        gripper.move();
//        gripper.drop();

        sleep(1000);
        arm.reset();
        gripper.reset();

////        sleep(1000);
//        sleep(1000);
//        gripper.reset();
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
                arm.moveDownSlow();
            }

            if( gamepad1.dpad_right){
                arm.moveUp();
            }
            if( gamepad1.b){
//                arm.setupPos();
//                arm.moveToPosition(1160); //changed for 435 rpm motor to 312rpm
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


        specimenCollect();
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
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_TWO_HANG);
        sleep(500);
        slides.moveToPositionLowPower(Slides.SlidesPos.LEVEL_2_HANG);
        sleep(1500);
        slides.moveToPosition(Slides.SlidesPos.RESET);
        sleep(1000);
        arm.moveToPosition(DoubleArm.DoubleArmPos.RESET);
        sleep(1000);
    }

    public void setGrabSpecimenPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_PICKUP);
        arm.setCurrentPosValue(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP);
        gripper.specimenPickUp();
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_RESET);
    }

    public void setCurrentPos() {
        slides.moveToCurrentPos();
        arm.moveToCurrentPos();
//        specimenClaw.setToCurrentPos();
    }
    
    public void autoCollectAndWait(EnhancedClaw.WRIST_POS wristPos, Slides.SlidesPos slides) {
        autoCollect(wristPos); //roller starts intaking
        this.slides.moveToPosition(slides);

        while (!isArmMovingDownInPos(Arm.ArmPos.AUTO_COLLECT, 50 ) ) {
            Thread.yield();
        }
        sleep(300);

    }

    public boolean hasGrabbedSpecimen() {
        //check if specimenClaw is in grab position
        return specimenClaw.isGrabbedSpecimen();
    }

    public void setSpecimenDropPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_DROP);
        arm.setCurrentPosValue(DoubleArm.DoubleArmPos.SPECIMEN_ARM_CLIP);
        gripper.specimenDrop();
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT);
    }

    public void setSpecimenClipPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_CLIP);
        arm.setCurrentPosValue(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP);
        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                sleep(250);
                gripper.reset();
            }
        });
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT);
    }



    public void setAfterDropSpecimenPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_CLIP);
        specimenClaw.reset();
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_RESET);
    }

    public void setResetPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.RESET);
        arm.setCurrentPosValue(DoubleArm.DoubleArmPos.RESET);
    }

    public void setGrabAndLiftSpecimenPos() {
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT);
        gripper.specimenPickUp();
        sleep(500);
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_LIFT);
        arm.setCurrentPosValue(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP);
    }

    public enum SpecimenState {

        MOVE(DoubleArm.DoubleArmPos.MOVE, Slides.SlidesPos.MOVE){
            @Override
            public void execute(SpecimenTool tool) {
                tool.slides.setCurrentPosValue(slidePos);
                tool.arm.setArmPos(doubleArmPos);
                tool.gripper.move();
            }
        },
        GRAB_SPECIMEN(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP, Slides.SlidesPos.SPECIMEN_PICKUP) {
            @Override
            public void execute(SpecimenTool tool) {
                tool.setGrabSpecimenPos();
                tool.slides.setCurrentPosValue(slidePos);
                tool.arm.setArmPos(doubleArmPos);
            }
        },
        LIFT_SPECIMEN(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP, Slides.SlidesPos.SPECIMEN_LIFT){
            @Override
            public void execute(SpecimenTool tool) {
                tool.setGrabAndLiftSpecimenPos();
                tool.slides.setCurrentPosValue(slidePos);
                tool.arm.setArmPos(doubleArmPos);
            }
        },
        DROP_SPECIMEN(DoubleArm.DoubleArmPos.SPECIMEN_DROP, Slides.SlidesPos.SPECIMEN_DROP) {
            @Override
            public void execute(SpecimenTool tool) {
                tool.setSpecimenDropPos();
                tool.slides.setCurrentPosValue(slidePos);
                tool.arm.setArmPos(doubleArmPos);
            }
        },
        CLIP_SPECIMEN(DoubleArm.DoubleArmPos.SPECIMEN_ARM_CLIP, Slides.SlidesPos.SPECIMEN_CLIP){
            @Override
            public void execute(SpecimenTool tool) {
                tool.setSpecimenClipPos();
                tool.slides.setCurrentPosValue(slidePos);
                tool.arm.setArmPos(doubleArmPos);
            }
        };
        public abstract void execute(SpecimenTool tool);



        SpecimenState(DoubleArm.DoubleArmPos amrPos, Slides.SlidesPos slidesPos){
            this.doubleArmPos = amrPos;
            this.slidePos = slidesPos;
        }

        Slides.SlidesPos  slidePos ;
        DoubleArm.DoubleArmPos doubleArmPos ;
        public String toString(){
            return new StringBuffer("CurrentState:")
                    .append(", SlidePos").append(slidePos)
                    .append(", ArmPos").append(doubleArmPos).toString();

        }

    }


}
