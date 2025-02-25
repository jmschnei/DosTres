package de.dfki.scilake.dostres.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.activation.UnsupportedDataTypeException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.tei_c.ns._1.TEI;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

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
//	    System.out.println(jsonInString);
//		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
//		System.out.println(json);
		return jsonInString;
	}

	public static void main(String[] args) throws Exception {
		InformationDecoder decoder = new InformationDecoder();
		String path2 = "src/main/resources/exampledata/file1.xml";
		String content = FileUtils.readFileToString(new File(path2),"utf-8");

		String s1 = decoder.grobidXMLToJSON(content);
		System.out.println("JSON: "+s1);
	}
}
