package de.dfki.scilake.dostres.data;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.grobid.core.data.Affiliation;
import org.grobid.core.data.Person;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Julian Moreno Schneider julian.moreno_schneider@dfki.de
 * @modified_by 
 * @project java
 * @date 07.03.2025
 * @date_modified 
 * @company DFKI
 * @description 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "type" })
public class ScientificAuthor extends BaseAnnotation{

	@JsonProperty("auth_firstname")
    String firstName;
	@JsonProperty("auth_middlename")
	String middleName;
	@JsonProperty("auth_lastname")
	String lastName;
	@JsonProperty("auth_title")
	String title;
	@JsonProperty("auth_suffix")
	String suffix;
	@JsonProperty("auth_rawname")
	String rawName;
	@JsonProperty("auth_orcid")
	String orcid;
	@JsonProperty("auth_corresp")
	boolean corresp;
	@JsonProperty("auth_affiliationsblocks")
	List<String> affiliationBlocks;
	@JsonProperty("auth_affiliationMarkers")
	List<String> affiliationMarkers;
	@JsonProperty("auth_markers")
	List<String> markers;
	@JsonProperty("auth_email")
	String email;
	@JsonProperty("auth_valid")
	boolean valid;

	@JsonProperty("affiliation")
    List<ScientificAffiliation> affiliations = new LinkedList<ScientificAffiliation>();

	public ScientificAuthor() {
        this.types = Arrays.asList(new String[]{"scilake:ScientificAuthor"});
	}
	
	/**
	 * Creates a simple ScientificAuthor and calculates the new id.
	 * @param _referenceContext 
	 * @param _person The person object identified by Grobid
	 */
	public ScientificAuthor(String referenceContext, Person person){
		super(referenceContext);
        this.types = Arrays.asList(new String[]{"scilake:ScientificAuthor"});
        firstName = person.getFirstName();
        middleName = person.getMiddleName();
        lastName = person.getLastName();
        title = person.getTitle();
        suffix = person.getSuffix();
        rawName = person.getRawName();
        orcid = person.getORCID();
        corresp = person.getCorresp();
        affiliationMarkers = person.getAffiliationMarkers();
        affiliationBlocks  = person.getAffiliationBlocks();
        markers = person.getMarkers();
        email = person.getEmail();
        valid = person.isValid();
        List<Affiliation> affs = person.getAffiliations();
        if(affs!=null){
            for (Affiliation affiliation : affs) {
                ScientificAffiliation aff = new ScientificAffiliation(referenceContext, affiliation);
                affiliations.add(aff);
            }
        }
        id = generateId(this.referenceContext);
	}

    public String generateId(String referenceContext) {
        String uid = UUID.randomUUID().toString().substring(0,8);
    	if(referenceContext==null || referenceContext.equalsIgnoreCase("")) {
    		return "http://scilake-project.eu/res/author/" + uid;
    	}
    	if(referenceContext.contains("#")) {
            return referenceContext.split("#")[0]+"/author/"+uid;
    	}
    	else {
            return referenceContext + "/author/"+uid;
    	}
    }

    /**
     * Writes JSONLD.
     * The task is delegated to Jackson.
     */
    public String toJSON()
    {
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(this);
        }catch(Exception e)
        {
            e.printStackTrace();
            return "{}";
        }
    }

    // /**
    //  * Writes JSONLD.
    //  * The task is delegated to Jackson.
    //  */
    // public String toJSON()
    // {
    //     JSONObject jsonAuthor = new JSONObject();
    //     try{
    //         ObjectMapper mapper = new ObjectMapper();
    //         mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //         JSONObject jsonPerson = new JSONObject();
    //         jsonPerson=new JSONObject(mapper.writeValueAsString(person));

    //         for (String k : jsonPerson.keySet()) {
    //             switch (k) {
    //                 case "layoutTokens":
    //                     break;
    //                 case "affiliations":
    //                     break;
    //                 default:
    //                     if(jsonPerson.get(k)!=null){
    //                         jsonAuthor.put(k, jsonPerson.get(k));                    
    //                     }
    //                     break;
    //             }
    //         }
    //         jsonAuthor.put("id", id);
    //         jsonAuthor.put("type", types);
    //         jsonAuthor.put("source", source);
    //         jsonAuthor.put("referenceContext", referenceContext);
    //         JSONArray affs = new JSONArray();
    //         for (ScientificAffiliation aff : affiliations) {
    //             affs.put(aff.toJSONObject());
    //         }
    //         jsonAuthor.put("affiliations", affs);
    //         //jsonPerson.put("metadata", metadata);
    //         return jsonAuthor.toString();
    //     }catch(Exception e)
    //     {
    //         e.printStackTrace();
    //         return "{}";
    //     }
    // }

    public static ScientificAuthor createScientificAuthorFromTEI(String id, Node node) throws Exception {
		ScientificAuthor sca = new ScientificAuthor();
        /**
         * Make the ID unique and related to the author information
         */
        sca.setReferenceContext("http://scilake-project.eu/res/"+UUID.randomUUID().toString().substring(0,8));

        NodeList authNodeList = node.getChildNodes();
        for (int h = 0; h < authNodeList.getLength(); h++) {
            Node authNode = authNodeList.item(h);
            String nn = authNode.getNodeName();
            if(nn.equalsIgnoreCase("persName")){
                NodeList childList = authNode.getChildNodes();
                for (int j = 0; j < childList.getLength(); j++) {
                    Node perNode = childList.item(j);
                    String tag = perNode.getNodeName();
                    if(tag.equalsIgnoreCase("rolename")){
                        Element elem = (Element) perNode;
                        String doc_title = elem.getTextContent();
                        sca.title = doc_title;
                    }
                    else if(tag.equalsIgnoreCase("forename")){
                        Element elem = (Element) perNode;
                        String name_type = elem.getAttribute("type");
                        String name = elem.getTextContent();
                        if(name_type.equalsIgnoreCase("first")){
                            sca.firstName = name;
                        }
                        else if(name_type.equalsIgnoreCase("middle")){
                            sca.middleName = name;
                        }
                        else{
                            // by default we assume it is a first name
                            sca.firstName = name;
                        }
                    }
                    else if(tag.equalsIgnoreCase("surname")){
                        Element elem = (Element) perNode;
                        String surname = elem.getTextContent();
                        sca.lastName = surname;
                    }
                /**
                sca.suffix = person.getSuffix();
                sca.rawName = person.getRawName();
                sca.orcid = person.getORCID();
                sca.corresp = person.getCorresp();
                sca.affiliationMarkers = person.getAffiliationMarkers();
                sca.affiliationBlocks  = person.getAffiliationBlocks();
                sca.markers = person.getMarkers();
                sca.email = person.getEmail();
                sca.valid = person.isValid();
                    */
                }
            }
            else if(nn.equalsIgnoreCase("email")){
                Element emailElem = (Element) authNode;
                String email = emailElem.getTextContent();
                sca.email = email;
            }
            else if(nn.equalsIgnoreCase("affiliation")){
                ScientificAffiliation aff = ScientificAffiliation.createScientificAffiliationFromNode(id,authNode);
                sca.affiliations.add(aff);
            }
        }
        sca.setId(sca.generateId(id));
        return sca;
    }

}
