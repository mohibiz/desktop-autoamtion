package automation.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("ApplicationName")
    @Expose
    private static String applicationName;
    @SerializedName("ARTName")
    @Expose
    private static String artName;
    @SerializedName("Environment")
    @Expose
    private static String environment;
    @SerializedName("CodeBase")
    @Expose
    private static String codeBase;
    @SerializedName("RunID")
    @Expose
    private static String runID;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("ScriptName")
    @Expose
    private String scriptName;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("RunTime")
    @Expose
    private String runTime;

    public Status() {
    }

    public String getRunID() {
        return runID;
    }

    public Status setRunID(String runID) {
        Status.runID = runID;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public Status setApplicationName(String applicationName) {
        Status.applicationName = applicationName;
        return this;
    }

    public String getArtName() {
        return artName;
    }

    public Status setArtName(String artName) {
        Status.artName = artName;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public Status setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Status setType(String type) {
        this.type = type;
        return this;
    }

    public String getScriptName() {
        return this.scriptName;
    }

    public Status setScriptName(String scriptName) {
        this.scriptName = scriptName;
        return this;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public Status setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public Status setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getRunTime() {
        return this.runTime;
    }

    public Status setRunTime(String runTime) {
        this.runTime = runTime;
        return this;
    }

    public String getEnvironment() {
        return environment;
    }

    public Status setEnvironment(String environment) {
        Status.environment = environment;
        return this;
    }

    public String getCodeBase() {
        return codeBase;
    }

    public Status setCodeBase(String codeBase) {
        Status.codeBase = codeBase;
        return this;
    }
}
