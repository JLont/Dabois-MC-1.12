package me.freakjoost.Dabois.module.render;

import java.util.ArrayList;
import java.util.List;

import me.freakjoost.Dabois.event.EventTarget;
import me.freakjoost.Dabois.event.events.EventRender3D;
import me.freakjoost.Dabois.event.events.EventUpdate;
import me.freakjoost.Dabois.module.Category;
import me.freakjoost.Dabois.module.Module;
import me.freakjoost.Dabois.utils.RenderUtils;
import me.freakjoost.Dabois.utils.XRayUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class XRay extends Module {

	private int solidBox;
	private int outlinedBox;
	private int crossBox;
	
	private int ores;
	
	public static ArrayList<Block> xrayBlocks;
	public static ArrayList<BlockPos> oresPos;
	
	public static int currentDist = 32;
	
	public XRay() {
		super("XRay", Keyboard.KEY_X, Category.RENDER);
		
		xrayBlocks = new ArrayList<>();
		XRayUtils.initXRayBlocks();
	}

	public void onEnable() {
		super.onEnable();
		outlinedBox = GL11.glGenLists(1);
		GL11.glNewList(outlinedBox, GL11.GL_COMPILE);
		RenderUtils.drawOutlinedBox();
		GL11.glEndList();
		
		solidBox = GL11.glGenLists(1);
		GL11.glNewList(solidBox, GL11.GL_COMPILE);
		RenderUtils.drawSolidBox();
		GL11.glEndList();
		
		crossBox = GL11.glGenLists(1);
		GL11.glNewList(crossBox, GL11.GL_COMPILE);
		RenderUtils.drawCrossBox();
		GL11.glEndList();
		
		ores = GL11.glGenLists(1);
	}

	public void onDisable() {
		super.onDisable();
		GL11.glDeleteLists(outlinedBox, 1);
		outlinedBox = 0;
		
		GL11.glDeleteLists(solidBox, 1);
		solidBox = 0;
		
		GL11.glDeleteLists(crossBox, 1);
		crossBox = 0;
		
		GL11.glDeleteLists(ores, 1);
		ores = 0;
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e){
		int playerX = MathHelper.floor(mc.player.posX);
		int playerY = MathHelper.floor(mc.player.posY);
		int playerZ = MathHelper.floor(mc.player.posZ);
		
		ArrayList<AxisAlignedBB> ironOres = new ArrayList<>();
		ArrayList<AxisAlignedBB> ironBlocks = new ArrayList<>();
		ArrayList<AxisAlignedBB> emeraldOres = new ArrayList<>();
		ArrayList<AxisAlignedBB> emeraldBlocks = new ArrayList<>();
		ArrayList<AxisAlignedBB> goldOres = new ArrayList<>();
		ArrayList<AxisAlignedBB> goldBlocks = new ArrayList<>();
		ArrayList<AxisAlignedBB> diamondOres = new ArrayList<>();
		ArrayList<AxisAlignedBB> diamondBlocks = new ArrayList<>();
		ArrayList<AxisAlignedBB> lava = new ArrayList<>();
		ArrayList<AxisAlignedBB> spawner = new ArrayList<>();
		ArrayList<AxisAlignedBB> unknown = new ArrayList<>();
		
		int radius = currentDist;
		
		for (int y = Math.max( 0, playerY - 96 ); y < playerY + 32; y++) // Check the y axis. from 0 or the players y-96 to the players y + 32
		{
			for (int x = playerX - radius; x < playerX + radius; x++) // Iterate the x axis around the player in radius.
			{
				for (int z = playerZ - radius; z < playerZ + radius; z++) // z axis.
				{
					IBlockState state = mc.world.getBlockState(new BlockPos(x, y, z));
					
					if(XRayUtils.isXRayBlock(mc.world.getBlockState(new BlockPos(x, y, z)).getBlock())){
						AxisAlignedBB bb = mc.world.getBlockState(new BlockPos(x, y, z)).getBoundingBox(mc.world, new BlockPos(x, y, z)).offset(new BlockPos(x, y, z));
						switch(mc.world.getBlockState(new BlockPos(x, y, z)).getBlock().getLocalizedName()){
						case "Iron Ore":
							ironOres.add(bb);
							break;
						case "Block of Iron":
							ironBlocks.add(bb);
							break;
						case "Emerald Ore":
							emeraldOres.add(bb);
							break;
						case "Block of Emerald":
							emeraldBlocks.add(bb);
							break;
						case "Gold Ore":
							goldOres.add(bb);
							break;
						case "Block of Gold":
							goldBlocks.add(bb);
							break;
						case "Diamond Ore":
							diamondOres.add(bb);
							break;
						case "Block of Diamond":
							diamondBlocks.add(bb);
							break;
						case "Lava":
							lava.add(bb);
							break;
						case "Monster Spawner":
							spawner.add(bb);
							break;
						default:
							unknown.add(bb);
							break;
						}
					}
				}
			}
		}	
		
		GL11.glNewList(ores, GL11.GL_COMPILE);
		GL11.glColor4f(0.90F, 0.91F, 0.98F, 0.25F);
		renderBoxes(ironOres, outlinedBox);
		renderBoxes(ironBlocks, solidBox);
		GL11.glColor4f(1, 1, 0, 0.25F);
		renderBoxes(goldOres, outlinedBox);
		renderBoxes(goldBlocks, solidBox);
		GL11.glColor4f(0, 1, 0, 0.25F);
		renderBoxes(emeraldOres, outlinedBox);
		renderBoxes(emeraldBlocks, solidBox);
		GL11.glColor4f(0, 1, 1, 0.25F);
		renderBoxes(diamondOres, outlinedBox);
		renderBoxes(diamondBlocks, solidBox);
		GL11.glColor4f(1, 0, 0, 0.25F);
		renderBoxes(lava, solidBox);
		GL11.glColor4f(0, 0, 0, 0.80F);
		renderBoxes(spawner, solidBox);
		GL11.glColor4f(1, 0, 0, 1F);
		renderBoxes(unknown, crossBox);
		GL11.glEndList();
	}
	
	@EventTarget
	public void onRender(EventRender3D event){
		// GL settings
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(2);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glPushMatrix();
		GL11.glTranslated(-mc.getRenderManager().renderPosX,
			-mc.getRenderManager().renderPosY,
			-mc.getRenderManager().renderPosZ);
		
		GL11.glCallList(ores);
		
		GL11.glPopMatrix();
		
		// GL resets
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
	
	private void renderBoxes(ArrayList<AxisAlignedBB> boxes, int displayList)
	{
		for(AxisAlignedBB bb : boxes)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(bb.minX, bb.minY, bb.minZ);
			GL11.glScaled(bb.maxX - bb.minX, bb.maxY - bb.minY,
				bb.maxZ - bb.minZ);
			GL11.glCallList(displayList);
			GL11.glPopMatrix();
		}
	}
}