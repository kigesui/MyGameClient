package com.kigesui.view;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class MyJoglCanvas extends GLCanvas implements GLEventListener {

    Logger logger = Logger.getLogger(MyJoglCanvas.class);
    
    private static final long serialVersionUID = -8665159581359965255L;

    private static final int fps = 60;

    /** The GL unit (helper class). */
    private GLU glu;

    /** The OpenGL animator. */
    private FPSAnimator animator;

    /** The earth texture. */
    private Texture earthTexture;

    public MyJoglCanvas(GLCapabilities capabilities, int width, int height) {
        super(capabilities);
        setSize(width, height);
        addGLEventListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL2(drawable.getGL().getGL2()));
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

        // Load earth texture.
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("data/earth.jpg");
            TextureData data = TextureIO.newTextureData(null,stream, false, "jpg");
            earthTexture = TextureIO.newTexture(data);
        }
        catch (IOException e) {
            logger.error("can't find picture");
            e.printStackTrace();
            System.exit(1);
        }

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
        if (animator == null || !animator.isAnimating()) {
            return;
        }
        final GL2 gl = drawable.getGL().getGL2();

        // Clear screen.
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

//        // Prepare light parameters.
//        float SHINE_ALL_DIRECTIONS = 1;
//        float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
//        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
//        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};
//
//        // Set light parameters.
//        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
//        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
//        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
//
//        // Enable lighting in GL.
//        gl.glEnable(GL2.GL_LIGHT1);
//        gl.glEnable(GL2.GL_LIGHTING);
//
//        // Set material properties.
//        float[] rgba = {0.3f, 0.5f, 1f};
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
//        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
//
//        // Set camera.
//        setCamera(gl, glu, 100);
//
//        // Write triangle.
//        gl.glColor3f(0.9f, 0.5f, 0.2f);
//        gl.glBegin(GL2.GL_TRIANGLE_FAN);
//        gl.glVertex3f(-20, -20, 0);
//        gl.glVertex3f(+20, -20, 0);
//        gl.glVertex3f(0, 20, 0);
//        gl.glEnd();
//        
//        // Set camera.
//        setCamera(gl, glu, 30);
//
//        // Draw sphere (possible styles: FILL, LINE, POINT).
//        gl.glColor3f(0.3f, 0.5f, 1f);
//        GLUquadric earth = glu.gluNewQuadric();
//        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
//        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
//        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
//        final float radius = 6.378f;
//        final int slices = 16;
//        final int stacks = 16;
//        glu.gluSphere(earth, radius, slices, stacks);
//        glu.gluDeleteQuadric(earth);

        // Set camera.
        setCamera(gl, glu, 30);

        // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);

        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);

        // Apply texture.
        earthTexture.enable(gl);
        earthTexture.bind(gl);

        // Draw sphere (possible styles: FILL, LINE, POINT).
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricTexture(earth, true);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 6.378f;
        final int slices = 16;
        final int stacks = 16;
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);

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


