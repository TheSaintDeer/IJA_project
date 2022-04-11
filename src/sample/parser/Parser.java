package sample.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Colors.log;



public class Parser {


    private File filename;
    private ArrayList diagram;
    private Scanner scanner;



    public Parser(String filename, ArrayList diagram) {
        this.filename = new File(filename);
        this.diagram = diagram;
    }

    public void parse() throws Exception {

        checkFilename();
        scanner = new Scanner(filename);
        scanner.useDelimiter("\\s+|(?<=[A-Za-z0-9])(?=[{])");

        checkWord(scanner.next(),"@startuml");
//        while (scanner.hasNext()) {
//            log(scanner.next(), Colors.PURPLE);
//        }
        body();
        scanner.close();

    }

    private void body() throws Exception {

        String token;

        while (scanner.hasNext()) {

            switch (token = scanner.next()) {
                case "interface":
                    checkInterface();
                    break;
                case "class":
                    checkClass();
                    break;
                case "{":
                    checkAttributes();
                    break;
                case "@enduml":
                    return;

                default:
//                throw new Exception("syntax error");
                    parseConnection(token);
                    break;

            }
        }

    }

    private void parseConnection(String token) {

        String o1 = token;
        String connection = null;
        String o2 = null;

        connection = scanner.next();
        o2 = scanner.next();

        System.out.println("o1: " + o1 + " con: " + connection + " o2: " + o2);

    }

    private void checkInterface() {
        log("interface: " + scanner.next(),Colors.BLUE);
    }

    private void checkClass() {
        log("class: " + scanner.next(),Colors.BLUE);
    }

    private void checkAttributes() {

        String str = scanner.nextLine();

        while ((str = scanner.nextLine()) != null && !str.equals("}")) {
            log(str,Colors.YELLOW);
            parseAttribute(str);
        }

    }

    private void parseAttribute(String str) {

        if (str.equals("")) return;
        String visibility = null;
        String name = null;
        String classifier = null;

        Pattern pattern1 = Pattern.compile("^([+\\-#~])(.+\\(\\))$");
        Pattern pattern2 = Pattern.compile("^([+\\-#~])(.+):(.+)$");

        Matcher matcher1 = pattern1.matcher(str.trim());
        Matcher matcher2 = pattern2.matcher(str.trim());

        if (matcher1.matches()) {
            visibility = matcher1.group(1);
            name = matcher1.group(2);
            log("visibility: "+visibility+" name: "+name,Colors.YELLOW);

        } else if (matcher2.matches()) {
            visibility = matcher2.group(1);
            classifier = matcher2.group(2);
            name = matcher2.group(3);
            log("visibility: " + visibility + " classifier: " + classifier + " name: " + name, Colors.YELLOW);
        } else {
            log("no match found"); // TODO
        }

    }

    private void checkWord(String next, String s) throws Exception {
        if (!next.equals(s)) throw new Exception("wrong syntax");
    }


    private void checkFilename() throws Exception {

        log("checking \"" + filename + "\" for existense");  // TODO

        if(filename.exists() && filename.isFile()){
            log(filename + " exists");
        }else{
            log("file \"" + filename + "\" does not exists, throwing exception");  // TODO
//            throw new FileNotFoundException(filename + " does not exists");

        }



    }

}
