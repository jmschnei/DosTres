//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.06.01 um 03:08:31 PM CEST 
//


package org.tei_c.ns._1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tei-c.org/ns/1.0}tei_macro.phraseSeq">
 *       &lt;attGroup ref="{http://www.tei-c.org/ns/1.0}tei_att.datable.attributes"/>
 *       &lt;attGroup ref="{http://www.tei-c.org/ns/1.0}tei_att.typed.attributes"/>
 *       &lt;attGroup ref="{http://www.tei-c.org/ns/1.0}tei_att.personal.attributes"/>
 *       &lt;attGroup ref="{http://www.tei-c.org/ns/1.0}tei_att.global.attributes"/>
 *       &lt;attGroup ref="{http://www.tei-c.org/ns/1.0}tei_att.editLike.attributes"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "persName")
public class PersName
    extends TeiMacroPhraseSeq
{

    @XmlAttribute(name = "calendar")
    protected String calendar;
    @XmlAttribute(name = "notAfter-iso")
    protected String notAfterIso;
    @XmlAttribute(name = "when-iso")
    protected String whenIso;
    @XmlAttribute(name = "notBefore-iso")
    protected String notBeforeIso;
    @XmlAttribute(name = "from-iso")
    protected String fromIso;
    @XmlAttribute(name = "to-iso")
    protected String toIso;
    @XmlAttribute(name = "period")
    protected String period;
    @XmlAttribute(name = "to-custom")
    protected List<String> toCustoms;
    @XmlAttribute(name = "notAfter-custom")
    protected List<String> notAfterCustoms;
    @XmlAttribute(name = "from-custom")
    protected List<String> fromCustoms;
    @XmlAttribute(name = "datingPoint")
    protected String datingPoint;
    @XmlAttribute(name = "datingMethod")
    protected String datingMethod;
    @XmlAttribute(name = "notBefore-custom")
    protected List<String> notBeforeCustoms;
    @XmlAttribute(name = "when-custom")
    protected List<String> whenCustoms;
    @XmlAttribute(name = "from")
    protected String from;
    @XmlAttribute(name = "notAfter")
    protected String notAfter;
    @XmlAttribute(name = "to")
    protected String to;
    @XmlAttribute(name = "when")
    protected String when;
    @XmlAttribute(name = "notBefore")
    protected String notBefore;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "subtype")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String subtype;
    @XmlAttribute(name = "sort")
    protected BigInteger sort;
    @XmlAttribute(name = "role")
    protected List<String> roles;
    @XmlAttribute(name = "nymRef")
    protected List<String> nymReves;
    @XmlAttribute(name = "key")
    protected String key;
    @XmlAttribute(name = "ref")
    protected List<String> reves;
    @XmlAttribute(name = "full")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String full;
    @XmlAttribute(name = "prev")
    protected String prev;
    @XmlAttribute(name = "synch")
    protected List<String> synches;
    @XmlAttribute(name = "sameAs")
    protected String sameAs;
    @XmlAttribute(name = "select")
    protected List<String> selects;
    @XmlAttribute(name = "copyOf")
    protected String copyOf;
    @XmlAttribute(name = "exclude")
    protected List<String> excludes;
    @XmlAttribute(name = "next")
    protected String next;
    @XmlAttribute(name = "corresp")
    protected List<String> corresps;
    @XmlAttribute(name = "resp")
    protected List<String> resps;
    @XmlAttribute(name = "cert")
    protected TeiDataCertainty cert;
    @XmlAttribute(name = "style")
    protected String style;
    @XmlAttribute(name = "rendition")
    protected List<String> renditions;
    @XmlAttribute(name = "rend")
    protected List<String> rends;
    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String base;
    @XmlAttribute(name = "id", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;
    @XmlAttribute(name = "space", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String space;
    @XmlAttribute(name = "coords")
    @XmlSchemaType(name = "anySimpleType")
    protected String coords;
    @XmlAttribute(name = "n")
    protected String n;
    @XmlAttribute(name = "source")
    protected List<String> sources;
    @XmlAttribute(name = "instant")
    protected String instant;
    @XmlAttribute(name = "extent")
    protected String extent;
    @XmlAttribute(name = "unit")
    protected String unit;
    @XmlAttribute(name = "scope")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String scope;
    @XmlAttribute(name = "max")
    protected String max;
    @XmlAttribute(name = "confidence")
    protected Double confidence;
    @XmlAttribute(name = "min")
    protected String min;
    @XmlAttribute(name = "atMost")
    protected String atMost;
    @XmlAttribute(name = "atLeast")
    protected String atLeast;
    @XmlAttribute(name = "precision")
    protected TeiDataCertainty precision;
    @XmlAttribute(name = "quantity")
    protected String quantity;
    @XmlAttribute(name = "evidence")
    protected List<String> evidences;

    /**
     * Ruft den Wert der calendar-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalendar() {
        return calendar;
    }

    /**
     * Legt den Wert der calendar-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalendar(String value) {
        this.calendar = value;
    }

    /**
     * Ruft den Wert der notAfterIso-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotAfterIso() {
        return notAfterIso;
    }

    /**
     * Legt den Wert der notAfterIso-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotAfterIso(String value) {
        this.notAfterIso = value;
    }

    /**
     * Ruft den Wert der whenIso-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhenIso() {
        return whenIso;
    }

    /**
     * Legt den Wert der whenIso-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhenIso(String value) {
        this.whenIso = value;
    }

    /**
     * Ruft den Wert der notBeforeIso-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotBeforeIso() {
        return notBeforeIso;
    }

    /**
     * Legt den Wert der notBeforeIso-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotBeforeIso(String value) {
        this.notBeforeIso = value;
    }

    /**
     * Ruft den Wert der fromIso-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromIso() {
        return fromIso;
    }

    /**
     * Legt den Wert der fromIso-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromIso(String value) {
        this.fromIso = value;
    }

    /**
     * Ruft den Wert der toIso-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToIso() {
        return toIso;
    }

    /**
     * Legt den Wert der toIso-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToIso(String value) {
        this.toIso = value;
    }

    /**
     * Ruft den Wert der period-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Legt den Wert der period-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriod(String value) {
        this.period = value;
    }

    /**
     * Gets the value of the toCustoms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toCustoms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToCustoms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getToCustoms() {
        if (toCustoms == null) {
            toCustoms = new ArrayList<String>();
        }
        return this.toCustoms;
    }

    /**
     * Gets the value of the notAfterCustoms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notAfterCustoms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotAfterCustoms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNotAfterCustoms() {
        if (notAfterCustoms == null) {
            notAfterCustoms = new ArrayList<String>();
        }
        return this.notAfterCustoms;
    }

    /**
     * Gets the value of the fromCustoms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fromCustoms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFromCustoms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFromCustoms() {
        if (fromCustoms == null) {
            fromCustoms = new ArrayList<String>();
        }
        return this.fromCustoms;
    }

    /**
     * Ruft den Wert der datingPoint-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatingPoint() {
        return datingPoint;
    }

    /**
     * Legt den Wert der datingPoint-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatingPoint(String value) {
        this.datingPoint = value;
    }

    /**
     * Ruft den Wert der datingMethod-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatingMethod() {
        return datingMethod;
    }

    /**
     * Legt den Wert der datingMethod-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatingMethod(String value) {
        this.datingMethod = value;
    }

    /**
     * Gets the value of the notBeforeCustoms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notBeforeCustoms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotBeforeCustoms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNotBeforeCustoms() {
        if (notBeforeCustoms == null) {
            notBeforeCustoms = new ArrayList<String>();
        }
        return this.notBeforeCustoms;
    }

    /**
     * Gets the value of the whenCustoms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the whenCustoms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWhenCustoms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getWhenCustoms() {
        if (whenCustoms == null) {
            whenCustoms = new ArrayList<String>();
        }
        return this.whenCustoms;
    }

    /**
     * Ruft den Wert der from-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrom() {
        return from;
    }

    /**
     * Legt den Wert der from-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Ruft den Wert der notAfter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotAfter() {
        return notAfter;
    }

    /**
     * Legt den Wert der notAfter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotAfter(String value) {
        this.notAfter = value;
    }

    /**
     * Ruft den Wert der to-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTo() {
        return to;
    }

    /**
     * Legt den Wert der to-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTo(String value) {
        this.to = value;
    }

    /**
     * Ruft den Wert der when-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhen() {
        return when;
    }

    /**
     * Legt den Wert der when-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhen(String value) {
        this.when = value;
    }

    /**
     * Ruft den Wert der notBefore-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotBefore() {
        return notBefore;
    }

    /**
     * Legt den Wert der notBefore-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotBefore(String value) {
        this.notBefore = value;
    }

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Ruft den Wert der subtype-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * Legt den Wert der subtype-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubtype(String value) {
        this.subtype = value;
    }

    /**
     * Ruft den Wert der sort-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSort() {
        return sort;
    }

    /**
     * Legt den Wert der sort-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSort(BigInteger value) {
        this.sort = value;
    }

    /**
     * Gets the value of the roles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<String>();
        }
        return this.roles;
    }

    /**
     * Gets the value of the nymReves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nymReves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNymReves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNymReves() {
        if (nymReves == null) {
            nymReves = new ArrayList<String>();
        }
        return this.nymReves;
    }

    /**
     * Ruft den Wert der key-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Legt den Wert der key-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the reves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReves() {
        if (reves == null) {
            reves = new ArrayList<String>();
        }
        return this.reves;
    }

    /**
     * Ruft den Wert der full-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFull() {
        if (full == null) {
            return "yes";
        } else {
            return full;
        }
    }

    /**
     * Legt den Wert der full-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFull(String value) {
        this.full = value;
    }

    /**
     * Ruft den Wert der prev-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrev() {
        return prev;
    }

    /**
     * Legt den Wert der prev-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrev(String value) {
        this.prev = value;
    }

    /**
     * Gets the value of the synches property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the synches property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSynches().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSynches() {
        if (synches == null) {
            synches = new ArrayList<String>();
        }
        return this.synches;
    }

    /**
     * Ruft den Wert der sameAs-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSameAs() {
        return sameAs;
    }

    /**
     * Legt den Wert der sameAs-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSameAs(String value) {
        this.sameAs = value;
    }

    /**
     * Gets the value of the selects property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selects property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelects().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSelects() {
        if (selects == null) {
            selects = new ArrayList<String>();
        }
        return this.selects;
    }

    /**
     * Ruft den Wert der copyOf-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCopyOf() {
        return copyOf;
    }

    /**
     * Legt den Wert der copyOf-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCopyOf(String value) {
        this.copyOf = value;
    }

    /**
     * Gets the value of the excludes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the excludes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExcludes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExcludes() {
        if (excludes == null) {
            excludes = new ArrayList<String>();
        }
        return this.excludes;
    }

    /**
     * Ruft den Wert der next-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNext() {
        return next;
    }

    /**
     * Legt den Wert der next-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNext(String value) {
        this.next = value;
    }

    /**
     * Gets the value of the corresps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the corresps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCorresps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCorresps() {
        if (corresps == null) {
            corresps = new ArrayList<String>();
        }
        return this.corresps;
    }

    /**
     * Gets the value of the resps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getResps() {
        if (resps == null) {
            resps = new ArrayList<String>();
        }
        return this.resps;
    }

    /**
     * Ruft den Wert der cert-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TeiDataCertainty }
     *     
     */
    public TeiDataCertainty getCert() {
        return cert;
    }

    /**
     * Legt den Wert der cert-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TeiDataCertainty }
     *     
     */
    public void setCert(TeiDataCertainty value) {
        this.cert = value;
    }

    /**
     * Ruft den Wert der style-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyle() {
        return style;
    }

    /**
     * Legt den Wert der style-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyle(String value) {
        this.style = value;
    }

    /**
     * Gets the value of the renditions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the renditions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRenditions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRenditions() {
        if (renditions == null) {
            renditions = new ArrayList<String>();
        }
        return this.renditions;
    }

    /**
     * Gets the value of the rends property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rends property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRends().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRends() {
        if (rends == null) {
            rends = new ArrayList<String>();
        }
        return this.rends;
    }

    /**
     * Ruft den Wert der base-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBase() {
        return base;
    }

    /**
     * Legt den Wert der base-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBase(String value) {
        this.base = value;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der lang-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Legt den Wert der lang-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Ruft den Wert der space-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpace() {
        return space;
    }

    /**
     * Legt den Wert der space-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpace(String value) {
        this.space = value;
    }

    /**
     * Ruft den Wert der coords-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoords() {
        return coords;
    }

    /**
     * Legt den Wert der coords-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoords(String value) {
        this.coords = value;
    }

    /**
     * Ruft den Wert der n-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getN() {
        return n;
    }

    /**
     * Legt den Wert der n-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setN(String value) {
        this.n = value;
    }

    /**
     * Gets the value of the sources property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sources property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSources().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSources() {
        if (sources == null) {
            sources = new ArrayList<String>();
        }
        return this.sources;
    }

    /**
     * Ruft den Wert der instant-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstant() {
        if (instant == null) {
            return "false";
        } else {
            return instant;
        }
    }

    /**
     * Legt den Wert der instant-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstant(String value) {
        this.instant = value;
    }

    /**
     * Ruft den Wert der extent-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtent() {
        return extent;
    }

    /**
     * Legt den Wert der extent-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtent(String value) {
        this.extent = value;
    }

    /**
     * Ruft den Wert der unit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Legt den Wert der unit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Ruft den Wert der scope-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScope() {
        return scope;
    }

    /**
     * Legt den Wert der scope-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScope(String value) {
        this.scope = value;
    }

    /**
     * Ruft den Wert der max-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMax() {
        return max;
    }

    /**
     * Legt den Wert der max-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMax(String value) {
        this.max = value;
    }

    /**
     * Ruft den Wert der confidence-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getConfidence() {
        return confidence;
    }

    /**
     * Legt den Wert der confidence-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setConfidence(Double value) {
        this.confidence = value;
    }

    /**
     * Ruft den Wert der min-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMin() {
        return min;
    }

    /**
     * Legt den Wert der min-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMin(String value) {
        this.min = value;
    }

    /**
     * Ruft den Wert der atMost-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAtMost() {
        return atMost;
    }

    /**
     * Legt den Wert der atMost-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAtMost(String value) {
        this.atMost = value;
    }

    /**
     * Ruft den Wert der atLeast-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAtLeast() {
        return atLeast;
    }

    /**
     * Legt den Wert der atLeast-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAtLeast(String value) {
        this.atLeast = value;
    }

    /**
     * Ruft den Wert der precision-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TeiDataCertainty }
     *     
     */
    public TeiDataCertainty getPrecision() {
        return precision;
    }

    /**
     * Legt den Wert der precision-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TeiDataCertainty }
     *     
     */
    public void setPrecision(TeiDataCertainty value) {
        this.precision = value;
    }

    /**
     * Ruft den Wert der quantity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Legt den Wert der quantity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the evidences property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the evidences property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEvidences().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEvidences() {
        if (evidences == null) {
            evidences = new ArrayList<String>();
        }
        return this.evidences;
    }

}
