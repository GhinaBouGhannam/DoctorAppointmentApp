package com.example.midtermproject;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;

public class HTTPCall {
    public static Pair<Integer,String> executeAPI(String requestType, String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType);

            connection.setRequestProperty("Content-Language", "en-US");

            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            //Send request
            if(!urlParameters.equals("")){DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();}
            //Get Response
            int responseCode = connection.getResponseCode();
            InputStream is;
            if(responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK ){
             is = connection.getInputStream();}
            else is=connection.getErrorStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return new Pair<>(responseCode,response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

        private static final String NAME_KEY = "name";
        private static final String PASS_KEY = "password";
    private static final String NAMEDOC_KEY = "doc_email";
    private static final String PASSDOC_KEY = "doc_password";
        private static final String PHONE_KEY = "phone_number";

        public static String mapToJsonString(String name, String password, String phonenumber) {
            try {

                // Create a Map to hold the data
                Map<String, Object> data = new HashMap<>();
                data.put(NAME_KEY, name);
                data.put(PASS_KEY, password);
                if(phonenumber!=""){
                    data.put(PHONE_KEY, phonenumber);
                }
                // Create an ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                // Convert the Map to JSON string
                return objectMapper.writeValueAsString(data);
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
                return null;
            }
        }

    public static String mapToJsonStringDoc(String name, String password) {
        try {

            // Create a Map to hold the data
            Map<String, Object> data = new HashMap<>();
            data.put(NAMEDOC_KEY, name);
            data.put(PASSDOC_KEY, password);

            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the Map to JSON string
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }

    public static String mapToJsonStringDocDetails(String name, String desc, String avail, String add ,String email,
                                                   String num,int fees,String password, String special) {
        try {
            // Create a Map to hold the data
            Map<String, Object> data = new HashMap<>();
            data.put("doc_fullname", name);
            data.put("doc_specialty", special);
            data.put( "doc_description", desc);
            data.put( "doc_availability", avail);
            data.put("doc_address", add);
            data.put("doc_email", email);
            data.put( "doc_phonenumber", num);
            data.put( "doc_fees", fees);
            data.put("doc_password",password);

            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the Map to JSON string
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }

    public static String mapToJsonStringAppointment( String doa, String startTime, String appointmentDescription, int userId, String docId, String appointmentAcception) {
        try {
            // Create a Map to hold the appointment data
            Map<String, Object> data = new HashMap<>();
            data.put("doa", doa);
            data.put("start_time", startTime);
            data.put("appointment_description", appointmentDescription);

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", userId);
            data.put("user", userMap);

            // Create a Map for the doctor data
            Map<String, Object> docMap = new HashMap<>();
            docMap.put("doc_id", docId);
            data.put("doc", docMap);

            data.put("appointment_acception", appointmentAcception);

            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the Map to JSON string
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }
}