## Planejamento
Para os testes automatizados de API resolvi utilizar a abordagem de pipelines
com healthcheck para garantir integridade do serviço, contrato para garantir 
que os atributos sejam imutáveis e aceitação para garantir funcionamento como na
UI. Não foi adicionado testes funcionais por ser uma API mockada,
 impossibilitando validações deste tipo.

Referência utilizada para essa abordagem: https://www.slideshare.net/elias.nogueira/de-a-mxima-cobertura-nos-seus-testes-de-api 

## Cenários desenvolvidos
```
Cenário 1 
Titulo:  Simulação - Listar opções extras 
Pré-condição: URL -> http://5b847b30db24a100142dce1b.mockapi.io/api/v1/simulador

Dado que seja realizado uma simulação através da API
Quando o retorno for positivo
Então deve ser listado as opções extras, com "id", lista de meses e valores

Reprodução:
Realizar uma requisição no serviço com protocolo GET
Resultado Esperado: Deve retornar um JSON com id, lista de meses e valores.


Cenário 2
Titulo:  Simulação - Resposta deve estar de acordo com schema
Pré-condição: URL -> http://5b847b30db24a100142dce1b.mockapi.io/api/v1/simulador

Dado que seja realizado uma simulação através da API
Quando retorno for positivo
Então a resposta JSON deve estar de acordo com o JSONSCHEMA validado

Reprodução:
Realizar uma requisição no serviço com protocolo GET
Resultado Esperado: Deve validar o contrato de JSONSCHEMA
```

## Pré-requisitos
[Java 8+](https://www.oracle.com/technetwork/pt/java/javase/downloads/jre8-downloads-2133155.html)
   
[Maven](https://maven.apache.org/)

## Utilizando
Para rodar os testes basta utilizar o comando Maven abaixo
```
mvn clean test
```
Ao rodar os testes, automaticamente é criado um report com as informações dos testes
rodados.
Para visualizar o report é necessário acessar a pasta target/AutomationReports. 
Lá estará um arquivo HTML que pode ser aberto em um browser e visualizado.

## Configurando Suite de testes
É possível alterar algumas configurações parametrizadas da suite de testes 
através do arquivo de configuração do testng (testng.xml).
- Habilitar/Desabilitar teste: Para habilitar ou desabilitar os testes basta mudar a flag
"enabled" que se encontra na tag    < test >. true para habilitar e false para desabilitar

## Link para Teste automatizado da UI
[Desafio-ui-automation](https://github.com/MateusPasqualini/desafio-ui-automation)
