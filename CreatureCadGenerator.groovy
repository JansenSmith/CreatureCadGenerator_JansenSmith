return new ICadGenerator(){

			@Override
			public ArrayList<CSG> generateCad(ArrayList<DHLink> dhLinks) {
				// TODO Auto-generated method stub
				return generateBody(null) ;
			}

			@Override
			public ArrayList<CSG> generateBody(MobileBase base) {
				ArrayList<CSG> allCad=new ArrayList<>();
				CSG r2d2 = (CSG)ScriptingEngineWidget.inlineGistScriptRun("2b81b9856a649a75cb47" , null);
				r2d2.setManipulator(base.getRootListener());
				allCad.add(r2d2);
				return allCad;
			}

			@Override
			public ArrayList<File> generateStls(MobileBase base,File baseDirForFiles) {
				// TODO Auto-generated method stub
				return null;
			}
    		
    	};