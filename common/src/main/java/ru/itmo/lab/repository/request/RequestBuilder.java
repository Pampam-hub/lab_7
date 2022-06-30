package ru.itmo.lab.repository.request;

import ru.itmo.lab.entity.Dragon;
import ru.itmo.lab.entity.DragonType;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class RequestBuilder {
    Request request = new Request();

    public RequestBuilder withName(String commandName) {
        request.commandName = commandName;
        return this;
    }

    public RequestBuilder withIntegerArgument(Integer integerArgument) {
        request.integerArgument = integerArgument;
        return this;
    }

    public RequestBuilder withDragonArgument(Dragon dragonArgument) {
        dragonArgument.setCreationDate();
        request.dragonArgument = dragonArgument;
        return this;
    }

    public RequestBuilder withDragonTypeArgument(DragonType dragonTypeArgument) {
        request.dragonTypeArgument = dragonTypeArgument;
        return this;
    }

    public RequestBuilder withUsernameArgument(String username) {
        request.usernameArgument = username;
        return this;
    }

    public RequestBuilder withPasswordArgument(String password) {
        request.passwordArgument = password;
        return this;
    }

    public RequestBuilder withRequestTypeArgument(RequestType requestType) {
        request.requestTypeArgument = requestType;
        return this;
    }

    public RequestBuilder withLocalTimeArgument() {
        request.currentTime = LocalDateTime.now();
        return this;
    }

    public Request build() {
        return request;
    }
}
