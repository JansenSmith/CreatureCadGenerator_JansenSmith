import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
//Create the kinematics model from the xml file describing the D-H compliant parameters. 
def file=["https://github.com/madhephaestus/carl-the-hexapod.git","CarlTheRobot.xml"]as String[]
String xmlContent = ScriptingEngine.codeFromGit(file[0],file[1])[0]

println "Loading the robot"
MobileBase base=null;
if(DeviceManager.getSpecificDevice(MobileBase.class, "CarlTheWalkingRobot")==null){
	BowlerStudio.speak("Connecting CarlTheWalkingRobot.");
	base = new MobileBase(IOUtils.toInputStream(xmlContent, "UTF-8"));
	DeviceManager.addConnection(base,"CarlTheWalkingRobot");
}else{
	base = (MobileBase)DeviceManager.getSpecificDevice(MobileBase.class, "CarlTheWalkingRobot");
}


def script = ["https://gist.github.com/e54cfebe4f55fb0549dd.git","ExampleCadGenerator.groovy"]as String[]
base.setGitCadEngine(script)
for(DHParameterKinematics appendge: base.getAllDHChains()){
	appendge.setGitCadEngine(script)
}

return null;