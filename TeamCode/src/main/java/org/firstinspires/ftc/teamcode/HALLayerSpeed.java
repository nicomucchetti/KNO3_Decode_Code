package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//This
//import org.firstinspires.ftc.robotcore.external.hardware;


public class HALLayerSpeed {
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
    private float actualSpeed = 10.0f;
    private float flyspeed = 2200.0f;


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
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheel1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void startTLM(Telemetry tlm){
        tlm.addLine("Started!");
    }
    public void addTLM(String caption, double num, Telemetry tlm){
        tlm.addData(caption, num);
    }
    public void updateTLM(Telemetry tlm){
        tlm.update();
    }
    private void setFlywheel(float flySpeed){
        flywheel1.setVelocity(flySpeed);
        flywheel2.setVelocity(-flySpeed);
    }
    public void changeSpeed(float change){
        actualSpeed += change;
        if(actualSpeed > 10.0f){
            actualSpeed = 10.0f;
        }
        flyspeed = actualSpeed* 250;

    }
    public float getSpeed(){
        return actualSpeed;
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
        setFlywheel(flyspeed);
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
    public void backLoad(){
        //setIntakeHex(1);
        setBottomIntake(-1);
        setTPUflapper(-1);
        setTopintake(-1);
    }
    public void drive(double speed, double strafe, double turn) {
        turn = -turn;
        double blgoalspeed = -speed - strafe - turn;

        double flgoalspeed = speed - strafe + turn;

        double brgoalspeed = speed - strafe - turn;

        double frgoalspeed = speed + strafe - turn;

        if(speed == 0 && strafe == 0 && turn == 0){
            backLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);
            frontLeft.setPower(0);

        } else {
            backLeft.setVelocity(blgoalspeed * 2150);
            backRight.setVelocity(brgoalspeed * 2150);
            frontLeft.setVelocity(flgoalspeed * 2150);
            frontRight.setVelocity(frgoalspeed * 2150);
           //Continue to set these
          //  backLeft.setPower(blgoalspeed);
           // frontLeft.setPower(flgoalspeed);
          //  backRight.setPower(brgoalspeed);
          //  frontRight.setPower(frgoalspeed);
        }
    }
}
