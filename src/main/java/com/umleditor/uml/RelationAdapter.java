package com.umleditor.uml;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RelationAdapter extends TypeAdapter<UMLRelationship> {


    @Override
    public void write(JsonWriter jsonWriter, UMLRelationship umlRelationship) throws IOException {

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
    public UMLRelationship read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
