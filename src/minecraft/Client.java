

import org.lwjgl.opengl.Display;

public class Client {

	public static final Client INSTANCE = new Client();
	
	
	public final double CLIENT_VERSION = 1.0;
	public final String CLIENT_NAME = "DaWae";
	
	public void onEnable(){
		Display.setTitle(CLIENT_NAME + " | " + CLIENT_VERSION);
	}
	
	public void onDisable(){
		
	}
}
