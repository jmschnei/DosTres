package de.dfki.scilake.dostres.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.grobid.core.data.BiblioItem;
import org.grobid.core.data.Metadata;
import org.grobid.core.data.Person;
import org.grobid.core.document.Document;
import org.grobid.core.document.DocumentPiece;
import org.grobid.core.document.DocumentPointer;
import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.engines.label.SegmentationLabels;
import org.grobid.core.engines.label.TaggingLabel;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.layout.Block;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tei_c.ns._1.TEI;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.SortedSetMultimap;

import de.dfki.scilake.dostres.data.ScientificAuthor;

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

	public Object analyzeToString(File auxFile, String language, String analysis) throws Exception {
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

	public Object analyze(File auxFile, String language, String analysis, boolean storeOutputInFile) throws Exception {
		System.out.println("DEBUG: Processing document "+auxFile.getName());
	    GrobidAnalysisConfig config = new GrobidAnalysisConfig.GrobidAnalysisConfigBuilder().build();
		try {
			Document teiFull = engine.fullTextToTEIDoc(auxFile, config);			
			if(storeOutputInFile){
				String s2 = auxFile.getAbsolutePath().replace(".pdf",".xml");
				File fOutput = null;
				fOutput = new File(s2);
				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fOutput));		
				osw.write(teiFull.getTei());
				osw.close();	
			}
			return teiFull;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("...ERROR");
			String msg = "Error extracting structure from document.";
			logger.severe(msg);
			throw new Exception(msg);
		}
	}

	public Object analyzeFromString(String auxFile, String language, String analysis, boolean storeOutputInFile) throws Exception {
		System.out.println("DEBUG: Processing document "+auxFile);
	    GrobidAnalysisConfig config = new GrobidAnalysisConfig.GrobidAnalysisConfigBuilder().build();
		try {
			//System.out.println(d.getTei());
			BiblioItem bib = new BiblioItem();
			String tei = engine.processHeader(auxFile, config, bib);

			System.out.println(bib.getTitle());
			System.out.println(bib.getAbstract());
			System.out.println(bib.getKeywords());
			List<Person> persons = bib.getFullAuthors();
			for (Person person : persons) {
				// System.out.println(person.toString());
				ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                String json_person = mapper.writeValueAsString(person);
				// System.out.println(json_person);
				ScientificAuthor sci = new ScientificAuthor("http://scilake-project.eu/res", person);
				System.out.println(sci.toJSON());
			}

			// Document d = engine.fullTextToTEIDoc(new File(auxFile), config);			

	// 	List<Block> blocks = d.getBlocks();
	// 		if(blocks!=null){
	// 		for (Block block : blocks) {
	// 			System.out.println("BLOCK: ---------------");
	// 			System.out.println(block.toString()); 
	// 			System.out.println(block.getText());
	// 			// if (block.getNbTokens() == 0)
	// 			// 	continue;

	// 			int start = block.getStartToken();
	// 			int end = block.getEndToken();

	// 			if (start == -1) {
	// 				continue;
	// 			}

	// 			for (int i = start; i < end; i++) {
	// 				//assertEquals(d.getTokenizations().get(i), block.getTokens().get(i - start));
	// 			}
	// //            assertTrue(endPtr.getTokenBlockPos() < endBlock.getTokens().size());
	// 		}
	// 	}


		// Metadata md = d.getMetadata();

		// SortedSetMultimap<String,DocumentPiece> sort = d.getLabeledBlocks();
		// for (String key : sort.keys()) {
		// 	System.out.println("================ KEY: ===================");
		// 	System.out.println(key);
		// 	System.out.println(sort.get(key));
		// }

        // for (TaggingLabel l : Arrays.asList(SegmentationLabels.BODY, SegmentationLabels.REFERENCES, SegmentationLabels.HEADER, SegmentationLabels.ACKNOWLEDGEMENT, SegmentationLabels.ANNEX,
        //     SegmentationLabels.FOOTNOTE, SegmentationLabels.HEADNOTE, SegmentationLabels.TOC)) {
		// 		System.out.println(l.getLabel());
		// 		SortedSet<DocumentPiece> parts = d.getDocumentPart(l);
        //     if (parts == null) {
		// 		System.out.println("NULL");
        //         continue;
        //     }
        //     for (DocumentPiece p : parts) {
		// 		System.out.println(p.toString());				
		// 		System.out.println(d.getDocumentPieceText(p));

        //         DocumentPointer startPtr = p.getLeft();
        //         DocumentPointer endPtr = p.getRight();

        //         Block endBlock = d.getBlocks().get(endPtr.getBlockPtr());
		// 		//assertTrue(endPtr.getTokenBlockPos() < endBlock.getTokens().size());
        //     }
        // }

        // for (TaggingLabel l : Arrays.asList(SegmentationLabels.BODY)) {
		// 		System.out.println(l.getLabel());
		// 		SortedSet<DocumentPiece> parts = d.getDocumentPart(l);
				
        //     if (parts == null) {
		// 		System.out.println("NULL");
        //         continue;
        //     }
        //     for (DocumentPiece p : parts) {
		// 		System.out.println(p.toString());				
		// 		System.out.println(d.getDocumentPieceText(p));

        //     }
        // }


			return tei;
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
			File file = new File("dostres/src/main/resources/exampledata/file1.pdf");
			System.out.println(file.exists());
			//Document d = (Document) grobid.analyze(file, "en", "all",false);

			// Document d = (Document) grobid.analyze(file, "en", "all",false);

			String tei = (String) grobid.analyzeFromString("dostres/src/main/resources/exampledata/file1.pdf", "en", "all",false);

			// //System.out.println(d.getTei());
			// BiblioItem bib = d.getResHeader();

			
			// System.out.println(bib.getTitle());
			// System.out.println(bib.getAbstract());
			// System.out.println(bib.getKeywords());
			// List<Person> persons = bib.getFullAuthors();
			// for (Person person : persons) {
			// 	System.out.println(person.toString());
			// }

			// FileOutputStream fileOut = new FileOutputStream("dostres/src/main/resources/exampledata/file1.ser");
            // ObjectOutputStream out = new ObjectOutputStream(fileOut);
            // out.writeObject(d);
            // out.close();
            // fileOut.close();
            // System.out.println("Serialized data is saved in file1.ser");
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
    
}
