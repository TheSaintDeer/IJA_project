package com.umleditor.uml;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class AttributeAdapter extends TypeAdapter<UMLAttribute> {


    @Override
    public void write(JsonWriter jsonWriter, UMLAttribute umlAttribute) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("visibility");
        jsonWriter.value(umlAttribute.getVisibility());

        jsonWriter.name("type");
        jsonWriter.value(umlAttribute.getType().getName());

        jsonWriter.name("name");
        jsonWriter.value(umlAttribute.getName());

        jsonWriter.endObject();
    }

    @Override
    public UMLAttribute read(JsonReader jsonReader) throws IOException {

        String visibility = "";
        String type = "";
        String name = "";
        String fieldname = "";

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
                name = jsonReader.nextString();
            }

            if ("type".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                type = jsonReader.nextString();
            }

            if ("visibility".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                visibility = jsonReader.nextString();
            }
        }

        UMLAttribute attribute = new UMLAttribute(visibility,name,UMLClassifier.forName(type));

        jsonReader.endObject();

        return attribute;
    }
}
