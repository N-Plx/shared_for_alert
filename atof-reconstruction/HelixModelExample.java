package HelixModelExample;
import org.jlab.rec.cvt.trajectory.Helix;
import cnuphys.magfield.MagneticFields;
import org.jlab.clas.swimtools.MagFieldsEngine;
import org.jlab.geom.prim.Point3D;
import java.util.Random;

/**
 * @file HelixModelExample.java
 * @brief Some methods to calculate a random helix and its intersection with atof layers
 * 
 * The HelixModelExample class contains methods to calculate a random helix and its intersection with atof layers
 * @author pilleux
 * @date 2024-12-17
 */
public class HelixModelExample {
    
     /**
     * Generates a random double between a and b (inclusive of a, exclusive of b).
     *
     * @param a The lower bound of the range.
     * @param b The upper bound of the range.
     * @return A random double between a and b.
     */
    public static double drawRandomDouble(double a, double b) {
        Random random = new Random();
        // Ensures the value is between a and b
        return a + (b - a) * random.nextDouble();
    } 
    
    /**
     * Generates a random helix trajectory.
     *
     * @return A random helix.
     */
    public static Helix drawRandomHelix() {
        
        //drawing random momentum for the target
        double p = drawRandomDouble(100,400);
        double theta = Math.PI/180. * drawRandomDouble(60,120);
        double phi = drawRandomDouble(0,2*Math.PI);
        double px = p*Math.sin(theta)*Math.cos(phi);
        double py = p*Math.sin(theta)*Math.sin(phi);
        double pt = Math.sqrt(px*px+py*py);
        double pz = p*Math.cos(theta);
        double tandip = pz/pt;
        
        //we approximate the point of closest approach by a random vertex inside the target
        double d0 = drawRandomDouble(0,3);//radius of the target
        double phi0 = drawRandomDouble(0,360);
        double Z0 = drawRandomDouble(-75,75);//length of the target
        double xb = 0; //vx=dca(x)+xb
        double yb = 0; //vy=dca(y)+yb
        
        int q = +1;
        
        Helix helix = new Helix(pt,d0,phi0,Z0,tandip,q,xb,yb);
        return helix;
    }

    /**
     * @brief Main method to demonstrate the functionality of the HelixModel class.
     * 
     * This is a simple test that randomly draws momenta and prints where the particles would hit the atof.
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        
        System.setProperty("CLAS12DIR", "../../");
        String mapDir = "/Users/npilleux/Desktop/alert/atof-reconstruction/coatjava/etc/data/magfield";

        try {
            MagneticFields.getInstance().initializeMagneticFields(mapDir,
                    "Symm_torus_r2501_phi16_z251_24Apr2018.dat","Symm_solenoid_r601_phi1_z1201_13June2018.dat");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        MagFieldsEngine enf = new MagFieldsEngine();
        enf.init();
        Helix helix = drawRandomHelix();
        //These are the points at the surface of the bar or the wedge with radii = the ones in the geometry service
        Point3D bar_inner_surface = helix.getPointAtRadius(77);
        Point3D wedge_inner_surface = helix.getPointAtRadius(80); 
        } 
    }   

