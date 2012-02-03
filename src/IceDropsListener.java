import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


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

public class IceDropsListener extends PluginListener{
	IceDropsProps ICP;
	IceDrops ID;
	public IceDropsListener(IceDropsProps ICP, IceDrops ID){
		this.ICP = ICP;
		this.ID = ID;
	}
	
	public boolean onCommand(Player player, String[] cmd){
		if(cmd[0].equals("/icedrops")){
			player.sendMessage("§6----- §7IceDrops "+ID.version+" by §aDarkDiplomat §6-----");
			if(!ID.isLatest()){
				player.sendMessage("§6----- §7Update Availible: v"+getCurrentVer()+" §6-----");
			}
			else{
				player.sendMessage("§6----- §7Latest Version is Installed§6 -----");
			}
			return true;
		}
		return false;
	}
	
	public boolean onBlockBreak(Player player, Block block){
		boolean SpawnWater = ICP.getNoWater();
		boolean RequireTool = ICP.getRequireTool();
		if (block.getType() == 79) {
			boolean rperm = false, c2perm = false, cperm = false;
			Plugin Realms = null, C2 = null, Cuboid = null;
			if (etc.getLoader().getPlugin("Realms")!=null){
				if (etc.getLoader().getPlugin("Realms").isEnabled()) {
					rperm = (Boolean)etc.getLoader().callCustomHook("Realms-PermissionCheck", new Object[] {"destroy", player, block});
					Realms = etc.getLoader().getPlugin("Realms");
				}
			}
			if (etc.getLoader().getPlugin("Cuboids2") != null){
				if (etc.getLoader().getPlugin("Cuboids2").isEnabled()){
					c2perm = (Boolean)etc.getLoader().callCustomHook("CuboidAPI", new Object[] {player, block, "CAN_MODIFY"});
					C2 = etc.getLoader().getPlugin("Cuboids2");
				}
			}
			if (etc.getLoader().getPlugin("CuboidPlugin") != null){
				if (etc.getLoader().getPlugin("CuboidPlugin").isEnabled()){
					cperm = (Boolean)etc.getLoader().callCustomHook("CuboidPlugin-PermissionCheck", new Object[] {player, block});
					Cuboid = etc.getLoader().getPlugin("CuboidPlugin");
				}
			}
			if (player.canUseCommand("/icedrops")) {
				if(RequireTool){
					if(isTool(player.getItemInHand())){
						if ((rperm) || (c2perm) || (cperm)){
							return drop(block);
						}else if((Realms == null) && (C2 == null) && (Cuboid == null)){
							return drop(block);
						}
					}
					else if(!SpawnWater){
						if ((rperm) || (c2perm) || (cperm)){
							block.setType(0);
							block.update();
							return true;
						}
						else if((Realms == null) && (C2 == null) && (Cuboid == null)){
							block.setType(0);
							block.update();
							return true;
						}
					}
				}
				else{
					if ((rperm) || (c2perm) || (cperm)){
						return drop(block);
					}else if(Realms == null && C2 == null && Cuboid == null){
						return drop(block);
					}
				}
			}
			else{
				if(!SpawnWater){
					if ((rperm) || (c2perm) || (cperm)){
						block.setType(0);
						block.update();
						return true;
					}else if(Realms == null && C2 == null && Cuboid == null){
						block.setType(0);
						block.update();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean drop(Block block){
		double drops = Math.random();
		boolean SpawnWater = ICP.getNoWater();
		double dropIceChance = ICP.getIceDrops();
		if ((drops <= dropIceChance) && (SpawnWater)) {
			block.getWorld().dropItem(block.getX(), block.getY(), block.getZ(), 79, 1, 0);
		}
		else if ((drops <= dropIceChance) && (!SpawnWater)) {
			block.getWorld().dropItem(block.getX(), block.getY(), block.getZ(), 79, 1, 0);
			block.setType(0);
			block.update();
			return true;
		}
		else if (!SpawnWater) {
			block.setType(0);
			block.update();
			return true;
		}
		return false;
	}
	
	public boolean isTool(int ToolID){
		String[] allowedTools = ICP.getToolIds().split(",");
		for(int i = 0; i < allowedTools.length; i++){
			int AllowedID = -2;
			try{
				AllowedID = Integer.parseInt(allowedTools[i]);
			}catch (NumberFormatException nfe){
				AllowedID = -2;
			}
			if(ToolID == AllowedID){
				return true;
			}
		}
		return false;
	}
	
	public String getCurrentVer(){
		String address = "http://www.visualillusionsent.net/cmod_plugins/Versions.html";
		String err = "§7Unable to retreive Current Version";
		URL url = null;
		try {
			url = new URL(address);
		} catch (MalformedURLException e) {
			return err;
		}
		String Version = null;
		String[] Vpre = new String[2];
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
			return err;
		}
		return Version;
	} 
}
