package com.kigesui.view;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class MyJoglCanvas extends GLCanvas implements GLEventListener {

    Logger logger = Logger.getLogger(MyJoglCanvas.class);
    
    private static final long serialVersionUID = -8665159581359965255L;

    private static final int fps = 60;

    /** The OpenGL animator. */
    private FPSAnimator animator;

    public MyJoglCanvas(GLCapabilities capabilities, int width, int height) {
        super(capabilities);
        setSize(width, height);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
//        drawable.setGL(new DebugGL(drawable.getGL()));
        final GL gl = drawable.getGL();

        // Enable z- (depth) buffer for hidden surface removal. 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        // Enable smooth shading.
//        gl.glShadeModel(GL.GL_SMOOTH);

        // Define "clear" color.
        gl.glClearColor(0f, 0f, 0f, 0f);

        // We want a nice perspective.
//        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

        // Start animator.
        animator = new FPSAnimator(this, fps);
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    public void requestFocus() {
        logger.info("request focus");
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL gl = drawable.getGL();

        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        logger.info("dispose");
    }

    
    
}


