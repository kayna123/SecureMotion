package com.smartcar.comfort;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.AirConditioner;
import com.smartcar.model.Car;
import com.smartcar.model.Heater;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service managing climate control (Air Conditioning and Cabin Heating).
 */
public class ClimateControlSystem extends BaseCarSystem {
    private final Car car;

    public ClimateControlSystem(Car car) {
        super("Dual-Zone Climate Control System");
        this.car = car;
    }

    /**
     * Interactively controls AC system.
     */
    public void acMenuInteractive() {
        AirConditioner ac = car.getAirConditioner();
        System.out.println("\n--- AIR CONDITIONING (AC) SYSTEM ---");
        System.out.println("AC Status:          " + (ac.isOn() ? "[ON]" : "[OFF]"));
        System.out.println("Target Temperature: " + ac.getTemperatureCelsius() + " C");
        System.out.println("Fan Blower Speed:   Level " + ac.getFanSpeed());

        System.out.println("1. Turn AC ON");
        System.out.println("2. Turn AC OFF");
        System.out.println("3. Adjust Temperature (16 C - 30 C)");
        System.out.println("4. Adjust Fan Speed (1 - 5)");
        System.out.println("5. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 5);
        switch (choice) {
            case 1:
                ac.setOn(true);
                // Turning AC on turns off conflicting heater
                if (car.getHeater().isOn()) {
                    car.getHeater().setOn(false);
                    System.out.println("[CLIMATE]: Heater automatically turned OFF to prevent conflict.");
                }
                this.active = true;
                System.out.println("[AC]: Compressor engaged. Cooling air flowing.");
                SystemLogger.log("AC turned ON.");
                break;
            case 2:
                ac.setOn(false);
                System.out.println("[AC]: Air conditioner turned OFF.");
                SystemLogger.log("AC turned OFF.");
                break;
            case 3:
                int temp = InputValidator.readInt("Set AC Temperature (C) [16 - 30]: ", 16, 30);
                ac.setTemperatureCelsius(temp);
                System.out.println("[AC]: Temperature calibrated to " + temp + " C.");
                break;
            case 4:
                int fan = InputValidator.readInt("Set Blower Speed [1 - 5]: ", 1, 5);
                ac.setFanSpeed(fan);
                System.out.println("[AC]: Fan speed set to level " + fan + ".");
                break;
            case 5:
                break;
        }
    }

    /**
     * Interactively controls Cabin Heater.
     */
    public void heaterMenuInteractive() {
        Heater heater = car.getHeater();
        System.out.println("\n--- CABIN HEATER SYSTEM ---");
        System.out.println("Heater Status:      " + (heater.isOn() ? "[ON]" : "[OFF]"));
        System.out.println("Target Temperature: " + heater.getTemperatureCelsius() + " C");

        System.out.println("1. Turn Heater ON");
        System.out.println("2. Turn Heater OFF");
        System.out.println("3. Adjust Heater Temperature (16 C - 30 C)");
        System.out.println("4. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 4);
        switch (choice) {
            case 1:
                heater.setOn(true);
                // Turning Heater on turns off conflicting AC
                if (car.getAirConditioner().isOn()) {
                    car.getAirConditioner().setOn(false);
                    System.out.println("[CLIMATE]: AC automatically turned OFF to prevent conflict.");
                }
                System.out.println("[HEATER]: Ceramic heating core energized. Warm air flowing.");
                SystemLogger.log("Cabin heater turned ON.");
                break;
            case 2:
                heater.setOn(false);
                System.out.println("[HEATER]: Heater turned OFF.");
                SystemLogger.log("Cabin heater turned OFF.");
                break;
            case 3:
                int temp = InputValidator.readInt("Set Heater Temperature (C) [16 - 30]: ", 16, 30);
                heater.setTemperatureCelsius(temp);
                System.out.println("[HEATER]: Heating set to " + temp + " C.");
                break;
            case 4:
                break;
        }
    }
}
