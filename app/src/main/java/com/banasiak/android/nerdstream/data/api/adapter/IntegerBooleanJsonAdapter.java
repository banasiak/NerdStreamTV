package com.banasiak.android.nerdstream.data.api.adapter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;

/**
 * (De)serializes a Boolean from/to an Integer
 */
public class IntegerBooleanJsonAdapter extends JsonAdapter<Boolean> {

    @Override
    public Boolean fromJson(JsonReader reader) throws IOException {
        JsonReader.Token peek = reader.peek();
        if(peek.equals(JsonReader.Token.NUMBER)) {
            int i = reader.nextInt();
            return i != 0;
        } else {
            return reader.nextBoolean();
        }
    }

    @Override
    public void toJson(JsonWriter writer, Boolean value) throws IOException {
        int i = value ? 1 : 0;
        writer.value(i);
    }

}
