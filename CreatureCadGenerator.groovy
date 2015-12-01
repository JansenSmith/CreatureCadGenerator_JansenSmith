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

base.setCadEngine("e54cfebe4f55fb0549dd","ExampleCadGenerator.groovy")
cad = base.getCadEngine();
File code = ScriptingEngine.fileFromGistID(cad[0],cad[1]);
ICadGenerator cadGen =  (ICadGenerator) ScriptingEngine.inlineScriptRun(code, null,ShellType.GROOVY);
println "Generating CAD"
return cadGen.generateBody(base)