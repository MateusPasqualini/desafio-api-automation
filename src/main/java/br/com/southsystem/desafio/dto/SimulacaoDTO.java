package br.com.southsystem.desafio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulacaoDTO {
    private Integer id;
    private List<String> meses;
    private List<String> valor;
}
