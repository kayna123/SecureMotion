package com.smartcar.comfort;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.util.SystemLogger;

/**
 * Service simulating vehicle acoustic warning horn.
 */
public class HornSystem extends BaseCarSystem {

    public HornSystem() {
        super("Acoustic Horn System");
    }

    /**
     * Honks the car horn and displays audible notification in terminal.
     */
    public void honkHorn() {
        System.out.println("\n=============================");
        System.out.println(">>>     BEEP BEEP!       <<<");
        System.out.println(">>> [Dual-Tone Acoustic Horn] <<<");
        System.out.println("=============================");
        SystemLogger.log("Horn pressed: BEEP BEEP!");
    }
}
