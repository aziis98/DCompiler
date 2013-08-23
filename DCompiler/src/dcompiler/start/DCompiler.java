package dcompiler.start;

import dcompiler.drawing.Screen;
import dcompiler.io.FileLoader;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Scanner;
import javax.swing.JFrame;

public class DCompiler extends Canvas implements Runnable {

    public static int dim_width = 800, dim_height = 600;
    private boolean running = false;
    private Screen screen;

    public DCompiler() {
	screen = new Screen(dim_width, dim_height);
	screen.clear_set(Color.BLACK.getRGB());
    }

    private void start() {
	running = true;
	new Thread(this).start();
    }

    private void stop() {
	running = false;
    }

    public static void main(String[] args) {
	DCompiler game = new DCompiler();
	game.setPreferredSize(new Dimension(dim_width, dim_height));
	game.setMaximumSize(new Dimension(dim_width, dim_height));
	game.setMinimumSize(new Dimension(dim_width, dim_height));
	game.setSize(new Dimension(dim_width, dim_height));

	JFrame f = new JFrame("My game");
	f.setResizable(false);
	f.add(game);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.pack();
	f.setLocationRelativeTo(null);
	f.setVisible(true);

	game.start();
    }

    @Override
    public void run() {
	int fps = 0, update = 0;
	long fpsTimer = System.currentTimeMillis();
	double nsPerUpdate = 1000000000.0 / 60;
	// Last update time in nanoseconds
	double then = System.nanoTime();
	double unprocessed = 0;
	while (running) {
	    double now = System.nanoTime();
	    unprocessed += (now - then) / nsPerUpdate;
	    then = now;
	    while (unprocessed >= 1) {
		// Update
		update++;
		update();
		unprocessed--;
	    }
	    try {
		Thread.sleep(1);
	    } catch (Exception e) {
		Thread.yield();
		e.printStackTrace();
	    }
	    // Render
	    fps++;
	    render();
	    // FPS Timer
	    if (System.currentTimeMillis() - fpsTimer > 1000) {
		// System.out.printf("%d fps, %d updates\n", fps, update);
		fps = 0;
		update = 0;
		fpsTimer += 1000;
	    }
	}
    }

    public void render() {
	BufferStrategy bs = this.getBufferStrategy();
	if (bs == null) {
	    createBufferStrategy(2);
	    requestFocus();
	    return;
	}

	Graphics g = bs.getDrawGraphics();
	g.drawImage(screen.image, 0, 0, dim_width, dim_height, null);
	g.dispose();
	bs.show();
    }

    public void update() {
	
    }

}
