/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gary.hapi.fhir.playground;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SampleClientTest {

    private static final FhirContext ctx = FhirContext.forR4();

    public SampleClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class SampleClient.
     */
    @Test
    public void testMain() {
        Bundle bundle = new Bundle();
        Patient patient = new Patient();
        patient.setId("Patient1");
        patient.addName().setFamily("SMITH");

        bundle.addEntry().setResource(patient);

        IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseR4"); // won't connect
        try {
            Bundle result = client.search()
                    .forResource("Patient")
                    .where(Patient.FAMILY.matches().value("SMITH"))
                    .returnBundle(Bundle.class)
                    .execute();
            System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(result));
        } catch (DataFormatException e) {
            Assert.fail();
        }
    }

}
