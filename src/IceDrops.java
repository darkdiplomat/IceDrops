import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
	private String name = "IceDrops";
	public final String version = "1.3.1";
	public final String author = "DarkDiplomat";
	Logger log = Logger.getLogger("Minecraft");
	IceDropsProps ICP;

	public void enable() {
		ICP = new IceDropsProps();
		if(!isLatest()){
			log.info("IceDrops: There is an update available!");
		}
		log.info(name + " version " + version + " by " + author + " is enabled!");
	}

	public void disable() {
		log.info(name + " version " + version + " is disabled!");
	}
	
	public void initialize() {
		IceDropsListener listener = new IceDropsListener(ICP, this);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_BROKEN, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.LOW);
	}
	
	public boolean isLatest(){
		String address = "http://www.visualillusionsent.net/cmod_plugins/Versions.html";
		URL url = null;
		try {
			url = new URL(address);
		} catch (MalformedURLException e) {
			return true;
		}
		String Version = null;
		String[] Vpre = new String[1]; 
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("IceDrops=")){
					Vpre = inputLine.split("=");
					Version = Vpre[1].replace("</p>", "");
				}
			}
			in.close();
		} catch (IOException e) {
			return true;
		}
		if(Version != null){
			return (Version.equals(version));
		}
		else{
			return true;
		}
	}
}
