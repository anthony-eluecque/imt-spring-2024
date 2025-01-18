package org.imt.tournamentmaster.model.match;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ImportingReport {

    public enum Status {
        OK, FAILED
    }

    public Status status;
    public long id;

    public ImportingReport(Status status, long id){
        this.status = status;
        this.id = id;
    }

}
