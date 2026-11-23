package com.smartcar.dashboard;

import com.smartcar.common.Displayable;
import com.smartcar.model.Car;
import com.smartcar.model.Door;
import com.smartcar.model.Tyre;
import com.smartcar.util.StringFormatter;
import com.smartcar.util.SystemLogger;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders the vehicle instrument cluster and central infotainment dashboard.
 * Demonstrates Map/HashMap usage from the Java Collections Framework.
 */
public class DashboardDisplay implements Displayable {
    private final Car car;

    public DashboardDisplay(Car car) {
        this.car = car;
    }

    @Override
    public void displayStatus() {
        // Collect quick telemetry status into a HashMap (Demonstrating Collections)
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("VIN", car.getVin());
        statusMap.put("Central Lock", car.isUnlocked() ? "UNLOCKED" : "LOCKED");
        statusMap.put("Engine", car.getEngine().isRunning() ? "RUNNING (800 RPM)" : "OFF (0 RPM)");
        statusMap.put("Current Gear", car.getCurrentGear().toString());
        statusMap.put("Vehicle Speed", String.format("%.1f km/h (Limit: %.0f km/h)", car.getCurrentSpeed(), car.getSpeedLimit()));
        statusMap.put("Fuel Gauge", String.format("%.1f%% (%.1f L)", car.getFuelTank().getFuelPercentage(), car.getFuelTank().getRemainingLitres()));
        statusMap.put("Driver Seatbelt", car.getDriverSeatbelt().isFastened() ? "FASTENED (SAFE)" : "UNFASTENED (ALERT!)");
        statusMap.put("Headlights", car.getHeadlights().isOn() ? "ON (" + car.getHeadlights().getBeamMode() + ")" : "OFF");
        statusMap.put("Side Mirrors", car.getLeftMirror().isFolded() ? "FOLDED" : "UNFOLDED");
        statusMap.put("Child Lock", car.isChildLockEnabled() ? "ENABLED (Rear Safe)" : "DISABLED");
        statusMap.put("AC System", car.getAirConditioner().isOn() ? "ON (" + car.getAirConditioner().getTemperatureCelsius() + " C)" : "OFF");
        statusMap.put("Heater System", car.getHeater().isOn() ? "ON (" + car.getHeater().getTemperatureCelsius() + " C)" : "OFF");
        statusMap.put("Trunk / Dikki", car.getTrunk().isOpen() ? "OPEN / AJAR" : "CLOSED");
        statusMap.put("Front Wiper", car.getFrontWiper().isOn() ? "ON (" + car.getFrontWiper().getSpeedLevel() + ")" : "OFF");
        statusMap.put("Rear Wiper", car.getRearWiper().isOn() ? "ON (" + car.getRearWiper().getSpeedLevel() + ")" : "OFF");
        statusMap.put("Airbags", car.getAirbag().isDeployed() ? "DEPLOYED!" : "ARMED (STANDBY)");
        statusMap.put("Service Status", String.format("%.0f / 10,000 km", car.getKilometersSinceLastService()));

        System.out.println("\n" + StringFormatter.createHeader("LIVE INSTRUMENT CLUSTER & VEHICLE DASHBOARD"));
        System.out.println("+--------------------------------------------------+");
        for (Map.Entry<String, String> entry : statusMap.entrySet()) {
            System.out.printf("| %-18s : %-27s |%n", entry.getKey(), entry.getValue());
        }
        System.out.println("+--------------------------------------------------+");

        // Doors telemetry
        System.out.println("\n[DOOR SENSORS]:");
        for (Door d : car.getDoors()) {
            System.out.printf("  %-25s -> %s, %s%n",
                    d.getPosition(),
                    d.isLocked() ? "LOCKED" : "UNLOCKED",
                    d.isOpen() ? "OPEN" : "CLOSED");
        }

        // Tyres telemetry
        System.out.println("\n[TYRE PRESSURE TELEMETRY (30-35 PSI Nominal)]:");
        for (Tyre t : car.getTyres()) {
            System.out.printf("  %-15s : %4.1f PSI [%s]%n",
                    t.getPosition(), t.getCurrentPressurePsi(), t.getPressureStatus());
        }

        // Active warnings / alerts banner
        System.out.println("\n[ACTIVE WARNINGS & ALERTS]:");
        boolean anyWarning = false;
        if (!car.getDriverSeatbelt().isFastened()) {
            System.out.println("  * [!] Driver seatbelt unfastened!");
            anyWarning = true;
        }
        if (car.getFuelTank().isLowFuel()) {
            System.out.println("  * [!] Low fuel warning (" + car.getFuelTank().getFuelPercentage() + "%)!");
            anyWarning = true;
        }
        if (car.getCurrentSpeed() > car.getSpeedLimit()) {
            System.out.println("  * [!] Overspeed warning (" + car.getCurrentSpeed() + " km/h)!");
            anyWarning = true;
        }
        for (Tyre t : car.getTyres()) {
            if (!t.isNormalPressure()) {
                System.out.println("  * [!] Tyre issue: " + t.getPosition() + " " + t.getPressureStatus());
                anyWarning = true;
            }
        }
        if (car.getKilometersSinceLastService() >= 10000.0) {
            System.out.println("  * [!] Routine service overdue (" + car.getKilometersSinceLastService() + " km)!");
            anyWarning = true;
        }
        if (car.getAirbag().isDeployed()) {
            System.out.println("  * [!] CRITICAL: Airbags deployed!");
            anyWarning = true;
        }
        if (!anyWarning) {
            System.out.println("  No active warnings. All safety systems nominal.");
        }

        // Display recent events
        SystemLogger.printRecentLogs();
    }
}
