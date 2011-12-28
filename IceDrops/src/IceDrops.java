import java.util.logging.Logger;

public class IceDrops extends Plugin{
	String name = "IceDrops";
	String version = "1.3";
	String author = "Darkdiplomat";
	Logger log = Logger.getLogger("Minecraft");
	IceDropsProps ICP;

	public void enable() {
		ICP = new IceDropsProps();
		log.info(name + " version " + version + " by " + author + " is enabled!");
	}

	public void disable() {
		log.info(name + " version " + version + " is disabled!");
	}
	
	public void initialize() {
		IceDropsListener listener = new IceDropsListener(ICP);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_BROKEN, listener, this, PluginListener.Priority.MEDIUM);
	}
}