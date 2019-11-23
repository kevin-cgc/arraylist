package arraylist;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.HttpURLConnection;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Pokemon {
    private static int imageScale = 100;

    private String name;
    private String type;
    private ImageIcon imageIcon;

    public Pokemon(String pokename, String poketype) {
        this.name = pokename;
        this.type = poketype;

        this.imageIcon = this.getImage();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @return the ii
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }


    public ImageIcon getImage() {
        String pokemonName = this.name;

        try {
            URL url = new URL("https://www.google.com/search?q=" + URLEncoder.encode(pokemonName, "UTF-8") + "%20pokemon&sa=X&tbm=isch&gbv=2&safe=active");
            // URL url = new URL("https://example.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("authority", "www.google.com");
            conn.setRequestProperty("upgrade-insecure-requests", "1");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) Gecko/20100101 Firefox/53.0");
            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.setRequestProperty("referer", "https://www.google.com/search?q=razer&gbv=1&prmd=ivns&source=lnms&tbm=isch&sa=X");
            // conn.setRequestProperty("accept-encoding", "gzip, deflate");
            conn.setRequestProperty("accept-language", "en-US,en;q=0.9");
            // conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // conn.setRequestProperty("Accept", "application/json");

            InputStream responseStream = conn.getInputStream();


            Scanner s = new Scanner(responseStream);
            s.useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";

            responseStream.close();
            s.close();

            Pattern r = Pattern.compile("<div class=\"rg_meta notranslate\">[^<]*\"ou\":\"([^\"]*)\",[^<]*<\\/div>");
            Matcher m = r.matcher(result);

            if (m.find()) {
                String imageUrlStr = m.group(1);

                // Image image = ImageIO.read(new URL(imageUrl)).getScaledInstance(Pokemon.imageScale, Pokemon.imageScale, Image.SCALE_DEFAULT);

                URL imageURL = new URL(imageUrlStr);
                HttpURLConnection imageConn = (HttpURLConnection) imageURL.openConnection();
                imageConn.setDoOutput(true);
                imageConn.setRequestProperty("authority", "cdn.bulbagarden.net");
                imageConn.setRequestProperty("upgrade-insecure-requests", "1");
                imageConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) Gecko/20100101 Firefox/53.0");
                imageConn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                imageConn.setRequestProperty("referer", "https://www.google.com/");
                imageConn.setReadTimeout(5000);

                Image image = ImageIO.read(imageConn.getInputStream()).getScaledInstance(Pokemon.imageScale, Pokemon.imageScale, Image.SCALE_DEFAULT);

                if (image != null) {
                    return new ImageIcon(image, pokemonName);
                }
            }
            
            return new ImageIcon("src/arraylist/hh.jpg");
        } catch (Exception e) {
            System.err.println(e);
            try {
                return new ImageIcon("src/arraylist/hh.jpg");
            } catch (Exception e2) {
                System.err.println(e2);
                return new ImageIcon();
            }
        }
    }
}
