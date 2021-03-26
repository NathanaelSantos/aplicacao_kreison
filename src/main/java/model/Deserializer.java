
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializer {
    private boolean isFullScreen;

    public void deserializer() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fOut = new FileInputStream("C:\\KreisonDelivery\\serializable.ser");
        ObjectInputStream oOut = new ObjectInputStream(fOut);
        FullScreen fullScreen = (FullScreen) oOut.readObject();
        oOut.close();
        setFullScreen(fullScreen.isFullScreen());
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }
}
