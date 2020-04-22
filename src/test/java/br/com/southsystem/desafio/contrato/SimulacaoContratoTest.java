package br.com.southsystem.desafio.contrato;

import br.com.southsystem.desafio.basetest.BaseTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class SimulacaoContratoTest extends BaseTest {

    @Test(groups = {"contrato"})
    public void validaContratoSimulacao() {
        given().
            spec(requestSpecification).
        when().
            get().
        then().
            statusCode(SC_OK).
            assertThat().
            body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/simulacao.json")));
    }
}
