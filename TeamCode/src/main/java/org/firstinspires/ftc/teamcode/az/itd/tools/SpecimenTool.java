package org.firstinspires.ftc.teamcode.az.itd.tools;

import static org.firstinspires.ftc.teamcode.az.itd.auto.AdvanceLeftAuto.LeftAutoHighDropArmSetupActionDone;

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

    public SpecimenTool(){
        super();
    }

    public SpecimenTool(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode);
    }

    private void init(LinearOpMode opMode){
        arm = new DoubleArm(opMode);
        gripper = new EnhancedClaw(opMode);
        slides = new Slides(opMode);
    }

    public void printPos(Telemetry telemetry){
        telemetry.addData("Slide Pos", slides.printCurrentPos());
        telemetry.addData("Arm Pos:", arm.getCurrentPosition());
        telemetry.addData("Gripper Pos:", gripper.toString());
        telemetry.update();
    }

    public void specimenToolInit() {
        slides.move();
        sleep(500);
        arm.move();
        sleep(1000);
        gripper.move();
    }

    public void extend(float factor) {slides.extend(factor);}
    //extend by a factor between 0 and 1










    public void specimenHangPos() {
        arm.setArmPos(DoubleArm.DoubleArmPos.SPECIMEN_DROP_INTEMEDIATE);
        slides.moveToPosition(Slides.SlidesPos.SPECIMEN_DROP);
        gripper.specimenDropPos();
//        gripper.specimenDrop();
    }

    public void teleOpspecimenHangPos() {
        arm.setArmPos(DoubleArm.DoubleArmPos.SPECIMEN_DROP);
        slides.moveToPosition(Slides.SlidesPos.SPECIMEN_DROP);
        gripper.teleOpspecimenDropPos();
//        gripper.specimenDrop();
    }

    public void specimenDrop() {
//        slides.moveToPosition(Slides.SlidesPos.RESET);
        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.SPECIMEN_DROP.getValue());


        gripper.drop();

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                sleep(500);
                gripper.stopPower();
            }
        });

    }





    public void teleOpSpecimenDrop(){
        gripper.specimenExtradrop();
    }



