//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.06.01 um 03:08:31 PM CEST 
//


package org.tei_c.ns._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für tei_data.certainty.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="tei_data.certainty">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="high"/>
 *     &lt;enumeration value="medium"/>
 *     &lt;enumeration value="low"/>
 *     &lt;enumeration value="unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tei_data.certainty")
@XmlEnum
public enum TeiDataCertainty {

    @XmlEnumValue("high")
    HIGH("high"),
    @XmlEnumValue("medium")
    MEDIUM("medium"),
    @XmlEnumValue("low")
    LOW("low"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown");
    private final String value;

    TeiDataCertainty(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TeiDataCertainty fromValue(String v) {
        for (TeiDataCertainty c: TeiDataCertainty.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
