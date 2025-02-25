package de.dfki.scilake.dostres.data;

public enum Format {
	PDF("application/pdf"),
	DOC("application/doc"),
	TURTLE("text/turtle"),
	JSON_LD("application/ld+json"),
	PLAINTEXT("text/plain"),
	RDF_XML("application/rdf+xml"),
	RDF("application/rdf"),
	N3("text/n3"),
	N_TRIPLES("application/n-triples"),
	JSON("application/json"),
	HTML("text/html"),
	CSV("text/comma-separated-values"),
	XML("application/xml");

	private final String contentType;

	Format(String contentType) {
		this.contentType = contentType;
	}

	public String contentType() {
		return contentType;
	}

	/**
	 * Given a textual content type, return its RDFSerialization object.
	 * @param contentType	The content type, in textual format.
	 * @return				The corresponding RDFSerialization object, or {@code null} if nothing found
	 */
	public static Format fromValue(final String contentType) {
		String normalizedContentType = contentType.toLowerCase();
		// chop off everything beginning from ';'. An example is "text/turtle; charset=UTF-8"
		int indexOfSemicolon = normalizedContentType.indexOf(';');
		if (indexOfSemicolon >= 0) {
			normalizedContentType = normalizedContentType.substring(0, indexOfSemicolon);
		}
		// now find the matching value
		for (Format format : Format.values()) {
			if (format.contentType().equals(normalizedContentType)) {
				return format;
			}
		}
		return null;
	}
}
