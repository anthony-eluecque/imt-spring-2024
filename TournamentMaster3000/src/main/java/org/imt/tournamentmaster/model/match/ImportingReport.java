package org.imt.tournamentmaster.model.match;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;

public class ImportingReport {

    public enum Status {
        OK, FAILED
    }

    public Status status;
    public long id;
    public Timestamp timestamp;

    public ImportingReport(Status status, long id, Timestamp timestamp){
        this.status = status;
        this.id = id;
        this.timestamp = timestamp;
    }

}
