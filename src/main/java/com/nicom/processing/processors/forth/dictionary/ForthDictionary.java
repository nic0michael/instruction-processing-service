package com.nicom.processing.processors.forth.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.nicom.processing.processors.forth.exceptions.VerbNotInDictionaryException;
import com.nicom.processing.processors.forth.utils.Utilities;

import java.util.List;

public class ForthDictionary {


    private static Map<String, String> verbDefinitions = new HashMap<String, String>();
    private static Map<String, String> verbCompiledDefinitions = new HashMap<String, String>();
    private static Map<String, String> verbDescriptions = new HashMap<String, String>();

    private static Map<String, String> systemVerbDefinitions = new HashMap<String, String>();
    private static Map<String, String> systemVerbCompiledDefinitions = new HashMap<String, String>();
    private static Map<String, String> systemVerbDescriptions = new HashMap<String, String>();

    private static List<Verb> verbHistory = new ArrayList<Verb>();
    public static final ForthDictionary INSTANCE = new ForthDictionary();

    private ForthDictionary() {
        loadDictionary();
    }

    public void clearDictionary() {
        verbDefinitions = new HashMap<>();
        verbCompiledDefinitions = new HashMap<>();
        verbDescriptions = new HashMap<>();
        verbHistory = new ArrayList<Verb>();
    }

    public void addVerbToDictionary(Verb verb) {
        String name = verb.getName().toUpperCase();
        String description;
        String definition = verbDefinitions.get(name);
        String compiledDefinition = verbCompiledDefinitions.get(name);

        if (definition == null) {
            verbDefinitions.put(name, verb.getDefinition());
            verbCompiledDefinitions.put(name, verb.getCompiledDefinition());
            description = verb.getDescription();
            if (description == null) {
                description = verb.getDefinition();
            }
            verbDescriptions.put(name, description);
        } else {
            if (verbIsNotSystemVerb(name)) {
                Verb previousVerb = new Verb(name, definition, compiledDefinition);
                verbHistory.add(previousVerb);
            }

            verbDefinitions.remove(name);
            verbCompiledDefinitions.remove(name);
            verbDescriptions.remove(name);

            description = verb.getDescription();
            if (description == null) {
                description = verb.getDefinition();
            }
            verbDescriptions.put(name, description);
            verbDefinitions.put(name, verb.getDefinition());
            verbCompiledDefinitions.put(name, verb.getCompiledDefinition());
        }
    }

    public void removeFromDictionary(Verb verb) {
        String name = verb.getName().toUpperCase();
        verbDefinitions.remove(name);
        verbCompiledDefinitions.remove(name);
        verbDescriptions.remove(name);

        Verb previousVerb = findPreviousVerb(verb);
        removePreviousVerbFromHistory(verb);
        if (previousVerb != null) {
            addVerbToDictionary(previousVerb);
        }
    }

    public String getDefinition(String verbName) throws VerbNotInDictionaryException {
        String definition = verbDefinitions.get(verbName.toUpperCase());
        if (definition == null) {
            definition = systemVerbDefinitions.get(verbName.toUpperCase());
        }
        if (definition == null) {
            throw new VerbNotInDictionaryException(verbName);
        }
        return definition;
    }

    public String getCompiledDefinition(String verbName) throws VerbNotInDictionaryException {
        String compiledDefinition = verbCompiledDefinitions.get(verbName.toUpperCase());
        if (compiledDefinition == null) {
            compiledDefinition = systemVerbCompiledDefinitions.get(verbName.toUpperCase());
        }
        if (compiledDefinition == null) {
            throw new VerbNotInDictionaryException(verbName);
        }
        return compiledDefinition;
    }

    public int size() {
        return verbDefinitions.size();
    }

    public String showVerbs() {
        StringBuilder result = new StringBuilder();
        int loop = 0;
        for (Map.Entry<String, String> entry : systemVerbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (loop > 0) {
                result.append(" ");
            }
            result.append(key);
            loop++;
        }
        for (Map.Entry<String, String> entry : verbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (loop > 0) {
                result.append(" ");
            }
            result.append(key);
            loop++;
        }
        String verbs = result.toString();
        String sortedNames = Utilities.sortVerbNamesInString(verbs);
        return sortedNames;
    }

    public String showVerbDetails() throws VerbNotInDictionaryException {
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (Map.Entry<String, String> entry : verbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (count > 0) {
                result.append("\n");
            }
            result.append("Verb: ");
            result.append(key);
            result.append(" Definition: ");
            result.append(value);
            count++;
        }
        
        return result.toString();
    }

