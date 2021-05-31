package TourPlannerUI.businesslayer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapQuestManager {

    public static String requestRoute(String start, String end) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL("http://www.mapquestapi.com/directions/v2/route?key=Yadh13E9Z3FiN2w6As9tobdlRwPuCEj3&from=" + start.replaceAll(" ","") + "&to=" + end.replaceAll(" ",""));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(0));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes("");
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static Image requestRouteImage(String session, JSONObject boundingBox) {
        //HttpURLConnection connection = null;
        try {
            String params;
            params = "&size=700,300";
            params += "&defaultMarker=none";
            params += "&zoom=11";
            params += "&rand=737758036";
            params += "&session="+session;
            String box = boundingBox.getJSONObject("lr").getFloat("lat") + "," + boundingBox.getJSONObject("lr").getFloat("lng") + "," + boundingBox.getJSONObject("ul").getFloat("lat") + "," + boundingBox.getJSONObject("ul").getFloat("lng");
            params += "&boundingBox="+box;

            URL url = new URL("http://www.mapquestapi.com/staticmap/v5/map?key=Yadh13E9Z3FiN2w6As9tobdlRwPuCEj3" + params);
            InputStream is = null;
            try {
                is = url.openStream();
            }
            catch (Exception e){
                return null;
            }

            Image image = SwingFXUtils.toFXImage(ImageIO.read(is), null);
            return image;

            /*
            OutputStream os = new FileOutputStream("test.jpg");
            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
            //connection.disconnect();

            return "He";
            /*
            FileInputStream inputstream = new FileInputStream("C:\Users\szabo\OneDrive\Desktop\Fachhochschule\4. Semester\SWE\tourplanner_szabo\"+from+"-"+to+".jpg");
                    Image image = new Image(inputstream);
            ImageView imageView = new ImageView(image);

            GridPane secondaryLayout = new GridPane();
            secondaryLayout.add(imageView,0,0);


            Scene secondScene = new Scene(secondaryLayout, 640, 480);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Show Map");
            newWindow.setScene(secondScene);

            newWindow.show();

/*
            //Create connection

            URL url = new URL("http://www.mapquestapi.com/staticmap/v5/map?key=Yadh13E9Z3FiN2w6As9tobdlRwPuCEj3" + params);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "image/jpeg");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(0));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes("");
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}