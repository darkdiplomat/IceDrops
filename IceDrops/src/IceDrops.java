import java.util.logging.Logger;

public class IceDrops extends Plugin{
	String name = "IceDrops";
	String version = "1.1";
	String author = "Darkdiplomat";
	static Logger log = Logger.getLogger("Minecraft");
  
	public void initialize() {
		IceDropsListener listener = new IceDropsListener();
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_BROKEN, listener, this, PluginListener.Priority.MEDIUM);
	}

	public void enable() {
		IceDropsProps.loadIni();
		log.info(this.name + " version " + this.version + " by " + this.author + " is enabled!");
	}

	public void disable() {
		log.info(this.name + " version " + this.version + " is disabled!");
	}
}