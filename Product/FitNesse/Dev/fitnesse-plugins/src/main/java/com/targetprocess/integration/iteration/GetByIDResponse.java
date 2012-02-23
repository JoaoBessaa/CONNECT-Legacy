
package com.targetprocess.integration.iteration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetByIDResult" type="{http://targetprocess.com}IterationDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getByIDResult"
})
@XmlRootElement(name = "GetByIDResponse")
public class GetByIDResponse {

    @XmlElement(name = "GetByIDResult")
    protected IterationDTO getByIDResult;

    /**
     * Gets the value of the getByIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link IterationDTO }
     *     
     */
    public IterationDTO getGetByIDResult() {
        return getByIDResult;
    }

    /**
     * Sets the value of the getByIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IterationDTO }
     *     
     */
    public void setGetByIDResult(IterationDTO value) {
        this.getByIDResult = value;
    }

}
