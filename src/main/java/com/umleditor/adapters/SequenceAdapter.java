package com.umleditor.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.umleditor.uml.UMLSequence;

import java.io.IOException;

public class SequenceAdapter extends TypeAdapter<UMLSequence> {

    @Override
    public void write(JsonWriter jsonWriter, UMLSequence sequence) throws IOException {

        jsonWriter.beginObject();

        jsonWriter.name("name");
        jsonWriter.value(sequence.getName());

        jsonWriter.name("from");
        jsonWriter.value(sequence.getNameClassFrom());

        jsonWriter.name("to");
        jsonWriter.value(sequence.getNameClassTo());

        jsonWriter.name("isActive");
        jsonWriter.value(sequence.isActive());

        jsonWriter.endObject();

    }

    @Override
    public UMLSequence read(JsonReader jsonReader) throws IOException {

        String name = "";
        String from = "";
        String to = "";
        String fieldname = null;
        boolean isActive;

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
                from = jsonReader.nextString();
            }

            if ("to".equals(fieldname)) {
                to = jsonReader.nextString();
            }

            if ("name".equals(fieldname)) {
                name = jsonReader.nextString();
            }

            if ("isActive".equals(fieldname)) {
                 isActive = jsonReader.nextBoolean();
            }
        }

        jsonReader.endObject();

        return new UMLSequence(name,from,to);
    }
}
