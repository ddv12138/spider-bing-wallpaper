package getBingWallpaer;

import java.net.MalformedURLException;
import java.net.URL;

public class StartConection {

	public static void main(String[] args) {
		URL url = null;
        try {
			for(int i = -1;i<8;i++) {
				url = new URL("http://cn.bing.com/HPImageArchive.aspx?idx="+i+"&n=1");
				HandleUrl handler = new HandleUrl(url);
				handler.saveWallpaperFromUrl();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
}
