package de.dfki.scilake.dostres.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.grobid.core.data.Affiliation;
import org.grobid.core.data.BiblioItem;
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
public class ScientificBibliography extends BaseAnnotation{

	@JsonProperty("bib_pageRange")
    String pageRange;
	@JsonProperty("bib_issue")
    String issue;
	@JsonProperty("bib_title")
    String title;
	@JsonProperty("bib_error")
    boolean error;
	@JsonProperty("bib_nbPages")
    int nbPages;
	@JsonProperty("bib_reference")
    String reference;
	// @JsonProperty("authors")
    // String authors;
	// @JsonProperty("")
    // "normalizedPublicationDate": {
    //     "month": -1,
    //     "year": 2010,
    //     "notNull": true,
    //     "yearString": "2010",
    //     "ambiguous": false,
    //     "day": -1
    // },
	@JsonProperty("bib_journal")
    String journal;
	@JsonProperty("bib_volumeBlock")
    String volumeBlock;
	@JsonProperty("bib_beginPage")
    int beginPage;
	@JsonProperty("bib_firstAuthorSurname")
    String firstAuthorSurname;
	@JsonProperty("bib_endPage")
    int endPage;
	@JsonProperty("bib_publicationDate")
    String publicationDate;
	@JsonProperty("bib_ordinal")
    int ordinal;
    //"originalAuthors": "Verbong, G.P.J., Geels, F.W.",
	// @JsonProperty("item")
    // int item = 0;
    // "supportType": null,
    // "copyright": null,
    // "year": null,
    // "s_Year": null,
    // "type": null,
    // "abstractHeader": null,
    // "originalNote": null,
    // "authorsTokensWorkingCopy": [],
    // "number": null,
    // "review": null,
    // "oaurl": null,
    // "e_Day": null,
    // "a_Day": null,
    // "meeting": null,
    // "englishTitle": null,
    // "arXivId": null,
    // "originalJournal": null,
    // "inDOI": null,
    // "dedication": null,
    // "fullEditors": null,
    // "typeDescription": null,
    // "version": null,

    // "authorString": null,
    // "s_Day": null,
    // "issn": null,
    // "phone": null,
    // "subtitle": null,
    // "isbn13": null,
    // "isbn10": null,
    // "doi": null,
    // "authors": "Verbong, G.P.J., Geels, F.W.",
    // "downloadDate": null,
    // "e_Year": null,
    // "d_Year": null,
    // "web": null,
    // "categories": null,
    // "ark": null,
    // "event": null,
    // "email": null,
    // "funding": null,
    // "address": null,
    // "journalAbbrev": null,
    // "e_Month": null,
    // "subjects": null,
    // "labeledTokens": null,
    // "abstract": null,
    // "uri": null,
    // "url": null,
    // "s_Month": null,
    // "collaboration": null,
    // "originalTitle": null,
    // "submission": null,
    // "country": null,
    // "originalEditors": null,
    // "keywords": null,
    // "istexId": null,
    // "language": null,
    // "d_Day": null,
    // "originalAbstract": null,
    // "institution": null,
    // "pii": null,
    // "a_Year": null,
    // "affiliation": null,
    // "keyword": null,
    // "day": null,
    // "normalizedSubmissionDate": null,
    // "smallImageURL": null,
    // "town": null,
    // "normalizedDownloadDate": null,
    // "degree": null,
    // "volume": null,
    // "locationPublisher": null,
    // "serverDate": null,
    // "month": null,
    // "parentItem": null,
    // "publisher": null,
    // "d_Month": null,
    // "originalAffiliation": null,
    // "normalizedServerDate": null,
    // "originalVolumeBlock": null,
    // "bookType": null,
    // "note": null,
    // "documentType": null,
    // "a_Month": null,
    // "edition": null,
    // "originalKeyword": null,
    // "error": true,
    // "publisherWebsite": null,
    // "pubnum": null,
    // "originalAddress": null,
    // "abstractTokensWorkingCopy": [],
    // "publisherPlace": null,
    // "editors": null,
    // "serieTitle": null,
    // "fullAffiliations": null,
    // "articleTitle": null,
    // "availabilityStmt": null,
    // "confidence": null,
    // "submissionDate": null,
    // "pmid": null,
    // "affiliationAddressBlock": null,
    // "largeImageURL": null,
    // "serie": null,
    // "labeledAbstract": null,
    // "workingGroup": null,
    // "location": null,
    // "pmcid": null,
    // "issne": null,
    // "bookTitle": null

	@JsonProperty("affiliation")
    List<ScientificAffiliation> affiliations = new LinkedList<ScientificAffiliation>();

	@JsonProperty("author")
    List<ScientificAuthor> authors = new LinkedList<ScientificAuthor>();

	public ScientificBibliography() {
	}
	
	/**
	 * Creates a simple ScientificAuthor and calculates the new id.
	 * @param _referenceContext 
	 * @param _person The person object identified by Grobid
	 */
	public ScientificBibliography(String referenceContext, BiblioItem bib){
		super(referenceContext);
        this.types = Arrays.asList(new String[]{"scilake:ScientificBibliographicEntry"});

        /**
         * TODO Include all needed variables that are still not included.
         */
        pageRange = bib.getPageRange();
        issue = bib.getIssue();
        title = bib.getTitle();
        error = bib.getError();
        nbPages = bib.getNbPages();
        reference = bib.getReference();
        journal = bib.getJournal();
        volumeBlock = bib.getVolumeBlock();
        beginPage = bib.getBeginPage();
        firstAuthorSurname = bib.getFirstAuthorSurname();
        endPage = bib.getEndPage();
        publicationDate = bib.getPublicationDate();
        ordinal = bib.getOrdinal();

        List<Affiliation> affs = bib.getFullAffiliations();
        if(affs!=null){
            for (Affiliation affiliation : affs) {
                ScientificAffiliation aff = new ScientificAffiliation(referenceContext, affiliation);
                affiliations.add(aff);
            }
        }
        List<Person> auths = bib.getFullAuthors();
        if(auths!=null){
            for (Person p : auths) {
                ScientificAuthor auth = new ScientificAuthor(referenceContext, p);
                authors.add(auth);
            }
        }
        id = generateId(this.referenceContext,bib.getTeiId());
	}

    public String generateId(String referenceContext,String teiId) {
        String uid = UUID.randomUUID().toString().substring(0,8);
    	if(referenceContext==null || referenceContext.equalsIgnoreCase("")) {
    		return "http://scilake-project.eu/res/reference/" + teiId;
    	}
    	if(referenceContext.contains("#")) {
            return referenceContext.split("#")[0]+"/reference/"+teiId;
    	}
    	else {
            return referenceContext + "/reference/"+teiId;
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
}
