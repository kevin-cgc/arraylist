package arraylist;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

import java.net.HttpURLConnection;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Pokemon {
    public final static ImageIcon hhImageIcon = new ImageIcon("src/arraylist/hh.jpg"); //new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Hulk_Hogan.jpg/100px-Hulk_Hogan.jpg")

    private final static int imageScale = 100;
    private final static Map<String, ImageIcon> imageCacheMap = new HashMap<String, ImageIcon>();
    static {
        imageCacheMap.put("", Pokemon.hhImageIcon);
    }
    private final static Map<String, Future<ImageIcon>> futureImageCacheMap = new ConcurrentHashMap<String, Future<ImageIcon>>();

    private String name;
    private String type;
    private ImageIcon imageIcon = hhImageIcon;

    private boolean makingRequest = false;

    public Pokemon(String pokename, String poketype) {
        this.name = pokename;
        this.type = poketype;

        if (Pokemon.imageCacheMap.containsKey(pokename)) {
            this.imageIcon = Pokemon.imageCacheMap.get(pokename);
        }
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

    /**
     * @return if image loaded
     */
    public boolean imageLoaded() {
        return this.imageIcon != Pokemon.hhImageIcon;
    }

    public boolean getMakingRequest() {
        return this.makingRequest;
    }

    public ImageIcon getImage() throws InterruptedException, ExecutionException {
        //uncomment next line if its too laggy
        // if (true) return Pokemon.hhImageIcon;

        String pokemonName = this.name;

        if (this.imageIcon != Pokemon.hhImageIcon) return this.imageIcon;
        if (Pokemon.imageCacheMap.containsKey(pokemonName)) return this.imageIcon = Pokemon.imageCacheMap.get(pokemonName);

        try {
            this.makingRequest = true;

            Future<ImageIcon> f = Pokemon.futureImageCacheMap.get(pokemonName);
            if (f != null) {
                // System.out.println(pokemonName+" using req");
                return this.imageIcon = f.get();
            }

            // System.out.println(pokemonName+" making req");
            CompletableFuture<ImageIcon> nf = new CompletableFuture<ImageIcon>();
            Pokemon.futureImageCacheMap.put(pokemonName, nf);

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

                conn.setReadTimeout(5000);
                InputStream responseStream = conn.getInputStream();


                Scanner s = new Scanner(responseStream);
                s.useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";

                responseStream.close();
                s.close();

                Pattern r = Pattern.compile("<div class=\"rg_meta notranslate\">[^<]*\"ou\":\"([^\"]*)\",[^<]*<\\/div>");
                Matcher m = r.matcher(result);

                if (m.find()) {
                    String imageUrlStr = m.group(1).replace("\\u003d", "\u003d").replace("\\u0026", "\u0026");

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

                    //sometimes throws a nullptrexception for reddit jpgs
                    Image image = ImageIO.read(imageConn.getInputStream()).getScaledInstance(Pokemon.imageScale, Pokemon.imageScale, Image.SCALE_DEFAULT);

                    if (image != null) {
                        ImageIcon ii = new ImageIcon(image, pokemonName);
                        Pokemon.imageCacheMap.put(pokemonName, ii);
                        this.imageIcon = ii;
                        nf.complete(ii);
                        return ii;
                    }
                }

                return Pokemon.hhImageIcon;
            } catch (Exception e) {
                // System.err.println(e);
                return Pokemon.hhImageIcon;
            } finally {
                nf.cancel(true);
                Pokemon.futureImageCacheMap.remove(pokemonName);
            }
        } finally {
            this.makingRequest = false;
        }
    }
}