//    State specimenState = State.SPECIMEN_READY_TO_PICKUP;
//    State sampleState = State.SAMPLE_READY_TO_COLLECT;

    public void eject() {
        gripper.drop();
        //sleep(500);
    }

    public void dropHighBasket() {
        arm.moveToPosition(DoubleArm.DoubleArmPos.BASKET_DROP);
        sleep(1000);
        slides.moveToPosition(Slides.SlidesPos.BASKET_DROP);
        sleep(700);
        gripper.sampleDrop();
    }



    public void levelOneAscent() {
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_ONE_ASCENT_PART_ONE);
        sleep(1200);
        slides.moveToPosition(Slides.SlidesPos.LEVEL_ONE_ASCENT);

        sleep(1000);
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_ONE_ASCENT);
    }

    public void collect() {
        slides.collect();
//        sleep(500);
        gripper.samplePickup();
//        sleep(500);
        arm.collect();
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
        gripper.specimenPickUp();
        sleep(500);

        arm.specimenCollect();
        sleep(1000);
        slides.specimenCollect();
        sleep(1000);
    }



 public void specimenLowBasket() {
        arm.lowBasketDrop();
        sleep(1000);
        slides.specimenCollect();
        sleep(1000);
        gripper.sampleDrop();
        sleep(500);
    }

    public void teleOpMove() {
        arm.move();
    }

    public void move() {
        arm.move();
        sleep(1000);
        gripper.move();
        sleep(500);
        slides.move();
    }

    public void reset() {
        slides.resetPos();
        sleep(2000);
        arm.reset();
        sleep(2000);
        gripper.reset();
        sleep(500);
    }

    public void specimenAutoReset(){
        slides.resetPos();
        sleep(2000);
        arm.reset();
        sleep(2000);
        gripper.specimenAutoReset();
        sleep(500);
    }

    public void resetAndWait() {
        gripper.move();
        sleep(200);
        slides.reset();
//        gripper.drop();

        sleep(1000);
        arm.reset();
        gripper.reset();

////        sleep(1000);
//        sleep(1000);
//        gripper.resetPos();
    }


    public void highReset () {
        slides.move();
        gripper.move();
        sleep(1000);
        arm.move();
        sleep(1000);

    }

    public void teleOpHighResetVertical () {
        slides.collect();
        gripper.samplePickUp90();
        sleep(1000);
        arm.collect();
        sleep(1000);

    }

    public void teleOpHighReset () {
        slides.collect();
        gripper.samplePickup();
        sleep(1000);
        arm.collect();
        sleep(1000);

    }

    public void level2Hang() {
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_TWO_HANG);
        sleep(500);
        slides.moveToPositionLowPower(Slides.SlidesPos.LEVEL_2_HANG_START);
        sleep(3500);

        slides.moveToPosition(Slides.SlidesPos.LEVEL_2_HANG_END);
        sleep(1000);
        arm.moveToPosition(DoubleArm.DoubleArmPos.RESET);
        sleep(1000);
    }

    public void level2HangPart1(){
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_TWO_HANG);
        sleep(500);
        slides.moveToPositionLowPower(Slides.SlidesPos.LEVEL_2_HANG_START);
    }

    public void level2HangPart2(){
        slides.moveToPosition(Slides.SlidesPos.LEVEL_2_HANG_END);
        sleep(1000);
        arm.moveToPosition(DoubleArm.DoubleArmPos.RESET);
    }

    public void setGrabSpecimenPos() {
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_PICKUP);
        arm.setCurrentPosValue(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP);
        gripper.specimenPickUp();
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_RESET);
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

    public void setGrabAndLiftSpecimenPos() {
//        specimenClaw.setCurrentPosValue(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT);
        gripper.specimenPickUp();
        sleep(500);
        slides.setCurrentPosValue(Slides.SlidesPos.SPECIMEN_LIFT);
        arm.setCurrentPosValue(DoubleArm.DoubleArmPos.SPECIMEN_PICKUP_UP);
    }

    public void teleOpSpecimenHangPos() {

        arm.setArmPos(DoubleArm.DoubleArmPos.TELEOP_SPECIMEN_DROP);


        slides.moveToPosition(Slides.SlidesPos.TELEOP_SPECIMEN_DROP);
        gripper.teleOpSpecimenDropPos();
//        gripper.specimenDrop();
    }







    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!!

    public void leftAutoReset(){
        slides.resetPos();
        sleep(2000);
        arm.reset();
        sleep(2000);
        gripper.leftAutoReset();
        sleep(500);
    }


    public void leftAutoDropHighBasket() {

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                //gripper.autoProtect();
                //sleep(500);
                arm.setPosAndWait((int) DoubleArm.DoubleArmPos.LEFT_AUTO_BASKET_DROP.getValue());
                slides.setPosAndWaitWithTolerance((int) Slides.SlidesPos.LEFT_AUTO_BASKET_DROP.getValue(), 5);
                sleep(1200);
                gripper.leftAutoSampleDrop();
                LeftAutoHighDropArmSetupActionDone = true;
            }
        });
    }

    public void leftAutoCollect(final Slides.SlidesPos slidesPos, final EnhancedClaw.WRIST_POS wristPos, final DoubleArm.DoubleArmPos armPos)   {
        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                gripper.leftAutoPickup(wristPos);
                sleep(500);
                slides.setPosAndWait((int) Slides.SlidesPos.LEFT_AUTO_INTERMEDIATE_PICKUP.getValue());
                arm.leftAutoPickup(armPos);
                slides.leftAutoPickup(slidesPos);

            }
        });
    }

    public void leftAutoLaterDropsHighBasket() {

//        slides.reset();
//        sleep(500);

        slides.setPosAndWaitWithTolerance((int) Slides.SlidesPos.RESET.getValue(), 5);
        sleep(400);

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                //gripper.autoProtect();
                //sleep(500);

                gripper.leftAutoIntermediate();
                arm.setPosAndWait((int) DoubleArm.DoubleArmPos.LEFT_AUTO_BASKET_DROP.getValue());
                slides.setPosAndWaitWithTolerance((int) Slides.SlidesPos.LEFT_AUTO_BASKET_DROP.getValue(), 10);
                sleep(800);
                gripper.leftAutoSampleDrop();

                LeftAutoHighDropArmSetupActionDone = true;
            }
        });
    }

    public void leftAutoResetEnd() {
        gripper.move();
        sleep(200);
        slides.reset();
//        gripper.drop();

        sleep(1000);
        arm.leftAutoReset();
        gripper.reset();

////        sleep(1000);
//        sleep(1000);
//        gripper.resetPos();
    }

    //--------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!!
    public void rightAutoSpecimenDrop() {
//        slides.moveToPosition(Slides.SlidesPos.RESET);
        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.getValue());

//        gripper.drop();
//
//        AZUtil.runInParallel(new Runnable() {
//            @Override
//            public void run() {
//                sleep(500);
//                gripper.stopPower();
//            }
//        });

    }

    public void firstRightAutoSpecimenDrop() {
//        slides.moveToPosition(Slides.SlidesPos.RESET);
        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.getValue());

        gripper.drop();

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                sleep(500);
                gripper.stopPower();
            }
        });

    }

    public void rightAutoSpecimenCollect() {
        gripper.rightAutoSpecimenPickUp();
        slides.reset();
        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_UP.getValue());
        sleep(500);
    }

    public void afterDropRightAutoSpecimenCollect() {

        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_INTERMEDIATE_WAIT.getValue());
        slides.reset();
        arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_UP);
        gripper.rightAutoSpecimenPickUp();
        sleep(500);
    }

    public void rightAutoSpecimenHangPos() {

        arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);


        slides.moveToPosition(Slides.SlidesPos.RIGHT_AUTO_SPECIMEN_DROP);
        gripper.rightAutoSpecimenDropPos();
