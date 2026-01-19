package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "Main_Auto", group = "Auto")
public class Main_Auto extends LinearOpMode {
    public CRServo br1;
    public CRServo bl1;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor flywheel1; // Expansion Hub
    private DcMotor flywheel2; // Expansion Hub
    private CRServo topintake2; // Expansion Hub
    private CRServo topintake;
    private CRServo intake12;

    public void intake(double intakePower) {
        br1.setPower(intakePower);
        bl1.setPower(-intakePower);
    }
    @Override
    public void runOpMode() {
        br1 = hardwareMap.get(CRServo.class, "br1");
        bl1 = hardwareMap.get(CRServo.class, "intake");
        // Initialize drive motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "fl");
        frontRightMotor = hardwareMap.get(DcMotor.class, "fr");
        backLeftMotor = hardwareMap.get(DcMotor.class, "bl");
        backRightMotor = hardwareMap.get(DcMotor.class, "br");

        // Set zero power behavior for drive motors
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize additional motors
        flywheel1 = hardwareMap.get(DcMotor.class, "flywheel1");
        flywheel1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel2 = hardwareMap.get(DcMotor.class, "flywheel2");
        flywheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topintake2 = hardwareMap.get(CRServo.class, "bottomIntake");
        topintake = hardwareMap.get(CRServo.class, "topintake");
        intake12 = hardwareMap.get(CRServo.class, "TPUflapper");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // Step 1: Forward
            telemetry.addData("Step", "1 - forward");
            telemetry.update();
            frontLeftMotor.setPower(-0.50);
            frontRightMotor.setPower(-0.50);
            backLeftMotor.setPower(0.50);
            backRightMotor.setPower(-0.50);
            sleep(3000);

            // Step 2: Stop all motors
            telemetry.addData("Step", "2 - Stop all motors");
            telemetry.update();
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            flywheel1.setPower(0);
            flywheel2.setPower(0);
            topintake2.setPower(0);
            topintake.setPower(0);
            intake12.setPower(0);

            // Step 3: Start parallel execution
            telemetry.addData("Step", "3 - Running commands in parallel");
            telemetry.update();
            flywheel1.setPower(1.00);
            flywheel2.setPower(-1.00);
            sleep(2000);
            flywheel1.setPower(0);
            flywheel2.setPower(0);
            intake(1);
            // Step 4: Start parallel execution
            telemetry.addData("Step", "4 - Running commands in parallel");
            telemetry.update();
            topintake2.setPower(-1.00);
            topintake.setPower(-1.00);
            intake12.setPower(-1.00);
            flywheel1.setPower(1.00);
            flywheel2.setPower(-1.00);
            sleep(3500);
            topintake2.setPower(0);
            topintake.setPower(0);
            intake12.setPower(0);
            flywheel1.setPower(0);
            flywheel2.setPower(0);
            intake(0);
            // Stop all motors
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            flywheel1.setPower(0);
            flywheel2.setPower(0);
            topintake2.setPower(0);
            topintake.setPower(0);
            intake12.setPower(0);

            telemetry.addData("Status", "Program Complete!");
            telemetry.update();
        }
    }
}
