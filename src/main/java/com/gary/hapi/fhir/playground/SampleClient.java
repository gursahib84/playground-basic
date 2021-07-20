package com.gary.hapi.fhir.playground;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import com.gary.hapi.fhir.playground.utils.PlaygroundUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SampleClient {

    public static void main(String[] theArgs) {

        // Create a FHIR client
        final FhirContext fhirContext = FhirContext.forR4();
        final IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));

        final Path filePath = Paths.get("src/main/resources/lastNames.txt");
        int count = 0;
        try {
            while (count < 3) {
                final List<Long> responseTimeList = new ArrayList<>();
                Double avResponseTime;
                if (count != 2) {
                    Files.lines(filePath).forEach((String lastName) -> responseTimeList.add(PlaygroundUtils.findPatientResourceUsingLastName(client, lastName)));
                    avResponseTime = responseTimeList.stream().mapToDouble(val -> val).average().orElse(0.0);

                } else {
                    Files.lines(filePath).forEach((String lastName) -> responseTimeList.add(PlaygroundUtils.findPatientResourceUsingLastNameNoCache(client, lastName)));
                    avResponseTime = responseTimeList.stream().mapToDouble(val -> val).average().orElse(0.0);

                }
                Logger.getLogger(SampleClient.class.getName()).log(Level.INFO, "Average response time of run {0} is {1}", new Object[]{count, avResponseTime});
                count++;
            }

        } catch (IOException ex) {
            Logger.getLogger(SampleClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
