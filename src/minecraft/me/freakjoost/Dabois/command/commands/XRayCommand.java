package me.freakjoost.Dabois.command.commands;

import net.minecraft.block.Block;
import me.freakjoost.Dabois.Dabois;
import me.freakjoost.Dabois.command.Command;
import me.freakjoost.Dabois.module.Module;
import me.freakjoost.Dabois.utils.PlayerUtils;
import me.freakjoost.Dabois.utils.XRayUtils;

public class XRayCommand implements Command {

    @Override
    public boolean run(String[] args) {
        if (args.length == 3) {
        	
        	if(!args[1].toLowerCase().equals("add") && !args[1].toLowerCase().equals("remove") && !args[1].toLowerCase().equals("distance")){
        		System.out.println(!args[1].toLowerCase().equals("add") && !args[1].toLowerCase().equals("remove"));
        		return false;
        	}
            
        	if(!isInteger(args[2])){
        		PlayerUtils.tellPlayer("Please enter a valid id number.");
                return true;
        	}
        	
    		if (Block.getBlockById(Integer.parseInt(args[2])) == null) {
                PlayerUtils.tellPlayer("The block with the id " + args[2] + " does not exist.");
                return true;
            }
    		
    		if(args[1].toLowerCase().equals("distance")){
    			XRayUtils.setDistance(Integer.getInteger(args[2]));
            	PlayerUtils.tellPlayer("The XRay distance has been set to " + args[2] + ".");
            	return true;
            }
    		
            if(args[1].toLowerCase().equals("add")){
            	if(XRayUtils.isXRayBlock(Block.getBlockById(Integer.parseInt(args[2])))){
            		PlayerUtils.tellPlayer("The block with the name " + Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName() + " was already registered.");
            		return true;
            	}
    			XRayUtils.add(Block.getBlockFromName(args[2]));
            	PlayerUtils.tellPlayer("The block with the name " + Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName() + " has been added.");
            	return true;
            }
            
            if(args[1].toLowerCase().equals("remove")){
            	if(XRayUtils.isXRayBlock(Block.getBlockById(Integer.parseInt(args[2])))){
            		XRayUtils.remove(Block.getBlockFromName(args[2]));
            		PlayerUtils.tellPlayer("The block with the name " + Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName() + " has been removed.");
            		return true;
            	}else{
            		PlayerUtils.tellPlayer("The block with the name " + Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName() + " was not registered.");
            		return true;
            	}
            }
            return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return "USAGE: -xray [add,remove,distance] [blockid,blockid,numberOfDistance]";
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
