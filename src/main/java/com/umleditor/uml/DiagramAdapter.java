package com.umleditor.uml;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DiagramAdapter extends TypeAdapter<ClassDiagram> {


    @Override
    public void write(JsonWriter jsonWriter, ClassDiagram classDiagram) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(classDiagram.getName());
        jsonWriter.name("classes");

        jsonWriter.beginArray();
        for (UMLClass c : classDiagram.getClasses()) {

                ClassAdapter classAdapter = new ClassAdapter();
                classAdapter.write(jsonWriter, c);
        }
        jsonWriter.endArray();

        jsonWriter.name("relations");
        jsonWriter.beginArray();

        for (UMLRelation c : classDiagram.getAllRelations()) {

            RelationAdapter relationAdapter = new RelationAdapter();
            relationAdapter.write(jsonWriter, c);

        }
        jsonWriter.endArray();
//        jsonWriter.name("sequences");

//        for (UMLRelationship c : classDiagram.getAllRelations()) {
//
//            RelationAdapter relationAdapter = new RelationAdapter();
//            relationAdapter.write(jsonWriter, c);
//
//        }

//        jsonWriter.value(classDiagram.getName());


        jsonWriter.endObject();

    }

    @Override
    public ClassDiagram read(JsonReader jsonReader) throws IOException {

        ClassDiagram diagram = new ClassDiagram("");

        jsonReader.beginObject();
        JsonToken token = jsonReader.peek();
        while (jsonReader.hasNext()) {

            token = jsonReader.peek();
            switch (jsonReader.nextName()) {
                case "name":
                    diagram.setName(jsonReader.nextString());
                    break;
                case "classes":
                    token = jsonReader.peek();
                    jsonReader.beginArray();
                    while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {

                        ClassAdapter classAdapter = new ClassAdapter();
                        diagram.addClass(classAdapter.read(jsonReader));

                    }
                    jsonReader.endArray();
                    break;

                case "relations":
//                    token = jsonReader.peek();
//                    String haha = jsonReader.nextName();
                    jsonReader.beginArray();
//                    System.out.println((jsonReader.peek().name()));
                    jsonReader.skipValue();

                    jsonReader.endArray();
                    break;

                case "sequences":
                    token = jsonReader.peek();
                    jsonReader.beginArray();
//                    System.out.println((jsonReader.peek().name()));
                    jsonReader.skipValue();
                    jsonReader.endArray();
                    break;

                default:
                    jsonReader.skipValue();
                    System.out.println("unknown object in class diagram");
            }

        }

        jsonReader.endObject();


        return diagram;
    }
}

//while (jsonReader.hasNext()) {
//        JsonToken token = jsonReader.peek();
//
//        if (token.equals(JsonToken.NAME)) {
//        //get the current token
//        fieldname = jsonReader.nextName();
//        }
//
//        if ("name".equals(fieldname)) {
//        //move to next token
//        token = jsonReader.peek();
//        newClass.setName(jsonReader.nextString());
//        }
//
//        if ("isAbstract".equals(fieldname)) {
//        //move to next token
//        token = jsonReader.peek();
//        newClass.setAbstract(jsonReader.nextBoolean());
//        }
//
//        if("attributes".equals(fieldname)) {
//        //move to next token
//
//        token = jsonReader.peek();
//
//        while (!token.equals(JsonToken.END_ARRAY)) {
//
//        token = jsonReader.peek();
//        jsonReader.beginArray();
//        token = jsonReader.peek();
//
//        while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {
//        UMLAttribute attribute = gson.fromJson(jsonReader, AttributeAdapter.class);
//        newClass.addAttribute(attribute);
//        }
//        jsonReader.endArray();
//
//        }
//
//
//        }
