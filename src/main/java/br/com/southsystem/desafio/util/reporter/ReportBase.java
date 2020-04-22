package br.com.southsystem.desafio.util.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ReportBase {
    private static ReportBase instance;
    private static final Object lock = new Object();

    private static ExtentReports reports = null;
    private static String message = null;
    private static String lastTestName = null;

    private static String testOutPutPath = System.getProperty("user.dir") + "/target/AutomationReports/";
    private String documentTitle;
    private static Map<Integer, ExtentTest> extentTestMap;

    static {
        extentTestMap = new HashMap();
    }

    private ReportBase() {
    }

    public static ReportBase getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new ReportBase();
            }
        }
        return instance;
    }

    public synchronized void initReport(ITestContext context) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(this.getReportName(context)));
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setDocumentTitle(documentTitle);
        htmlReporter.config().setReportName(documentTitle);
        reports = new ExtentReports();
        reports.setSystemInfo("Environment", "QA");
        reports.attachReporter(htmlReporter);
    }

    public synchronized void initTestSkipped(String method) {
        if (!lastTestName.equals(method)) {
            initTest(method);
        }

    }

    public synchronized ExtentTest initTest(String method) {
        message = String.format("Init test '%s'.", method);
        return initTest(method, message);
    }

    public synchronized ExtentTest initTest(String method, String message) {
        ExtentTest testInfo = reports.createTest(method);
        extentTestMap.put((int) Thread.currentThread().getId(), testInfo);
        testInfo.log(Status.INFO, message);
        lastTestName = method;
        return testInfo;
    }

    public synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public synchronized void finishTest(ITestResult result) {
        if (result.getStatus() == 1) {
            message = String.format("Test '%s' is passed.", result.getName());
            getTest().log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
        } else if (result.getStatus() == 2) {
            message = String.format("Test '%s' failure.", result.getName());
            getTest().log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
            getTest().log(Status.FAIL, MarkupHelper.createCodeBlock(result.getThrowable().toString()));
            message = String.format("%s Details: %s.", message, result.getThrowable().toString());
        } else if (result.getStatus() == 3) {
            message = String.format("Test '%s' is skipped.", result.getName());
            getTest().log(Status.SKIP, MarkupHelper.createLabel(message, ExtentColor.ORANGE));
            getTest().log(Status.SKIP, MarkupHelper.createCodeBlock(result.getThrowable().toString()));
            message = String.format("%s Details: %s.", message, result.getThrowable().toString());
        } else {
            message = String.format("Test '%s' with status '%s'.", result.getName(), result.getStatus());
            getTest().log(Status.WARNING, MarkupHelper.createLabel(message, ExtentColor.GREY));
            getTest().log(Status.WARNING, MarkupHelper.createCodeBlock(result.getThrowable().toString()));
            message = String.format("%s Details: %s.", message, result.getThrowable().toString());
        }
    }

    public synchronized void finishReport() {
        reports.flush();
    }

    public synchronized void logCodeBlock(String text, String codeBlock) {
        try {
            getTest().log(Status.INFO, text);
            getTest().log(Status.INFO, MarkupHelper.createCodeBlock(codeBlock));
        } catch (NullPointerException e){
            e.getMessage();
        }
    }

    private String getReportName(ITestContext context) {
        String reportName = String.format("%sReport", context.getSuite().getName());
        String finalReportPath = String.format("%s/%s.html", testOutPutPath, reportName);
        documentTitle = String.format("Automation Test Report - %s", reportName);
        if (!(new File(testOutPutPath)).exists()) {
            (new File(testOutPutPath)).mkdir();
        }

        return finalReportPath;
    }
}

