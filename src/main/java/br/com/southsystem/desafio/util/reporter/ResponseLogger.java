package br.com.southsystem.desafio.util.reporter;

import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ResponseLogger extends ResponseLoggingFilter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        String log = ResponsePrinter.print(response, response, LogDetail.ALL, true);
        ReportBase.getInstance().logCodeBlock("response", log);
        return response;
    }

}
