package ru.itmo.lab.repository.request;

import ru.itmo.lab.entity.Dragon;
import ru.itmo.lab.entity.DragonType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Request implements Serializable {
    protected String commandName;
    private String clientInfo;
    protected LocalDateTime currentTime;
    protected Integer integerArgument;
    protected DragonType dragonTypeArgument;
    protected Dragon dragonArgument;

    protected String usernameArgument;
    protected String passwordArgument;
    protected RequestType requestTypeArgument;

    public String getCommandName() {
        return commandName;
    }

    public Integer getIntegerArgument() {
        return integerArgument;
    }

    public Dragon getDragonArgument() {
        return dragonArgument;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public DragonType getDragonTypeArgument() {
        return dragonTypeArgument;
    }

    public String getUsernameArgument() {
        return usernameArgument;
    }

    public String getPasswordArgument() {
        return passwordArgument;
    }

    public RequestType getRequestTypeArgument() {
        return requestTypeArgument;
    }

    @Override
    public String toString() {
        return "Request{" +
                "commandName='" + commandName + '\'' +
                ", clientInfo='" + clientInfo + '\'' +
                ", currentTime=" + currentTime +
                ", integerArgument=" + integerArgument +
                ", dragonArgument=" + dragonArgument +
                '}';
    }
}
