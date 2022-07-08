package src;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;

import java.awt.event.KeyListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

    // =========================== Variables ========================== //
    public boolean[] key = new boolean[68836];
    public int mouseDeltaX = 0;
    private int mousePrevX = 0;
    public int mouseDeltaY = 0;
    private int mousePrevY = 0;

    // ========================= Key Listener ========================= //
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode > 0 && keyCode < key.length) key[keyCode] = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode > 0 && keyCode < key.length) key[keyCode] = false;
    }
    // ===================== Mouse Motion Listener ===================== //
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseDeltaX = mousePrevX - e.getX();
        mousePrevX = e.getX();
        mouseDeltaY = mousePrevY - e.getY();
        mousePrevY = e.getY();
    }
    // ======================== Mouse Listener ======================== //
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    // ======================== Focus Listener ======================== //
    @Override
    public void focusGained(FocusEvent e) {}
    @Override
    public void focusLost(FocusEvent e) {
        for (int i = 0; i < key.length; i++) key[i] = false;
    }
}