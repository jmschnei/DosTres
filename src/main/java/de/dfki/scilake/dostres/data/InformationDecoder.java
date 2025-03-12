package de.dfki.scilake.dostres.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import javax.activation.UnsupportedDataTypeException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.data.Person;
import org.grobid.core.document.Document;
import org.grobid.core.document.DocumentPiece;
import org.grobid.core.document.DocumentPointer;
import org.grobid.core.engines.label.SegmentationLabels;
import org.grobid.core.engines.label.TaggingLabel;
import org.grobid.core.layout.Block;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.tei_c.ns._1.TEI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dfki.scilake.dostres.engine.GrobidEngine;

@Component
public class InformationDecoder {

	public InformationDecoder(){
	}

	public File decodeMultipartfile(MultipartFile file, String path, String format) throws Exception {
		File result = null;
		if(path==null || path.equalsIgnoreCase("")) {
			Path p = Files.createTempFile("pdffile_", ".pdf");
			result = p.toFile();
		}
		else {
			result = new File(path);
		}
		file.transferTo(result);
		//		Format inFormat = Format.fromValue(format);
		//		if (inFormat.equals(Format.PDF)) {
		//			
		//		}
		////		else if (inFormat.equals(Format.WORD)) {
		////			
		////		} 
		//		else {
		//			throw new Exception("[InformationDecoder (decodeFile)] Input Format not supported.");
		//		}		
		return result;
	}

	public String encode(Object output, String format) throws Exception {
		String result = null;
		Format outFormat = Format.fromValue(format);
		// if(output instanceof LegalDocument){
		// 	LegalDocument ld = (LegalDocument) output;
		// 	switch (outFormat) {
		// 		case XML:
		// 			return ld.toXML().toString();
		// 		case RDF:
		// 			return ld.toRDF(false,"RDF").toString();
		// 		case TURTLE:
		// 			return ld.toRDF(false,"TURTLE").toString();
		// 		case JSON:
		// 			return ld.toJSON().toString();
		// 		default:
		// 			throw new UnsupportedDataTypeException("[InformationDecoder (encode)] Output Format not recognized.");
		// 	}
		// }
		if(outFormat==null) {
			throw new UnsupportedDataTypeException("[InformationDecoder (encode)] Output Format not recognized.");
		}
		if (outFormat.equals(Format.PLAINTEXT)) {
			throw new Exception("[InformationDecoder (encode)] Output Format not implemented.");
		}
		else if (outFormat.equals(Format.XML)) {
			result = output.toString();
		}
		else if (outFormat.equals(Format.JSON)) {
			result = grobidXMLToJSON(output.toString());
		} 
		else if (outFormat.equals(Format.TURTLE)) {
			result = grobidXMLToRDF(output.toString(), "TURTLE");
		}
		else {
			throw new Exception("[InformationDecoder (encode)] Output Format not supported.");
		}		
		return result;
	}

	public String fileExtension(String format) throws Exception {
		Format outFormat = Format.fromValue(format);
		switch (outFormat) {
			case XML:
				return ".xml";
			case RDF:
				return ".rdf";
			case JSON:
				return ".json";
			case TURTLE:
				return ".ttl";
			case PLAINTEXT:
				return ".txt";
			default:
				throw new UnsupportedDataTypeException("[InformationDecoder (encode)] Output Format not recognized.");
		}
	}

