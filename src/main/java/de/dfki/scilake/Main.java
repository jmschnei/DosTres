package de.dfki.scilake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.grobid.core.data.BibDataSet;
import org.apache.commons.io.IOUtils;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.document.Document;
import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dfki.scilake.dostres.data.ScientificDocument;
import de.dfki.scilake.dostres.data.ScientificDocumentPart;

public class Main {

    public static void process_Energy_Single_File() throws Exception {
        long startTime = System.nanoTime();

	    String pGrobidHome = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/grobid-0.7.2/grobid-home";
	    System.out.println(">>>>>>>> GROBID_HOME_PATH=" + pGrobidHome);
        GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
	    GrobidProperties.getInstance(grobidHomeFinder);
	    System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
		Engine engine = GrobidFactory.getInstance().createEngine();

        GrobidAnalysisConfig config = new GrobidAnalysisConfig.GrobidAnalysisConfigBuilder().build();

        long middleTime = System.nanoTime();

        String folderPath = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/ArticleSegmentation/Use Cases Data/Energy/test3";
        File folder = new File(folderPath);

        long middleTime2 = 0;

        for (File file : folder.listFiles()) {
            if(file.getName().startsWith(".") || file.isDirectory()){
                continue;
            }
            System.out.println("DEBUG: Processing document "+file.getName());
            // String teiFull = engine.fullTextToTEI(file, config);
            
            Document d = engine.fullTextToTEIDoc(file, config);
			BiblioItem bib = new BiblioItem();
			String tei = engine.processHeader(file.getAbsolutePath(), config, bib);

            //ScientificDocument dd = ScientificDocument.createScientificDocument(d.getTei(), bib);
            // System.out.println(dd.toRDF("TURTLE"));

            List<BibDataSet> bibs = d.getBibDataSets();
            for (BibDataSet bibDataSet : bibs) {
                System.out.println("==========================");
                System.out.println(bibDataSet.toString());                
                System.out.println("==========================");
                System.out.println(bibDataSet.toTEI());                
                System.out.println("==========================");
                System.out.println(bibDataSet.getResBib().toString());                
                System.out.println("==========================");
                System.out.println(bibDataSet.getResBib().toTEI(4));
                System.out.println("==========================");
                JSONObject jsonLocation = new JSONObject();
                try{
                    ObjectMapper mapper = new ObjectMapper();
                    // mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    jsonLocation=new JSONObject(mapper.writeValueAsString(bibDataSet.getResBib()));
                    System.out.println(jsonLocation.toString(4));
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                System.out.println("==========================");
            }

            if(d.getResHeader()!=null){
                System.out.println(d.getResHeader());
            }

            System.out.println("DEBUG: document "+file.getName()+"...DONE");
            if (middleTime2==0){
                middleTime2 = System.nanoTime();
            }
        }
        long endTime = System.nanoTime();
        long duration1 = (middleTime - startTime);  //divide by 1000000 to get milliseconds.
        long duration2 = (endTime - middleTime2);  //divide by 1000000 to get milliseconds.

        System.out.println("Initialization duration: "+duration1);
        System.out.println("Processing duration: "+duration2);
    }

    public static void process_Energy_UseCase_Files() throws Exception {
        long startTime = System.nanoTime();

	    String pGrobidHome = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/grobid-0.7.2/grobid-home";
	    System.out.println(">>>>>>>> GROBID_HOME_PATH=" + pGrobidHome);
        GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
	    GrobidProperties.getInstance(grobidHomeFinder);
	    System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
		Engine engine = GrobidFactory.getInstance().createEngine();

        GrobidAnalysisConfig config = new GrobidAnalysisConfig.GrobidAnalysisConfigBuilder().build();

        long middleTime = System.nanoTime();

        String folderPath = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/ArticleSegmentation/Use Cases Data/Energy/test2";
        File folder = new File(folderPath);

        long middleTime2 = 0;

        for (File file : folder.listFiles()) {
            if(file.getName().startsWith(".") || file.isDirectory()){
                continue;
            }
            System.out.println("DEBUG: Processing document "+file.getName());
            // String teiFull = engine.fullTextToTEI(file, config);
            
            Document d = engine.fullTextToTEIDoc(file, config);
			BiblioItem bib = new BiblioItem();
			String tei = engine.processHeader(file.getAbsolutePath(), config, bib);

            ScientificDocument dd = ScientificDocument.createScientificDocument(d.getTei(), bib);

            // System.out.println(dd.toRDF("TURTLE"));
            // if(d.getResHeader()!=null){
            //     System.out.println(d.getResHeader());
            // }

            System.out.println("DEBUG: document "+file.getName()+"...DONE");
            
            String s2 = folderPath + "/output/" + 
                        file.getName().replace(".pdf",".ttl");
            File fOutput = null;
            fOutput = new File(s2);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fOutput));		
            osw.write(dd.toRDF("TURTLE"));
            osw.close();	
            System.out.println("DEBUG: document "+fOutput.getName()+"...STORED");

            if (middleTime2==0){
                middleTime2 = System.nanoTime();
            }
        }
        long endTime = System.nanoTime();
        long duration1 = (middleTime - startTime);  //divide by 1000000 to get milliseconds.
        long duration2 = (endTime - middleTime2);  //divide by 1000000 to get milliseconds.

        System.out.println("Initialization duration: "+duration1);
        System.out.println("Processing duration: "+duration2);
    }

    public static void process_All_UseCase_Files() throws Exception {
        long startTime = System.nanoTime();

        long middleTime = System.nanoTime();

        String folderPath = "/Users/jumo04/Documents/DFKI/Projects/Scilake/Data/Use Cases Data/";
        String middlePath = "/contents_subset/output";
        String [] cases = new String [] {"energy", "cancer", "neuroscience", "transport_maritime", "transport_ccam"};
        //String [] cases = new String [] {"test"};

        long middleTime2 = 0;

        for (String string : cases) {
            File folder = new File(folderPath + string + middlePath);
            System.out.println("DEBUG: Processing folder "+folder.getAbsolutePath());
            for (File file : folder.listFiles()) {
                if(file.getName().startsWith(".") || file.isDirectory() || !file.getName().contains("_body")){
                    continue;
                }
                System.out.println("DEBUG: Processing document "+file.getName());
                // String teiFull = engine.fullTextToTEI(file, config);
                
                String tei = IOUtils.toString(new FileInputStream(file), "UTF-8");
                ScientificDocument dd = ScientificDocument.createScientificDocumentFromTEI(tei);

                // System.out.println(dd.toRDF("TURTLE"));
                // if(d.getResHeader()!=null){
                //     System.out.println(d.getResHeader());
                // }

                System.out.println("DEBUG: document "+file.getName()+"...DONE");
                
                // if(true){
                //     break;
                // }
                // String s2 = folderPath + string + middlePath + "/output/" + 
                String s2 = folderPath + "processed/" + string + "/" +  
                            file.getName().replace("_body.xml",".ttl");
                File fOutput = null;
                fOutput = new File(s2);
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fOutput));		
                osw.write(dd.toRDF("TURTLE"));
                osw.close();	
                System.out.println("DEBUG: document "+fOutput.getName()+"...STORED");

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
        //process_Energy_UseCase_Files();
        //process_Energy_Single_File();
        process_All_UseCase_Files();
    }
}