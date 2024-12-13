//============================================================
// This script prints volumes for ATOF detector
//============================================================
import org.jlab.geom.base.*;
import org.jlab.clasrec.utils.*;
import org.jlab.geom.detector.alert.ATOF.*;
import org.jlab.detector.units.SystemOfUnits.Length;
import org.jlab.detector.base.DetectorType;
import org.jlab.detector.base.GeometryFactory;
import org.jlab.detector.calib.utils.DatabaseConstantProvider;
import org.jlab.geom.prim.Point3D;

//Will be used when calibration constants are defined
DatabaseConstantProvider cp = new DatabaseConstantProvider(11, "default");

//AlertTOFFactory is the class holding everything for the geometry
AlertTOFFactory factory = new AlertTOFFactory();
//Detector is the class holding the full detector geometry
//This function builds the detector according to the defined geometry
//With the calib constants that are loaded
//Building the detector means creating:
//The sectors holding superlayers
//The superlayers holding layers
//The layers holding components
//They are referred with indices
//The components are the scintillator paddles, their geometry is defined
Detector atof = factory.createDetectorCLAS(cp);

//This is for creating an output file with our geometry
def outFile = new File("atof_geometry.txt");
def writer=outFile.newWriter();
//header for the output
writer << "sector,superlayer,layer,component,x0,y0,z0,x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4,x5,y5,z5,x6,y6,z6,x7,y7,z7\n";

//The detector has been built with 15 sectors
int nsectors = atof.getNumSectors();
//We can start looping through them
for(int isec=0; isec<nsectors; isec++) 
{
	//We can get the superlayers from the sectors
	//There are two SL
	//SL=0/bottom/bar and SL=1/top/wedge
	int nsuperlayers = atof.getSector(isec).getNumSuperlayers();
	//We can loop through them
	for(int isl=0; isl<nsuperlayers; isl++) 
	{
		//Each SL is divided into 4 layers = quarters of sectors = 1 bar+1 wedge
		int nlayers = atof.getSector(isec).getSuperlayer(isl).getNumLayers();
		//We can loop through them
		for(int ilay=0; ilay<nlayers; ilay++) 
		{
			//Layers are divided in components = z slices
			int ncomponents = atof.getSector(isec).getSuperlayer(isl).getLayer(ilay).getNumComponents(); 
			if(ncomponents!=0) 
			{  
			   if(isl==0)
			   	{
					//Bottom bar not sliced in z, index=10
					Component comp = atof.getSector(isec).getSuperlayer(isl).getLayer(ilay).getComponent(10);
                                        writer<< writeString(isec, isl, ilay, comp);
				}
				else
				{
					//Top wedge has 10 slices over which we can start looping
					for(int icomp=0; icomp<ncomponents; icomp++) 
					{
						Component comp = atof.getSector(isec).getSuperlayer(isl).getLayer(ilay).getComponent(icomp);
						writer<< writeString(isec, isl, ilay, comp);
					}
				}
			}
		}
	} 	
}
writer.close();

public String writeString(int sector, int superlayer,int layer, Component comp) {
        StringBuilder str = new StringBuilder();

	//Each bar or wedge is defined by 8 points
	//The 8 points are their 8 vertices ("corners") 
	//top face
	Point3D p0 = comp.getVolumePoint(0);
	double top_x_0 = p0.x();
	double top_y_0 = p0.y();
	double top_z_0 = p0.z();
	Point3D p1 = comp.getVolumePoint(1);
	double top_x_1 = p1.x();
	double top_y_1 = p1.y();
	double top_z_1 = p1.z();
	Point3D p2 = comp.getVolumePoint(2);
	double top_x_2 = p2.x();
	double top_y_2 = p2.y();
	double top_z_2 = p2.z();
	Point3D p3 = comp.getVolumePoint(3);
	double top_x_3 = p3.x();
	double top_y_3 = p3.y();
	double top_z_3 = p3.z();
	//bottom face
	Point3D p4 = comp.getVolumePoint(4);
	double bottom_x_4 = p4.x();
	double bottom_y_4 = p4.y();
	double bottom_z_4 = p4.z();
	Point3D p5 = comp.getVolumePoint(5);
	double bottom_x_5 = p5.x();
	double bottom_y_5 = p5.y();
	double bottom_z_5 = p5.z();
	Point3D p6 = comp.getVolumePoint(6);
	double bottom_x_6 = p6.x();
	double bottom_y_6 = p6.y();
	double bottom_z_6 = p6.z();
	Point3D p7 = comp.getVolumePoint(7);
	double bottom_x_7 = p7.x();
	double bottom_y_7 = p7.y();
	double bottom_z_7 = p7.z();

	//identifiers
	str.append(String.format("%d,%d,%d,%d,", sector, superlayer, layer, comp.getComponentId()));		
	str.append(String.format("%.4f,%.4f,%.4f,", top_x_0, top_y_0,top_z_0));
	str.append(String.format("%.4f,%.4f,%.4f,", top_x_1, top_y_1,top_z_1));
	str.append(String.format("%.4f,%.4f,%.4f,", top_x_2, top_y_2,top_z_2));
	str.append(String.format("%.4f,%.4f,%.4f,", top_x_3, top_y_3,top_z_3));
	str.append(String.format("%.4f,%.4f,%.4f,", bottom_x_4, bottom_y_4,bottom_z_4));
	str.append(String.format("%.4f,%.4f,%.4f,", bottom_x_5, bottom_y_5,bottom_z_5));
	str.append(String.format("%.4f,%.4f,%.4f,", bottom_x_6, bottom_y_6,bottom_z_6));
	str.append(String.format("%.4f,%.4f,%.4f", bottom_x_7, bottom_y_7,bottom_z_7));
	str.append("\n");
        
        return str.toString();
}
