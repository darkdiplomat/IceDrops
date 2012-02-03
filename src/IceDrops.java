import java.util.logging.Logger;

/**
* IceDrops v1.x
* Copyright (C) 2012 Visual Illusions Entertainment
* @author darkdiplomat <darkdiplomat@visualillusionsent.net>
* 
* This file is part of IceDrops
* 
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see http://www.gnu.org/copyleft/gpl.html.
*/

public class IceDrops extends Plugin{
	String name = "IceDrops";
	String version = "1.3.2";
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
