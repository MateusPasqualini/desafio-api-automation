package br.com.southsystem.desafio.datafactory;

import br.com.southsystem.desafio.builder.TestData;

import java.util.Arrays;
import java.util.List;


public class TestDataFactory {

    public TestData createData() {
        return TestData.builder().
                meses(criaListaDeMeses()).
                valores(criaListaDeValores()).
                build();
    }

    private List<String> criaListaDeMeses() {
        return Arrays.asList("112",
                "124",
                "136",
                "148");
    }

    private List<String> criaListaDeValores() {
        return Arrays.asList("2.802",
                "3.174",
                "3.564",
                "3.971");
    }
}
