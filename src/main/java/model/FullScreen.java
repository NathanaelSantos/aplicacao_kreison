
package model;

import java.io.Serializable;

public class FullScreen implements Serializable{

    private static final long serialVersionUID = 7100179587555243994L;
    private boolean fullScreen;

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }
  
}
