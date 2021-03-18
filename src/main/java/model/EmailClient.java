package model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EmailClient {

    private static String API_KEY = "65bea60c483ba82bc096694a63837ede-29561299-46a8433e";
    private static Object emailTo;
    private static Object emailFrom;
    private static Object codeText;
    private static Object subjectText;

    public EmailClient(Object emailTo, Object emailFrom, Object subjectText, Object codeText){
        EmailClient.setEmailTo(emailTo);
        EmailClient.setEmailFrom(emailFrom);
        EmailClient.setSubjectText(subjectText);
        EmailClient.setCodeText(codeText);
    }

    public JsonNode sendSimpleMessage() throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/sandbox868d888d056c4e669d10d3862e70d665.mailgun.org/messages")
			    .basicAuth("api", getApiKey())
                .queryString("from", getEmailFrom())
                .queryString("to", getEmailTo())
                .queryString("subject", getSubjectText())
                .queryString("text", "Olá, tudo bem? aqui está o seu código: "+ getCodeText())
                .asJson();
        return request.getBody();
    }

    public static Object getEmailTo() {
        return emailTo;
    }

    public static void setEmailTo(Object emailTo) {
        EmailClient.emailTo = emailTo;
    }

    public static Object getEmailFrom() {
        return emailFrom;
    }

    public static void setEmailFrom(Object emailFrom) {
        EmailClient.emailFrom = emailFrom;
    }

    public static Object getCodeText() {
        return codeText;
    }

    public static void setCodeText(Object codeText) {
        EmailClient.codeText = codeText;
    }

    public static Object getSubjectText() {
        return subjectText;
    }

    public static void setSubjectText(Object subjectText) {
        EmailClient.subjectText = subjectText;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}