    public void addSystemVerbs(String systemVerbNames) {
        String[] verbNames = systemVerbNames.split(" ");
        for (String verbName : verbNames) {
            Verb verb = new Verb(verbName, verbName, verbName);
            addSystemVerbToSystemDictionary(verb);
        }
    }

    private Verb findPreviousVerb(Verb verbToFind) {
        Verb verbFound = null;
        String uuid = verbToFind.getUuid();
        for (Verb verb : verbHistory) {
            if (verb.equals(verbToFind.getName().toUpperCase()) && !verb.guidEquals(uuid)) {
                verbFound = verb;
            }
        }
        return verbFound;
    }

    private boolean verbIsNotSystemVerb(String name) {
        String definition = systemVerbDefinitions.get(name.toUpperCase());
        return definition == null;
    }

    private void removePreviousVerbFromHistory(Verb verbToFind) {
        int foundIndex = -1;
        Verb verbFound = null;
        for (int i = verbHistory.size() - 1; i >= 0; i--) {
            Verb verb = verbHistory.get(i);
            if (verb.equals(verb) && !verb.equals(verbToFind.getName(), verbToFind.getDefinition(), verbToFind.getCompiledDefinition())) {
                foundIndex = i;
            }
        }
        if (foundIndex > -1) {
            verbHistory.remove(foundIndex);
        }
    }

    public void addSystemVerbToSystemDictionary(Verb verb) {
        String name = verb.getName().toUpperCase();
        String description = "SYSTEM VERB";
        systemVerbDefinitions.put(name, verb.getDefinition());
        systemVerbCompiledDefinitions.put(name, verb.getCompiledDefinition());
        systemVerbDescriptions.put(name, description);
    }

