package com.nicom.processing.processors.forth.utils;


import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.TreeMap;


import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import com.nicom.processing.processors.forth.exceptions.LineIsEmptyException;

public class Utilities {

private static final Logger logger = Logger.getLogger("Utilities");


public static String removeUnwantedSpaces(String line) throws LineIsEmptyException {
    if(isEmptyString(line)){
        throw new LineIsEmptyException();
    }
    String str = line;
    str = removeDuplicateSpaces(str);
    str = removeFirstAndLastSpace(str);
    return str;
}

private static String removeDuplicateSpaces(String line) throws LineIsEmptyException {
    if(isEmptyString(line)){
        throw new LineIsEmptyException();
    }
    String str = line.replaceAll("\\s+", " ");

    return str;

}

private static String removeFirstAndLastSpace(String line) throws LineIsEmptyException {
    if(isEmptyString(line)){
        throw new LineIsEmptyException();
    }
    String str = line;
    str = str.replaceFirst("^ *", "");
    if (str.charAt(str.length() - 1) == ' ') {
        str=str.substring(0,str.length() - 1);
    }
    return str;
}

public static String[] sortStringArray(String[] unsortedArray) {
    String[] sortedArray = new String[unsortedArray.length];
    TreeMap<String, String> treeMap = new TreeMap<>();
    for (String element : unsortedArray) {
        treeMap.put(element, element);
    }
    Collection<String> sortedList = treeMap.values();
    int count = 0;
    for (String element : sortedList) {
        sortedArray[count] = element;
        count++;
    }
    return sortedArray;
}

public static String sortVerbNamesInString(String names) {

    String verbs = names.toString();
    String[] verbNames = verbs.split(" ");
    String[] sortedVerbNames = Utilities.sortStringArray(verbNames);
    StringBuilder result = new StringBuilder();
    int loop = 0;
    for (String verbName : sortedVerbNames) {
        if (loop > 0) {
            result.append(" ");
        }
        result.append(verbName);
        loop++;
    }
    return result.toString();
}


public static boolean stringIsEmpty(String str) {
    return str==null || str.length()==0;
}

public static boolean isEmptyString(String str){
    return str==null || str.length()==0;
}

public static boolean isNumeric(String verb) {
    NumberFormat formatter = NumberFormat.getInstance();
    ParsePosition pos = new ParsePosition(0);
    formatter.parse(verb, pos);
    return verb.length() == pos.getIndex();
}

public static boolean isInteger(String val) {
    boolean retval = false;
    try {
        //int i = new Integer(val).parseInt(val);
        int i = Integer.parseInt(val);
        retval = true;
    } catch (Exception e) {
        // this is not an error but a test
    }
    return retval;
}


public static boolean isDouble(String val) {
    boolean retval = false;
    try {
        //int i = new Integer(val).parseInt(val);
        double d = Double.parseDouble(val);
        retval = true;
    } catch (Exception e) {
        // this is not an error but a test
    }

    return retval;
}
//
//public static String getComputerIpAddress() throws UnknownHostException{
//    String ip = InetAddress.getLocalHost().getHostAddress();
//    return ip;
//}
//
//public static String getComputerName() throws UnknownHostException{
//    String computerName = InetAddress.getLocalHost().getHostName();
//    return computerName;
//}
//
//public static String getIpAddressOfHosst(String hostUrl) throws UnknownHostException{
//    String ip = InetAddress.getByName(hostUrl).getHostAddress();
//    return ip;
//}



public static String now(String s) {
    SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
    Date date = new Date();
    GregorianCalendar gregoriancalendar = new GregorianCalendar();
    gregoriancalendar.setTime(date);
    Date date1 = gregoriancalendar.getTime();
    String s1 = simpledateformat.format(date1);
    return s1;
}

public static String now(String s, Date date) {
    SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
    String s1 = simpledateformat.format(date);
    return s1;
}

public static long GetTimeInMilliSeconds() { // It returns millseconds from Jan 1, 1970.
    Calendar now = Calendar.getInstance();
    return now.getTimeInMillis();
}

public static String date() { // Banking Y2K format
    return now("yyyy-MM-dd");
}

public static String dateBritish() {
    return now("dd/MM/yyyy");
}

public static String day() {
    return now("dd");
}

public static String month() {
    return now("MM");
}

public static String year() {
    return now("yyyy");
}

public static String dateUSA() {
    return now("MM/dd/yyyy");
}
public static String dateY2k() { return now("yyyy-MM-dd"); }


public static String time() {
    return now("HH:mm:ss");
}

public static String now() {
    return now("yyyy-MM-dd HH:mm:ss");
}

public static String timeStamp() {
    return now("yyyyMMddHHmmss");
}

public static String dateBeginningCurrentMonth() {
    String s = (new StringBuilder()).append(now("yyyy-MM-")).append("01").toString();
    return s;
}


public char toAscii(int c) {
    char ch = (char) c;

    return ch;
}

public void hundredMsDelay(int val) { // 10   =   1 second
    int delay = 100 * val;
    try {
        //  add to unit test System.out.println(System.currentTimeMillis());
        Thread.sleep(delay);
    } catch (Exception e) {
    }
}


public StringBuffer readFile(String fileIoFileName) throws Exception {
    int eofCharacter = -1;
    char c;
    InputStream in;
    String st;

    StringBuffer fileContentBuffer = new StringBuffer();
    //boolean fileOpperationFailed=false;

    try {
        System.out.println("Reading File " + fileIoFileName);
        URL newURL = this.getClass().getClassLoader().getResource(fileIoFileName);
        System.out.println("url : " + newURL);
        in = newURL.openStream();

        do {
            c = (char) in.read();
            fileContentBuffer.append(c);
        } while (c != eofCharacter); //EOF -1
        in.close();
    } catch (Exception e) {
        logger.severe("Failed to read file:" + fileIoFileName);
        throw new Exception("Failed to read file:" + fileIoFileName, e);

    }

    return fileContentBuffer;
}

public void writeFile(String fileIoFileName, StringBuffer fileContentBuffer) throws Exception {
    String str = "";

    try {

        System.out.println("writing File " + fileIoFileName);
        FileWriter out = new FileWriter(fileIoFileName);

        int len = 0;

        //fileContentBuffer.append(eofCharacter);
        if (fileContentBuffer != null) {
            char[] buf = new char[fileContentBuffer.length()];
            len = fileContentBuffer.length();
            for (int j = 0; j < len; j++) {
                buf[j] = fileContentBuffer.charAt(j);
            }

            out.write(buf, 0, fileContentBuffer.length());
        }

        out.flush();
        out.close();
    } catch (Exception e) {
        logger.severe("Failed to write file:" + fileIoFileName);
        throw new Exception("Failed to write file:" + fileIoFileName, e);
    }
}

public void appendFile(String fileIoFileName, StringBuffer fileContentBuffer) throws Exception {
    String str = "";
    char[] charBuffer = null;

    try {
        System.out.println("writing File " + fileIoFileName);
        FileWriter out = new FileWriter(fileIoFileName, true);

        int len = 0;
        //fileContentBuffer.append(eofCharacter);

        if (fileContentBuffer != null) {
            char[] buf = new char[fileContentBuffer.length()];
            charBuffer = buf;
            len = fileContentBuffer.length();
            for (int j = 0; j < len; j++) {
                buf[j] = fileContentBuffer.charAt(j);
            }
            out.write(buf, 0, fileContentBuffer.length());
        }

        out.flush();
        out.close();
    } catch (Exception e) {
        logger.severe("Failed to write append:" + fileIoFileName);
        throw new Exception("Failed to append file:" + fileIoFileName, e);
    }
}


void deleteFile(File newFile) {
    if (newFile.delete()) {
        System.out.println(newFile.getName() + " is deleted!");
    } else {
        System.out.println("Delete operation is failed.");
    }

}


}
