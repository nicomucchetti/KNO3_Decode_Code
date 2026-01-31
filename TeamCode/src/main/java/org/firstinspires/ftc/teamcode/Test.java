package org.firstinspires.ftc.teamcode;



import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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


@TeleOp(name = "Test")
@Disabled
public class Test extends OpMode {
    public static boolean equalsWithinTolerance(double a, double b, double tolerance) {
        return Math.abs(a - b) <= tolerance;
    }
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
    public CRServo bottomIntake;
    public CRServo topintake;
    public double aboutZero = 0.1;
    public DcMotorEx intake;
    public CRServo TPUflapper;
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
        TPUflapper.setPower(-flywheelSpeed);
    }

    public void intake(double intakePower) {
        intake.setPower(-intakePower);
       // TPUflapper.setPower(-intakePower);
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
        bottomIntake = hardwareMap.get(CRServo.class, "bottomintake");
        TPUflapper = hardwareMap.get(CRServo.class, "TPUflapper");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        camS = hardwareMap.get(Servo.class, "cam");

        aprilTagWebcam.init(hardwareMap, telemetry);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reverse backLeft motor direction (mounted opposite)
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);

        flySpeed = 1;
    }

    @Override
    public void loop() {
        speed = dir * gamepad1.right_stick_y * 0.95;
        strafe = gamepad1.right_stick_x * 0.95;
        turn = gamepad1.left_stick_x * 0.95;

        if(gamepad1.a){
            if(dir == 1){
                dir = -1;
                dire = "Shooter";
            } else{
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
            topintake.setPower(-1);
            TPUflapper.setPower(-1);
            bottomIntake.setPower(1);
        } else if(gamepad1.right_trigger == 0 && !gamepad1.dpad_down) {
            fly(0);
            topintake.setPower(0);
            TPUflapper.setPower(0);
            bottomIntake.setPower(0);
        }
        if(gamepad1.dpad_down){
            intake(1);
            topintake.setPower(1);
            bottomIntake.setPower(-1);
            fly(-1);
        }
        if (gamepad1.left_trigger > 0) {
            intake(1);
        } else if (gamepad1.left_trigger == 0 && !gamepad1.dpad_down) {
            intake(0);
        }

//AprilTag Code

        aprilTagWebcam.update();
        AprilTagDetection id20 = aprilTagWebcam.getTagBySpecificId(20);
        aprilTagWebcam.displayDetectionTelemetry(id20);

        //Nico Code
        double blgoalspeed = speed + strafe - turn;
        blgain = 0;
        if (blspeed > blgoalspeed) {
            blgain += 0.04;
        } else if (blspeed < blgoalspeed) {
            blgain -= 0.04;
        }
        double flgoalspeed = speed - strafe - turn;
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
        if (equalsWithinTolerance(gamepad1.left_stick_x, 0, 0.1) && equalsWithinTolerance(gamepad1.left_stick_y, 0, 0.1) && equalsWithinTolerance(gamepad1.right_stick_y, 0, 0.1) && equalsWithinTolerance(gamepad1.right_stick_x, 0, 0.1)) {
            backLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);
            frontRight.setPower(0);
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
        telemetry.addData("BackLeftPosNico", backLeft.getCurrentPosition());
        telemetry.addData("FrontLeftPos", frontLeft.getCurrentPosition());
        telemetry.addData("BackRightPos", backRight.getCurrentPosition());
        telemetry.addData("FrontRightPos", frontRight.getCurrentPosition());
        telemetry.addData("RightTrigger", gamepad1.right_trigger);
        telemetry.addData("RunTime", getRuntime());
        double blcurrentPos = backLeft.getCurrentPosition();
        double bltime = getRuntime();
        blspeed = (blcurrentPos - blpreviousPos) / (bltime - blpreviousTime) / 2400;
        blpreviousPos = blcurrentPos;
        blpreviousTime = bltime;
        telemetry.addData("BLSpeed", blspeed);
        double flcurrentPos = frontLeft.getCurrentPosition();
        double fltime = getRuntime();
        flspeed = (flcurrentPos - flpreviousPos) / (fltime - flpreviousTime) / 2400;
        flpreviousPos = flcurrentPos;
        flpreviousTime = fltime;
        telemetry.addData("FLSpeed", flspeed);

        double brcurrentPos = backRight.getCurrentPosition();
        double brtime = getRuntime();
        brspeed = (brcurrentPos - brpreviousPos) / (brtime - brpreviousTime) / 2400;
        brpreviousPos = brcurrentPos;
        brpreviousTime = brtime;
        telemetry.addData("BRSpeed", brspeed);
        double frcurrentPos = frontRight.getCurrentPosition();
        double frtime = getRuntime();
        frspeed = (frcurrentPos - frpreviousPos) / (frtime - frpreviousTime) / 2400;
        frpreviousPos = frcurrentPos;
        frpreviousTime = frtime;
        telemetry.addData("FRSpeed", frspeed);
        telemetry.addData("2 - 10 Launcher Speed :", flySpeed * 10);
        telemetry.addData("Direction Forward:",dire);
       
        //telemetry.addData("Current Drawn:", "Not operational");
        telemetry.update();

        sleep(1);
    }
}


