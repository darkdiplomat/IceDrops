public class IceDropsListener extends PluginListener{
	IceDropsProps ICP;
	public IceDropsListener(IceDropsProps ICP){
		this.ICP = ICP;
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
							drop(block);
						}else if((Realms == null) && (C2 == null) && (Cuboid == null)){
							drop(block);
						}
					}
					else if(!SpawnWater){
						if ((rperm) || (c2perm) || (cperm)){
							block.setType(0);
							block.update();
						}
						else if((Realms == null) && (C2 == null) && (Cuboid == null)){
							block.setType(0);
							block.update();
						}
					}
				}
				else{
					if ((rperm) || (c2perm) || (cperm)){
						drop(block);
					}else if(Realms == null && C2 == null && Cuboid == null){
						drop(block);
					}
				}
			}
			else{
				if(!SpawnWater){
					if ((rperm) || (c2perm) || (cperm)){
						block.setType(0);
						block.update();
					}else if(Realms == null && C2 == null && Cuboid == null){
						block.setType(0);
						block.update();
					}
				}
			}
		}
		return false;
	}
	
	public void drop(Block block){
		double drops = Math.random();
		boolean SpawnWater = ICP.getNoWater();
		double dropIceChance = ICP.getIceDrops();
		if ((drops <= dropIceChance) && (SpawnWater)) {
			etc.getServer().getWorld(0).dropItem(block.getX(), block.getY(), block.getZ(), 79, 1, 0);
		}
		else if ((drops <= dropIceChance) && (!SpawnWater)) {
			etc.getServer().getWorld(0).dropItem(block.getX(), block.getY(), block.getZ(), 79, 1, 0);
			block.setType(0);
			block.update();
		}
		else if (!SpawnWater) {
			block.setType(0);
			block.update();
		}
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
}