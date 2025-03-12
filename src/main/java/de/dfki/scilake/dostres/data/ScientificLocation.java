package de.dfki.scilake.dostres.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.grobid.core.data.Person;
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
public class ScientificLocation extends BaseAnnotation{

	@JsonProperty("location_name")
    String name;

	//@JsonProperty("location_type")
    String location_type;

	public ScientificLocation() {
	}
	
	/**
	 * Creates a simple ScientificAuthor and calculates the new id.
	 * @param _referenceContext 
	 * @param _person The person object identified by Grobid
	 */
	public ScientificLocation(String referenceContext, String name){
		super(referenceContext);
        this.types = Arrays.asList(new String[]{"scilake:ScientificLocation"});
		this.name = name;
        id = generateId(this.referenceContext);
	}

    public String generateId(String referenceContext) {
        String uid = UUID.randomUUID().toString().substring(0,8);
    	if(referenceContext==null || referenceContext.equalsIgnoreCase("")) {
    		return "http://scilake-project.eu/res/location/" + name.replace(" ", "_");
    	}
    	if(referenceContext.contains("#")) {
            return referenceContext.split("#")[0]+"/location/"+name.replace(" ", "_");
    	}
    	else {
            return referenceContext + "/location/"+name.replace(" ", "_");
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

    /**
     * Writes JSONLD.
     * The task is delegated to Jackson.
     */
    public JSONObject toJSONObject()
    {
        JSONObject jsonLocation = new JSONObject();
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            jsonLocation=new JSONObject(mapper.writeValueAsString(this));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return jsonLocation;
    }

}
