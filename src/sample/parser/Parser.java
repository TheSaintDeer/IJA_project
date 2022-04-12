package sample.parser;

import sample.uml.ClassDiagram;
import sample.uml.UMLAttribute;
import sample.uml.UMLOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import static sample.Colors.log;



public class Parser {

    private File filename;
    private ClassDiagram diagram;
    private Scanner scanner;

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
//        while (scanner.hasNext()) {
//            log(scanner.next(), Colors.PURPLE);
//        }
        body();
        scanner.close();

    }

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

    private void parseConnection(String token) {


//        String o1 = token;
//        String connection = null;
//        String o2 = null;
//
//        connection = scanner.next();
//        o2 = scanner.next();

//        System.out.println("o1: " + o1 + " con: " + connection + " o2: " + o2);

    }

    private void checkInterface() {

        if (checkWord(scanner.next(),"class")) {
            diagram.createClass(scanner.next()).setAbstract(true);
        }
    }

    private void checkClass() {

        diagram.createClass(scanner.next()).setAbstract(false);
    }

    private void checkAttributes() {

        String str = scanner.nextLine();

        while ((str = scanner.nextLine()) != null && !str.equals("}")) {
//            log(str,Colors.YELLOW);
//            diagram
            parseAttribute(str);
        }

    }

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
            for (String s :
                    args_str.split(",[ ]*")) {
                UMLAttribute attr = diagram.getLast().getAttributeByName(s);
                if (attr == null) continue;
                attributes.add(attr);
            }
//            System.out.println(attributes.toString());
            diagram.getLast().addOperation(UMLOperation.create(visibility, name, diagram.classifierForName("method") ,(attributes).toArray()));


        } else if (matcher2.matches()) {
            visibility = matcher2.group(1);
            classifier = matcher2.group(2);
            name = matcher2.group(3);
            diagram.getLast().addAttribute(new UMLAttribute(visibility,name, diagram.classifierForName(classifier)));
//            log("visibility: " + visibility + " classifier: " + classifier + " name: " + name, Colors.YELLOW);
        } else {
            System.out.println("no match found"); // TODO
        }



    }

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


    private void checkFilename() throws Exception {

        System.out.println("checking \"" + filename + "\" for existense");  // TODO

        if(filename.exists() && filename.isFile()){
            System.out.println(filename + " exists");
        }else{
            System.out.println("file \"" + filename + "\" does not exists, throwing exception");  // TODO
            throw new FileNotFoundException(filename + " does not exists");

        }



    }

}
