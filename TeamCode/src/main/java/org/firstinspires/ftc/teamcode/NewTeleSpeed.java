package org.firstinspires.ftc.teamcode;




import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "NewTeleSpeed")
public class NewTeleSpeed extends OpMode {
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
        if(gamepad1.left_bumper){
            hal.changeSpeed(-0.1f);
        }
        if(gamepad1.right_bumper){
            hal.changeSpeed(0.1f);
        }
        hal.addTLM("Flyspeed", hal.getSpeed(), telemetry);

        if(gamepad1.left_trigger > 0 && !gamepad1.dpad_down){
            hal.turnOnIntake();
        } else if(gamepad1.left_trigger == 0 && !gamepad1.dpad_down) {
            hal.turnOffIntake();
        } else{
            hal.backLoad();
        }
        if(gamepad1.right_trigger > 0 && !gamepad1.dpad_down){
            hal.turnOnShooter();
        } else if (gamepad1.right_trigger == 0 && !gamepad1.dpad_down){
            hal.turnOffShooter();
        }
       // telemetry.addData("BL Speed", hal.getBLSpeed());
        hal.updateTLM(telemetry);
        sleep(1);
    }
}
