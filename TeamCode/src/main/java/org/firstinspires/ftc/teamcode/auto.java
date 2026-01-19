package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous(name = "AutoTest")
public class auto extends OpMode {

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
    public CRServo topintake;
    public CRServo br1;
    public CRServo bl1;
    public CRServo intake12;
    public double speed;
    public double strafe;
    public double turn;
    public double flySpeed;
    public Servo camS;
    public Camera cam;
    public double pause = 0;
    public int dir = 1;
    public String dire = "Intake";
    AprilTagWebcam aprilTagWebcam  = new AprilTagWebcam();

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
        backRight = hardwareMap.get(DcMotorEx.class, "1");
        backLeft = hardwareMap.get(DcMotorEx.class, "2");
        frontRight = hardwareMap.get(DcMotorEx.class, "3");
        frontLeft = hardwareMap.get(DcMotorEx.class, "4");
        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");
        topintake = hardwareMap.get(CRServo.class, "topintake");
        topintake2 = hardwareMap.get(CRServo.class, "bottomIntake");
        intake12 = hardwareMap.get(CRServo.class, "TPUflapper");
        br1 = hardwareMap.get(CRServo.class, "br1");
        bl1 = hardwareMap.get(CRServo.class, "intake");
        camS = hardwareMap.get(Servo.class, "cam");

        aprilTagWebcam.init(hardwareMap, telemetry);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flySpeed = 1;
    }

    @Override
    public void loop() {

        resetRuntime();
        while(getRuntime() < 2.4){
            speed = -1.0;
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
        }
        resetRuntime();
        while (getRuntime() < 10) {
            intake(1);
            fly(flySpeed);
            topintake.setPower(-1);
            topintake2.setPower(-1);
        }
        fly(0);
        topintake.setPower(0);
        topintake2.setPower(0);
        intake(0);
        resetRuntime();
        while(getRuntime() < 0.5){
            speed = 0;
            strafe = 1;
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

//cam servo code



            backLeft.setPower(blgoalspeed + blgain);
            frontLeft.setPower(flgoalspeed + flgain);
            backRight.setPower(brgoalspeed + brgain);
            frontRight.setPower(frgoalspeed + frgain);
            //if no controler input - no wheel movement

        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);
        while(true){

        }
    }
}
