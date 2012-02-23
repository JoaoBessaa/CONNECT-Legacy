/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.hiem.dte.marshallers;

import org.w3c.dom.Element;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.SubscribeRequestType;

/**
 *
 * @author rayj
 */
public class AdapterSubscribeRequestMarshaller {

    private static final String ContextPath = "gov.hhs.fha.nhinc.common.nhinccommonadapter";

    public Element marshalSubscribeRequest(SubscribeRequestType object) {
        return new Marshaller().marshal(object, ContextPath);
    }

    public SubscribeRequestType unmarshalSubscribeRequest(Element element) {
        return (SubscribeRequestType) new Marshaller().unmarshal(element, ContextPath);
    }
}