//        gripper.specimenDrop();
    }
    //--------------------------------------------------------------------------------------------------------------------

    public void rightAutoSpecimenHangPosNew() {

        arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);


        slides.moveToPosition(Slides.SlidesPos.RIGHT_AUTO_SPECIMEN_DROP);
        gripper.rightAutoSpecimenDropPos();
//        gripper.specimenDrop();
    }

    public void rightAutoSpecimenHangPosSlidesDown() {

        arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_SLIDES_DOWN);


        slides.moveToPosition(Slides.SlidesPos.RIGHT_AUTO_SPECIMEN_DROP_SLIDES_DOWN_INITIAL);
        gripper.rightAutoSpecimenDropPosSlidesDown();
//        gripper.specimenDrop();
    }

    public void rightAutoSpecimenDropSlidesDown() {


        slides.moveToPosition(Slides.SlidesPos.RIGHT_AUTO_SPECIMEN_DROP_SLIDES_DOWN);

        sleep(700);

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                gripper.drop();
                sleep(100);
                gripper.rollerCollect();

                arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_INTERMEDIATE_WAIT.getValue());
                slides.reset();
                arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_UP);
                gripper.rightAutoSpecimenPickUp();
                sleep(500);


            }
        });
//        gripper.specimenDrop();
    }

    public void firstRightAutoSlidesDownSpecimenDrop() {
//        slides.moveToPosition(Slides.SlidesPos.RESET);
        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.getValue());

        gripper.drop();



    }

    public enum SpecimenState {
        BASE(DoubleArm.DoubleArmPos.COLLECT, Slides.SlidesPos.COLLECT) {
            @Override
            public void execute(SpecimenTool tool) {

            }
        },
        COLLECT_SPECIMEN(DoubleArm.DoubleArmPos.SPECIMEN_DROP, Slides.SlidesPos.SPECIMEN_DROP) {
            @Override
            public void execute(SpecimenTool tool) {
                tool.specimenCollect();
            }
        },
        DROP(DoubleArm.DoubleArmPos.SPECIMEN_ARM_CLIP, Slides.SlidesPos.SPECIMEN_CLIP) {
            @Override
            public void execute(SpecimenTool tool) {
                tool.teleOpSpecimenHangPos();
            }
        },
        EJECT(DoubleArm.DoubleArmPos.SPECIMEN_ARM_CLIP, Slides.SlidesPos.SPECIMEN_CLIP) {
            @Override
            public void execute(SpecimenTool tool) {
                tool.eject();
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

    public enum HangState{
        BASE(DoubleArm.DoubleArmPos.COLLECT, Slides.SlidesPos.COLLECT) {
            @Override
            public void execute(SpecimenTool tool){

            }
        },
        Part1(DoubleArm.DoubleArmPos.SPECIMEN_DROP, Slides.SlidesPos.SPECIMEN_DROP) {
            @Override
            public void execute(SpecimenTool tool) {
                tool.level2HangPart1();
            }
        },
        Part2(DoubleArm.DoubleArmPos.SPECIMEN_ARM_CLIP, Slides.SlidesPos.SPECIMEN_CLIP){
            @Override
            public void execute(SpecimenTool tool) {
                tool.level2HangPart2();
            }
        };

        public abstract void execute(SpecimenTool tool);

        HangState(DoubleArm.DoubleArmPos amrPos, Slides.SlidesPos slidesPos){
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


    public enum SampleState{
        BASE(DoubleArm.DoubleArmPos.COLLECT, Slides.SlidesPos.COLLECT) {
            @Override
            public void execute(SpecimenTool tool){

            }
        },
        HIGHBASKET(DoubleArm.DoubleArmPos.SPECIMEN_DROP, Slides.SlidesPos.SPECIMEN_DROP) {
            @Override
            public void execute(SpecimenTool tool) {
                tool.dropHighBasket();
            }
        },
        EJECT(DoubleArm.DoubleArmPos.SPECIMEN_ARM_CLIP, Slides.SlidesPos.SPECIMEN_CLIP){
            @Override
            public void execute(SpecimenTool tool) {
                tool.eject();
            }
        },

        HIGH_TO_MOVE(DoubleArm.DoubleArmPos.SPECIMEN_ARM_CLIP, Slides.SlidesPos.SPECIMEN_CLIP){
            @Override
            public void execute(SpecimenTool tool) {
                tool.highReset();
            }
        };

        public abstract void execute(SpecimenTool tool);

        SampleState(DoubleArm.DoubleArmPos amrPos, Slides.SlidesPos slidesPos){
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
}
