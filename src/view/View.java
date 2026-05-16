package view;

import java.awt.Graphics;

public abstract class View {
    protected int x;
    protected int y;

    // Az absztrakt metódus
    public abstract void draw(Graphics g);
}
