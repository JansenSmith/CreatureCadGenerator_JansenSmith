import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
def base;
//Check if the device already exists in the device Manager
if(args==null){
	base=DeviceManager.getSpecificDevice( "CarlTheWalkingRobot",{
			//If the device does not exist, prompt for the connection
			MobileBase m = MobileBaseLoader.fromGit(
				"https://github.com/madhephaestus/carl-the-hexapod.git",
				"CarlTheRobot.xml"
				)
			if(m==null)
				throw new RuntimeException("Arm failed to assemble itself")
			println "Connecting new device robot arm "+m
			return m
		})
}else
	base=args.get(0)
	
def script = ["https://gist.github.com/e54cfebe4f55fb0549dd.git","ExampleCadGenerator.groovy"]as String[]
base.setGitCadEngine(script)
for(DHParameterKinematics appendge: base.getAllDHChains()){
	appendge.setGitCadEngine(script)
}

return null;