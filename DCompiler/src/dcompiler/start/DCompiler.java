package dcompiler.start;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class DCompiler extends Canvas implements Runnable {

    public static int _WIDTH = 1000, _HEIGHT = 800;
    private boolean running = false;
    
    // Objects declaration
    private Screen screen;
    
    public DCompiler() {
        screen = new Screen(_WIDTH, _HEIGHT);
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
        game.setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
        game.setMaximumSize(new Dimension(_WIDTH, _HEIGHT));
        game.setMinimumSize(new Dimension(_WIDTH, _HEIGHT));
        game.setSize(new Dimension(_WIDTH, _HEIGHT));
        
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
            unprocessed += (now - then ) / nsPerUpdate;
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
        g.drawImage(screen.image, 0, 0, _WIDTH, _HEIGHT, null);
        g.dispose();
        bs.show();
    }

    public void update() {
	
    }
}
