package telran.project.dto;

import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class JsonReader {

    public String httpGetString(String webService) throws IOException {
        URL url = new URL(webService);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        StringBuilder builder = new StringBuilder();
        try (
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        )
        {
            bufferedReader.lines()
                    .forEach(
                            builder::append
                    );
        }
        return builder.toString();
    }
}
