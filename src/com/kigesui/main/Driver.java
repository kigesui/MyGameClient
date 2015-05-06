package com.kigesui.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GLCapabilities;
import com.kigesui.view.MyJoglCanvas;




public class Driver {

    static Logger logger = Logger.getLogger(Driver.class);
    
    public static void main(String[] args) {
        logger.info("client started");
        System.out.println("Client Start!");

        GLCapabilities capabilities = createGLCapabilities();
        MyJoglCanvas canvas = new MyJoglCanvas(capabilities, 800, 500);
        JFrame frame = new JFrame("MyGame Client");
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        canvas.requestFocus();

    }

    private static GLCapabilities createGLCapabilities() {
        // TODO Auto-generated method stub
        GLCapabilities capabilities = new GLCapabilities(null);
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);
        return capabilities;
    }

}
