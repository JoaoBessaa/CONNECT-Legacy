/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *
 */
package gov.hhs.fha.nhinc.util.format;

import java.util.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rayj
 */
public class DocumentClassCodeBuilderTest {

    public DocumentClassCodeBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void BuildListWith3Items() {
        List<String> documentClassCodeList = new ArrayList<String>();
        documentClassCodeList.add("classcode1");
        documentClassCodeList.add("classcode2");
        documentClassCodeList.add("classcode3");
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(documentClassCodeList);
        assertEquals("('classcode1','classcode2','classcode3')", classCodes);
    }

    @Test
    public void BuildListWith1Item() {
        List<String> documentClassCodeList = new ArrayList<String>();
        documentClassCodeList.add("classcode1");
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(documentClassCodeList);
        assertEquals("('classcode1')", classCodes);
    }

    @Test
    public void BuildListWith0Items() {
        List<String> documentClassCodeList = new ArrayList<String>();
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(documentClassCodeList);
        assertEquals("", classCodes);
    }

    @Test
    public void BuildListWithNullInput() {
        String classCodes = DocumentClassCodeParser.buildDocumentClassCodeItem(null);
        assertEquals("", classCodes);
    }
}