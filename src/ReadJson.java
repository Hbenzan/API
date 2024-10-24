import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLOutput;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Program for print data in JSON format.
public class ReadJson {
    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

        JSONObject file = new JSONObject();
        file.put("Full Name", "ditto");
        file.put("Roll No.", (1704310046));
        file.put("Tution Fees", (65400));


        // To print in JSON format.
        System.out.print(file.get("Tution Fees"));
        System.out.print(file.get("Full Name"));
      //  pull();
    }

    public  void pull() throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {

            URL url = new URL("https://pokeapi.co/api/v2/pokemon/ditto");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totlaJson);
        System.out.println(jsonObject);

        try {

            String name = (String)jsonObject.get("name");
            System.out.println(name);

            org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("abilities");
            int n =   msg.size(); //(msg).length();
            for (int i = 0; i < n; ++i) {
                JSONObject test =(JSONObject) msg.get(i);
                System.out.println(test);
                JSONObject hb =(JSONObject) test.get("ability");
                System.out.println(hb);
                String benz =(String) hb.get("name");
                System.out.println(benz);
                // System.out.println(person.getInt("key"));
            }


          //  String height = (String)jsonObject.get("height");
        //    String birthYear = (String)jsonObject.get("birth_year");
//            System.out.println(birthYear);
//            System.out.println(height);
//
//            String eyecolor = (String)jsonObject.get("brown");
//            System.out.println(eyecolor);
//
//            String mass = (String)jsonObject.get("151");
//            System.out.println(mass);


        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }
}


