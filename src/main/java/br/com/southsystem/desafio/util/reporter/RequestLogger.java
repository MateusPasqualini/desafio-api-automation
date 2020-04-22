package br.com.southsystem.desafio.util.reporter;

import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class RequestLogger extends RequestLoggingFilter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        String log = RequestPrinter.print(requestSpec, requestSpec.getMethod(), requestSpec.getURI(), LogDetail.ALL, true);
        ReportBase.getInstance().logCodeBlock("Request "+ requestSpec.getURI(), log);
        return ctx.next(requestSpec, responseSpec);
    }


}
