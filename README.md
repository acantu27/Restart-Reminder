# Restart Reminder

<img align="left" src="https://i.imgur.com/axQv2ai.png">

**Restart Reminder** is an application which constructs and sends a simple email to a specified user to instruct them of a restart. The alert is triggered when the system uptime exceeds the specified time values (*default: 3 days*). A secondary notification is triggered (*default: 7 days*) and provides another method of reminding. Either with another email address or the address of another person. 



## Configuration

All the necessary steps to get this project working.

### Email Settings

**Optional:** It is **highly recommended** to register a new email for this application and add it to the address book of the email recipient(s). Reasons: 
 - Hardcoded credentials and passwords
 - Default settings don't offer the best security

Configure email for [less secure apps](https://support.google.com/accounts/answer/6010255?hl=en). This is dependent on service provider.
  - Using Gmail in combination with less secure apps will be the easiest and quickest setup. 
  - It's recommended to use [App Passwords](https://support.google.com/mail/answer/185833) or similar methods.



### Download & Configure

- [Clone or manually download](https://help.github.com/en/articles/cloning-a-repository).

- Modify these values for (Email.java) to connect and send emails. If using TLS and the less secure email connection, then you won't need to change `host` or `port`.

```java
/* Default email credentials and settings */
private String host = "smtp.gmail.com", username = "gmail_username", password = "gmail_password";
private int port = 587;

/* Default email information */
private String emailSender = "gmail_username@gmail.com", emailRecepient = "",
               emailSubject = "Email Subject - Restart Reminder",
               emailBody = "System restart recommended.";
```

- **Optional:** Modify these values for (Schedule.java) to change the schedule. 
  - Otherwise, email reminders will be sent everyday after 3 days with the secondary method starting after 7 days.
  - System is checked and reminders are sent at 6:30 PM.

```java
/* Scheduler values */
private static final long MS_IN_DAY = 24 * 60 * 60 * 1000;  // 24 Hours in MS, Used to repeat everyday
private static final int SCHEDULE_HOUR = 18;                // Schedule Hour (24-hour format e.g. 3PM = 15)
private static final int SCHEDULE_MIN = 30;                 // Schedule Minute (60 minute format)

/* Amount of days until reminder sent */
private static final int NEED_RESTART = 3;                  // Amount of days elapsed to begin reminding
private static final int NEED_IMM_RESTART = 7;              // Amount of days elapsed to alert other user
```



### Installation

Create a JAR.
 - Ex. Build Artifact with Intellij. JAR will be located in `~/out/.../restartreminder/`.

**Optional:** Allow it to boot at startup.
- Windows
  - Drop into `Startup` folder.
- Linux
  - Add to `Startup Applications` with `Gnome Tweak Tooll`.

## Usage

I'd recommend only for personal use. Defintely wouldn't advise using in any business/commerical environment. 

It is possible to use Restart Reminder to send SMS and/or MMS with enough information using [SMS/MMS gateways](https://en.wikipedia.org/wiki/SMS_gateway#Email_clients). Just change recepient to proper domain gateway with the phone number.
  - Can use to SMS/MMS a user as the primary or secondary reminder.
  - Ex. Sending a SMS/MMS to 713-123-4567 with T-Mobile carrier is `emailRecepient = "71312345657@tmomail.net"`.



## TODO

- [ ] Fix all instances where I misspelled 'recipient'.
- [ ] Configure/tweak/test to support [App Passwords](https://support.google.com/mail/answer/185833) by default.
- [ ] Add logging.
- [ ] Add 'Annoy' feature.



## Why!?

1. **For those whom disable auto-restart.** Windows restarts systems during specified times to apply updates. This can cause issues with some applications. Disabling auto-restart fixes the issue but now leaves the system susceptible since updates aren't applied without manually restarting.

2. **Fix all the annoying issues that occur when users don't reboot their system often.** You know the ones...



## Resources

1. [Google - Send email from a printer, scanner, or app](https://support.google.com/a/answer/176600?hl=en)
2. [Google - Sing in using App Passwords](https://support.google.com/mail/answer/185833)
3. [Google - Less secure apps & your Google Account](https://support.google.com/accounts/answer/6010255?hl=en)
4. [Wikipedia - SMS Gateway](https://en.wikipedia.org/wiki/SMS_gateway#Email_clients)
5. [Github - Cloning a Repository](https://help.github.com/en/articles/cloning-a-repository)
