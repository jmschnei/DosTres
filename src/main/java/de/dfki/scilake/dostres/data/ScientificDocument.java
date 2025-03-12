package de.dfki.scilake.dostres.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
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

import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.grobid.core.document.Document;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.data.Person;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.qurator.commons.BaseAnnotation;
import de.qurator.commons.LabelPositionAnnotation;
import de.qurator.commons.TextAnnotation;

/**
 * @author julianmorenoschneider
 * @project java
 * @date 27 Feb 2025
 * @date_modified 27 Feb 2025
 * @company DFKI
 * @description 
 * 
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value={"context", "offset_end"}, allowGetters = true)
@JsonPropertyOrder({"@context", "id", "type"})
public class ScientificDocument {

	/// Identifier. This must be a URI.
	@JsonProperty("id")
	@JsonAlias( {"@id"} )
	private String id;

	/// Text of the document. Only one.
	@JsonProperty("text")
	private String text;

	/// Begin index of the string
	@JsonProperty("offset_ini")
	private int offsetIni = 0;

	/// End index of the string
	@JsonProperty("offset_end")
	private int offsetEnd = 0;

	/// Types of the document.
	@JsonProperty("type")
	@JsonAlias({"@type"})
	private List<String> types = new ArrayList<String>();

	/// Parts of the document. 
	private List<ScientificDocumentPart> parts = new ArrayList<ScientificDocumentPart>();

	/// Metadata elements: version, author, date, etc.
	private Map<String, Object> metadata = new HashMap<String, Object>();

	// /// Annotations
	// @JsonProperty("annotations")
	// protected List<BaseAnnotation> annotations = new ArrayList<BaseAnnotation>();

	/// Authors
	@JsonProperty("authors")
	protected List<ScientificAuthor> authors = new ArrayList<ScientificAuthor>();

	// /// Annotations
	// @JsonProperty("references")
	// protected List<ScientificReference> references = new ArrayList<BaseAnnotation>();

////  We could also use List<LynxAnnotationUnit>, but this brings some problems in occasions to be further investigated......
//	@JsonProperty("labelUnits")
//    public List<Label> labels;

	// /// Generations: translations, summaries, new pieces of information included in the document.
	// @JsonProperty("document_annotations")
	// protected List<BaseAnnotation> docAnnotations = new ArrayList<BaseAnnotation>();

	public ScientificDocument() {
		String uid = UUID.randomUUID().toString().substring(0,8);
		setId("http://scilake-project.eu/res/"+uid);
		metadata.put("language", "en");
		//metadata.put("id_local", uid);
		setDefaultTypes();
	}

	/**
	 * Default constructor. 
	 * Two fields are mandatory if not provided, they are created randomly to grant a valid LynxDocument.
	 */
	public ScientificDocument(String text) {
		String uid = UUID.randomUUID().toString().substring(0,8);
		setId("http://scilake-project.eu/res/"+uid);
		metadata.put("language", "en");
		//metadata.put("id_local", uid);
		setText(text);
		setDefaultTypes();
		offsetEnd = text.length();
	}

	/**
	 * Minimal constructor.
	 * @param id Identifier. This must be a URI.
	 * @param idlocal Local  Identifier, which does not need to be a URI.
	 * @param text Text of the document.
	 * @param lan Language. ISO code, 2 letters.
	 */
	public ScientificDocument(String id, String text, String lan) {
		setText(text);
		setId(id);
		metadata.put("language", lan);
		//metadata.put("id_local", idlocal);
		setDefaultTypes();
		offsetEnd = text.length();
	}

	/**
	 * Copy constructor.
	 */
	public ScientificDocument(ScientificDocument doc) {
		setText(doc.getText());
		setId(doc.getId());
//		setTranslations(doc.getTranslations());
		setType(doc.getType());
		setMetadata(doc.getMetadata());
		setParts(doc.getParts());
//		setAnnotations(doc.getAnnotations());
//		setDocumentAnnotations(doc.getDocumentAnnotations());
		offsetEnd = text.length();
	}

    @JsonProperty("@context")
    public String getContext() {
        return "http://nlp.dfki.de/doc/jsonld/ScientificDocument.json";
    }

	/**
	 * @return the offsetIni
	 */
	@JsonProperty("offset_ini")
	public int getOffsetIni() {
		return offsetIni;
	}

	/**
	 * @param offsetIni the offsetIni to set
	 */
	@JsonSetter
	public void setOffsetIni(int offsetIni) {
		this.offsetIni = offsetIni;
	}

	/**
	 * @param offsetEnd the offsetEnd to set
	 */
	@JsonSetter
	public void setOffsetEnd(int offsetEnd) {
		this.offsetEnd = offsetEnd;
	}

	@JsonProperty("offset_end")
	public int getOffsetEnd() {
		return text.length();
	}

	@JsonGetter
	public String getId() {
		return id;
	}

	@JsonSetter
	public void setId(String id) {
		this.id = id;


		// TODO: Change referenceContext from all annotations and parts.
		// for (DFKIDocumentPart part : parts) {
		// 	part.referenceContext = this.getId();
		// 	part.setId(part.generateId(part.referenceContext));
		// }
	}

	public List<String> setDefaultTypes() {
		if (!types.contains("nif:Context")) { types.add("nif:Context"); }
		if (!types.contains("nif:OffsetBasedString")) { types.add("nif:OffsetBasedString"); }
		if (!types.contains("dfki:ScientificDocument")) { types.add("dfki:ScientificDocument"); }
		return types;
	}

	public List<String> getType() {
		return types;
	}

	public void setType(List<String> type) {
		this.types = type;
	}

	/**
	 * Adds a new type to a DFKIDocument
	 *
	 * @param _ype Adds a type to a DFKIDocument
	 */
	public void addType(String type) {
		this.types.add(type);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		offsetEnd = text.length();
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public void addMetadata(String key, Object obj) {
		metadata.put(key, obj);
	}

	// public List<BaseAnnotation> getAnnotations() {
	// 	return annotations;
	// }

	// public void setAnnotations(List<BaseAnnotation> annotations) {
	// 	this.annotations = annotations;
	// }

	// /**
	//  * Updates an existing annotation. If the annotation does not exist, the new
	//  * one will not be added
	//  *
	//  * * @param DFKIAnnotation The annotation
	//  * @return true if there was effectively an update.
	//  */
	// public boolean updateAnnotation(BaseAnnotation newAnnotation) {
	// 	String newannid = newAnnotation.getId();
	// 	int tam = annotations.size();
	// 	boolean found = false;
	// 	for (int i = 0; i < tam; i++) {
	// 		BaseAnnotation ann = annotations.get(i);
	// 		if (ann.getId().equals(newannid)) {
	// 			annotations.remove(i);
	// 			found = true;
	// 			break;
	// 		}
	// 	}
	// 	if (found) {
	// 		annotations.add(newAnnotation);
	// 	}
	// 	return found;
	// }

	// public boolean deleteAnnotation(String annotationId) {
	// 	int tam = annotations.size();
	// 	for (int i = 0; i < tam; i++) {
	// 		BaseAnnotation ann = annotations.get(i);
	// 		if (ann.getId().equals(annotationId)) {
	// 			annotations.remove(i);
	// 			return true;
	// 		}
	// 	}
	// 	return false;
	// }

// 	/**
// 	 * Adds an annotation. 
// 	 * The id of the annotation will be overriden.
// 	 * @param ann Annotation to be added
// 	 */
// 	public void addAnnotation(BaseAnnotation ba)
// 	{
// 		ba.referenceContext = getId();
// 		ba.setId(ba.generateId(ba.referenceContext));
// 		annotations.add(ba);
// 	}

// 	@JsonIgnore
// 	public List<BaseAnnotation> getDocumentAnnotations() {
// 		return docAnnotations;
// 	}

// 	public void setDocumentAnnotations(List<BaseAnnotation> generations) {
// 		this.docAnnotations = generations;
// 	}
	
// 	public void addDocumentAnnotation(BaseAnnotation gen) {
// //		gen.setId(this.id);
// 		docAnnotations.add(gen);
// 	}

	public List<ScientificDocumentPart> getParts() {
		return parts;
	}

	public List<ScientificAuthor> getAuthors() {
		return authors;
	}

	public void setAuthors(List<ScientificAuthor> authors) {
		this.authors = authors;
	}

	/**
	 */
	public void setParts(List<ScientificDocumentPart> parts) {
		for (ScientificDocumentPart part : parts) {
			part.referenceContext = this.getId();
			part.setId(part.generateId(part.referenceContext));
			this.parts.add(part);
		}
	}

	public void addPart(ScientificDocumentPart part) {		
		part.referenceContext = getId();
		part.setId(part.generateId(part.referenceContext));
		parts.add(part);
	}
	
	/**
	 * Comparison method
	 * TODO Probably the comparison is not complete. Some more fields have to be compared.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
//			System.out.println("The same");
			return true;
		}
		if (!(o instanceof ScientificDocument)) {
//			System.out.println("Not an instance");
			return false;
		}
		ScientificDocument that = (ScientificDocument) o;

		InputStream isThat = new ByteArrayInputStream(that.toRDF("TURTLE").getBytes());
		Model modelThat = ModelFactory.createDefaultModel();
		modelThat.read(isThat, null,"TTL");

		InputStream isThis = new ByteArrayInputStream(this.toRDF("TURTLE").getBytes());
		Model modelThis = ModelFactory.createDefaultModel();
		modelThis.read(isThis, null,"TTL");

//		model1.write(System.out, "TTL");
//		model2.write(System.out, "TTL");
//		
		if(modelThis.isIsomorphicWith(modelThat)) {
			return true;
		}
		else {
			return false;
		}
//		System.out.println(model1.isIsomorphicWith(model2));

		
////		System.out.println("Instance but ...");
////		System.out.println(Objects.equals(getId(), that.getId()));
////		System.out.println(Objects.equals(getText(), that.getText()));
////		System.out.println(Objects.equals(getMetadata(), that.getMetadata()));
//		if(Objects.equals(getId(), that.getId())
//				&& Objects.equals(getText(), that.getText())) {
//
////			Set<String> keys = metadata.keySet();
////			Set<String> keys2 = that.getMetadata().keySet();
////			System.out.println("Keys1 size: " + keys.size());
////			System.out.println("Keys1 size: " + keys.size());
////			System.out.println(Objects.equals(keys, keys2));
////			Collection<Object> objs = metadata.values();
////			Collection<Object> objs2 = that.getMetadata().values();
////			System.out.println("objs1 size: " + objs.size());
////			System.out.println("objs2 size: " + objs2.size());
////			System.out.println(Objects.equals(objs, objs2));
////			for (String key : keys) {
////				System.out.println("\t" + metadata.get(key).toString());
////				System.out.println("\t" + that.getMetadata().get(key).toString());
////			}
//			return Objects.equals(getMetadata(), that.getMetadata());
//		}
//		else {
//			return false;
//		}
	}

	public String toJSON() {
		return toJSON(true);
	}
	
	public String toJSON(boolean explicitContext) {
		try {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                String json = mapper.writeValueAsString(this);
    //			System.out.println(json);
                if (explicitContext) {
                    json = json.replace("\"http://nlp.dfki.de/doc/jsonld/ScientificDocument.json\"", getContextExplicit());
                }
                json = json.replace("\"id\"", "\"@id\"");
                json = json.replace("\"type\"", "\"@type\"");
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return "{}";
            }

		}
		catch(Exception e) {
			e.printStackTrace();
			return "{}";
		}
	}
	
	public String toRDF(String syntax) {
		try {
            String json1 = toJSON(true);
            // Model model = ModelFactory.createDefaultModel();
            Model model = createModel();
            StringReader reader = new StringReader(json1);
            System.out.println(json1);
            model.read(reader, null, "JSON-LD");
            StringWriter out = new StringWriter();
            model.write(out, syntax);
            return out.toString();    
        }
		catch(Exception e) {
			e.printStackTrace();
			return "ERROR: "+e.getMessage();
		}
	}

    public static Model createModel(){
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefixes(setPrefixes());
		return model;
	}

	public static Map<String, String> setPrefixes(){
		Model model = ModelFactory.createDefaultModel();
		Map<String, String> prefixes = model.getNsPrefixMap();
		prefixes.put("nif-ann", "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-annotation#");
		prefixes.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		prefixes.put("xsd", "http://www.w3.org/2001/XMLSchema#");
		prefixes.put("itsrdf", "http://www.w3.org/2005/11/its/rdf#");
		prefixes.put("nif", "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#");
		prefixes.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		prefixes.put("lkg", "http://lkg.lynx-project.eu/def/");
		prefixes.put("lkg-res", "http://lkg.lynx-project.eu/res/");
		prefixes.put("eli", "http://data.europa.eu/eli/ontology#");
		prefixes.put("owl", "http://www.w3.org/2002/07/owl#");
		prefixes.put("foaf", "http://xmlns.com/foaf/spec/");
		prefixes.put("dct", "http://purl.org/dc/terms/");
		prefixes.put("dbo", "http://dbpedia.org/ontology/");
		//prefixes.put("scilake", "http://scilake-project.eu/ontology/");
		//prefixes.put("dfki", "http://dfki-nlp.de/ontology/");
		return prefixes;
	}

    public static String getContextExplicit() {
        try {
    //			InputStream is = DFKIDocument.class.getClassLoader().getResourceAsStream("contexts/DFKIdocument.json");
            InputStream is = ScientificDocument.class.getClassLoader().getResourceAsStream("contexts/scilake_ctx.json");
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");
            String cachecontext = writer.toString();
            return cachecontext;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return '{' +
                "id:'" + id + '\'' +
                ", types:" + types +
                ", text:'" + text + '\'' +
                ", offsetIni:'" + offsetIni + '\'' +
                ", offsetEnd:'" + offsetEnd + '\'' +
//                ", annotations:'" + annotations.toString() + '\'' +
                ", parts:'" + parts.toString() + '\'' +
//                ", textannotations:'" + docAnnotations.toString() + '\'' +
                '}';
    }

	public static ScientificDocument createScientificDocument(String xmlString,BiblioItem bib) throws Exception {
		ScientificDocument dd = new ScientificDocument("http://scilake-project.eu/res/"+UUID.randomUUID().toString().substring(0,8), "Example text to be modified", "en");
        List<String> types = new ArrayList<String>(Arrays.asList(new String[]{"nif:Context", "nif:OffsetBasedString", "scilake:ScientificDocument"}));
		dd.setType(types);

		String wholeText = "";
		int counter = 0;

		List<ScientificAuthor> authors = new LinkedList<ScientificAuthor>();
		System.out.println("TITLE: "+bib.getTitle());
		String doc_title = bib.getTitle();
		if(doc_title!=null){
			ScientificDocumentPart titlePart = new ScientificDocumentPart(dd.getId(), doc_title, "", counter, counter+doc_title.length(), doc_title, "title");
			dd.addPart(titlePart);
			counter = counter + doc_title.length() + 1;
			wholeText = wholeText + " " + doc_title;
		}
		System.out.println("Abstract" + bib.getAbstract());
		String abstr = bib.getAbstract();
		if (abstr!=null){
			ScientificDocumentPart abstrPart = new ScientificDocumentPart(dd.getId(), abstr, "", counter, counter+abstr.length(), abstr, "abstract");
			dd.addPart(abstrPart);
			counter = counter + abstr.length() + 1;
			wholeText = wholeText + " " + abstr;
		}
		System.out.println(bib.getKeywords());
		/**
		 * TODO include keywords
		 */
		List<Person> persons = bib.getFullAuthors();
		if (persons!=null){
			for (Person person : persons) {
				System.out.println(person.toString());
				// ScientificAuthor sci = new ScientificAuthor("http://scilake-project.eu/res", person);
				ScientificAuthor sci = new ScientificAuthor(dd.getId(), person);
				System.out.println(sci.toJSON());
				authors.add(sci);
			}
			dd.setAuthors(authors);
		}

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		InputSource inputSource = new InputSource(new StringReader(xmlString));
		org.w3c.dom.Document xmlDocument = builder.parse(inputSource);
		XPath xPath = XPathFactory.newInstance().newXPath();

		String expression = "//body/div";
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		System.out.println(nodeList.getLength());

		for (int k = 0; k < nodeList.getLength(); k++) {
            Node node = nodeList.item(k);
			Element e = (Element) node;
            NodeList heads = e.getElementsByTagName("head");
			String title = null;
			String number = null;
			String text = "";
        	for (int j = 0; j < heads.getLength(); j++) {
                Node head = heads.item(j);
				Element head_element = (Element) head;
				number = head_element.getAttribute("n");
				title = head_element.getTextContent();
			}
            NodeList ps = e.getElementsByTagName("p");
        	for (int j = 0; j < ps.getLength(); j++) {
                Node p = ps.item(j);
				Element p_element = (Element) p;
				text = text + " " + p_element.getTextContent();
				//System.out.println(p_element.getTextContent());
			}
			text = text.trim();
			ScientificDocumentPart qdp = new ScientificDocumentPart(dd.getId(), title, number, counter, counter+text.length(), text, "section");
			dd.addPart(qdp);
			counter = counter + text.length() + 1;
			wholeText = wholeText + " " + text;
		}
		
		String expression_ack = "//back/div";
		NodeList nodeList_back_div = (NodeList) xPath.compile(expression_ack).evaluate(xmlDocument, XPathConstants.NODESET);
		for (int k = 0; k < nodeList_back_div.getLength(); k++) {
            Node node = nodeList_back_div.item(k);
			Element e = (Element) node;
			if (e.getAttribute("type")!=null && e.getAttribute("type").equalsIgnoreCase("acknowledgement")){
				NodeList heads = e.getElementsByTagName("head");
				String title = null;
				for (int j = 0; j < heads.getLength(); j++) {
					Node head = heads.item(j);
					Element head_element = (Element) head;
					title = head_element.getTextContent();
				}
				String text = "";
				NodeList ps = e.getElementsByTagName("p");
				for (int j = 0; j < ps.getLength(); j++) {
					Node p = ps.item(j);
					Element p_element = (Element) p;
					text = text + " " + p_element.getTextContent();
					//System.out.println(p_element.getTextContent());
				}
				text = text.trim();
				ScientificDocumentPart qdp = new ScientificDocumentPart(dd.getId(), title, "", counter, counter+text.length(), text, "acknowledgement");
				dd.addPart(qdp);
				counter = counter + text.length() + 1;
				wholeText = wholeText + " " + text;
			}
		}
		dd.setText(wholeText.trim());
		return dd;
	}

	public static void main(String[] args) throws Exception {

		String path2 = "dostres/src/main/resources/exampledata/file1.xml";

		ScientificDocument dd = new ScientificDocument("http://dfki.de/ont/res/"+UUID.randomUUID().toString().substring(0,8), "Example text to be modified", "en");
        List<String> types = new ArrayList<String>(Arrays.asList(new String[]{"nif:Context", "nif:OffsetBasedString", "scilake:ScientificDocument"}));
		dd.setType(types);

			// //System.out.println(d.getTei());
			// BiblioItem bib = new BiblioItem();
			// String tei = engine.processHeader(auxFile, config, bib);

			// System.out.println(bib.getTitle());
			// System.out.println(bib.getAbstract());
			// System.out.println(bib.getKeywords());
			// List<Person> persons = bib.getFullAuthors();
			// for (Person person : persons) {
			// 	// System.out.println(person.toString());
			// 	ObjectMapper mapper = new ObjectMapper();
            //     mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            //     String json_person = mapper.writeValueAsString(person);
			// 	// System.out.println(json_person);
			// 	ScientificAuthor sci = new ScientificAuthor("http://scilake-project.eu/res", person);
			// 	System.out.println(sci.toJSON());
			// }



		FileInputStream fileIS = new FileInputStream(path2);
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		org.w3c.dom.Document xmlDocument = builder.parse(fileIS);
		XPath xPath = XPathFactory.newInstance().newXPath();

		/**
		 * Extract header text and information
		 * Title: <fileDesc>
			<titleStmt>
		 */



		String expression = "//body/div";
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		System.out.println(nodeList.getLength());

		String wholeText = "";
		int counter = 0;
		for (int k = 0; k < nodeList.getLength(); k++) {
            Node node = nodeList.item(k);
			Element e = (Element) node;
            NodeList heads = e.getElementsByTagName("head");
			String title = null;
			String number = null;
			String text = "";
        	for (int j = 0; j < heads.getLength(); j++) {
                Node head = heads.item(j);
				Element head_element = (Element) head;
				number = head_element.getAttribute("n");
				title = head_element.getTextContent();
			}
            NodeList ps = e.getElementsByTagName("p");
        	for (int j = 0; j < ps.getLength(); j++) {
                Node p = ps.item(j);
				Element p_element = (Element) p;
				text = text + " " + p_element.getTextContent();
				//System.out.println(p_element.getTextContent());
			}
			text = text.trim();
			ScientificDocumentPart qdp = new ScientificDocumentPart(dd.getId(), title, number, counter, counter+text.length(), text, "section");
			dd.addPart(qdp);
			counter = counter + text.length() + 1;
			wholeText = wholeText + " " + text;
		}
		
		String expression_ack = "//back/div";
		NodeList nodeList_back_div = (NodeList) xPath.compile(expression_ack).evaluate(xmlDocument, XPathConstants.NODESET);
		for (int k = 0; k < nodeList_back_div.getLength(); k++) {
            Node node = nodeList_back_div.item(k);
			Element e = (Element) node;
			if (e.getAttribute("type")!=null && e.getAttribute("type").equalsIgnoreCase("acknowledgement")){
				NodeList heads = e.getElementsByTagName("head");
				String title = null;
				for (int j = 0; j < heads.getLength(); j++) {
					Node head = heads.item(j);
					Element head_element = (Element) head;
					title = head_element.getTextContent();
				}
				String text = "";
				NodeList ps = e.getElementsByTagName("p");
				for (int j = 0; j < ps.getLength(); j++) {
					Node p = ps.item(j);
					Element p_element = (Element) p;
					text = text + " " + p_element.getTextContent();
					//System.out.println(p_element.getTextContent());
				}
				text = text.trim();
				ScientificDocumentPart qdp = new ScientificDocumentPart(dd.getId(), title, "", counter, counter+text.length(), text, "acknowledgement");
				dd.addPart(qdp);
				counter = counter + text.length() + 1;
				wholeText = wholeText + " " + text;
			}
		}



		dd.setText(wholeText.trim());
		// /**
    	//  * Annotation element
    	//  */
    	// Label l = new Label();
    	// l.add("taClassRef", "http://dbpedia.org/ontology/Location");
    	// l.add("taIdentRef", "http://dbpedia.org/resource/Berlin");
    	// LabelPositionAnnotation lpa = new LabelPositionAnnotation("http://document1.org/res/", 40, 46, "Berlin");
    	// lpa.addLabel(l);
    	// dd.addAnnotation(lpa);
    	// /**
    	//  *  Generation element
    	//  */
    	// TextAnnotation ta = new TextAnnotation("This is a summary of the sentence", "en");
    	// dd.addDocumentAnnotation(ta);
    	// /**
    	//  * Part element
    	//  */
    	// ScientificDocumentPart qdp = new ScientificDocumentPart("http://scilake-project.de/res/56b56027", "title", "2.1", 0, 22, "The capital of Germany");
    	// dd.addPart(qdp);

		System.out.println("==================================================================");
		System.out.println("==================================================================");
		System.out.println("==================================================================");
		// System.out.println(dd.toJSON(false));
		System.out.println("==================================================================");
		System.out.println("==================================================================");
		System.out.println("==================================================================");
		System.out.println(dd.toRDF("TURTLE"));
		System.out.println("==================================================================");
		System.out.println("==================================================================");
		System.out.println("==================================================================");

		String s2 = path2.replace(".xml",".ttl");
		File fOutput = null;
		fOutput = new File(s2);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fOutput));		
		osw.write(dd.toRDF("TURTLE"));
		osw.close();	

	}

}
