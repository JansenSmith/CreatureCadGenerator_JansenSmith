import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
import com.neuronrobotics.bowlerstudio.vitamins.*;
println "Loading STL file"
// Load an STL file from a git repo
// Loading a local file also works here
File servoFile = ScriptingEngine.fileFromGit(
	"https://github.com/NeuronRobotics/BowlerStudioVitamins.git",
	"BowlerStudioVitamins/stl/servo/smallservo.stl");
// Load the .CSG from the disk and cache it in memory
CSG servo  = Vitamins.get(servoFile);
return new ICadGenerator(){
	@Override 
	public ArrayList<CSG> generateCad(DHParameterKinematics d, boolean toManufacture ) {
		ArrayList<DHLink> dhLinks = d.getChain().getLinks();
		ArrayList<CSG> allCad=new ArrayList<>();
		int i=0;
		for(DHLink dh:dhLinks){
			CSG tmpSrv = servo.clone();
			tmpSrv.setManipulator(dh.getListener());
			allCad.add(tmpSrv)
			println "Generating link: "+(i++)
		}
		return allCad;
	}
	@Override 
	public ArrayList<CSG> generateBody(MobileBase b, boolean toManufacture ) {
		ArrayList<CSG> allCad=new ArrayList<>();
		double size =40;
		CSG r2d2 = new Cube(	size,// X dimention
			size,// Y dimention
			size//  Z dimention
			).toCSG()
		r2d2.setManipulator(b.getRootListener());
		allCad.add(r2d2);
		//Grab all of the DH chains on the base
		for(DHParameterKinematics chain:b.getAllDHChains()){
			println "Loading limb: "+chain.getScriptingName()
			// For each limb, generate its cad
			for(CSG csg: generateCad(chain.getChain().getLinks())){
				allCad.add(csg);// add the cad objects to be passed back
			}
		}
		return allCad;
	}
};