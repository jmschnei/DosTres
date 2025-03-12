package de.dfki.scilake.dostres.data;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
//@JsonDeserialize(using = LynxPartDeserializer.class)
public class ScientificDocumentPart extends PositionAnnotation {

	@JsonProperty("sci_title")
	public String title;           //title, e.g. "Introduction"

	@JsonProperty("sci_number")
	public String number;           //title, e.g. "2.1"

	/// Type of document part.
	@JsonProperty("part_type")
	private String partType;

	public ScientificDocumentPart() {
	}
	
	/**
	 * Creates a simple ScientificDocumentPart and calculates the new id.
	 * @param _parent Such as http://skg.scilake-project.eu/res/doc001
	 * @param _offset_ini 0 is the beginning of the document
	 * @param _offset_end we do not verify that you give a dumb number. may crash if you lie.
	 * @param _title Fully optional
	 */
	public ScientificDocumentPart(String referenceContext, String title, String number, Integer offset_ini, Integer offset_end, String anchorOf, String partType){
		super(referenceContext, offset_ini, offset_end, anchorOf);
        this.types = Arrays.asList(new String[]{"dfki:ScientificDocumentPart", "nif:OffsetBasedString"});
		//this.parent = parent;
		this.title = title;
		this.number = number;
		this.partType = partType;
	}

	@JsonGetter //("title")
	public String getTitle() {
		return title;
	}

	@JsonSetter //("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonGetter //("sci_number")
	public String getNumber() {
		return number;
	}

	@JsonSetter //("sci_number")
	public void setNumber(String number) {
		this.number = number;
	}

	@JsonGetter //("sci_number")
	public String getPartType() {
		return partType;
	}

	@JsonSetter //("sci_number")
	public void setPartType(String partType) {
		this.partType = partType;
	}

	@JsonIgnore
	private String getHash(){
		return referenceContext.split("#")[0];
	}
	
	/**
     * Writes JSONLD.
     * The task is delegated to Jackson.
     */
    public String toJSON()
    {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }catch(Exception e)
        {
            e.printStackTrace();
            return "{}";
        }
    }
}
