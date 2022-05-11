package com.umleditor.uml;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class AttributeAdapter extends TypeAdapter<UMLAttribute> {


    @Override
    public void write(JsonWriter jsonWriter, UMLAttribute umlAttribute) throws IOException {

    }

    @Override
    public UMLAttribute read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
