import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;

class Ball extends JPanel {
    
    //coordinates of the ball
    private int ballX = 50; 
    private int ballY = 50;
    //ball speed in x and y direction
    private int ballSpeedX= 2; 
    private int ballSpeedY = 2; 
    //ball size
    private int ballSize = 100;
    //ball color
    private Color color;

    public Ball(int ballX, int ballY, int ballSpeedX, int ballSpeedY, Color color, int ballSize) {
        Timer timer = new Timer(20, e -> {
            bounce();
            
            repaint();
        });
        timer.start();
        this.ballX = ballX;
        this.ballY = ballY;
        this.color = color;
        this.ballSize = ballSize;
        this.ballSpeedX = ballSpeedX;
        this.ballSpeedY = ballSpeedY;
    }

    public void bounce() {
         ballX += ballSpeedX;
         ballY += ballSpeedY;
        if (ballX <= 0 || ballX >= getWidth() - ballSize) {
            ballSpeedX = -ballSpeedX;
        }
        if (ballY <= 0 || ballY >= getHeight() - ballSize) {
            ballSpeedY = -ballSpeedY;
        }
    }
@Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(ballX, ballY, ballSize, ballSize);
    }

public void setRadius(int newRadius) {
    this.ballSize = newRadius;
}


}