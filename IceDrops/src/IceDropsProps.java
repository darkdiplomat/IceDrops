import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IceDropsProps{
	public IceDropsProps(){
		loadIni();
	}
	
	private String INIFILE = "plugins/config/IceDrops/IceDropsConfig.ini";
	private Logger log = Logger.getLogger("Minecraft");
	private String IceDropsChance = "0.05";
	private String SpawnWater = "false";
	private String RequireTool = "false";
	private String ToolIds = "257,270,274,278,285";

	public double getIceDrops() {
		double IceDrops = 0.05D;
		try {
			IceDrops = Double.parseDouble(IceDropsChance);
		} catch (NumberFormatException n) {
			log.log(Level.SEVERE, "[IceDrops] Unable to parse IceDropsChance, using defaults");
			IceDrops = 0.05D;
		}
		return IceDrops;
	}

	public boolean getNoWater() {
		boolean NoWater = true;
		try {
			NoWater = Boolean.parseBoolean(SpawnWater);
		} catch (Exception e) {
			log.log(Level.SEVERE, "[IceDrops] Unable to parse SpawnWater, using defaults");
			NoWater = true;
		}
		return NoWater;
	}
  
	public String getToolIds(){
		return ToolIds;
	}
  
	public boolean getRequireTool(){
		boolean Require = false;
		try{
			Require = Boolean.parseBoolean(RequireTool);
		}catch(Exception e){
			log.log(Level.SEVERE, "[IceDrops] Unable to parse RequireTool, using defaults");
			Require = false;
		}
		return Require;
	}

	private void loadIni() {
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

	private void createIni() {
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