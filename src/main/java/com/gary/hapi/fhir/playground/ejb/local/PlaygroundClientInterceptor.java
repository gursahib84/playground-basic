/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gary.hapi.fhir.playground.ejb.local;

import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import java.io.IOException;
import javax.interceptor.Interceptor;

@Interceptor
public class PlaygroundClientInterceptor implements IClientInterceptor {

    private long startTime;
    private long endTime;

    @Override
    public void interceptRequest(IHttpRequest ihr) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void interceptResponse(IHttpResponse ihr) throws IOException {
        endTime = System.currentTimeMillis();
    }

    public long requestStopWatch() {
        return (endTime - startTime);
    }

}
