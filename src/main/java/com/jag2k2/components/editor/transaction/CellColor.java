package com.jag2k2.components.editor.transaction;

import java.awt.*;

public class CellColor {
    static public Color getBackgroundColor(float amount){
        if (amount > 0) {
            return new Color(207, 255, 207);
        }
        else return null;
    }
}
