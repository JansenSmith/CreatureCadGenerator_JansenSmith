return new ICadGenerator(){
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
	public ArrayList<CSG> generateBody(MobileBase b) {
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
	@Override 
	public ArrayList<File> generateStls(MobileBase b,File baseDirForFiles) {
		return null;
	}
};