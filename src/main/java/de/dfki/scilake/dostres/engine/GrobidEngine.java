package de.dfki.scilake.dostres.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GrobidEngine {

    Logger logger = Logger.getLogger(GrobidEngine.class.getName());

	Engine engine;
	GrobidHomeFinder grobidHomeFinder;
	
//	@Value("${grobid.homepath}")
//	private String pGrobidHome = null;
	
	public GrobidEngine(@Value("${grobid.homepath}") String pGrobidHome) {
	    System.out.println(">>>>>>>> GROBID_HOME_PATH=" + pGrobidHome);
		grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
	    GrobidProperties.getInstance(grobidHomeFinder);
	    System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
		engine = GrobidFactory.getInstance().createEngine();
	}
	
    @PostConstruct
    public void seetup() {
    }

    public void init(String pGrobidHome) {
		System.out.println(">>>>>>>> GROBID_HOME_PATH=" + pGrobidHome);
		grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
	    GrobidProperties.getInstance(grobidHomeFinder);
	    System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
		engine = GrobidFactory.getInstance().createEngine();
    }
	
	public JSONObject listModels() {
		JSONObject json = new JSONObject();
		json.put("Grobid model", "heading");
		return json;
	}

	public Object analyze(File auxFile, String language, String analysis) throws Exception {
		System.out.println("DEBUG: Processing document "+auxFile.getName());
		File fOutput = null;
	    GrobidAnalysisConfig config = new GrobidAnalysisConfig.GrobidAnalysisConfigBuilder().build();
		try {
			String s2 = auxFile.getAbsolutePath().replace(".pdf",".xml");
			String teiFull = engine.fullTextToTEI(auxFile, config);
			fOutput = new File(s2);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fOutput));		
			osw.write(teiFull);
			osw.close();
			System.out.println("...Extracted");
			logger.info("Document "+auxFile.getName()+ " has been properly processed.");
			return teiFull;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("...ERROR");
			String msg = "Error extracting structure from document.";
			logger.severe(msg);
			throw new Exception(msg);
		}
	}

	public String trainModel(String trainingData, String modelName, String language, String analysis) throws Exception {
		// TODO Method to be implemented.		
		throw new Exception("Method trainModel not implemented.");
	}
	
	public String checkTraining(String trainingId) throws Exception {
		// TODO Method to be implemented.
		throw new Exception("Method cehckTraining not implemented.");
	}
			
    public static void main(String[] args) {
		try {
			GrobidEngine grobid = new GrobidEngine("/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/grobid-0.7.2/grobid-home");
//			ClassPathResource cpr = new ClassPathResource();
			File file = new File("src/main/resources/exampledata/file1.pdf");
			System.out.println(file.exists());
			grobid.analyze(file, "en", "all");			
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
    
}
