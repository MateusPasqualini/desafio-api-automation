package br.com.southsystem.desafio.util.reporter;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.internal.NoParameterValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.ProxySpecification;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.util.Iterator;
import java.util.Map;

public class RequestPrinter {
    private static final String NONE = "none";

    private RequestPrinter() {
        throw new IllegalStateException("RequestPrinter cannot be instantiated");
    }

    public static String print(FilterableRequestSpecification requestSpec, String requestMethod, String completeRequestUri, LogDetail logDetail, boolean shouldPrettyPrint) {
        StringBuilder builder = new StringBuilder();
        if (logDetail == LogDetail.ALL || logDetail == LogDetail.METHOD) {
            addSingle(builder, "Request method:", requestMethod);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.URI) {
            addSingle(builder, "Request URI:", completeRequestUri);
        }

        if (logDetail == LogDetail.ALL) {
            addProxy(requestSpec, builder);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.PARAMS) {
            addMapDetails(builder, "Request params:", requestSpec.getRequestParams());
            addMapDetails(builder, "Query params:", requestSpec.getQueryParams());
            addMapDetails(builder, "Form params:", requestSpec.getFormParams());
            addMapDetails(builder, "Path params:", requestSpec.getNamedPathParams());
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.HEADERS) {
            addHeaders(requestSpec, builder);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.COOKIES) {
            addCookies(requestSpec, builder);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.BODY) {
            addBody(requestSpec, builder, shouldPrettyPrint);
        }

        return StringUtils.removeEnd(builder.toString(), SystemUtils.LINE_SEPARATOR);

    }

    private static void addProxy(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        builder.append("Proxy:");
        ProxySpecification proxySpec = requestSpec.getProxySpecification();
        appendThreeTabs(builder);
        if (proxySpec == null) {
            builder.append(NONE);
        } else {
            builder.append(proxySpec.toString());
        }

        builder.append(SystemUtils.LINE_SEPARATOR);
    }

    private static void addBody(FilterableRequestSpecification requestSpec, StringBuilder builder, boolean shouldPrettyPrint) {
        builder.append("Body:");
        if (requestSpec.getBody() != null) {
            String body;
            if (shouldPrettyPrint) {
                body = (new Prettifier()).getPrettifiedBodyIfPossible(requestSpec);
            } else {
                body = requestSpec.getBody();
            }

            builder.append(SystemUtils.LINE_SEPARATOR).append(body);
        } else {
            appendTab(appendTwoTabs(builder)).append(NONE);
        }

    }

    private static void addCookies(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        builder.append("Cookies:");
        Cookies cookies = requestSpec.getCookies();
        if (!cookies.exist()) {
            appendTwoTabs(builder).append(NONE).append(SystemUtils.LINE_SEPARATOR);
        }

        int i = 0;

        Cookie cookie;
        for (Iterator var4 = cookies.iterator(); var4.hasNext(); builder.append(cookie).append(SystemUtils.LINE_SEPARATOR)) {
            cookie = (Cookie) var4.next();
            if (i++ == 0) {
                appendTwoTabs(builder);
            } else {
                appendFourTabs(builder);
            }
        }

    }

    private static void addHeaders(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        builder.append("Headers:\n");
        Headers headers = requestSpec.getHeaders();
        if (!headers.exist()) {
            appendTwoTabs(builder).append(NONE).append(SystemUtils.LINE_SEPARATOR);
        } else {
            Header header;
            for (Iterator var4 = headers.iterator(); var4.hasNext(); builder.append(header).append(SystemUtils.LINE_SEPARATOR)) {
                header = (Header) var4.next();
                appendThreeTabs(builder);
            }
        }

    }


    private static void addSingle(StringBuilder builder, String str, String requestPath) {
        appendTab(builder.append(str)).append(requestPath).append(SystemUtils.LINE_SEPARATOR);
    }

    private static void addMapDetails(StringBuilder builder, String title, Map<String, ?> map) {
        appendTab(builder.append(title));
        if (map.isEmpty()) {
            builder.append(NONE).append(SystemUtils.LINE_SEPARATOR);
        } else {
            int i = 0;

            for (Iterator var4 = map.entrySet().iterator(); var4.hasNext(); builder.append(SystemUtils.LINE_SEPARATOR)) {
                Map.Entry entry = (Map.Entry) var4.next();
                if (i++ != 0) {
                    appendFourTabs(builder);
                }

                Object value = entry.getValue();
                builder.append((String) entry.getKey());
                if (!(value instanceof NoParameterValue)) {
                    builder.append("=").append(value);
                }
            }
        }

    }

    private static StringBuilder appendFourTabs(StringBuilder builder) {
        appendTwoTabs(appendTwoTabs(builder));
        return builder;
    }

    private static StringBuilder appendTwoTabs(StringBuilder builder) {
        appendTab(appendTab(builder));
        return builder;
    }

    private static StringBuilder appendThreeTabs(StringBuilder builder) {
        appendTwoTabs(appendTab(builder));
        return builder;
    }

    private static StringBuilder appendTab(StringBuilder builder) {
        return builder.append("\t");
    }
}
