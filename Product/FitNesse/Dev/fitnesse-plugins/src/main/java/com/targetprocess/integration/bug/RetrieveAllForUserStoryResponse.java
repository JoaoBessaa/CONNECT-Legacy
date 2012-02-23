
package com.targetprocess.integration.bug;

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
 *         &lt;element name="RetrieveAllForUserStoryResult" type="{http://targetprocess.com}ArrayOfBugDTO" minOccurs="0"/>
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
    "retrieveAllForUserStoryResult"
})
@XmlRootElement(name = "RetrieveAllForUserStoryResponse")
public class RetrieveAllForUserStoryResponse {

    @XmlElement(name = "RetrieveAllForUserStoryResult")
    protected ArrayOfBugDTO retrieveAllForUserStoryResult;

    /**
     * Gets the value of the retrieveAllForUserStoryResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBugDTO }
     *     
     */
    public ArrayOfBugDTO getRetrieveAllForUserStoryResult() {
        return retrieveAllForUserStoryResult;
    }

    /**
     * Sets the value of the retrieveAllForUserStoryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBugDTO }
     *     
     */
    public void setRetrieveAllForUserStoryResult(ArrayOfBugDTO value) {
        this.retrieveAllForUserStoryResult = value;
    }

}