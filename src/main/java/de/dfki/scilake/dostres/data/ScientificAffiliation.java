package de.dfki.scilake.dostres.data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.grobid.core.data.Affiliation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
public class ScientificAffiliation extends BaseAnnotation{

	// //@JsonProperty("")
    // HashMap<String,String> map = new HashMap<String,String>();

	@JsonProperty("aff_acronym")
    String acronym;
	@JsonProperty("aff_name")
    String name;
	@JsonProperty("aff_url")
    String url;
    //"institutions":["University of Oldenburg“],
    //"departments":["Institute for Social Sciences“],
    //"laboratories":null,
	@JsonProperty("aff_country")
    String country;
	@JsonProperty("aff_postCode")
    String postCode;
	@JsonProperty("aff_postBox")
    String postBox;
	@JsonProperty("aff_region")
    String region;
	@JsonProperty("aff_settlement")
    String settlement;
	@JsonProperty("aff_addrLine")
    String addrLine;
	@JsonProperty("aff_marker")
    String marker;
	@JsonProperty("aff_addressString")
    String addressString;
	@JsonProperty("aff_affiliationString")
    String affiliationString;
	@JsonProperty("aff_failAffiliation")
    boolean failAffiliation;
	@JsonProperty("aff_rawAffiliationString")
    String rawAffiliationString;
	@JsonProperty("aff_key")
    String key;

	@JsonProperty("aff_institution")
    List<ScientificLocation> institutions;

	@JsonProperty("aff_department")
    List<ScientificLocation> departments;

	@JsonProperty("aff_laboratory")
    List<ScientificLocation> laboratories;

	public ScientificAffiliation() {
        this.types = Arrays.asList(new String[]{"scilake:ScientificAffiliation"});
        institutions = new LinkedList<ScientificLocation>();
        departments = new LinkedList<ScientificLocation>();
        laboratories = new LinkedList<ScientificLocation>();
	}
	
	/**
	 * Creates a simple ScientificAuthor and calculates the new id.
	 * @param _referenceContext 
	 * @param _person The person object identified by Grobid
	 */
	public ScientificAffiliation(String referenceContext,Affiliation aff){
		super(referenceContext);
        this.types = Arrays.asList(new String[]{"scilake:ScientificAffiliation"});
        acronym = aff.getAcronym();
        name = aff.getName();
        url = aff.getURL();
        country = aff.getCountry();
        postCode = aff.getPostCode();
        postBox = aff.getPostBox();
        region = aff.getRegion();
        settlement = aff.getSettlement();
        addrLine = aff.getAddrLine();
        marker = aff.getMarker();
        addressString = aff.getAddressString();
        affiliationString = aff.getAffiliationString();
        failAffiliation = aff.getFailAffiliation();
        rawAffiliationString = aff.getRawAffiliationString();
        key = aff.getKey();
        if(aff.getInstitutions()!=null){
            institutions = new LinkedList<ScientificLocation>();    
            for (String string : aff.getInstitutions()) {
                ScientificLocation loc = new ScientificLocation(referenceContext, string);
                institutions.add(loc);
            }
        }
        if(aff.getDepartments()!=null){
            departments = new LinkedList<ScientificLocation>();
            for (String string : aff.getDepartments()) {
                ScientificLocation loc = new ScientificLocation(referenceContext, string);
                departments.add(loc);
            }
        }
        if(aff.getLaboratories()!=null){
            laboratories = new LinkedList<ScientificLocation>();
            for (String string : aff.getLaboratories()) {
                ScientificLocation loc = new ScientificLocation(referenceContext, string);
                laboratories.add(loc);
            }
        }
        id = generateId(this.referenceContext,aff.getKey());
	}

