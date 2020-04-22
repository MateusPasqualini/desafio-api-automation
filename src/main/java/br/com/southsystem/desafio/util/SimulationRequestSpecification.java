package br.com.southsystem.desafio.util;

import br.com.southsystem.desafio.util.reporter.RequestLogger;
import br.com.southsystem.desafio.util.reporter.ResponseLogger;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class SimulationRequestSpecification {

    private static final String BASEURI = PropertiesManager.getInstance().getProperty("baseuri");
    private static final String BASEPATH = PropertiesManager.getInstance().getProperty("basepath");

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder().
                setBaseUri(BASEURI).
                setBasePath(BASEPATH).
                addFilter(new RequestLogger()).
                addFilter(new ResponseLogger()).
                build();
    }
}
