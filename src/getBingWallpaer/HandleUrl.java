package getBingWallpaer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HandleUrl {
	URL url = null;
	public HandleUrl(URL url) {
		this.url = url;
	}
    protected void saveWallpaperFromUrl() {
    	URLConnection urlconn = null;
    	BufferedReader br = null;
        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream = null;
        ByteArrayOutputStream output = null;
    	try {
			urlconn = url.openConnection();
			br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			String buf = null;
			String html = "";
	        while ((buf = br.readLine()) != null) {
	                html+=buf;
	        }
	        Document doc = Jsoup.parse(html);
	        
	        Element urlEle = doc.select("url").first();
	        Element dateEle = doc.select("startdate").first();
	        String refer = urlEle.text();
	        String date = dateEle.text();
	        String filename = refer.substring(refer.lastIndexOf("/")+1, refer.length());
	        String newUrl = "http://cn.bing.com"+refer;
	        System.out.println("地址:"+newUrl);
	        
	        url = new URL(newUrl);
	        urlconn = url.openConnection();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        Date picdate = sdf.parse(date);
	        sdf = new SimpleDateFormat("yyyy-MM");
	        String filepath = sdf.format(picdate);
	        filepath = ".\\bing-wallpaper\\"+filepath+"\\"+filename;
	        System.out.println("存储路径："+filepath);
	        
	        File img = new File(filepath);
	        if(!img.exists()) {
	        	img.getParentFile().mkdirs();
	        	img.createNewFile();
	        }
	        
	        dataInputStream = new DataInputStream(url.openStream());
	        fileOutputStream = new FileOutputStream(img);
	        output = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024]; 
	        int length;
	        while ((length = dataInputStream.read(buffer)) > 0) {  
	            output.write(buffer, 0, length);  
	        }
	        fileOutputStream.write(output.toByteArray());
	        System.out.println("保存成功<--->"+filename+"<--->"+filepath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
	        try {
				br.close();
		        dataInputStream.close();
		        fileOutputStream.close();
		        output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
