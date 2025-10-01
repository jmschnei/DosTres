package de.dfki.scilake.dostres.execution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import org.grobid.core.data.BibDataSet;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.document.Document;
import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.dfki.scilake.dostres.data.InformationDecoder;
import de.dfki.scilake.dostres.data.ScientificDocument;
import de.dfki.scilake.dostres.engine.GrobidEngine;

public class FolderProcessor {

	static GrobidEngine grobid;
	static InformationDecoder decoder;

    public static void initialization(){
        String pGrobidHome = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/grobid-0.7.2/grobid-home";
        grobid = new GrobidEngine(pGrobidHome);
        decoder = new InformationDecoder();
    }

    public static void processUseCaseFiles(String folderPath) throws Exception {
        long startTime = System.nanoTime();

	    String pGrobidHome = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/grobid-0.7.2/grobid-home";
	    System.out.println(">>>>>>>> GROBID_HOME_PATH=" + pGrobidHome);
        GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
	    GrobidProperties.getInstance(grobidHomeFinder);
	    System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
		Engine engine = GrobidFactory.getInstance().createEngine();
        GrobidAnalysisConfig config = new GrobidAnalysisConfig.GrobidAnalysisConfigBuilder().build();

        long middleTime = System.nanoTime();
        File folder = new File(folderPath);
        long middleTime2 = 0;

        for (File file : folder.listFiles()) {
            if(file.getName().startsWith(".") || file.isDirectory()){
                continue;
            }
            if(file.getName().endsWith(".pdf")){
                System.out.println("DEBUG: Processing document "+file.getName());
                // String teiFull = engine.fullTextToTEI(file, config);
                
                // try {
                //     /**
                //      * Extract input information from Body
                //      */
                //     Object output = grobid.analyze(file, language, analysis,false);
                //     String sResult = decoder.encode(output, outputFormat);
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                Document d = null;
                try {
                    d = engine.fullTextToTEIDoc(file, config);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                // BiblioItem bib = new BiblioItem();
                // String tei = engine.processHeader(file.getAbsolutePath(), config, bib);
                // ScientificDocument dd = ScientificDocument.createScientificDocument(d.getTei(), bib);

                // System.out.println(dd.toRDF("TURTLE"));
                // if(d.getResHeader()!=null){
                //     System.out.println(d.getResHeader());
                // }

                System.out.println("DEBUG: document "+file.getName()+"...DONE");
                
                // String s2 = folderPath + "/output/" + 
                //             file.getName().replace(".pdf",".ttl");
                // File fOutput = null;
                // fOutput = new File(s2);
                // OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fOutput));		
                // osw.write(dd.toRDF("TURTLE"));
                // osw.close();

                // String s3 = folderPath + "/output/" + 
                //             file.getName().replace(".pdf",".xml");
                // File fOutput3 = null;
                // fOutput3 = new File(s3);
                // OutputStreamWriter osw3 = new OutputStreamWriter(new FileOutputStream(fOutput3));
                // osw3.write(tei);
                // osw3.close();	
                // System.out.println("DEBUG: document "+fOutput3.getName()+"...STORED");

                String s4 = folderPath + "/output/" + 
                            file.getName().replace(".pdf","_body.xml");
                File fOutput4 = null;
                fOutput4 = new File(s4);
                OutputStreamWriter osw4 = new OutputStreamWriter(new FileOutputStream(fOutput4));
                osw4.write(d.getTei());
                osw4.close();
                System.out.println("DEBUG: document "+fOutput4.getName()+"...STORED");

                if (middleTime2==0){
                    middleTime2 = System.nanoTime();
                }
            }
        }
        long endTime = System.nanoTime();
        long duration1 = (middleTime - startTime);  //divide by 1000000 to get milliseconds.
        long duration2 = (endTime - middleTime2);  //divide by 1000000 to get milliseconds.

        System.out.println("Initialization duration: "+duration1);
        System.out.println("Processing duration: "+duration2);
    }

    public static void main(String[] args) throws Exception {
        // initialization();
         String energy_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/energy/contents";
        String energy_1_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/energy/contents_subset_1";
        String cancer_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/cancer/contents";
        String neuroscience_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/neuroscience/contents";
        String transport_ccam_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/transport_ccam/contents";
        String transport_maritime_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/transport_maritime/contents";
        String outputFormat = "";
        String language = "";
        String analysis = "";
        // processUseCaseFiles(energy_path);
        // processUseCaseFiles(cancer_path);
        // processUseCaseFiles(neuroscience_path);
        processUseCaseFiles(transport_ccam_path);
        processUseCaseFiles(transport_maritime_path);
    }

}
