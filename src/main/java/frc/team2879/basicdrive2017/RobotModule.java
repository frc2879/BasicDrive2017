package frc.team2879.basicdrive2017;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.module.ModuleConfig;
import com.frc2879.xboxcontroller.XboxController;
import jaci.openrio.toast.lib.registry.Registrar;


public class RobotModule extends IterativeModule {

    public static Logger logger;

    public static ModuleConfig pref;

    public static String DRIVE_TYPE;
    public static boolean DRIVE_SQUAREDINPUTS;
    public static boolean DRIVE_BRAKE;

    public static int TALONS_LF_ID, TALONS_LB_ID, TALONS_RF_ID, TALONS_RB_ID;

    public static boolean TALONS_LF_INV, TALONS_LB_INV, TALONS_RF_INV, TALONS_RB_INV;

    public CANTalon[] talons;

    public RobotDrive robotDrive;

    public final XboxController driveJoystick = new XboxController(0);

    private double getdoublefromstickname(String name) {
        switch (name.toLowerCase()) {
            case "leftx" : return driveJoystick.leftStick.getX();
            case "lefty" : return driveJoystick.leftStick.getY();
            case "rightx" : return driveJoystick.rightStick.getX();
            case "righty" : return driveJoystick.rightStick.getY();
            default : return 0;
        }
    }

    public static String MECANUMDRIVE_X, MECANUMDRIVE_Y, MECANUMDRIVE_ROT;

    @Override
    public String getModuleName() {
        return "BasicDrive2017";
    }

    @Override
    public String getModuleVersion() {
        return "0.1.0";
    }

    @Override
    public void robotInit() {
        logger = new Logger("BasicDrive2017", Logger.ATTR_DEFAULT);
        //TODO: Module Init

        pref = new ModuleConfig("BasicDrive2017");

        driveJoystick.leftStick.setDeadZone(pref.getDouble("xboxcontroller.deadzones.leftstick", 0.1));
        driveJoystick.rightStick.setDeadZone(pref.getDouble("xboxcontroller.deadzones.rightstick", 0.1));

        DRIVE_SQUAREDINPUTS = pref.getBoolean("drive.squaredinputs", false);
        DRIVE_BRAKE = pref.getBoolean("drive.brake", false);

        TALONS_LF_ID = pref.getInt("talons.left.front.id", 1);
        TALONS_LB_ID = pref.getInt("talons.left.back.id", 2);
        TALONS_RF_ID = pref.getInt("talons.right.front.id", 3);
        TALONS_RB_ID = pref.getInt("talons.right.back.id", 4);

        TALONS_LF_INV = pref.getBoolean("talons.left.front.inverted", false);
        TALONS_LB_INV = pref.getBoolean("talons.left.back.inverted", false);
        TALONS_RF_INV = pref.getBoolean("talons.right.front.inverted", false);
        TALONS_RB_INV = pref.getBoolean("talons.right.back.inverted", false);

        MECANUMDRIVE_X = pref.getString("mecanumdrive.control.x", "leftx");
        MECANUMDRIVE_Y = pref.getString("mecanumdrive.control.y", "lefty");
        MECANUMDRIVE_ROT = pref.getString("mecanumdrive.control.rotation", "rightx");

        talons = new CANTalon[] {
                Registrar.canTalon(TALONS_LF_ID), Registrar.canTalon(TALONS_LB_ID),
                Registrar.canTalon(TALONS_RF_ID), Registrar.canTalon(TALONS_RB_ID)};

        for (CANTalon t: talons) {
            t.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            t.enableBrakeMode(DRIVE_BRAKE);
            t.set(0);
        }

        robotDrive = new RobotDrive(talons[0], talons[1], talons[2], talons[3]);

        robotDrive.setSafetyEnabled(false);
    }

    public void teleopPeriodic() {
        robotDrive.mecanumDrive_Cartesian(getdoublefromstickname(MECANUMDRIVE_X), getdoublefromstickname(MECANUMDRIVE_Y),getdoublefromstickname(MECANUMDRIVE_ROT),0);
    }
}
