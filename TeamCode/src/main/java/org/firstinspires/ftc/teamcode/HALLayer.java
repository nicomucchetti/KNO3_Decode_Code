package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
//This
//import org.firstinspires.ftc.robotcore.external.hardware;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
public class HALLayer {
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private double blpreviousPos, blpreviousTime, blspeed, blgain = 0;
    private double flpreviousPos, flpreviousTime, flspeed, flgain = 0;
    private double brpreviousPos, brpreviousTime, brspeed, brgain = 0;
    private double frpreviousPos, frpreviousTime, frspeed, frgain = 0;
    private DcMotorEx frontRight;
    private DcMotorEx intake;
    private CRServo bottomIntake;
    private CRServo TPUflapper;
    private CRServo topintake;
    private DcMotorEx flywheel1;
    private DcMotorEx flywheel2;


    public void initHardware(HardwareMap hardwareMap){

        backRight = hardwareMap.get(DcMotorEx.class, "br");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        bottomIntake = hardwareMap.get(CRServo.class, "bottomintake");
        TPUflapper = hardwareMap.get(CRServo.class, "TPUflapper");
        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");
        topintake = hardwareMap.get(CRServo.class, "topintake");
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    private void setFlywheel(float flySpeed){
        flywheel1.setPower(flySpeed);
        flywheel2.setPower(-flySpeed);
    }
    private void setTopintake(float speed){
        topintake.setPower(-speed);
    }
    private void setIntakeHex(float intakeSpeed){
        intake.setPower(-intakeSpeed);
    }
    private void setBottomIntake(float intakeSpeed){
        bottomIntake.setPower(intakeSpeed);
    }
    private void setTPUflapper(float intakeSpeed){
        TPUflapper.setPower(-intakeSpeed);
    }
    public void turnOffShooter(){
        setTopintake(0);
        setFlywheel(0);
    }
    public void turnOnShooter(){
        setTopintake(1);
        setFlywheel(1);
    }
    public void turnOffIntake(){
        setIntakeHex(0);
        setBottomIntake(0);
        setTPUflapper(0);
    }
    public void turnOnIntake(){
        setIntakeHex(1);
        setBottomIntake(1);
        setTPUflapper(1);
    }
    public void drive(double speed, double strafe, double turn) {
        turn = -turn;
        double blgoalspeed = -speed - strafe - turn;
        blgain = 0;
        if (blspeed > blgoalspeed) {
            blgain += 0.04;
        } else if (blspeed < blgoalspeed) {
            blgain -= 0.04;
        }
        double flgoalspeed = speed - strafe + turn;
        flgain = 0;
        if (flspeed / 2400 > flgoalspeed) {
            flgain += 0.04;
        } else if (flspeed / 2400 < flgoalspeed) {
            flgain -= 0.04;
        }
        double brgoalspeed = speed - strafe - turn;
        brgain = 0;
        if (brspeed > brgoalspeed) {
            brgain += 0.04;
        } else if (brspeed < brgoalspeed) {
            brgain -= 0.04;
        }
        double frgoalspeed = speed + strafe - turn;
        frgain = 0;
        if (frspeed / 2400 > frgoalspeed) {
            frgain += 0.04;
        } else if (frspeed / 2400 < frgoalspeed) {
            frgain -= 0.04;
        }
        if(speed == 0 && strafe == 0 && turn == 0){
            backLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);
            frontLeft.setPower(0);

        } else {
            backLeft.setPower(blgoalspeed + blgain);
            frontLeft.setPower(flgoalspeed + flgain);
            backRight.setPower(brgoalspeed + brgain);
            frontRight.setPower(frgoalspeed + frgain);
        }
    }
}
