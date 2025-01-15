package org.imt.tournamentmaster.model.match;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ImportingReport {
    public String status;
    public ImportingReport(String status){
        this.status = status;
    }
}
