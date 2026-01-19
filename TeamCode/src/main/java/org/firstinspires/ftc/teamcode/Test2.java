package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import static android.os.SystemClock.sleep;

import android.util.Size;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;



import android.util.Size;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;


@TeleOp(name = "Test2")
public class Test2 extends OpMode {
    public DcMotor Test1;
    public DcMotor Test2;
    public DcMotor Test3;

    public DcMotor Test4;
    @Override
    public void init() {
        Test1 = hardwareMap.get(DcMotor.class, "test1");
        Test2 = hardwareMap.get(DcMotor.class, "test2");
        Test3 = hardwareMap.get(DcMotor.class, "test3");
        Test4 = hardwareMap.get(DcMotor.class, "test4");
    }
    @Override
    public void loop() {
    Test1.setPower(-1 * gamepad1.left_stick_y);
    Test2.setPower(gamepad1.left_stick_y);
    Test4.setPower(gamepad1.right_stick_y);
    Test3.setPower(gamepad1.right_stick_y);
    }
}