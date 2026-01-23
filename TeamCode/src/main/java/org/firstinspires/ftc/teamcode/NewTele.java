package org.firstinspires.ftc.teamcode;




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
@TeleOp(name = "NewTele")
public class NewTele extends OpMode {
    HALLayer hal = new HALLayer();
    @Override
    public void init() {

        hal.initHardware(hardwareMap);
    }

    @Override
    public void loop() {
        double speed = gamepad1.right_stick_y * 0.95;
        double strafe = gamepad1.right_stick_x * 0.95;
        double turn = gamepad1.left_stick_x * 0.95;
        hal.drive(speed, strafe, turn);
    }
}
