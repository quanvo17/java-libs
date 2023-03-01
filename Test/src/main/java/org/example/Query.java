package org.example;

public class Query {
    private String userName;
    private String ipAddress;
    private String sparkApp;
    private String sessionId;
    private String thriftHost;
    private String thriftPort;
    private int groupId;
    private String statement;
    private long startTime;

    public Query(String userName, String ipAddress, String sparkApp, String sessionId, String thriftHost, String thriftPort, int groupId, String statement, long startTime) {
        this.userName = userName;
        this.ipAddress = ipAddress;
        this.sparkApp = sparkApp;
        this.sessionId = sessionId;
        this.thriftHost = thriftHost;
        this.thriftPort = thriftPort;
        this.groupId = groupId;
        this.statement = statement;
        this.startTime = startTime;
    }

    public Query() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSparkApp() {
        return sparkApp;
    }

    public void setSparkApp(String sparkApp) {
        this.sparkApp = sparkApp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getThriftHost() {
        return thriftHost;
    }

    public void setThriftHost(String thriftHost) {
        this.thriftHost = thriftHost;
    }

    public String getThriftPort() {
        return thriftPort;
    }

    public void setThriftPort(String thriftPort) {
        this.thriftPort = thriftPort;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
