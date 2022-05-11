package com.umleditor.parser;

import com.umleditor.uml.ClassDiagram;
import com.umleditor.uml.UMLAttribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class for parsing input file
 */
public class Parser {

    private File filename;
    private ClassDiagram diagram;
    private Scanner scanner;

    /**
     * Function for snacking individual parts of the parser.
     * @param args - tokens
     * @param diagram - Using diagram
     * @throws Exception
     */
    public void parse(String[] args, ClassDiagram diagram) throws Exception {

        if (args.length != 0) {
            this.filename = new File(args[0]);
        } else {
            return;
        }

        this.diagram = diagram;

        checkFilename();
        scanner = new Scanner(filename);
        scanner.useDelimiter("\\s+|(?<=[A-Za-z0-9])(?=[{])");

        checkWord(scanner.next(),"@startuml");
        body();
        scanner.close();

    }

    /**
     * Function for parsing the body of UML code.
     */
    private void body() {

        String token;

        while (scanner.hasNext()) {

            switch (token = scanner.next()) {
                case "abstract":
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
                    parseConnection(token);
//                    throw new Exception("syntax error");
                    break;

            }
        }

    }

    /**
     * Link parsing relation.
     * @param token - part of input code
     */
    private void parseConnection(String token) {

        String o1 = token;
        String connection = null;
        String o2 = null;

        connection = scanner.next();
        o2 = scanner.next();

        diagram.createRelat(o1, o2, connection);
    }

    /**
     * Controlling interface
     */
    private void checkInterface() {

        if (checkWord(scanner.next(),"class")) {
            diagram.createClass(scanner.next()).setAbstract(true);
        }
    }

    /**
     * Controlling interface
     */
    private void checkClass() {

        diagram.createClass(scanner.next()).setAbstract(false);
    }

    /**
     * Controlling attribute
     */
    private void checkAttributes() {

        String str = scanner.nextLine();

        while ((str = scanner.nextLine()) != null && !str.equals("}")) {
//            log(str,Colors.YELLOW);
//            diagram
            parseAttribute(str);
        }

    }

    /**
     * Parsing attributes of class
     * @param str - String with UML code
     */
    private void parseAttribute(String str) {

        if (str.equals("")) return;
        String visibility;
        String name;
        String args_str;
        String classifier;
        ArrayList<UMLAttribute> attributes = new ArrayList<>();


        Pattern pattern1 = Pattern.compile("^([+\\-#~])([\\S\\w]+)\\((.*)\\)$");
        Pattern pattern2 = Pattern.compile("^([+\\-#~])(.+):(.+)$");

        Matcher matcher1 = pattern1.matcher(str.trim());
        Matcher matcher2 = pattern2.matcher(str.trim());

        if (matcher1.matches()) {
            visibility = matcher1.group(1);
            name = matcher1.group(2);
            args_str = matcher1.group(3);

            if (args_str.equals(""))
                for (String s : args_str.split(",[ ]*")) {
                    UMLAttribute attr = diagram.getLast().getAttributeByName(s);
                    if (attr == null) continue;
                    attributes.add(attr);
                }
            diagram.getLast().addAttribute(new UMLAttribute(visibility, name, diagram.classifierForName("method")));


        } else if (matcher2.matches()) {
            visibility = matcher2.group(1);
            classifier = matcher2.group(2);
            name = matcher2.group(3);
            diagram.getLast().addAttribute(new UMLAttribute(visibility,name, diagram.classifierForName(classifier)));
        } else {
            System.out.println("no match found"); // TODO
        }



    }

    /**
     * Controlling type tokens
     * @param next - next token
     * @param s - current token
     * @return If everything is true, then returns true, else false
     */
    private boolean checkWord(String next, String s) {
        if (!next.equals(s)) {
            try {
                throw new Exception("wrong syntax");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * Controlling file
     * @throws Exception using library
     */
    private void checkFilename() throws Exception {

        System.out.println("checking \"" + filename + "\" for existense");  // TODO

        if(filename.exists() && filename.isFile()){
            System.out.println(filename + " exists");
        }else{
            System.out.println("file \"" + filename + "\" does not exists, throwing exception");  // TODO
//            throw new FileNotFoundException(filename + " does not exists");

        }



    }

}
