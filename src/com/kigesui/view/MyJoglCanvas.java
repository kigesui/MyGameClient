package com.kigesui.view;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class MyJoglCanvas extends GLCanvas implements GLEventListener {

    Logger logger = Logger.getLogger(MyJoglCanvas.class);
    
    private static final long serialVersionUID = -8665159581359965255L;

    private static final int fps = 60;

    /** The GL unit (helper class). */
    private GLU glu;

    /** The OpenGL animator. */
    private FPSAnimator animator;

    public MyJoglCanvas(GLCapabilities capabilities, int width, int height) {
        super(capabilities);
        setSize(width, height);
        addGLEventListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
//        drawable.setGL(new DebugGL(drawable.getGL()));
        final GL2 gl = drawable.getGL().getGL2();

        // Enable z- (depth) buffer for hidden surface removal. 
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);

        // Enable smooth shading.
        gl.glShadeModel(GL2.GL_SMOOTH);

        // Define "clear" color.
        gl.glClearColor(0f, 0f, 0f, 0f);

        // We want a nice perspective.
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // Create GLU.
        glu = new GLU();

        // Start animator.
        animator = new FPSAnimator(this, fps);
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
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
        final GL2 gl = drawable.getGL().getGL2();

        // Clear screen.
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // Set camera.
        setCamera(gl, glu, 100);

        // Write triangle.
        gl.glColor3f(0.9f, 0.5f, 0.2f);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex3f(-20, -20, 0);
        gl.glVertex3f(+20, -20, 0);
        gl.glVertex3f(0, 20, 0);
        gl.glEnd();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        logger.info("dispose");
    }

    private void setCamera(GL2 gl, GLU glu, float distance) {
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(0, 0, distance, 0, 0, 0, 0, 1, 0);

        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    
}


