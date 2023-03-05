package telran.project.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import telran.project.dto.JsonReader;
import telran.project.repository.CurrSubscrRepository;

import java.io.IOException;


@Component

public class CurrencyService {


    @Autowired
    JsonReader jsonReader;
    @Autowired
    CurrSubscrRepository subscrRepository;


    public String getCurrencyRate(String from, String to) throws IOException {

        String webService = "https://api.frankfurter.app/latest?amount=1&from=" + from + "&to=" + to;
        String json = jsonReader.httpGetString(webService);

        JSONObject jo = new JSONObject(json);

        double rate = jo.getJSONObject("rates").getDouble(to);
        return "1 " + from + " = " + rate + " " + to;
    }

    public String getListOfAvailableCurrency() throws IOException {
        String webService = "https://api.frankfurter.app/currencies";
        String json = jsonReader.httpGetString(webService);
        JSONObject jo = new JSONObject(json);
        String list = jo.toString().replace('{', ' ').replace('}', ' ').
                replaceAll(",", "\n");
        return list;
    }
}


