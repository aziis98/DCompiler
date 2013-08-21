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
	this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }
    
    public void pixel_set(int color, int x, int y) {
	this.pixels[y * w + x] = color;
    }
    
    public void clear_set(int color) {
	for (int i = 0; i < pixels.length; i++) {
	    pixels[i] = color;	    
	}
    }
    
}
