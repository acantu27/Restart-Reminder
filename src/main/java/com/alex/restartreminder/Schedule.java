package com.alex.restartreminder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>Restart Reminder</h1>
 * <p><b>Restart Reminder</b> is an application which constructs and
 * sends a simple, singlepart email (RFC822) to a specified user to instruct
 * them of a restart. The alert is triggered when the system uptime
 * exceeds the specified time values (default is 3 days). Another user
 * can be notified when a second time value (default is 7 days) is met.</p>
 *
 * @author  Alex Cantu
 * @version 1.0
 *
 */
public class Schedule extends TimerTask {

    /* Scheduler values */
    private static final long MS_IN_DAY = 24 * 60 * 60 * 1000;      // 24 Hours in MS, Used to repeat everyday
    private static final int SCHEDULE_HOUR = 14;                     // Schedule Hour (24-hour format e.g. 3PM = 15)
    private static final int SCHEDULE_MIN = 24;                     // Schedule Minute (60 minute format)

    /* Amount of days until reminder sent */
    private static final int NEED_RESTART = 0;                      // Amount of days elapsed to begin reminding
    private static final int NEED_IMM_RESTART = 7;                  // Amount of days elapsed to alert other user

    /**
     * Returns a Calendar object calculated with the value of
     * tomorrow at the specified time. Used to set the time
     * when the scheduler begins to run.
     *
     * @return result object sent to tomorrow at certain time
     */
    private static Date getNextDay() {
        Calendar nextDay = new GregorianCalendar();
        /* UNCOMMENT WHEN DONE TESTING!!! */
        nextDay.add(Calendar.DATE, 1);
        nextDay.set(Calendar.HOUR_OF_DAY, SCHEDULE_HOUR);
        nextDay.set(Calendar.MINUTE, SCHEDULE_MIN);

        return nextDay.getTime();
    }

    /**
     * Constructs and sends email using Email object. Modifies the default
     * email body to include more relevant and specific information.
     * @param info  object provides days fo uptime and date of boot
     */
    private static void sendReminder(SystemInfo info) {
        System.out.println("Computer has NOT been restarted in " + NEED_RESTART + " or more days.");
        Email reminderEmail = new Email();
        String emailBody = "It has been " + info.getDays() + " days since your last reboot.\n" +
                "Please reboot at your earliest convenience.\n" +
                "Computer was last restarted at " + info.getStartDate();
        reminderEmail.setEmailBody(emailBody);
        reminderEmail.sendEmail();
    }

    /**
     * Constructs and sends an email using Email object. Modifies the default
     * email body, subject, and recepient to provide more relevant information.
     *
     * Provides a method of alerting another user or trying an another
     * method of communication when uptime gets too high.
     *
     * @param info object for os name and boot date
     */
    private static void sendReminderUser(SystemInfo info) {
        System.out.println("Computer has NOT been restarted in " + NEED_IMM_RESTART + " or more days.");
        Email secondEmail = new Email();
        secondEmail.setEmailRecepient("SECOND_RECIPIENT_EMAIL_ADDRESS");
        secondEmail.setEmailSubject("IMPORTANT: " + info.getCurrentOS() + " Requires a Restart!");
        String newBody = "The specified computer needs to be restarted.\n" +
                "It has been " + info.getDays() + " since it has been restarted.\n" +
                "Computer was last restarted at " + info.getStartDate();
        secondEmail.setEmailBody(newBody);
        secondEmail.sendEmail();
    }

    /**
     * Checks uptime against elapsed days to determine when to send a
     * reminder email and when to trigger the secondary reminder.
     */
    private static void checkSystem() {
        SystemInfo sysInfo = new SystemInfo();

        if(sysInfo.getDays() >= NEED_IMM_RESTART) {
            System.out.println("Execute function alert user");
            sendReminderUser(sysInfo);
        }

        if(sysInfo.getDays() >= NEED_RESTART) {
            System.out.println("Execute reminder");
            sendReminder(sysInfo);
        }
        else
            System.out.println("System isn't required to be restarted at this time.");

    }

    public void run() {
        checkSystem();
    }

    public static void main(String[] args) {

        System.out.println("Starting Restart Reminder... " + new Date());

        /* Check system uptime at start */
        Schedule firstRun = new Schedule();
        firstRun.checkSystem();

        TimerTask sendEmail = new Schedule();
        Timer timer = new Timer();

        /* Schedule to run everyday after tomorrows specified date*/
        timer.scheduleAtFixedRate(sendEmail, getNextDay(), MS_IN_DAY);
    }
}
