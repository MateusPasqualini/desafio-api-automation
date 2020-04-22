package br.com.southsystem.desafio.aceitacao;

import br.com.southsystem.desafio.basetest.BaseTest;
import br.com.southsystem.desafio.builder.TestData;
import br.com.southsystem.desafio.datadriven.TestDataProvider;
import br.com.southsystem.desafio.dto.SimulacaoDTO;
import br.com.southsystem.desafio.service.SimulacaoService;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class SimulacaoAceitacaoTest extends BaseTest {

    @Test(groups = {"aceitacao"},dataProvider = "valuesData", dataProviderClass = TestDataProvider.class)
    public void deveRetornarListaDeOpcoes(TestData data) {
        SimulacaoService simulacaoService = new SimulacaoService(requestSpecification);

        SimulacaoDTO response = simulacaoService.buscarOpcoes(SC_OK);

        assertThat(response.getId(), is(1));
        assertThat(response.getMeses(), is(data.getMeses()));
        assertThat(response.getValor(), is(data.getValores()));
    }
}
