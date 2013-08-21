package dcompiler.drawing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {

    public int w, h;
    public int[] pixels;
    public BufferedImage image;

    public Screen(int w, int h) {
	this.w = w;
	this.h = h;
	this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }
    
}
