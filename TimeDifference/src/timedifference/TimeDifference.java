package timedifference;

import java.util.Scanner;

 /**
  *  Gets the difference in time between two given values
  *  Inputs should be in the format "(h):(mm)(am/pm)"
  *  Ex: 1:59am
  *      2:36pm
  *  Inputs can be given as command line arguments. If no command line arguments supplied, program will prompt for them.
  *  Command prompt syntax:
  *     TimeDifference.jar (start_time) (end_time)
  *     Ex: 
  *         TimeDifference.jar 12:50pm 1:40am
  **/
public class TimeDifference {
    public static void main(String args[])
    {
        String start = "";
        String end = "";
        boolean validArgs = args.length == 0 || args.length == 2;
        Scanner scan = new Scanner(System.in);
        if(args.length == 0) // No arguments supplied. Prompt for times.
        {
            System.out.print("Enter start time: ");
            start = scan.nextLine().toLowerCase();
            System.out.print("Enter end time: ");
            end = scan.nextLine().toLowerCase();
        }
        else if(args.length == 2)
        {
            start = args[0];
            end = args[1];
        }
        
        if(validArgs && isValid(start) && isValid(end))
        {
            System.out.println(getDifferenceAsString(start, end));
        }
        else
        {
            System.out.println("Invalid input");
        }
        System.out.print("Enter to continue...");
        scan.nextLine();
    }
    
    // Checks whether the input is valid
    private static boolean isValid(String input)
    {
        if(input.contains(":") && (input.contains("am") || input.contains("pm")))
        {
            try
            {
                String period = input.contains("am") ? "am" : "pm";
                int hour = Integer.parseInt(input.substring(0, input.indexOf(":")));
                int minute = Integer.parseInt(input.substring(input.indexOf(":") + 1, input.indexOf(period)));
                return hour >= 0 && hour <= 12 && minute >= 0 && minute < 60;
            }
            catch(NumberFormatException e)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    // Returns the difference in "0:00" format
    private static String getDifferenceAsString(String start, String end)
    {
        int startMinutes = convertToMinutes(start);
        int endMinutes = convertToMinutes(end);
        if(startMinutes <= endMinutes)
        {
            return convertToString(endMinutes - startMinutes); // Straight difference
        }
        else
        {
            return convertToString(1440 - startMinutes + endMinutes); // Time spills into next day. 1440 is 24:00
        }
    }
    
    // Converts "0:00am/pm" to an int representing the number of minutes since 0:00 (12 AM)
    private static int convertToMinutes(String input)
    {
        String period = input.contains("am") ? "am" : "pm";
        int hour = Integer.parseInt(input.substring(0, input.indexOf(":")));
        int minute = Integer.parseInt(input.substring(input.indexOf(":") + 1, input.indexOf(period)));
        if(hour == 12)
        {
            if(period.equals("am")) // 12 AM is 0:00
            {
                hour -=12;
            }
        }
        else if(period.equals("pm")) // Account for pm hours. 12pm will not be changed.
        {
            hour += 12;
        }
        return (hour * 60) + minute;
    }
    
    // Converts given number of minutes into "0:00" format
    // Ex: 127 minutes becomes 2:07
    private static String convertToString(int input)
    {
        String output = "";
        output += input / 60;
        output += ":";
        if(input % 60 < 10) { output += "0"; }
        output += input % 60;
        return output;
    }
}
