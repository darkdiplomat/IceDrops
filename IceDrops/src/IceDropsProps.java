import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IceDropsProps{
	private static String INIFILE = "plugins/config/IceDrops/IceDropsConfig.ini";
	static Logger log = Logger.getLogger("Minecraft");
	static String IceDropsChance = "0.05";
	static String SpawnWater = "false";
	static String RequireTool = "false";
	static String ToolIds = "257,270,274,278,285";

	public static double getIceDrops() {
		double IceDrops = 0.05D;
		try {
			IceDrops = Double.parseDouble(IceDropsChance);
		} catch (NumberFormatException n) {
			log.log(Level.SEVERE, "[IceDrops] Unable to parse IceDropsChance, using defaults");
			IceDrops = 0.05D;
		}
		return IceDrops;
	}

	public static boolean getNoWater() {
		boolean NoWater = true;
		try {
			NoWater = Boolean.parseBoolean(SpawnWater);
		} catch (Exception e) {
			log.log(Level.SEVERE, "[IceDrops] Unable to parse SpawnWater, using defaults");
			NoWater = true;
		}
		return NoWater;
	}
  
	public static String getToolIds(){
		return ToolIds;
	}
  
	public static boolean getRequireTool(){
		boolean Require = false;
		try{
			Require = Boolean.parseBoolean(RequireTool);
		}catch(Exception e){
			log.log(Level.SEVERE, "[IceDrops] Unable to parse RequireTool, using defaults");
			Require = false;
		}
		return Require;
	}

	public static void loadIni() {
		File inifile = new File(INIFILE);
		if (inifile.exists()){
			try {
				Properties iniSettings = new Properties();
				iniSettings.load(new FileInputStream(inifile));
				IceDropsChance = iniSettings.getProperty("Ice-Drops-Chance", IceDropsChance);
				SpawnWater = iniSettings.getProperty("Spawn-Water", SpawnWater);
				RequireTool = iniSettings.getProperty("Require-Tool", RequireTool);
				ToolIds = iniSettings.getProperty("Tool-IDs", ToolIds);
			} catch (Exception e) {
				log.log(Level.SEVERE, "[IceDrops] file load failed, using defaults.");
			}
			createIni();
		}
		else{
			createIni();
		}
	}

	public static void createIni() {
		File inifile = new File(INIFILE);
		try {
			inifile.getParentFile().mkdirs();
			BufferedWriter outChannel = new BufferedWriter(new FileWriter(inifile));
			outChannel.write("# The format is 0.00 For a 25% chance type 0.25 For 100% type 1.00"); outChannel.newLine();
			outChannel.write("Ice-Drops-Chance=" + IceDropsChance); outChannel.newLine();
			outChannel.write("# Set to false if you don't want water to spawn after breaking ice"); outChannel.newLine();
			outChannel.write("Spawn-Water=" + SpawnWater); outChannel.newLine();
			outChannel.write("# Set to true to require the use of tools to get Ice"); outChannel.newLine();
			outChannel.write("Require-Tool="+RequireTool); outChannel.newLine();
			outChannel.write("# ID's of the Tools you wish to allow Ice to drop with (requires above to be true) Sperate with a Comma ','"); outChannel.newLine();
			outChannel.write("Tool-IDs="+ToolIds);
			outChannel.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, "[IceDrops] file creation failed, using defaults.");
		}
	}
}