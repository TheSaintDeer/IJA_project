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

            if("attributes".equals(fieldname)) {
                //move to next token

                token = jsonReader.peek();

                if (fieldname.equals(JsonToken.BEGIN_ARRAY)) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        UMLAttribute attribute = (UMLAttribute) gson.fromJson(jsonReader, AttributeAdapter.class);
                        newClass.addAttribute(attribute);

                    }
                    jsonReader.endArray();
                }


            }
        }

        jsonReader.endObject();

        return newClass;
    }
}
