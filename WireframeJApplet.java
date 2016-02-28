//Karen abarca
//Fernando Arey Duran A00397411
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Arrays;


class Point3D {
   public float x, y, z;
   public Point3D( float X, float Y, float Z ) {
      x = X;  y = Y;  z = Z;
   }
}

class Faces implements Comparable<Faces>{
  public int x[];
  public int y[];
  public int n;
  public float z;
  public Faces(int valor){
    x = new int[valor];
    y = new int[valor];
    n = valor;
  } 
  @Override
  public int compareTo(Faces o) {
      if (z < o.z) {
          return -1;
      }
      if (z > o.z) {
          return 1;
      }
      return 0;
  } 
}

class Edge {
   public int a, b;
   public Edge( int A, int B ) {
      a = A;  b = B;
   }
}

public class WireframeJApplet extends JApplet
                  implements KeyListener, FocusListener, MouseListener {

   int width, height;
   // int mx, my;  // the most recently recorded mouse coordinates

   int azimuth = 35, elevation = 30;

   Point3D[] vertices;
   Edge[] edges;
   Faces []faces;
   float[] z = new float[25] ;
   int colores[] = new int[78]; //arreglo de colores para colores aleatorios
   int r []= {0,   255, 255, 103, 140, 201, 255, 178, 115, 41, 209, 64,  36,  41,  110, 255, 135, 41,  115, 140,  206, 64,  127, 178, 11};
   int v []= {206, 64,  127, 178, 11,  135, 241, 94,  115, 178, 0,   255, 255, 103, 140, 201, 255, 178, 115, 41, 209, 64,  36,  41,  110};
   int a []= {209, 64,  36,  41,  110, 255, 135, 41,  115, 140, 206, 64,  127, 178, 11,  135, 241, 94,  115, 178, 0,   255, 255, 103, 140,};

   boolean focussed = false;   // True when this applet has input focus.

   DisplayPanel canvas;

   public void init() {
      Random rdm = new Random();
      for(int i = 0; i<78; i++){
        colores[i] = rdm.nextInt(154)+100;
      }

      vertices = new Point3D[ 24 ];
      vertices[0] = new Point3D( -1, -1, -1 );
      vertices[1] = new Point3D( -1, -1,  1 );
      vertices[2] = new Point3D( -1,  1, -1 );
      vertices[3] = new Point3D( -1,  1,  1 );
      vertices[4] = new Point3D(  1, -1, -1 );
      vertices[5] = new Point3D(  1, -1,  1 );
      vertices[6] = new Point3D(  1,  1, -1 );
      vertices[7] = new Point3D(  1,  1,  1 );
      vertices[8] = new Point3D(  3,  1, -1);
      vertices[9] = new Point3D(  3,  1,  1 );
      vertices[10] = new Point3D(  -1,  3,  -1 );
      vertices[11] = new Point3D(  -1,  3,  1 );
      vertices[12] = new Point3D(  1,  3,  -1 );
      vertices[13] = new Point3D(  1,  3,  1 );
      vertices[14] = new Point3D(  3,  3,  1 );
      vertices[15] = new Point3D(  3,  3,  -1);
      vertices[16] = new Point3D(  1,  3,  3 );
      vertices[17] = new Point3D(  3,  3,  3 );
      vertices[18] = new Point3D(  1,  1,  3);
      vertices[19] = new Point3D(  3,  1,  3);
      vertices[20] = new Point3D(  1,  5,  1 );
      vertices[21] = new Point3D(  1,  5,  3 );
      vertices[22] = new Point3D(  3,  5,  1 );
      vertices[23] = new Point3D(  3,  5,  3 );



      edges = new Edge[ 44 ];
      edges[ 0] = new Edge( 0, 1 );
      edges[ 1] = new Edge( 0, 2 );
      edges[ 2] = new Edge( 0, 4 );
      edges[ 3] = new Edge( 1, 3 );
      edges[ 4] = new Edge( 1, 5 );
      edges[ 5] = new Edge( 2, 3 );
      edges[ 6] = new Edge( 2, 6 );
      edges[ 7] = new Edge( 3, 7 );
      edges[ 8] = new Edge( 4, 5 );
      edges[ 9] = new Edge( 4, 6 );
      edges[10] = new Edge( 5, 7 );
      edges[11] = new Edge( 6, 7 );
      edges[12] = new Edge( 6, 8 );
      edges[13] = new Edge( 8, 9 );
      edges[14] = new Edge( 7, 9 );
      edges[15] = new Edge( 2, 10 );
      edges[16] = new Edge( 10, 11);
      edges[17] = new Edge( 11, 3);
      edges[18] = new Edge( 10, 12 );
      edges[19] = new Edge( 11, 13 );
      edges[20] = new Edge( 12, 6 );
      edges[21] = new Edge( 13, 7);
      edges[22] = new Edge( 12, 13);
      edges[23] = new Edge( 12, 15);
      edges[24] = new Edge( 13, 14);
      edges[25] = new Edge( 14, 15);
      edges[26] = new Edge( 8, 15 );
      edges[27] = new Edge( 9, 14);
      edges[28] = new Edge( 16, 17);
      edges[29] = new Edge( 13, 16 );
      edges[30] = new Edge( 14, 17 );
      edges[31] = new Edge( 9, 19 );
      edges[32] = new Edge( 7, 18);
      edges[33] = new Edge( 18, 19);
      edges[34] = new Edge( 18, 16);
      edges[35] = new Edge( 19, 17);
      edges[36] = new Edge( 20, 21);
      edges[37] = new Edge( 20, 22);
      edges[38] = new Edge( 20, 13);
      edges[39] = new Edge( 23, 21);
      edges[40] = new Edge( 23, 22);
      edges[41] = new Edge( 22, 14);
      edges[42] = new Edge( 21, 18);
      edges[43] = new Edge( 23, 19);

      canvas = new DisplayPanel();  // Create drawing surface and
      setContentPane(canvas);       //    install it as the applet's content pane.

      canvas.addFocusListener(this);   // Set up the applet to listen for events
      canvas.addKeyListener(this);     //                          from the canvas.
      canvas.addMouseListener(this);

   } // end init();

   class DisplayPanel extends JPanel {
      public void paintComponent(Graphics g) {
         super.paintComponent(g);

         if (focussed)
            g.setColor(Color.cyan);
         else
            g.setColor(Color.lightGray);

         int width = getSize().width;  // Width of the applet.
         int height = getSize().height; // Height of the applet.
         g.drawRect(0,0,width-1,height-1);
         g.drawRect(1,1,width-3,height-3);
         g.drawRect(2,2,width-5,height-5);

         if (!focussed) {
            g.setColor(Color.magenta);
            g.drawString("Click to activate",100,120);
            g.drawString("Use arrow keys to change azimuth and elevation",100,150);

         }
         else {

            double theta = Math.PI * azimuth / 180.0;
            double phi = Math.PI * elevation / 180.0;
            float cosT = (float)Math.cos( theta ), sinT = (float)Math.sin( theta );
            float cosP = (float)Math.cos( phi ), sinP = (float)Math.sin( phi );
            float cosTcosP = cosT*cosP, cosTsinP = cosT*sinP,
                  sinTcosP = sinT*cosP, sinTsinP = sinT*sinP;

            // project vertices onto the 2D viewport
            Point[] points;
            points = new Point[ vertices.length ];
            int j;
            int scaleFactor = width/8;
            float near = 3;  // distance from eye to near plane
            float nearToObj = 10f;  // distance from near plane to center of object
            for ( j = 0; j < vertices.length; ++j ) {
               float x0 = vertices[j].x;
               float y0 = vertices[j].y;
               float z0 = vertices[j].z;
               z[j] = z0;  //guardo las z para asignarle su respectiva z a cada cara
               // compute an orthographic projection
               float x1 = cosT*x0 + sinT*z0;
               float y1 = -sinTsinP*x0 + cosP*y0 + cosTsinP*z0;

               // now adjust things to get a perspective projection
               float z1 = cosTcosP*z0 - sinTcosP*x0 - sinP*y0;
               x1 = x1*near/(z1+near+nearToObj);
               y1 = y1*near/(z1+near+nearToObj);
               

               // the 0.5 is to round off when converting to int
               points[j] = new Point(
                  (int)(width/2 + scaleFactor*x1 + 0.5),
                  (int)(height/2 - scaleFactor*y1 + 0.5)
               );
            }
            //pintacara
            faces = new Faces[25];

            //v0,v1,v2,v3--cara0
            for(int i = 0; i<25; i++){
              faces[i] = new Faces(4);
            }

            faces[0].x[0] = points[0].x;
            faces[0].y[0] = points[0].y;
            faces[0].x[1] = points[1].x;
            faces[0].y[1] = points[1].y;
            faces[0].x[2] = points[3].x;
            faces[0].y[2] = points[3].y;
            faces[0].x[3] = points[2].x;
            faces[0].y[3] = points[2].y;

            faces[1].x[0] = points[0].x;
            faces[1].y[0] = points[0].y;
            faces[1].x[1] = points[1].x;
            faces[1].y[1] = points[1].y;
            faces[1].x[2] = points[5].x;
            faces[1].y[2] = points[5].y;
            faces[1].x[3] = points[4].x;
            faces[1].y[3] = points[4].y;

            faces[2].x[0] = points[0].x;
            faces[2].y[0] = points[0].y;
            faces[2].x[1] = points[4].x;
            faces[2].y[1] = points[4].y;
            faces[2].x[2] = points[6].x;
            faces[2].y[2] = points[6].y;
            faces[2].x[3] = points[2].x;
            faces[2].y[3] = points[2].y;

            faces[3].x[0] = points[6].x;
            faces[3].y[0] = points[6].y;
            faces[3].x[1] = points[2].x;
            faces[3].y[1] = points[2].y;
            faces[3].x[2] = points[3].x;
            faces[3].y[2] = points[3].y;
            faces[3].x[3] = points[7].x;
            faces[3].y[3] = points[7].y;
            
            faces[4].x[0] = points[3].x;
            faces[4].y[0] = points[3].y;
            faces[4].x[1] = points[7].x;
            faces[4].y[1] = points[7].y;
            faces[4].x[2] = points[5].x;
            faces[4].y[2] = points[5].y;
            faces[4].x[3] = points[1].x;
            faces[4].y[3] = points[1].y;
            
            faces[5].x[0] = points[4].x;
            faces[5].y[0] = points[4].y;
            faces[5].x[1] = points[5].x;
            faces[5].y[1] = points[5].y;
            faces[5].x[2] = points[7].x;
            faces[5].y[2] = points[7].y;
            faces[5].x[3] = points[6].x;
            faces[5].y[3] = points[6].y;

            faces[6].x[0] = points[6].x;
            faces[6].y[0] = points[6].y;
            faces[6].x[1] = points[12].x;
            faces[6].y[1] = points[12].y;
            faces[6].x[2] = points[10].x;
            faces[6].y[2] = points[10].y;
            faces[6].x[3] = points[2].x;
            faces[6].y[3] = points[2].y;

            faces[7].x[0] = points[2].x;
            faces[7].y[0] = points[2].y;
            faces[7].x[1] = points[10].x;
            faces[7].y[1] = points[10].y;
            faces[7].x[2] = points[11].x;
            faces[7].y[2] = points[11].y;
            faces[7].x[3] = points[3].x;
            faces[7].y[3] = points[3].y;
            
            faces[8].x[0] = points[3].x;
            faces[8].y[0] = points[3].y;
            faces[8].x[1] = points[7].x;
            faces[8].y[1] = points[7].y;
            faces[8].x[2] = points[13].x;
            faces[8].y[2] = points[13].y;
            faces[8].x[3] = points[11].x;
            faces[8].y[3] = points[11].y;
            
            faces[9].x[0] = points[6].x;
            faces[9].y[0] = points[6].y;
            faces[9].x[1] = points[7].x;
            faces[9].y[1] = points[7].y;
            faces[9].x[2] = points[13].x;
            faces[9].y[2] = points[13].y;
            faces[9].x[3] = points[12].x;
            faces[9].y[3] = points[12].y;

            faces[10].x[0] = points[12].x;
            faces[10].y[0] = points[12].y;
            faces[10].x[1] = points[13].x;
            faces[10].y[1] = points[13].y;
            faces[10].x[2] = points[11].x;
            faces[10].y[2] = points[11].y;
            faces[10].x[3] = points[10].x;
            faces[10].y[3] = points[10].y;

            faces[11].x[0] = points[6].x;
            faces[11].y[0] = points[6].y;
            faces[11].x[1] = points[7].x;
            faces[11].y[1] = points[7].y;
            faces[11].x[2] = points[9].x;
            faces[11].y[2] = points[9].y;
            faces[11].x[3] = points[8].x;
            faces[11].y[3] = points[8].y;

            faces[12].x[0] = points[6].x;
            faces[12].y[0] = points[6].y;
            faces[12].x[1] = points[8].x;
            faces[12].y[1] = points[8].y;
            faces[12].x[2] = points[15].x;
            faces[12].y[2] = points[15].y;
            faces[12].x[3] = points[12].x;
            faces[12].y[3] = points[12].y;

            faces[13].x[0] = points[9].x;
            faces[13].y[0] = points[9].y;
            faces[13].x[1] = points[14].x;
            faces[13].y[1] = points[14].y;
            faces[13].x[2] = points[13].x;
            faces[13].y[2] = points[13].y;
            faces[13].x[3] = points[7].x;
            faces[13].y[3] = points[7].y;

            faces[14].x[0] = points[8].x;
            faces[14].y[0] = points[8].y;
            faces[14].x[1] = points[9].x;
            faces[14].y[1] = points[9].y;
            faces[14].x[2] = points[14].x;
            faces[14].y[2] = points[14].y;
            faces[14].x[3] = points[15].x;
            faces[14].y[3] = points[15].y;

            faces[15].x[0] = points[15].x;
            faces[15].y[0] = points[15].y;
            faces[15].x[1] = points[14].x;
            faces[15].y[1] = points[14].y;
            faces[15].x[2] = points[13].x;
            faces[15].y[2] = points[13].y;
            faces[15].x[3] = points[12].x;
            faces[15].y[3] = points[12].y;

            faces[16].x[0] = points[9].x;
            faces[16].y[0] = points[9].y;
            faces[16].x[1] = points[7].x;
            faces[16].y[1] = points[7].y;
            faces[16].x[2] = points[18].x;
            faces[16].y[2] = points[18].y;
            faces[16].x[3] = points[19].x;
            faces[16].y[3] = points[19].y;

            faces[17].x[0] = points[9].x;
            faces[17].y[0] = points[9].y;
            faces[17].x[1] = points[19].x;
            faces[17].y[1] = points[19].y;
            faces[17].x[2] = points[17].x;
            faces[17].y[2] = points[17].y;
            faces[17].x[3] = points[14].x;
            faces[17].y[3] = points[14].y;

            faces[18].x[0] = points[19].x;
            faces[18].y[0] = points[19].y;
            faces[18].x[1] = points[17].x;
            faces[18].y[1] = points[17].y;
            faces[18].x[2] = points[16].x;
            faces[18].y[2] = points[16].y;
            faces[18].x[3] = points[18].x;
            faces[18].y[3] = points[18].y;

            faces[19].x[0] = points[7].x;
            faces[19].y[0] = points[7].y;
            faces[19].x[1] = points[18].x;
            faces[19].y[1] = points[18].y;
            faces[19].x[2] = points[16].x;
            faces[19].y[2] = points[16].y;
            faces[19].x[3] = points[13].x;
            faces[19].y[3] = points[13].y;

            faces[20].x[0] = points[13].x;
            faces[20].y[0] = points[13].y;
            faces[20].x[1] = points[16].x;
            faces[20].y[1] = points[16].y;
            faces[20].x[2] = points[17].x;
            faces[20].y[2] = points[17].y;
            faces[20].x[3] = points[14].x;
            faces[20].y[3] = points[14].y;

            faces[20].x[0] = points[13].x;
            faces[20].y[0] = points[13].y;
            faces[20].x[1] = points[16].x;
            faces[20].y[1] = points[16].y;
            faces[20].x[2] = points[21].x;
            faces[20].y[2] = points[21].y;
            faces[20].x[3] = points[20].x;
            faces[20].y[3] = points[20].y;

            faces[21].x[0] = points[13].x;
            faces[21].y[0] = points[13].y;
            faces[21].x[1] = points[20].x;
            faces[21].y[1] = points[20].y;
            faces[21].x[2] = points[22].x;
            faces[21].y[2] = points[22].y;
            faces[21].x[3] = points[14].x;
            faces[21].y[3] = points[14].y;

            faces[22].x[0] = points[14].x;
            faces[22].y[0] = points[14].y;
            faces[22].x[1] = points[17].x;
            faces[22].y[1] = points[17].y;
            faces[22].x[2] = points[23].x;
            faces[22].y[2] = points[23].y;
            faces[22].x[3] = points[22].x;
            faces[22].y[3] = points[22].y;

            faces[23].x[0] = points[17].x;
            faces[23].y[0] = points[17].y;
            faces[23].x[1] = points[16].x;
            faces[23].y[1] = points[16].y;
            faces[23].x[2] = points[21].x;
            faces[23].y[2] = points[21].y;
            faces[23].x[3] = points[23].x;
            faces[23].y[3] = points[23].y;

            faces[24].x[0] = points[20].x;
            faces[24].y[0] = points[20].y;
            faces[24].x[1] = points[21].x;
            faces[24].y[1] = points[21].y;
            faces[24].x[2] = points[23].x;
            faces[24].y[2] = points[23].y;
            faces[24].x[3] = points[22].x;
            faces[24].y[3] = points[22].y;

            //este for asigna a cada face una Z la cual depende de los vertices que lo conforman
            for(int i = 0; i<25; i++){ 
               for(int k = 0; k<4; k++)
                  for(int y = 0; y<24; y++){
                     if(faces[i].x[k] == points[y].x && faces[i].y[k] == points[y].y){
                        if(k == 0){
                           faces[i].z += z[y];
                        }
                        else if(faces[i].z < vertices[y].z){
                           faces[i].z += z[y];
                        }
                     }
                  }
                  faces[i].z = faces[i].z/4;
            }

            Arrays.sort(faces); //arregla el arreglo de mayor a menor
            
            for(int i = 23; i>=0; i--){

               System.out.printf("arreglo [%d] :%f\n",i,faces[i].z);
               //g.setColor(new Color(colores[i*3],colores[i*3],colores[i*2],255));  
               g.setColor(new Color(r[i],v[i],a[i]));
               //g.setColor(new Color(0,249-(j*5),0+(j*5),255));
               g.fillPolygon(faces[i].x,faces[i].y,faces[i].n);    
               
              
            }



            // draw the wireframe
            for ( j = 0; j < edges.length; ++j ) {
               g.setColor(new Color(0,249-(j*5),0+(j*5)));
               g.drawLine(
                  points[ edges[j].a ].x, points[ edges[j].a ].y,
                  points[ edges[j].b ].x, points[ edges[j].b ].y
               );
            }
         }
      }  // end paintComponent()
    } // end nested class DisplayPanel

   // ------------------- Event handling methods ----------------------

   public void focusGained(FocusEvent evt) {
      focussed = true;
      canvas.repaint();
   }

   public void focusLost(FocusEvent evt) {
      focussed = false;
      canvas.repaint();
   }

   public void keyTyped(KeyEvent evt) {
   }  // end keyTyped()

   public void keyPressed(KeyEvent evt) {

      int key = evt.getKeyCode();  // keyboard code for the key that was pressed

      if (key == KeyEvent.VK_LEFT) {
         azimuth += 5;
         canvas.repaint();
      }
      else if (key == KeyEvent.VK_RIGHT) {
         azimuth -= 5;
         canvas.repaint();
      }
      else if (key == KeyEvent.VK_UP) {
         elevation -= 5;
         canvas.repaint();
      }
      else if (key == KeyEvent.VK_DOWN) {
         elevation += 5;
         canvas.repaint();
      }

   }  // end keyPressed()

   public void keyReleased(KeyEvent evt) {
      // empty method, required by the KeyListener Interface
   }

   public void mousePressed(MouseEvent evt) {
      canvas.requestFocus();
   }

   public void mouseEntered(MouseEvent evt) { }  // Required by the
   public void mouseExited(MouseEvent evt) { }   //    MouseListener
   public void mouseReleased(MouseEvent evt) { } //       interface.
   public void mouseClicked(MouseEvent evt) { }
   public void mouseDragged( MouseEvent e ) { }
} // end class