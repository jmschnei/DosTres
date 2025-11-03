package de.dfki.scilake.dostres.execution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;
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

        File folder = new File(folderPath + "contents/");

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

                File folder2 = new File(folderPath + "tei/");
                if (!folder2.exists()){
                    folder2.mkdir();
                }
                String s4 = folderPath + "tei/" + 
                            file.getName().replace(".pdf",".xml");
                File fOutput4 = null;
                fOutput4 = new File(s4);
                if(fOutput4.exists()){
                    System.out.println("DEBUG: document "+fOutput4.getName()+"...SKIPPED");
                    continue;
                }

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
                OutputStreamWriter osw4 = new OutputStreamWriter(new FileOutputStream(fOutput4));
                osw4.write(d.getTei());
                osw4.close();
                System.out.println("DEBUG: document "+fOutput4.getName()+"...STORED");
            }
        }
        long endTime = System.nanoTime();
        long duration2 = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Processing duration: "+(duration2/1000000));
    }

    public static void uncompressFolder(String folderTar) throws Exception {
        File dir = new File(folderTar);
        File listDir[] = dir.listFiles();
        if (listDir.length!=0){
            for (File i:listDir){
                /*  Warning! this will try and extract all files in the directory
                    if other files exist, a for loop needs to go here to check that
                    the file (i) is an archive file before proceeding */
                //File iFile = new File(i);
                if (i.isDirectory() || !i.getName().endsWith(".tar.gz")){
                    continue;
                }
                // System.out.println("Processing file: " + i.getName());
                String fileName2 = i.getName().substring(0, i.getName().lastIndexOf('.'));
                fileName2 = fileName2.substring(0, fileName2.lastIndexOf('.'));

                String fileName = i.toString();
                String tarFileName = fileName +".tar";
                FileInputStream instream= new FileInputStream(fileName);
                GZIPInputStream ginstream =new GZIPInputStream(instream);
                FileOutputStream outstream = new FileOutputStream(tarFileName);
                byte[] buf = new byte[1024]; 
                int len;
                while ((len = ginstream.read(buf)) > 0) 
                {
                    outstream.write(buf, 0, len);
                }
                ginstream.close();
                outstream.close();
                //There should now be tar files in the directory
                //extract specific files from tar
                TarArchiveInputStream myTarFile=new TarArchiveInputStream(new FileInputStream(tarFileName));
                TarArchiveEntry entry = null;
                int offset;
                FileOutputStream outputFile=null;
                //read every single entry in TAR file
                while ((entry = myTarFile.getNextTarEntry()) != null) {
                    //the following two lines remove the .tar.gz extension for the folder name
                    File outputDir =  new File(i.getParent() + "/" + fileName2 + "/" + entry.getName());
                    if(! outputDir.getParentFile().exists()){ 
                        outputDir.getParentFile().mkdirs();
                    }
                    // System.out.println("Extracting: " + entry.getName());
                    // File f = entry.getFile();
                    // System.out.println("File in tar: " + f.getName());

                    //if the entry in the tar is a directory, it needs to be created, only files can be extracted
                    if(entry.isDirectory()){
                        outputDir.mkdirs();
                    }else{
                        System.out.println("" + entry.getName());
                        byte[] content = new byte[(int) entry.getSize()];
                        offset=0;
                        myTarFile.read(content, offset, content.length - offset);
                        outputFile=new FileOutputStream(outputDir);
                        IOUtils.write(content,outputFile);  
                        outputFile.close();
                    }
                }
                //close and delete the tar files, leaving the original .tar.gz and the extracted folders
                myTarFile.close();
                File tarFile =  new File(tarFileName);
                tarFile.delete();
                //processUseCaseFiles(i.getParent() + "/" + fileName2 + "/");
             }
         }
    }

    public static void processTarGzFile(String folderTar) throws Exception {
        // TarArchiveInputStream tarInput = new TarArchiveInputStream(new GZIPInputStream(new FileInputStream(tarGzFilePath)));
        // try  {
        //     TarArchiveEntry currentEntry = tarInput.getNextTarEntry();
        //     while(currentEntry != null) {
        //         File f = currentEntry.getFile();
        //         System.out.println("Processing file: ");

        //         currentEntry = tarInput.getNextTarEntry();
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // tarInput.close();
        File dir = new File(folderTar);
        File listDir[] = dir.listFiles();
        if (listDir.length!=0){
            for (File i:listDir){
                /*  Warning! this will try and extract all files in the directory
                    if other files exist, a for loop needs to go here to check that
                    the file (i) is an archive file before proceeding */
                //File iFile = new File(i);
                if (i.isDirectory() || !i.getName().endsWith(".tar.gz")){
                    continue;
                }
                // System.out.println("Processing file: " + i.getName());
                String fileName2 = i.getName().substring(0, i.getName().lastIndexOf('.'));
                fileName2 = fileName2.substring(0, fileName2.lastIndexOf('.'));

                String fileName = i.toString();
                String tarFileName = fileName +".tar";
                FileInputStream instream= new FileInputStream(fileName);
                GZIPInputStream ginstream =new GZIPInputStream(instream);
                FileOutputStream outstream = new FileOutputStream(tarFileName);
                byte[] buf = new byte[1024]; 
                int len;
                while ((len = ginstream.read(buf)) > 0) 
                {
                    outstream.write(buf, 0, len);
                }
                ginstream.close();
                outstream.close();
                //There should now be tar files in the directory
                //extract specific files from tar
                TarArchiveInputStream myTarFile=new TarArchiveInputStream(new FileInputStream(tarFileName));
                TarArchiveEntry entry = null;
                int offset;
                FileOutputStream outputFile=null;
                //read every single entry in TAR file
                while ((entry = myTarFile.getNextTarEntry()) != null) {
                    //the following two lines remove the .tar.gz extension for the folder name
                    File outputDir =  new File(i.getParent() + "/" + fileName2 + "/" + entry.getName());
                    if(! outputDir.getParentFile().exists()){ 
                        outputDir.getParentFile().mkdirs();
                    }
                    // System.out.println("Extracting: " + entry.getName());
                    // File f = entry.getFile();
                    // System.out.println("File in tar: " + f.getName());

                    //if the entry in the tar is a directory, it needs to be created, only files can be extracted
                    if(entry.isDirectory()){
                        outputDir.mkdirs();
                    }else{
                        System.out.println("" + entry.getName());
                        byte[] content = new byte[(int) entry.getSize()];
                        offset=0;
                        myTarFile.read(content, offset, content.length - offset);
                        outputFile=new FileOutputStream(outputDir);
                        IOUtils.write(content,outputFile);  
                        outputFile.close();
                    }
                }
                //close and delete the tar files, leaving the original .tar.gz and the extracted folders
                myTarFile.close();
                File tarFile =  new File(tarFileName);
                tarFile.delete();
                processUseCaseFiles(i.getParent() + "/" + fileName2 + "/");
             }
         }
    }

    public static void processFolders(String folderTar) throws Exception {
        File dir = new File(folderTar);
        File listDir[] = dir.listFiles();
        if (listDir.length!=0){
            for (File i:listDir){
                /*  Warning! this will try and extract all files in the directory
                    if other files exist, a for loop needs to go here to check that
                    the file (i) is an archive file before proceeding */
                //File iFile = new File(i);
                if (!i.isDirectory()){
                    continue;
                }
                processUseCaseFiles(i.getPath() + "/");
             }
         }
    }

    public static void main(String[] args) throws Exception {
        // initialization();
        //  String energy_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/energy/contents";
        // String energy_1_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/energy/contents_subset_1";
        // String cancer_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/cancer/contents";
        // String neuroscience_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/neuroscience/contents";
        // String transport_ccam_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/transport_ccam/contents";
        // String transport_maritime_path = "/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Data/NewData/transport_maritime/contents";
        // String outputFormat = "";
        // String language = "";
        // String analysis = "";
        // // processUseCaseFiles(energy_path);
        // // processUseCaseFiles(cancer_path);
        // // processUseCaseFiles(neuroscience_path);
        // processUseCaseFiles(transport_ccam_path);
        // processUseCaseFiles(transport_maritime_path);


        // String path = "/Volumes/TOSHIBA EXT/DFKI/Scilake/data/energy/contents/";
        // processUseCaseFiles(path);

        // String tarFile = "/Volumes/TOSHIBA EXT/DFKI/Scilake/data/energy/cT2/";
        // processTarGzFile(tarFile);

        // String tarFileFolder = "/Volumes/TOSHIBA EXT/DFKI/Scilake/data/cancer/graph.openaire.eu/datasets/scilake/pilots/2025-03-28/cancer/contents_subset/";
        // processTarGzFile(tarFileFolder);

        // String tarFolder = "/Volumes/TOSHIBA EXT/DFKI/Scilake/data/energy/compressTest/archive_10/";
        // processUseCaseFiles(tarFolder);

        //String tarFileFolder = "/Volumes/TOSHIBA EXT/DFKI/Scilake/data/transport_maritime/graph.openaire.eu/datasets/scilake/pilots/2025-03-28/transport_maritime/contents/";
        // String tarFileFolder = "/Volumes/TOSHIBA EXT/DFKI/Scilake/data/transport_ccam/";
        String tarFileFolder = "/Volumes/TOSHIBA EXT/DFKI/Scilake/data/neuroscience/graph.openaire.eu/datasets/scilake/pilots/2025-03-28/neuroscience/contents_subset/";
        processFolders(tarFileFolder);

    }

}
