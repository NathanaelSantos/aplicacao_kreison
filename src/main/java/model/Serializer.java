/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serializer {

    public void Serializer(boolean b) throws FileNotFoundException, IOException {
        FullScreen fullScreen = new FullScreen();
        fullScreen.setFullScreen(b);
       
        FileOutputStream fOut = new FileOutputStream("C:\\KreisonDelivery\\serializable.ser");
        ObjectOutputStream oOut = new ObjectOutputStream(fOut);
        oOut.writeObject(fullScreen);
        oOut.close();
       
    }

}
