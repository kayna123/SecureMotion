package com.smartcar.controller;

import com.smartcar.comfort.*;
import com.smartcar.dashboard.DashboardDisplay;
import com.smartcar.engine.*;
import com.smartcar.exception.CarException;
import com.smartcar.model.Car;
import com.smartcar.maintenance.*;
import com.smartcar.safety.*;
import com.smartcar.security.*;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Controller class coordinating user commands and delegating to individual subsystems.
 * Contains the main menu event loop and handles exceptions gracefully without crashing.
 */
public class CarMenuController {
    private final Car car;

    // Subsystem instances
    private final EngineControlSystem engineControlSystem;
    private final FuelMonitoringSystem fuelMonitoringSystem;
    private final GearShiftSystem gearShiftSystem;
    private final SpeedControlSystem speedControlSystem;
    private final SeatbeltSystem seatbeltSystem;
    private final AirbagSystem airbagSystem;
    private final CollisionDetectionSystem collisionDetectionSystem;
    private final ParkingSensorSystem parkingSensorSystem;
    private final RearCameraSystem rearCameraSystem;
    private final SpeedLimitSystem speedLimitSystem;
    private final ChildLockSystem childLockSystem;
    private final HeadlightControlSystem headlightControlSystem;
    private final MirrorControlSystem mirrorControlSystem;
    private final ClimateControlSystem climateControlSystem;
    private final HornSystem hornSystem;
    private final TrunkControlSystem trunkControlSystem;
    private final WiperControlSystem wiperControlSystem;
    private final DoorLockSystem doorLockSystem;
    private final TyrePressureMonitoringSystem tyrePressureMonitoringSystem;
    private final ServiceReminderSystem serviceReminderSystem;
    private final DashboardDisplay dashboardDisplay;

    public CarMenuController(Car car) {
        this.car = car;

        // Instantiate subsystems sharing the single Car instance
        this.engineControlSystem = new EngineControlSystem(car);
        this.fuelMonitoringSystem = new FuelMonitoringSystem(car);
        this.gearShiftSystem = new GearShiftSystem(car);
        this.speedControlSystem = new SpeedControlSystem(car);
        this.seatbeltSystem = new SeatbeltSystem(car);
        this.airbagSystem = new AirbagSystem(car);
        this.collisionDetectionSystem = new CollisionDetectionSystem(car, airbagSystem);
        this.parkingSensorSystem = new ParkingSensorSystem();
        this.rearCameraSystem = new RearCameraSystem(car);
        this.speedLimitSystem = new SpeedLimitSystem(car);
        this.childLockSystem = new ChildLockSystem(car);
        this.headlightControlSystem = new HeadlightControlSystem(car);
        this.mirrorControlSystem = new MirrorControlSystem(car);
        this.climateControlSystem = new ClimateControlSystem(car);
        this.hornSystem = new HornSystem();
        this.trunkControlSystem = new TrunkControlSystem(car);
        this.wiperControlSystem = new WiperControlSystem(car);
        this.doorLockSystem = new DoorLockSystem(car);
        this.tyrePressureMonitoringSystem = new TyrePressureMonitoringSystem(car);
        this.serviceReminderSystem = new ServiceReminderSystem(car);
        this.dashboardDisplay = new DashboardDisplay(car);
    }

    /**
     * Starts the interactive menu loop.
     */
    public void startMenuLoop() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = InputValidator.readInt("Enter Your Choice: ", 0, 23);

            try {
                switch (choice) {
                    case 1:
                        engineControlSystem.startEngine();
                        break;
                    case 2:
                        engineControlSystem.stopEngine();
                        break;
                    case 3:
                        seatbeltSystem.seatbeltMenuInteractive();
                        break;
                    case 4:
                        gearShiftSystem.changeGearInteractive();
                        break;
                    case 5:
                        headlightControlSystem.headlightMenuInteractive();
                        break;
                    case 6:
                        mirrorControlSystem.mirrorMenuInteractive();
                        break;
                    case 7:
                        doorLockSystem.doorLockMenuInteractive();
                        break;
                    case 8:
                        fuelMonitoringSystem.monitorFuelInteractive();
                        break;
                    case 9:
                        childLockSystem.childLockMenuInteractive();
                        break;
                    case 10:
                        airbagSystem.displayAirbagStatusInteractive();
                        break;
                    case 11:
                        parkingSensorSystem.parkAssistInteractive();
                        break;
                    case 12:
                        climateControlSystem.acMenuInteractive();
                        break;
                    case 13:
                        climateControlSystem.heaterMenuInteractive();
                        break;
                    case 14:
                        hornSystem.honkHorn();
                        break;
                    case 15:
                        collisionDetectionSystem.simulateCollisionInteractive();
                        break;
                    case 16:
                        speedLimitSystem.speedLimitMenuInteractive();
                        break;
                    case 17:
                        tyrePressureMonitoringSystem.checkTyrePressureInteractive();
                        break;
                    case 18:
                        serviceReminderSystem.serviceReminderInteractive();
                        break;
                    case 19:
                        trunkControlSystem.trunkMenuInteractive();
                        break;
                    case 20:
                        wiperControlSystem.frontWiperMenuInteractive();
                        break;
                    case 21:
                        wiperControlSystem.rearWiperMenuInteractive();
                        break;
                    case 22:
                        rearCameraSystem.activateRearCameraInteractive();
                        break;
                    case 23:
                        dashboardDisplay.displayStatus();
                        break;
                    case 0:
                        System.out.println("\nExiting Smart Car Safety and Control System. Drive safely!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid selection! Please choose an option from the menu.");
                }
            } catch (CarException e) {
                // Polymorphic exception handling for all custom car exceptions
                System.out.println("\n[SYSTEM ALERT / EXCEPTION] " + e.getMessage());
                SystemLogger.logAlert(e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[UNEXPECTED ERROR] An unexpected error occurred: " + e.getMessage());
            }

            if (running && choice != 23) {
                InputValidator.pressEnterToContinue();
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n----------- MAIN MENU -----------");
        System.out.println("1.  Start Engine");
        System.out.println("2.  Stop Engine");
        System.out.println("3.  Seatbelt Detection");
        System.out.println("4.  Change Gear");
        System.out.println("5.  Headlight Control");
        System.out.println("6.  Side Mirror Control");
        System.out.println("7.  Door Lock / Unlock");
        System.out.println("8.  Fuel Monitoring");
        System.out.println("9.  Child Lock");
        System.out.println("10. Airbag System");
        System.out.println("11. Parking Sensor");
        System.out.println("12. AC Control");
        System.out.println("13. Heater Control");
        System.out.println("14. Horn");
        System.out.println("15. Collision Detection");
        System.out.println("16. Speed Limit Alert");
        System.out.println("17. Tyre Pressure Monitoring");
        System.out.println("18. Service Reminder");
        System.out.println("19. Trunk Control");
        System.out.println("20. Front Wiper");
        System.out.println("21. Rear Wiper");
        System.out.println("22. Rear Camera Detection");
        System.out.println("23. View Car Status (Dashboard)");
        System.out.println("0.  Exit");
        System.out.println("---------------------------------");
    }
}
