package tb.sockets.client;

import java.io.*;
import java.util.Base64;

public class CoderDecoder {

    public static String serialize( Serializable serializable ) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream( byteArrayOutputStream );
        objectOutputStream.writeObject( serializable );
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    public static Object deserialize(String str) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(str);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        Object object  = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }
}
