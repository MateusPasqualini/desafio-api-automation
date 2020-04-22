package br.com.southsystem.desafio.basetest;

import br.com.southsystem.desafio.util.SimulationRequestSpecification;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    protected RequestSpecification requestSpecification;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpecification = SimulationRequestSpecification.getRequestSpecification();
    }
}
