package de.dfki.scilake.dostres.api;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
// import org.xmlunit.builder.DiffBuilder;
// import org.xmlunit.diff.DefaultNodeMatcher;
// import org.xmlunit.diff.Diff;
// import org.xmlunit.diff.ElementSelectors;

import de.dfki.scilake.dostres.data.InformationDecoder;
import de.dfki.scilake.dostres.engine.GrobidEngine;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * @author julianmorenoschneider
 * @project documentstructurerecognition (dostres)
 * @package de.dfki.scilake.dostres.api
 * @date 20.02.2025
 *
 * Class describing the REST API endpoints available in the DOSTRES tool
 */
@RestController
@RequestMapping("dostres")
@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
//,
//        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
//        )
@OpenAPIDefinition(
		info = @Info(
				title = "Document Structure Recognition Rest API",
				version = "0.0.1",
				description = "",
				contact = @Contact(name = "Scilake DFKI Team",
				email = "julian.moreno_schneider@dfki.de")))
public class RestAPIController {

	Logger logger = Logger.getLogger(RestAPIController.class.getName());

	@Autowired
	GrobidEngine grobid;

	//@Autowired
	//LegalEngine legal;

	@Autowired
	InformationDecoder decoder;

	@PostConstruct
	public void setup() {
		try {
		} catch (Exception e) {
			logger.severe(e.getMessage());
			System.exit(0);
		}
	}
	
	@Operation(hidden=true)
	@RequestMapping(value = "/testURL", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<String> testURL(
			@RequestParam(value = "preffix", required = false) String preffix,
            @RequestBody(required = false) String postBody) throws Exception {
    	HttpHeaders responseHeaders = new HttpHeaders();
    	responseHeaders.add("Content-Type", "text/plain");
    	ResponseEntity<String> response = new ResponseEntity<String>("The restcontroller of Document Structure Recognition (DOSTRES) Service is working properly", responseHeaders, HttpStatus.OK);
    	return response;
	}

	/**
	 * Method of the endpoint for semantically analysing information (text, documents, etc.) 
	 * 
	 * @param analysis Analysis from Grobid to use
	 * @param language Language of the text in the document
	 * @param acceptHeader Output format of the processed information (XML, JSON and TURTLE are supported).
	 * @param contentTypeHeader Format of the file to be processed (PDF is supported).
	 * @param allParams generic parameter not used
	 * @param file File to be processed
	 * @return
	 * @throws Exception
	 */
	@Operation(
			summary = "Analyse PDF document to recognize structural elements.",
			responses = {
					@ApiResponse(responseCode = "200",
							description = "Message containing the processed object (Scilake Document), stating that the information object (text, document, etc.) has been correctly processed.",
							content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "400",
					description = "Bad request.",
					content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
					@ApiResponse(responseCode = "500",
					description = "An error has ocurred in the server.",
					content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
			})
	@CrossOrigin
	@RequestMapping(value = "/analyzeText", method = {RequestMethod.POST})
	public ResponseEntity<String> analyzeText(
			HttpServletRequest request,
			@RequestParam(value = "analysis", required = false) String analysis,
			@RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "Accept", required = false) String acceptHeader,
			@RequestHeader(value = "Content-Type", required = false) String contentTypeHeader,
            @RequestParam Map<String, String> allParams,
            @RequestParam("file") MultipartFile file,
            @RequestBody(required = false) String postBody) throws Exception {
		if (language == null || language.equalsIgnoreCase("")) {
			language = "en";
			logger.warning("The input language is not defined. English (\"EN\") is used.");
		}
		if (analysis == null || analysis.equalsIgnoreCase("")) {
			analysis = "all";
			logger.warning("The input analysis is not defined. All (\"all\") is used.");
		}
		if (contentTypeHeader == null || contentTypeHeader.equalsIgnoreCase("")) {
			throw new Exception("Content-Type Header is not defined.");
		}
		if (acceptHeader == null || acceptHeader.equalsIgnoreCase("")) {
			language = "en";
			logger.warning("The input language is not defined. English (\"EN\") is used.");
			//throw new Exception("Accept Header is not defined.");
		}
		if (file == null) {
			throw new Exception("Error: Multipartfile 'file' body can not be null.");
		}
       	try {       		
       		/**
       		 * Extract input information from Body depending on Content-Type Header
       		 */
       		File auxFile = decoder.decodeMultipartfile(file,null,contentTypeHeader);       		
        	Object output = grobid.analyze(auxFile, language, analysis);

        	HttpHeaders responseHeaders = new HttpHeaders();
    		ResponseEntity<String> response = null;    		
            HttpStatus hStatus = HttpStatus.OK;
            String sResult = null;
        	if(output != null) {
                responseHeaders.add("Content-Type", acceptHeader);
                sResult = decoder.encode(output,acceptHeader);
        	}
        	else {
        		sResult = "Error: output object is NULL.";
        		responseHeaders.add("Content-Type", "text/plain");
            	hStatus = HttpStatus.INTERNAL_SERVER_ERROR;            	
        	}
    		response = new ResponseEntity<String>(sResult, responseHeaders, hStatus);
            return response;
        } catch (Exception e) {
        	logger.severe(e.getMessage());
            throw e;
        }
    }

}
