package org.firstinspires.ftc.teamcode;


/*
*
* Focals (pixels) - Fx: 473.69 Fy: 473.69
Optical center - Cx: 332.124 Cy: 233.179
Radial distortion (Brown's Model)
K1: 0.00556051 K2: -0.0690059 K3: 0.0180892
P1: 0.000154844 P2: 0.00311682
Skew: 0*/
import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

//This has working camera!
@TeleOp(name = "AprilTracking")
public class AprilTracking extends OpMode {
    public double xSet = -22.4;
    public double currenty = 90;
    public double currentx = 90;
    public double ySet = 117.3;
    public double zSet = 28.9;
    public double setpointTolerance = 2.0;
    public DcMotorEx frontLeft;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;
    public double blpreviousPos, blpreviousTime, blspeed, blgain = 0;
    public double flpreviousPos, flpreviousTime, flspeed, flgain = 0;
    public double brpreviousPos, brpreviousTime, brspeed, brgain = 0;
    public double frpreviousPos, frpreviousTime, frspeed, frgain = 0;
    public DcMotorEx frontRight;
    public DcMotorEx flywheel1;
    public DcMotorEx flywheel2;
    public CRServo topintake2;
    //New comment
    public CRServo topintake;
    public CRServo br1;
    public Servo camLR;
    public Servo camUD;
    public CRServo bl1;
    public double speed;
    public double strafe;
    public double turn;
    public double flySpeed;
    public Servo camS;
    public CRServo intake12;
    public Camera cam;
    public double pause = 0;
    public int dir = 1;
    public String dire = "Intake";
    AprilTagWebcam aprilTagWebcam  = new AprilTagWebcam();
    public static boolean equalsWithinTolerance(double a, double b, double tolerance) {
        return Math.abs(a - b) <= tolerance;
    }

    public void fly(double flywheelSpeed) {
        flywheel1.setPower(flywheelSpeed);
        flywheel2.setPower(-flywheelSpeed);
        intake12.setPower(-flywheelSpeed);
    }

    public void intake(double intakePower) {
        br1.setPower(intakePower);
        bl1.setPower(-intakePower);
    }
    VisionPortal portal1;
    VisionPortal portal2;

    AprilTagProcessor aprilTagProcessor1;
    AprilTagProcessor aprilTagProcessor2;


    @Override
    public void init() {
        backRight = hardwareMap.get(DcMotorEx.class, "br");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");
        topintake = hardwareMap.get(CRServo.class, "topintake");
        topintake2 = hardwareMap.get(CRServo.class, "bottomIntake");
        br1 = hardwareMap.get(CRServo.class, "br1");
        bl1 = hardwareMap.get(CRServo.class, "intake");
        camS = hardwareMap.get(Servo.class, "cam");
        intake12 = hardwareMap.get(CRServo.class, "TPUflapper");
        camLR = hardwareMap.get(Servo.class, "lrgimbal");
        camUD = hardwareMap.get(Servo.class, "udgimbal");
        camLR.setPosition(0.8);
        camUD.setPosition(0.5);
        aprilTagWebcam.init(hardwareMap, telemetry);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flySpeed = 1;
    }

    @Override
    public void loop() {
        speed = dir * gamepad1.right_stick_y * 0.95;
        strafe = gamepad1.right_stick_x * 0.95;
        turn = gamepad1.left_stick_x * 0.95;
        {
            if (gamepad1.a) {
                if (dir == 1) {
                    dir = -1;
                    dire = "Shooter";
                } else {
                    dir = 1;
                    dire = "Intake";
                }
                sleep(50);
            }


            if (gamepad1.right_bumper /* && bumper ot pressed */) {
                pause = getRuntime();
                if (flySpeed >= 1) {
                    flySpeed = 1;
                } else {
                    flySpeed += 0.1;
                }
                sleep(200);
            }
            if (gamepad1.left_bumper /* && bumper ot pressed */) {
                pause = getRuntime();
                if (flySpeed <= 0.0) {
                    flySpeed = 0.0;
                } else {
                    flySpeed -= 0.1;
                }
                sleep(200);
            }
            if (gamepad1.right_trigger > 0 && !gamepad1.dpad_down) {
                fly(flySpeed);
               // flywheel1.setVelocity(100);
                //flywheel2.setVelocity(-100);
                topintake.setPower(-1);
                topintake2.setPower(-1);
            } else if(gamepad1.right_trigger == 0 && !gamepad1.dpad_down) {
                fly(0);
                topintake.setPower(0);
                topintake2.setPower(0);
            }
            if(gamepad1.dpad_down){
                intake(1);
                topintake.setPower(1);
                topintake2.setPower(1);
                fly(-1);
            }
            if (gamepad1.left_trigger > 0) {
                intake(1);
            } else if (gamepad1.left_trigger == 0 && !gamepad1.dpad_down) {
                intake(0);
            }

            //Nico Code
            double blgoalspeed = -1 * (speed + strafe - turn);
            blgain = 0;
            if (blspeed > blgoalspeed) {
                blgain += 0.04;
            } else if (blspeed < blgoalspeed) {
                blgain -= 0.04;
            }
            double flgoalspeed = 1 * (speed - strafe - turn);
            flgain = 0;
            if (flspeed / 2400 > flgoalspeed) {
                flgain += 0.04;
            } else if (flspeed / 2400 < flgoalspeed) {
                flgain -= 0.04;
            }
            double brgoalspeed = speed - strafe + turn;
            brgain = 0;
            if (brspeed > brgoalspeed) {
                brgain += 0.04;
            } else if (brspeed < brgoalspeed) {
                brgain -= 0.04;
            }
            double frgoalspeed = speed + strafe + turn;
            frgain = 0;
            if (frspeed / 2400 > frgoalspeed) {
                frgain += 0.04;
            } else if (frspeed / 2400 < frgoalspeed) {
                frgain -= 0.04;
            }
            //if no controler input - no wheel movement
            if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_y == 0 && gamepad1.right_stick_x == 0) {
                backLeft.setPower(0);
                backRight.setPower(0);
                frontRight.setPower(0);
                frontLeft.setPower(0);
            }
//cam servo code


            backLeft.setPower(blgoalspeed + blgain);
            frontLeft.setPower(flgoalspeed + flgain);
            backRight.setPower(brgoalspeed + brgain);
            frontRight.setPower(frgoalspeed + frgain);
            //if no controler input - no wheel movement
            if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_y == 0 && gamepad1.right_stick_x == 0) {
                backLeft.setPower(0);
                backRight.setPower(0);
                frontRight.setPower(0);
                frontLeft.setPower(0);
            }
            //telemetry.addData("RunTime", getRuntime());
            double blcurrentPos = backLeft.getCurrentPosition();
            double bltime = getRuntime();
            blspeed = (blcurrentPos - blpreviousPos) / (bltime - blpreviousTime) / 2400;
            blpreviousPos = blcurrentPos;
            blpreviousTime = bltime;
            //telemetry.addData("BLSpeed", blspeed);
            double flcurrentPos = frontLeft.getCurrentPosition();
            double fltime = getRuntime();
            flspeed = (flcurrentPos - flpreviousPos) / (fltime - flpreviousTime) / 2400;
            flpreviousPos = flcurrentPos;
            flpreviousTime = fltime;
            //telemetry.addData("FLSpeed", flspeed);

