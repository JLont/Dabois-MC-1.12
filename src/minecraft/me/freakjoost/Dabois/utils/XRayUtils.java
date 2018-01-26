package me.freakjoost.Dabois.utils;

import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import me.freakjoost.Dabois.module.render.XRay;

public class XRayUtils
{
	public static void initXRayBlocks()
	{
		// ores
		add(Blocks.IRON_ORE);
		add(Blocks.IRON_BLOCK);
		
		add(Blocks.GOLD_ORE);
		add(Blocks.GOLD_BLOCK);
		
		add(Blocks.EMERALD_ORE);
		add(Blocks.EMERALD_BLOCK);
		
		add(Blocks.DIAMOND_ORE);
		add(Blocks.DIAMOND_BLOCK);

		add(Blocks.LAVA);
		add(Blocks.FLOWING_LAVA);
		
		// spawners
		add(Blocks.MOB_SPAWNER);
	}
	
	public static void add(Block block)
	{
		XRay.xrayBlocks.add(block);
	}
	
	public static void remove(Block block)
	{
		XRay.xrayBlocks.remove(block);
	}
	
	public static boolean isXRayBlock(Block b)
	{
		return XRay.xrayBlocks.contains(b);
	}
	
	public static void setDistance(int d)
	{
		XRay.currentDist = d;
	}
	
	public static void sortBlocks()
	{
		Collections.sort(XRay.xrayBlocks,
			(o1, o2) -> Block.getIdFromBlock(o1) - Block.getIdFromBlock(o2));
	}
}