	/**
	 * Method to convert XML (TEI format from Grobid) into RDF (in TURTLE format)
	 * 
	 * @param content String containing XML with TEI format
	 * @return String containing RDF (TURTLE)
	 * @throws Exception If something goes wrong
	 */
	private String grobidXMLToRDF(String content, String format) throws Exception {
		/**
		 * Generation of Java Object (through JAXB and POJO)
		 */
	    JAXBContext jaxbContext = null;
	    jaxbContext = JAXBContext.newInstance(TEI.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    TEI t = (TEI) jaxbUnmarshaller.unmarshal(IOUtils.toInputStream(content,"utf-8"));

	    
		// TODO Auto-generated method stub


		




		
		return null;
	}

	/**
	 * Method to convert XML (TEI format from Grobid) into JSON
	 * 
	 * @param content String containing XML with TEI format
	 * @return String containing JSON
	 * @throws Exception If something goes wrong
	 */
	private String grobidXMLToJSON(String content) throws Exception {
		/**
		 * Generation of Java Object (through JAXB and POJO)
		 */
	    JAXBContext jaxbContext = null;
	    jaxbContext = JAXBContext.newInstance(org.tei_c.ns._1.TEI.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    org.tei_c.ns._1.TEI t = (org.tei_c.ns._1.TEI) jaxbUnmarshaller.unmarshal(IOUtils.toInputStream(content,"utf-8"));
	    /**
	     * Conversion of Java Object to JSON (through Jackson)
	     */
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.setSerializationInclusion(Include.NON_NULL);
	    mapper.setSerializationInclusion(Include.NON_EMPTY);
	    String jsonInString = mapper.writeValueAsString(t);
	    //System.out.println(jsonInString);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
		//System.out.println(json);
		//return jsonInString;
		return json;
	}

	public static void main(String[] args) throws Exception {
		// InformationDecoder decoder = new InformationDecoder();
		String path2 = "dostres/src/main/resources/exampledata/file1.xml";
		// String content = FileUtils.readFileToString(new File(path2),"utf-8");

		
		// String s1 = decoder.grobidXMLToJSON(content);
		// System.out.println("JSON: "+s1);

		// String path_json = "dostres/src/main/resources/exampledata/file1.json";
		// File fOutput = null;
		// fOutput = new File(path_json);
		// OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fOutput));		
		// osw.write(s1);
		// osw.close();
		// GrobidEngine grobid = new GrobidEngine("/Users/julianmorenoschneider/Documents/DFKI/Projects/SciLake/Developments/grobid-0.7.2/grobid-home");

		// Document d = null;
        // try {
        //     FileInputStream fileIn = new FileInputStream("dostres/src/main/resources/exampledata/file1.ser");
        //     ObjectInputStream in = new ObjectInputStream(fileIn);
        //     d = (Document) in.readObject();
        //     in.close();
        //     fileIn.close();
		// } catch (IOException i) {
        //     i.printStackTrace();
        //     return;
        // } catch (ClassNotFoundException c) {
        //     System.out.println("Employee class not found");
        //     c.printStackTrace();
        //     return;
        // }

//         List<Block> blocks = d.getBlocks();

//         for (Block block : blocks) {
//             if (block.getNbTokens() == 0)
//                 continue;

//             int start = block.getStartToken();
//             int end = block.getEndToken();

//             if (start == -1) {
//                 continue;
//             }

//             for (int i = start; i < end; i++) {
//                 //assertEquals(d.getTokenizations().get(i), block.getTokens().get(i - start));
//             }
// //            assertTrue(endPtr.getTokenBlockPos() < endBlock.getTokens().size());
//         }

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
        //         DocumentPointer startPtr = p.getLeft();
        //         DocumentPointer endPtr = p.getRight();

        //         Block endBlock = d.getBlocks().get(endPtr.getBlockPtr());
		// 		//assertTrue(endPtr.getTokenBlockPos() < endBlock.getTokens().size());
        //     }
        // }

		FileInputStream fileIS = new FileInputStream(path2);
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		org.w3c.dom.Document xmlDocument = builder.parse(fileIS);
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "//body/div";
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		System.out.println(nodeList.getLength());

		for (int k = 0; k < nodeList.getLength(); k++) {
            Node node = nodeList.item(k);
			Element e = (Element) node;
            NodeList heads = e.getElementsByTagName("head");
        	for (int j = 0; j < heads.getLength(); j++) {
                Node head = heads.item(j);
				Element head_element = (Element) head;
				System.out.println(head_element.getAttribute("n"));
				System.out.println(head_element.getTextContent());
			}
            NodeList ps = e.getElementsByTagName("p");
        	for (int j = 0; j < ps.getLength(); j++) {
                Node p = ps.item(j);
				Element p_element = (Element) p;
				System.out.println(p_element.getTextContent());
			}
		}


	}
}
