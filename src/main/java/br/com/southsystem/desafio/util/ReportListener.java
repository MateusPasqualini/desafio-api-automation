package br.com.southsystem.desafio.util;


import br.com.southsystem.desafio.util.reporter.ReportBase;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ReportListener implements ITestListener {
    public ReportListener() {
    }

    public void onFinish(ITestContext context) {
        ReportBase.getInstance().finishReport();
    }

    public void onStart(ITestContext context) {
        ReportBase.getInstance().initReport(context);
    }

    public void onTestFailure(ITestResult result) {
        ReportBase.getInstance().finishTest(result);
    }

    public void onTestSkipped(ITestResult result) {

        ReportBase.getInstance().initTestSkipped(result.getMethod().getMethodName());
        ReportBase.getInstance().finishTest(result);
    }

    public void onTestStart(ITestResult result) {
        ReportBase.getInstance().initTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        ReportBase.getInstance().finishTest(result);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

}

