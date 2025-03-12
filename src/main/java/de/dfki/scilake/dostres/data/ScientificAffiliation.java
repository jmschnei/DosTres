package de.dfki.scilake.dostres.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.grobid.core.data.Affiliation;
import org.grobid.core.data.Person;
import org.json.JSONArray;
import org.json.JSONObject;

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
}
