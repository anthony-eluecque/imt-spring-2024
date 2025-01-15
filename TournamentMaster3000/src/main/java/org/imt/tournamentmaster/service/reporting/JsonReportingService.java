package org.imt.tournamentmaster.service.reporting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonReportingService implements ReportingService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

}
