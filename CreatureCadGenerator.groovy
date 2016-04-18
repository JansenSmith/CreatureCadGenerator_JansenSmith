import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
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

base.setGitCadEngine(["https://gist.github.com/e54cfebe4f55fb0549dd.git","ExampleCadGenerator.groovy"]as String[])
cad = base.getGitCadEngine();

ICadGenerator cadGen =  (ICadGenerator) ScriptingEngine
					 .gitScriptRun(
            cad[0], // git location of the library
            cad[1] , // file to load
            null// no parameters (see next tutorial)
            );
println "Generating CAD"

return null;