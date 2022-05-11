package com.umleditor.uml;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ClassAdapter extends TypeAdapter<UMLClass> {


    @Override
    public void write(JsonWriter jsonWriter, UMLClass umlClass) throws IOException {

        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(umlClass.getName());
        jsonWriter.name("isAbstract");
        jsonWriter.value(umlClass.isAbstract());
        jsonWriter.name("attributes");

        jsonWriter.beginArray();
        for (UMLAttribute c : umlClass.getAttributes()) {
            AttributeAdapter classAdapter = new AttributeAdapter();
            classAdapter.write(jsonWriter, c);
        }
        jsonWriter.endArray();
        jsonWriter.endObject();

    }

    @Override
    public UMLClass read(JsonReader jsonReader) throws IOException {

        UMLClass newClass = new UMLClass("");
        String fieldname = null;
        Gson gson = new Gson();

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken token = jsonReader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = jsonReader.nextName();
            }

            if ("name".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                newClass.setName(jsonReader.nextString());
            }

            if ("isAbstract".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                newClass.setAbstract(jsonReader.nextBoolean());
            }

            if("attributes".equals(fieldname)) {
                //move to next token

                token = jsonReader.peek();

                while (!token.equals(JsonToken.END_ARRAY)) {

//                    token = jsonReader.peek();
                    jsonReader.beginArray();
//                    token = jsonReader.peek();

                    while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {
                        AttributeAdapter attribute = new AttributeAdapter();
                        newClass.addAttribute(attribute.read(jsonReader));
                    }
                    jsonReader.endArray();

                }


            }
        }

        jsonReader.endObject();

        return newClass;
    }
}
