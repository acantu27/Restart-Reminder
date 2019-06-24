package com.alex.restartreminder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * SystemInfo Class
 *
 * Gathers relevant information about the current system. Gets
 * the OS name, days since boot, and boot date.
 */
public class SystemInfo {

    private String currentOS = System.getProperty("os.name").toLowerCase();

    private long uptime = 0;                // System uptime in ms
    private int days = 0;                   // System uptime in days
    private Date startDate;                 // System boot date

    public SystemInfo() {
        getSystemUptime();
    }

    /* Getter methods */
    public int getDays() {
        return days;
    }

    public String getCurrentOS() {
        return currentOS;
    }

    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gathers system uptime using methods dependent on the
     * Operating System. Saves the uptime values as amount of
     * days and milliseconds.
     *
     * Parses from 'net stats srv' to get uptime from Windows.
     * Parses from 'uptime -s' to get uptime from Linux.
     */
    public void getSystemUptime() {

        try {
            if(currentOS.contains("win")) {
                System.out.println("Windows OS identified: " + currentOS);
                Process process = Runtime.getRuntime().exec("net stats srv");
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = in.readLine();
                if(line != null) {
                    if(line.startsWith("Statistics since")) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                                "'Statistics since' MM/dd/yyyy hh:mm:ss a");
                        Date bootDate = simpleDateFormat.parse(line);
                        uptime = System.currentTimeMillis() - bootDate.getTime();
                        days = (int) TimeUnit.MILLISECONDS.toDays(uptime);
                    }
                }
            } else if (currentOS.contains("nux") || currentOS.contains("nix")) {
                System.out.println("Linux OS identified: " + currentOS);
                Process process = Runtime.getRuntime().exec("uptime -s");
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = in.readLine();
                if(line != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date bootDate = simpleDateFormat.parse(line);
                    startDate = bootDate;
                    uptime = System.currentTimeMillis() - bootDate.getTime();
                    days = (int) TimeUnit.MILLISECONDS.toDays(uptime);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

}
