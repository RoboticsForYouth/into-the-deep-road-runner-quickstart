package org.firstinspires.ftc.teamcode.az.itd.auto;

import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public abstract class LeftAutoPosValues extends LinearOpMode {

    Vector2d drop0 =   new Vector2d(16.25, 10.25);
    public static final int      BASKET_HEADING_0 = -45;



    Vector2d collect1 = new Vector2d(14.25, 14.5);
    public static final int     COLLECT_HEADING_1 = -20;



    Vector2d collect1_1 =  new Vector2d(14, 22.5);



    Vector2d drop1 =         new Vector2d(14.5, 14);
    public static final int        BASKET_HEADING_1 = -45;



    Vector2d collect2 =    new Vector2d(9.75, 15.5);
    public static final int        COLLECT_HEADING_2 = 10;



    Vector2d collect2_1 =      new Vector2d(10, 21);



    Vector2d drop2 =        new Vector2d(13.75, 12);
    public static final int        BASKET_HEADING_2 = -45;



    Vector2d collect3 =     new Vector2d(13.75, 16);
    public static final int        COLLECT_HEADING_3 = 27;



    Vector2d collect3_1 = new Vector2d(13.75, 20.5);



    Vector2d drop3 =      new Vector2d(12.5, 12.75);
    public static final int        BASKET_HEADING_3 = -40;



    Vector2d park1 =            new Vector2d(52, 0);
    public static final int          PARK_HEADING_1 = -90;

    Vector2d park2 =          new Vector2d(52, -10);



}
