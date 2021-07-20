/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gary.hapi.fhir.playground.utils;

import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.gary.hapi.fhir.playground.ejb.local.PlaygroundClientInterceptor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class PlaygroundUtils {

    public static Long findPatientResourceUsingLastName(final IGenericClient client, final String lastName) {
        PlaygroundClientInterceptor interceptor = new PlaygroundClientInterceptor();
        client.registerInterceptor(interceptor);
        try {
            Bundle bundle = client
                    .search()
                    .forResource("Patient")
                    .where(Patient.FAMILY.matches().value(lastName))
                    .returnBundle(Bundle.class)
                    .cacheControl(new CacheControlDirective().setNoCache(false))
                    .execute();
        } catch (FhirClientConnectionException e) {
            Logger.getLogger(PlaygroundUtils.class.getName()).log(Level.SEVERE, e.getLocalizedMessage());
            return 0L;
        }
        return interceptor.requestStopWatch();

    }

    public static Long findPatientResourceUsingLastNameNoCache(final IGenericClient client, final String lastName) {
        PlaygroundClientInterceptor interceptor = new PlaygroundClientInterceptor();
        client.registerInterceptor(interceptor);
        try {
            Bundle bundle = client
                    .search()
                    .forResource("Patient")
                    .where(Patient.FAMILY.matches().value(lastName))
                    .returnBundle(Bundle.class)
                    .cacheControl(new CacheControlDirective().setNoCache(true))
                    .execute();
        } catch (FhirClientConnectionException e) {
            Logger.getLogger(PlaygroundUtils.class.getName()).log(Level.SEVERE, e.getLocalizedMessage());
            return 0L;
        }
        return interceptor.requestStopWatch();

    }
}
