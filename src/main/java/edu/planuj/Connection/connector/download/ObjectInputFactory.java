package edu.planuj.Connection.connector.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class ObjectInputFactory {

    public ObjectInput createObjectInput(InputStream stream) throws IOException {
        return new ObjectInputStream(stream);
    }
}
