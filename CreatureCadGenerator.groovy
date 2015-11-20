import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics;
import com.neuronrobotics.sdk.addons.kinematics.math.RotationNR
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;
import com.neuronrobotics.sdk.util.ThreadUtil;
import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
//Create the kinematics model from the xml file describing the D-H compliant parameters. 
String xmlContent = ScriptingEngine.codeFromGistID("bcb4760a449190206170","CarlTheRobot.xml")[0];
println "Loading the robot"
MobileBase base=null;
if(DeviceManager.getSpecificDevice(MobileBase.class, "CarlTheWalkingRobot")==null){
	BowlerStudio.speak("I did not fine a device called CarlTheWalkingRobot. Connecting CarlTheWalkingRobot.");
	base = new MobileBase(IOUtils.toInputStream(xmlContent, "UTF-8"));
	DeviceManager.addConnection(base,"CarlTheWalkingRobot");
}else{
	base = (MobileBase)DeviceManager.getSpecificDevice(MobileBase.class, "CarlTheWalkingRobot");
}
println "Loading STL file"
// Load an STL file from a git repo
// Loading a local file also works here
File servoFile = ScriptingEngine.fileFromGit(
	"https://github.com/NeuronRobotics/BowlerStudioVitamins.git",
	"BowlerStudioVitamins/stl/servo/smallservo.stl");
// Load the .CSG from the disk and cache it in memory
CSG servo  = Vitamins.get(servoFile);
ICadGenerator cadGen = ICadGenerator(){
	@Override 
	public ArrayList<CSG> generateCad(ArrayList<DHLink> dhLinks) {
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
	public ArrayList<CSG> generateBody(MobileBase base) {
		ArrayList<CSG> allCad=new ArrayList<>();
		CSG r2d2 = servo.clone();
		r2d2.setManipulator(base.getRootListener());
		allCad.add(r2d2);
		//Grab all of the DH chains on the base
		for(DHParameterKinematics chain:base.getAllDHChains()){
			println "Loading limb: "+chain.getScriptingName()
			// For each limb, generate its cad
			for(CSG csg: generateCad(chain,false)){
				allCad.add(csg);// add the cad objects to be passed back
			}
		}
		return allCad;
	}
	@Override 
	public ArrayList<File> generateStls(MobileBase base,File baseDirForFiles) {
		return null;
	}
};
println "Generating CAD"
return cadGen.generateBody(base)