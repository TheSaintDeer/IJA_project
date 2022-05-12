package com.umleditor.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.umleditor.uml.UMLRelation;

import java.io.IOException;

public class RelationAdapter extends TypeAdapter<UMLRelation> {


    @Override
    public void write(JsonWriter jsonWriter, UMLRelation umlRelationship) throws IOException {

        jsonWriter.beginObject();

        jsonWriter.name("from");
        jsonWriter.value(umlRelationship.fromClass);

        jsonWriter.name("to");
        jsonWriter.value(umlRelationship.toClass);

        jsonWriter.name("type");
        jsonWriter.value(String.valueOf(umlRelationship.typeRelationship));

        jsonWriter.endObject();

    }

    @Override
    public UMLRelation read(JsonReader jsonReader) throws IOException {

        String from = "";
        String to = "";
        String type = "";
        String fieldname = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken token = jsonReader.peek();

            if (token.equals(JsonToken.END_OBJECT)) {
                //get the current token
                break;
            }

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = jsonReader.nextName();
            }

            if ("from".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                from = jsonReader.nextString();
            }

            if ("to".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                to = jsonReader.nextString();
            }

            if ("type".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                type = jsonReader.nextString();
            }
        }

        jsonReader.endObject();

        return new UMLRelation(from,to,type);
    }
}