    public String generateId(String referenceContext, String key) {
        String uid = UUID.randomUUID().toString().substring(0,8);
    	if(referenceContext==null || referenceContext.equalsIgnoreCase("")) {
    		return "http://scilake-project.eu/res/affiliation/" + key;
    	}
    	if(referenceContext.contains("#")) {
            return referenceContext.split("#")[0]+"/affiliation/"+key;
    	}
    	else {
            return referenceContext + "/affiliation/"+key;
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
    // public JSONObject toJSONObject()
    // {
    //     JSONObject jsonAffiliation = new JSONObject();
    //     try{
    //         ObjectMapper mapper = new ObjectMapper();
    //         mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //         jsonAffiliation = new JSONObject(mapper.writeValueAsString(this));

    //         JSONObject jsonAff = new JSONObject();
    //         jsonAff=new JSONObject(mapper.writeValueAsString(affiliation));
    //         for (String k : jsonAff.keySet()) {
    //             switch (k) {
    //                 case "institutions":
    //                     break;
    //                 case "departments":
    //                     JSONArray arrd = jsonAff.getJSONArray(k);
    //                     for (int z = 0;z<arrd.length();z++){
    //                         ScientificLocation loc = new ScientificLocation(referenceContext, arrd.getString(z));
    //                         departments.add(loc);
    //                     }
    //                     break;
    //                 case "laboratories":
    //                     JSONArray arrl = jsonAff.getJSONArray(k);
    //                     for (int z = 0;z<arrl.length();z++){
    //                         ScientificLocation loc = new ScientificLocation(referenceContext, arrl.getString(z));
    //                         laboratories.add(loc);
    //                     }
    //                     break;
    //                 default:
    //                     if(jsonAff.get(k)!=null){
    //                         jsonAffiliation.put(k, jsonAff.get(k));                    
    //                     }
    //                     break;
    //             }
    //         }
    //         JSONArray inst = new JSONArray();
    //         for (ScientificLocation loc : institutions) {
    //             inst.put(loc.toJSONObject());
    //         }
    //         jsonAffiliation.put("institutions", inst);
    //         JSONArray deps = new JSONArray();
    //         for (ScientificLocation loc : departments) {
    //             deps.put(loc.toJSONObject());
    //         }
    //         jsonAffiliation.put("departments", deps);
    //         JSONArray labs = new JSONArray();
    //         for (ScientificLocation loc : laboratories) {
    //             labs.put(loc.toJSONObject());
    //         }
    //         jsonAffiliation.put("laboratories", labs);
    //         //jsonPerson.put("metadata", metadata);
    //     }catch(Exception e)
    //     {
    //         e.printStackTrace();
    //     }
    //     return jsonAffiliation;
    // }

    public static ScientificAffiliation createScientificAffiliationFromNode(String id, Node node) throws Exception {
		ScientificAffiliation sca = new ScientificAffiliation();
        /**
         * Make the ID unique and related to the author information
         */
        //sca.setId(sca.generateId(id, id));

        Element e = (Element) node;
        NodeList childList = node.getChildNodes();

        String key = e.getAttribute("key");
        sca.key = key;
        boolean hasRaw = false;

        for (int h = 0; h < childList.getLength(); h++) {
            Node authNode = childList.item(h);
            String nn = authNode.getNodeName();
            if(nn.equalsIgnoreCase("address")){
                Element add = (Element) authNode;
                NodeList childs = authNode.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++) {
                    Node n = childs.item(j);
                    String nname = n.getNodeName();
                    if(nname.equalsIgnoreCase("settlement")){
                        Element elem = (Element) n;
                        String settlement = elem.getTextContent();
                        sca.settlement = settlement;
                    }
                    else if(nname.equalsIgnoreCase("region")){
                        Element elem = (Element) n;
                        String region = elem.getTextContent();
                        sca.region = region;
                    }
                    else if(nname.equalsIgnoreCase("country")){
                        Element elem = (Element) n;
                        String country = elem.getTextContent();
                        sca.country = country;
                        if(elem.hasAttribute("key")){
                            String country_key = elem.getAttribute("key");
                            // System.out.println("Country key: " + country_key);
                        }
                    }
                    else if(nname.equalsIgnoreCase("postcode")){
                        Element elem = (Element) n;
                        String postcode = elem.getTextContent();
                        sca.postCode = postcode;
                    }
                    else if(nname.equalsIgnoreCase("addrLine")){
                        Element elem = (Element) n;
                        String addrLine = elem.getTextContent();
                        sca.addrLine = addrLine;
                    }
                    else if(nname.equalsIgnoreCase("postBox")){
                        Element elem = (Element) n;
                        String postBox = elem.getTextContent();
                        sca.postBox = postBox;
                    }
                    else if(nname.equalsIgnoreCase("marker")){
                        Element elem = (Element) n;
                        String marker = elem.getTextContent();
                        sca.marker = marker;
                    }
                }
            }
            else if(nn.equalsIgnoreCase("orgName")){
                Element elem = (Element) authNode;
                String org_type = elem.getAttribute("type");
                String org = elem.getTextContent();
                ScientificLocation loc = new ScientificLocation(id, org);
                if(org_type.equalsIgnoreCase("department")){
                    sca.departments.add(loc);
                }
                else if(org_type.equalsIgnoreCase("institution")){
                    sca.institutions.add(loc);
                }
                else{
                    sca.laboratories.add(loc);
                }
            }
            else if(nn.equalsIgnoreCase("note")){
                Element elem = (Element) authNode;
                String note_type = elem.getAttribute("type");
                String note = elem.getTextContent();
                if(note_type.equalsIgnoreCase("raw_affiliation")){
                    sca.rawAffiliationString = note;
                    hasRaw = true;
                }
            }
        }
        if(!hasRaw) {
            sca.rawAffiliationString = node.getTextContent();
        }
        sca.setId(sca.generateId(id,sca.key));
        return sca;
    }
}