    private void loadDictionary() {

        addSystemVerbs(": ;");
        addSystemVerbs("+ - * /");
        addSystemVerbs("=");
        addSystemVerbs("0=");
        addSystemVerbs("0<");
        addSystemVerbs(">");
        addSystemVerbs("<");
        addSystemVerbs("<>");
        addSystemVerbs("=>");
        addSystemVerbs("<=");
        addSystemVerbs("NOT");
        addSystemVerbs("+");
        addSystemVerbs("-");
        addSystemVerbs("*");
        addSystemVerbs("/");
        addSystemVerbs("D+");
        addSystemVerbs("D-");
        addSystemVerbs("D*");
        addSystemVerbs("D/");
        addSystemVerbs("D>");
        addSystemVerbs("D<");
        addSystemVerbs("D=");
        addSystemVerbs("D0=");
        addSystemVerbs("D0<");

        addSystemVerbs(".");// POP AND PRINT TOS
        

        addSystemVerbs("1+");
        addSystemVerbs("1-");
        addSystemVerbs("2+");
        addSystemVerbs("2*");//SHIFT LEFT
        addSystemVerbs("2/");//SHIFT RIGHT
        addSystemVerbs("2-");
        addSystemVerbs("2+");
        addSystemVerbs(">>");//ADVANCED FEATURE :  BORROWED FROM JAVA BIT SHIFT
        addSystemVerbs(">>>");//ADVANCED FEATURE :  BORROWED FROM JAVA BIT SHIFT
        addSystemVerbs("<<");//ADVANCED FEATURE :  BORROWEWD FROM JAVA BIT SHIFT
        addSystemVerbs("!");// STORE     TO VARIABLE
        addSystemVerbs("+!");// APPEND TO VARIABLE
        addSystemVerbs("-!");// APPEND TO VARIABLE
        addSystemVerbs("$!");// STORE TO STRING VARIABLE
        addSystemVerbs("$L");// START OF STRING (THE STRING ENDS WITH A ")
        addSystemVerbs("@");// FETCH FROM NUMERIC VARIABLE
        addSystemVerbs("?");// PRINT NUMERIC VARIABLE  
        
        addSystemVerbs(".COMPUTERNAME");//Utilities.getComputerIpAddress()
        addSystemVerbs(".CNAME");//Utilities.getComputerIpAddress()
        addSystemVerbs(".COMPUTERIP");//Utilities.getComputerName()
        addSystemVerbs(".CIP");//Utilities.getComputerName()

        addSystemVerbs(".D");//ADVANCED FEATURE : LIST THE DICTIONARY
        addSystemVerbs(".DATE");// ADVANCED FEATURE : PRINT DATE IN Y2K BANKING FORMAT (Y2K COMPLIANT DATE)
        addSystemVerbs(".DATEBRITISH");//ADVANCED FEATURE :  PRINT DATE BRITISH FORMAT  (Y2K COMPLIANT DATE)
        addSystemVerbs(".DATESF");// ADVANCED FEATURE : PRINT Y2K COMPLIANT DATE USING FORMAT STRING ON STACK TOP
        addSystemVerbs(".DATESIMPLEFORMAT");// ADVANCED FEATURE : PRINT Y2K COMPLIANT DATE USING FORMAT STRING ON STACK TOP YYYY-MM-DD HH:MM:SS
        addSystemVerbs(".DATETIME");//ADVANCED FEATURE :  PRINT DATE AND TIME  (Y2K COMPLIANT DATE)
        addSystemVerbs(".DATEUSA");//ADVANCED FEATURE :  PRINT DATE USA FORMAT  (Y2K COMPLIANT DATE)
        addSystemVerbs(".DAY");// PRINTS THE DAY
        addSystemVerbs(".DICT");//ADVANCED FEATURE : LIST THE DICTIONARY
        addSystemVerbs(".DICTDEF");//ADVANCED FEATURE : LIST ALL THE DEFINITIONS OF ALL THE VERBS
        addSystemVerbs(".ERRORLOG");// PRINT ERROR LOG
        addSystemVerbs(".H");// HELP
        addSystemVerbs(".HASHMAPVARIABLES");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE HASHMAP VARIABLES
        addSystemVerbs(".HM");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE HASHMAP VARIABLES
        addSystemVerbs(".INETADDRESS");// PRINTS THE IP ADDRESS OF A SERVER 
        addSystemVerbs(".LISTVARIABLES");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE LIST VARIABLES
        addSystemVerbs(".L");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE LIST VARIABLES
        addSystemVerbs(".MODE");
        addSystemVerbs(".MONTH");// PRINTS THE MONTH
        addSystemVerbs(".MTH");// PRINTS THE MONTH
        addSystemVerbs(".OS");// PRINT OPERATING SYSTEM COMPATIBILITY
        addSystemVerbs(".RSSFEEDMESSAGE");// PRINTS A MESSAGE WHERE THE INDEX IS THE TOS 
        
        addSystemVerbs(".SERVERIP");//Utilities.getIpAddressOfHosst()        
        addSystemVerbs(".SIP");//Utilities.getIpAddressOfHosst()
        addSystemVerbs(".STACK");// ADVANCED FEATURE : LIST THE STACK
        addSystemVerbs(".STATUS");
        addSystemVerbs(".S");// ADVANCED FEATURE : LIST THE STACK
        addSystemVerbs(".TIME");// ADVANCED FEATURE : PRINT TIME
        addSystemVerbs(".TIMESTAMP");// PRINT DATE
        addSystemVerbs(".VARIABLES");//ADVANCED FEATURE : LIST THE VARIABLES AND THEIR CONTENT
        addSystemVerbs(".V");//ADVANCED FEATURE : LIST THE VARIABLES AND THEIR CONTENT //showVariables()
        addSystemVerbs(".YEAR");// PRINTS THE YEAR
        addSystemVerbs(".YR");// PRINTS THE YEAR          
        addSystemVerbs(".STACK");
        addSystemVerbs(".HELP");

        addSystemVerbs("$@");//PUST THE VALUE OF A STRING VARIABLE TO TOS
        addSystemVerbs("$CONSTANT");//ADVANCED FEATURE :  DEFINE STRING CONSTANT WE ALSO HAVE STRING CONSTANTS
        addSystemVerbs("$VARIABLE");// DEFINE STRING VARIABLE
        addSystemVerbs("?DUP");// QDUP

        addSystemVerbs("AND");// LOGICAL AND
        addSystemVerbs("ALLOT");// STORE TOS TO NUMERIC VARIABLE
        addSystemVerbs("ASC");

        addSystemVerbs("BIN");
        addSystemVerbs("BASE64ENCODE");
        addSystemVerbs("BASE64DECODE");
        addSystemVerbs("BINARYTODEC");
        addSystemVerbs("BASE32DECODE");//BASE 32 DECODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP
        addSystemVerbs("BASE32ENCODE");//BASE 32 ENCODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP
        addSystemVerbs("BASE64DECODE");//BASE 32 DECODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP
        addSystemVerbs("BASE64ENCODE");//BASE 32 ENCODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP   
        addSystemVerbs("BIN");// BINARY MODE BASE 2 (SEE DEC)
        addSystemVerbs("BYE");// QUIT PROGRAM  

        addSystemVerbs("CEIL");
        addSystemVerbs("COS");
        addSystemVerbs("CR");
        addSystemVerbs("CONSTANT");// DEFINE CONSTANT
        addSystemVerbs("COPY");//BLOCK TO BLOCK COPY
        addSystemVerbs("COUNTER");//PUSH NR OF CLOCK TICKS TO TOS // IT RETURNS MILLSECONDS FROM JAN 1, 1970.
        addSystemVerbs("CR");// SENDS CR OR \N    
        addSystemVerbs("CHR$");

        
        addSystemVerbs("DATE@");//ADVANCED FEATURE :  PUSH DATE TO STACK
        addSystemVerbs("DATEUSA@");//ADVANCED FEATURE :  PUSH DATE TO STACK         
        addSystemVerbs("DATEBRITISH@");//ADVANCED FEATURE :  PUSH DATE TO STACK                   
        addSystemVerbs("DATESIMPLEFORMAT@");//ADVANCED FEATURE :  PUSH DATE TO STACK WHERE FORMAT STRING IS ON TOS YYYY-MM-DD HH:MM:SS
        addSystemVerbs("DATETIME@");//ADVANCED FEATURE :  PUSH DATE TO STACK
        addSystemVerbs("DELAY");//ADVANCED FEATURE :  100MS DELAY
        addSystemVerbs("DEC");// DECIMAL MODE BASE 10 (SEE BIN)
        addSystemVerbs("DEGTORAD");
        addSystemVerbs("DECTOBINARY");
        addSystemVerbs("DECTOHEX");
        addSystemVerbs("DECTOOCTAL");
        addSystemVerbs("DMOD");
        addSystemVerbs("DO");
        addSystemVerbs("DROP");        
        addSystemVerbs("DSQR");
        addSystemVerbs("DUP");
          

        addSystemVerbs("EDIT");// MULTIPLE TEXT EDITOR PUSH (1 TO 7) TO SELECT EDITOR THE PUSH PATH TO FILE TO EDIT AS A STRING 
        addSystemVerbs("ELSE");
        addSystemVerbs("EMIT");// SEND SPACES NR OF SPACES IS TOS
        addSystemVerbs("EXEC");// EXECUTE AN O/S COMMAND
        addSystemVerbs("EXECNANO");// EDIT FILE WITH NANO  FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECKATE");// EDIT FILE WITH KATE FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECWRITE");
        addSystemVerbs("EXECKWRITE");//EDIT FILE WITH KWRITE FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECVI");// EDIT FILE WITH VI FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECVIM");// EDIT FILE WITH VIM FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECWORDPAD");// EDIT FILE WITH WORDPAD FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECNOTEPAD");// EDIT FILE WITH NOTEPAD FIRST PUSH PATH TO FILE TO EDIT AS A STRING

        addSystemVerbs("FLOOR");
        addSystemVerbs("FLUSH");//FLUSHES CHANGED BLOCKS TO DISK                   
        addSystemVerbs("FILE@");//READ A FILE FROM DISK WHOSE FILESPECK(FILENAME AND PATH) IN ON TOS SAVE STRING READ TO TOS
        addSystemVerbs("FORGET");// REMOVES A VERB FROM THE DICTIONARY (DICTIONARYINDEXMAP)

        addSystemVerbs("HELP");// DISPLAY THIS LIST OF VERBS
        addSystemVerbs("HEX");// HEXADECIMAL MODE BASE 16 (SEE BIN) 
        addSystemVerbs("HEXTODEC");
        addSystemVerbs("HMAPVARIABLE");// DEFINE HASHMAP VARIABLE
        addSystemVerbs("HTMLLIST@");//DEPRICATED// LIST THE TAGS OF HTML PAGE WHOSE URL IS ON THE STACK TOP SAVE THIS ON THE STACK TOP
        addSystemVerbs("HTMLREAD@"); //  TEXT READ THE HTML PAGE WHOSE URL IS ON THE STACK TOP SAVE THIS ON THE STACK TOP

        addSystemVerbs("IF");
        addSystemVerbs("INETADDRESS@");// PUSHES THE IP ADDRESS OF A SERVER ON THESTACK

        addSystemVerbs("LATEST"); //     WILL CONNECT TO THE WEBSERVER AND DISPLAY LATEST VERSION AVAILABLE FOR DOWNOLOAD
        addSystemVerbs("LFS"); // SEND A NUMBER OF LF'S TO PRINT NUMBER IS TOS
        addSystemVerbs("LIST"); // LIST BLOCK N WHERE N IS TOS
        addSystemVerbs("LISTVARIABLE");// DEFINE LIST VARIABLE
        addSystemVerbs("LOAD"); // LOAD BLOCK FROM DISK N WHERE N IS TOS
        addSystemVerbs("LOG");
        addSystemVerbs("LOGBASE10");
        addSystemVerbs("LOOP");

        addSystemVerbs("MAX"); // KEEP THE MAXIMUN OF TWO NUMBERS ON THE STACK DROP THE OTHER
        addSystemVerbs("MIN");// KEEP THE MINIMUM OF TWO NUMBERS ON THE STACK DROP THE OTHER
        addSystemVerbs("MOD");// POP AND DIVIDE THE TOP TWO MUMBERS ON STACK AND PUSH THE MODULUS

        addSystemVerbs("NEGATE");// CHANGE THE SIGN OF THE NUMBER AT TOS NGATIVE/POSITIVE        
        addSystemVerbs("NOP");

        addSystemVerbs("OCT");// OCTAL MODE BASE 16 (SEE BIN) 
        addSystemVerbs("OCTALTODEC");
        addSystemVerbs("OR");// LOGICAL OR
        addSystemVerbs("OSSETLINUX"); // SET O/S COMPATIBILITY  TO LINUX
        addSystemVerbs("OSSETMAC"); // SET O/S COMPATIBILITY  TO MAC OS
        addSystemVerbs("OSSETSWING"); // SET O/S COMPATIBILITY  FOR SWING CLIENT
        addSystemVerbs("OSSETUNITTEST"); // SET O/S COMPATIBILITY  FOR UNIT TESTING
        addSystemVerbs("OSSETWEBSERVER"); // SET O/S COMPATIBILITY  FOR WEB SERVER
        addSystemVerbs("OSSETWINDOWS"); // SET O/S COMPATIBILITY  TO WINDOWS
        addSystemVerbs("OVER");

        addSystemVerbs("POP");// POP FROM STACK // INTERNAL OPERATION
        addSystemVerbs("PUSH");// PUSH ONTO STACK // INTERNAL OPERATION
        addSystemVerbs("PWR");

        addSystemVerbs("RADTODEG");
        addSystemVerbs("RND");// POPS SMALEST AND LARGEST NUMBERS AND PUSHES A RANDOM NUMBER  TO STACK
        addSystemVerbs("ROT");
        addSystemVerbs("ROUND");
        addSystemVerbs("RSSFEEDREAD"); // READS ALL MESSAGES FROM RSS STREAM WHERE URL IS ONTO THE STACK TOP
        addSystemVerbs("RSSFEEDMESSAGE@");// READS A MESSAGE WHERE THE INDEX IS THE TOS AND PUSHES THIS MESSAGE TO STACK
        addSystemVerbs("RSSFEEDSIZE@");// PUSHES THE NUMBER OF MESSAGES TO TOS
        addSystemVerbs("RSSJ2EEREAD");// READS ALL MESSAGES FROM RSS STREAM FOR THIS PROJECT  ONTO THE STACK TOP

        addSystemVerbs("SIN");
        addSystemVerbs("SPACE");// PRINT ONE SPACE
        addSystemVerbs("SPACES");// PRINT N SPACES WHERE N IS TOS
        addSystemVerbs("SQARE");// PUSH THE SQUARE THE TOS
        addSystemVerbs("SQR");// PUSH THE SQUARE ROOT OF TOS
        addSystemVerbs("SWAP");
        addSystemVerbs("SYSTEM!");// ADVANCED FEATURE : STORE SYSTEM VARIABLES TO PROPERTIES FILE ON HARD DISK
        addSystemVerbs("SYSTEM@");// ADVANCED FEATURE : RETRIEVE SYSTEM VARIABLES FROM PROPERTIES FILE ON HARD DISK

        addSystemVerbs("TAN");
        addSystemVerbs("TIME@");// ADVANCED FEATURE : PUSH DATE TO STACK
        addSystemVerbs("TIMESTAMP@");//ADVANCED FEATURE :  PUSH DATE TIME STAMP TO STACK
        addSystemVerbs("?TIME");// ADVANCED FEATURE : PRINT DATE TO STACK
        addSystemVerbs("?TIMESTAMP");//ADVANCED FEATURE :  PRINT DATE TIME STAMP TO STACK

        addSystemVerbs("VARIABLE");// DEFINE  VARIABLE

        addSystemVerbs("WEBPAGE@");// READ AND STORE HTML WEBPAGE ONTO STACK TOP

        addSystemVerbs("XOR");// LOGICAL EXCLUSIVE OR
        addSystemVerbs("XMLMAKE");//ADVANCED FEATURE :  PARSE VARIABLES INTO VARIABLE XML TO GENERATE XML
        addSystemVerbs("XML!");// ADVANCED FEATURE : SAVE XML TAGS TO VARIABLES XML
        addSystemVerbs("XML@");// ADVANCED FEATURE : PUSH XML TAGS TO STACK

    }

}
