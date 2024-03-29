package com.umleditor.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.umleditor.uml.ClassDiagram;
import com.umleditor.uml.UMLSequence;
import com.umleditor.uml.UMLClass;
import com.umleditor.uml.UMLRelation;

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
        jsonWriter.name("sequences");
        jsonWriter.beginArray();

        for (UMLSequence s : classDiagram.getSequences()) {

            SequenceAdapter sequenceAdapter = new SequenceAdapter();
            sequenceAdapter.write(jsonWriter, s);

        }
        jsonWriter.endArray();


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
                    token = jsonReader.peek();
                    jsonReader.beginArray();
                    while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {

                        RelationAdapter relationAdapter = new RelationAdapter();
                        diagram.addRelation(relationAdapter.read(jsonReader));

                    }
                    jsonReader.endArray();
                    break;

                case "sequences":
                    token = jsonReader.peek();
                    jsonReader.beginArray();
                    while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {

                        SequenceAdapter sequenceAdapter = new SequenceAdapter();
                        diagram.addSequence(sequenceAdapter.read(jsonReader));

                    }
                    jsonReader.endArray();
                    break;

                default:
//                    jsonReader.skipValue();
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