            double brcurrentPos = backRight.getCurrentPosition();
            double brtime = getRuntime();
            brspeed = (brcurrentPos - brpreviousPos) / (brtime - brpreviousTime) / 2400;
            brpreviousPos = brcurrentPos;
            brpreviousTime = brtime;
            //telemetry.addData("BRSpeed", brspeed);
            double frcurrentPos = frontRight.getCurrentPosition();
            double frtime = getRuntime();
            frspeed = (frcurrentPos - frpreviousPos) / (frtime - frpreviousTime) / 2400;
            frpreviousPos = frcurrentPos;
            frpreviousTime = frtime;
            /*telemetry.addData("FRSpeed", frspeed);
            telemetry.addData("2 - 10 Launcher Speed :", flySpeed * 10);
            telemetry.addData("Direction Forward:", dire);
        */
        }
        aprilTagWebcam.update();
        AprilTagDetection id20 = aprilTagWebcam.getTagBySpecificId(20);
        telemetry.addData("2 - 10 Launcher Speed :", flySpeed * 10);
        if(gamepad1.y){
            while(true){
                while(true) {
                    id20 = null;
                        while(id20 == null){
                            aprilTagWebcam.update();
                            id20 = aprilTagWebcam.getTagBySpecificId(20);
                            aprilTagWebcam.displayDetectionTelemetry(id20);

                            if(backLeft.getPower() > 0){
                                backLeft.setPower(backLeft.getPower()-0.05);
                            }
                            if(frontLeft.getPower() > 0){
                                frontLeft.setPower(frontLeft.getPower()-0.05);
                            }
                            if(backRight.getPower() > 0){
                                backRight.setPower(backRight.getPower()-0.05);
                            }
                            if(frontRight.getPower() > 0){
                                frontRight.setPower(frontRight.getPower()-0.05);
                            }
                        }


                        currentx = id20.ftcPose.x;




                    if (equalsWithinTolerance(currentx, xSet, 2)) {
                        strafe = 0;
                        break;
                    } else {
                        if (currentx < xSet) {
                            speed = 0;
                            turn = 0;
                            strafe = 0.5;
                        } else if (currentx > xSet) {
                            speed = 0;
                            turn = 0;
                            strafe = -0.5;
                        }
                    }
                    backLeft.setPower(-1 * (speed + strafe - turn));
                    frontLeft.setPower(1 * (speed - strafe - turn));
                    backRight.setPower(speed - strafe + turn);
                    frontRight.setPower(speed + strafe + turn);
                }
                while(true) {
                    id20 = null;
                    while(id20 == null){
                        aprilTagWebcam.update();
                        id20 = aprilTagWebcam.getTagBySpecificId(20);
                        aprilTagWebcam.displayDetectionTelemetry(id20);
                        if(backLeft.getPower() > 0){
                            backLeft.setPower(backLeft.getPower()-0.05);
                        }
                        if(frontLeft.getPower() > 0){
                            frontLeft.setPower(frontLeft.getPower()-0.05);
                        }
                        if(backRight.getPower() > 0){
                            backRight.setPower(backRight.getPower()-0.05);
                        }
                        if(frontRight.getPower() > 0){
                            frontRight.setPower(frontRight.getPower()-0.05);
                        }

                    }

                        currenty = id20.ftcPose.y;


                    if (equalsWithinTolerance(currenty, ySet, 2)) {
                        strafe = 0;
                        speed = 0;
                        break;
                    } else {
                        if (currenty < ySet) {
                            speed = -0.5;
                            turn = 0;
                            strafe = 0;
                        } else if (currenty > ySet) {
                            speed = 0.5;
                            turn = 0;
                            strafe = 0;
                        }
                    }
                    backLeft.setPower(-1 * (speed + strafe - turn));
                    frontLeft.setPower(1 * (speed - strafe - turn));
                    backRight.setPower(speed - strafe + turn);
                    frontRight.setPower(speed + strafe + turn);
                }
                break;

            }
        }


        aprilTagWebcam.displayDetectionTelemetry(id20);
        telemetry.update();

        sleep(1);
    }
}


