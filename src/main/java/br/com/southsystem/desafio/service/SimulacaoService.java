package br.com.southsystem.desafio.service;

import br.com.southsystem.desafio.dto.SimulacaoDTO;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class SimulacaoService {
    private final RequestSpecification requestSpecification;

    public SimulacaoService(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public SimulacaoDTO buscarOpcoes(int expectedStatus) {
        return given().
                    spec(requestSpecification).
                when().
                    get().
                then().
                    statusCode(expectedStatus).
                    extract().
                    as(SimulacaoDTO.class);
    }
}
