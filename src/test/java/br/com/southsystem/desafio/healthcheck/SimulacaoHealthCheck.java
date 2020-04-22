package br.com.southsystem.desafio.healthcheck;

import br.com.southsystem.desafio.basetest.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class SimulacaoHealthCheck extends BaseTest {

    @Test(groups = {"healthcheck"})
    public void healthCheck() {
        given().
            spec(requestSpecification).
        when().
            get().
        then().
            statusCode(SC_OK);
    }
}
