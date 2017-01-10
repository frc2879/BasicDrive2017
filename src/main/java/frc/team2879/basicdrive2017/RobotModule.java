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

    public static int TALONS_LF, TALONS_LB, TALONS_RF, TALONS_RB;

    public CANTalon[] talons;

    public RobotDrive robotDrive;

    public final XboxController driveJoystick = new XboxController(0);

    @Override
    public String getModuleName() {
        return "BasicDrive2017";
    }

    @Override
    public String getModuleVersion() {
        return "0.0.1";
    }

    @Override
    public void robotInit() {
        logger = new Logger("BasicDrive2017", Logger.ATTR_DEFAULT);
        //TODO: Module Init

        pref = new ModuleConfig("BasicDrive2017");

        DRIVE_SQUAREDINPUTS = pref.getBoolean("drive.squaredinputs", false);
        DRIVE_BRAKE = pref.getBoolean("drive.brake", false);

        TALONS_LF = pref.getInt("talons.left.front", 1);
        TALONS_LB = pref.getInt("talons.left.back", 2);
        TALONS_RF = pref.getInt("talons.right.front", 3);
        TALONS_RB = pref.getInt("talons.right.back", 4);

        talons = new CANTalon[] {
                Registrar.canTalon(TALONS_LF), Registrar.canTalon(TALONS_LB),
                Registrar.canTalon(TALONS_RF), Registrar.canTalon(TALONS_RB)};

        for (CANTalon t: talons) {
            t.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            t.enableBrakeMode(DRIVE_BRAKE);
            t.set(0);
        }

        robotDrive = new RobotDrive(talons[0], talons[1], talons[2], talons[3]);

        robotDrive.setSafetyEnabled(false);
    }

    public void teleopPeriodic() {
        robotDrive.mecanumDrive_Cartesian(driveJoystick.leftStick.getX(), driveJoystick.leftStick.getY(), driveJoystick.rightStick.getX(),0);
    }
}
