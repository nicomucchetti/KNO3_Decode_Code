package org.firstinspires.ftc.teamcode;




import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name = "NewAuto")
public class NewAuto extends OpMode {
    HALLayerSpeed hal = new HALLayerSpeed();


    @Override
    public void init() {

        hal.initHardware(hardwareMap);
        hal.startTLM(telemetry);
    }

    @Override
    public void loop() {
        double speed = gamepad1.right_stick_y * 0.95;
        double strafe = gamepad1.right_stick_x * 0.95;
        double turn = gamepad1.left_stick_x * 0.95;
        hal.drive(speed, strafe, turn);

        hal.addTLM("Flyspeed", hal.getSpeed(), telemetry);

        hal.drive(-0.25f, 0.0f, 0.0f);
        sleep(1300);
        hal.turnOnIntake();
        hal.turnOnShooter();
        sleep(8000);
        hal.turnOffIntake();
        hal.turnOffShooter();
        hal.drive(0.0f, -1.0f, 0.0f);
        sleep(2000);
        hal.drive(0.0f, 0.0f, 0.0f);
       // telemetry.addData("BL Speed", hal.getBLSpeed());
        hal.updateTLM(telemetry);
        sleep(100000000);

    }
}
