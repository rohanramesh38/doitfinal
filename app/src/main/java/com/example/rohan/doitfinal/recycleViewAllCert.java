package com.example.rohan.doitfinal;

public class recycleViewAllCert {
    private String TestClient;
    private String CertificationExam;

    public recycleViewAllCert() {
    }

    public String getTestClient() {
        return TestClient;
    }

    public void setTestClient(String testClient) {
        TestClient = testClient;
    }

    public String getCertificationExam() {
        return CertificationExam;
    }

    public void setCertificationExam(String certificationExam) {
        CertificationExam = certificationExam;
    }

    public recycleViewAllCert(String testClient, String certificationExam) {
        TestClient = testClient;
        CertificationExam = certificationExam;
    }
}
