package me.freakjoost.Dabois.module.render;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Vector;

import me.freakjoost.Dabois.event.EventTarget;
import me.freakjoost.Dabois.event.events.EventRender3D;
import me.freakjoost.Dabois.event.events.EventUpdate;
import me.freakjoost.Dabois.module.Category;
import me.freakjoost.Dabois.module.Module;
import me.freakjoost.Dabois.utils.RenderUtils;
import me.freakjoost.Dabois.utils.XRayUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ChestEsp extends Module {

	private int solidBox;
	private int outlinedBox;
	private int crossBox;
	
	private int normalChests;
	private final ArrayList<Entity> minecarts = new ArrayList<>();
	
	private int chestCounter;
	
	private TileEntityChest openChest;
	private final LinkedHashSet<BlockPos> emptyChests = new LinkedHashSet<>();
	private final LinkedHashSet<BlockPos> nonEmptyChests =
		new LinkedHashSet<>();
	
	public ChestEsp() {
		super("Chest Esp", Keyboard.KEY_NUMPAD1, Category.RENDER);
	}

	public void onEnable() {
		super.onEnable();
		
		emptyChests.clear();
		nonEmptyChests.clear();
		
		solidBox = GL11.glGenLists(1);
		GL11.glNewList(solidBox, GL11.GL_COMPILE);
		RenderUtils.drawSolidBox();
		GL11.glEndList();
		
		outlinedBox = GL11.glGenLists(1);
		GL11.glNewList(outlinedBox, GL11.GL_COMPILE);
		RenderUtils.drawOutlinedBox();
		GL11.glEndList();
		
		crossBox = GL11.glGenLists(1);
		GL11.glNewList(crossBox, GL11.GL_COMPILE);
		RenderUtils.drawCrossBox();
		GL11.glEndList();
		
		normalChests = GL11.glGenLists(1);
	}

	public void onDisable() {
		super.onDisable();
		
		GL11.glDeleteLists(solidBox, 1);
		solidBox = 0;
		
		GL11.glDeleteLists(outlinedBox, 1);
		outlinedBox = 0;
		
		GL11.glDeleteLists(crossBox, 1);
		crossBox = 0;
		
		GL11.glDeleteLists(normalChests, 1);
		normalChests = 0;
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e){
		ArrayList<AxisAlignedBB> basicNew = new ArrayList<>();
		ArrayList<AxisAlignedBB> basicEmpty = new ArrayList<>();
		ArrayList<AxisAlignedBB> basicNotEmpty = new ArrayList<>();
		ArrayList<AxisAlignedBB> trappedNew = new ArrayList<>();
		ArrayList<AxisAlignedBB> trappedEmpty = new ArrayList<>();
		ArrayList<AxisAlignedBB> trappedNotEmpty = new ArrayList<>();
		ArrayList<AxisAlignedBB> enderChests = new ArrayList<>();
		
		for(TileEntity tileEntity : mc.world.loadedTileEntityList)
			if(tileEntity instanceof TileEntityChest)
			{
				// ignore other block in double chest
				TileEntityChest chest = (TileEntityChest)tileEntity;
				if(chest.adjacentChestXPos != null
					|| chest.adjacentChestZPos != null)
					continue;
				
				// get hitbox
				mc.world.getBlockState(chest.getPos());
				AxisAlignedBB bb = mc.world.getBlockState(chest.getPos()).getBoundingBox(mc.world, chest.getPos()).offset(chest.getPos());
				
				if(bb == null)
					continue;
				
				// larger box for double chest
				if(chest.adjacentChestXNeg != null)
				{
					BlockPos pos2 = chest.adjacentChestXNeg.getPos();
					AxisAlignedBB bb2 = mc.world.getBlockState(pos2).getBoundingBox(mc.world, pos2).offset(pos2);
					bb = bb.union(bb2);
					
				}else if(chest.adjacentChestZNeg != null)
				{
					BlockPos pos2 = chest.adjacentChestZNeg.getPos();
					AxisAlignedBB bb2 = mc.world.getBlockState(pos2).getBoundingBox(mc.world, pos2).offset(pos2);
					bb = bb.union(bb2);
				}
				
				// add to appropriate list
				boolean trapped = (chest.getChestType() == BlockChest.Type.TRAP);
				if(emptyChests.contains(chest.getPos()))
					if(trapped)
						trappedEmpty.add(bb);
					else
						basicEmpty.add(bb);
				else if(nonEmptyChests.contains(chest.getPos()))
					if(trapped)
						trappedNotEmpty.add(bb);
					else
						basicNotEmpty.add(bb);
				else if(trapped)
					trappedNew.add(bb);
				else
					basicNew.add(bb);
				
			}else if(tileEntity instanceof TileEntityEnderChest)
			{
				AxisAlignedBB bb = mc.world.getBlockState(tileEntity.getPos()).getBoundingBox(mc.world, tileEntity.getPos()).offset(tileEntity.getPos());;
				enderChests.add(bb);
			}
		
		GL11.glNewList(normalChests, GL11.GL_COMPILE);
		GL11.glColor4f(0, 1, 0, 0.25F);
		renderBoxes(basicNew, solidBox);
		GL11.glColor4f(0, 1, 0, 0.5F);
		renderBoxes(basicNew, outlinedBox);
		renderBoxes(basicEmpty, outlinedBox);
		renderBoxes(basicNotEmpty, outlinedBox);
		renderBoxes(basicNotEmpty, crossBox);
		GL11.glColor4f(1, 0.5F, 0, 0.25F);
		renderBoxes(trappedNew, solidBox);
		GL11.glColor4f(1, 0.5F, 0, 0.5F);
		renderBoxes(trappedNew, outlinedBox);
		renderBoxes(trappedEmpty, outlinedBox);
		renderBoxes(trappedNotEmpty, outlinedBox);
		renderBoxes(trappedNotEmpty, crossBox);
		GL11.glColor4f(0, 1, 1, 0.25F);
		renderBoxes(enderChests, solidBox);
		GL11.glColor4f(0, 1, 1, 0.5F);
		renderBoxes(enderChests, outlinedBox);
		GL11.glEndList();
		
		// minecarts
		minecarts.clear();
		for(net.minecraft.entity.Entity entity : mc.world.loadedEntityList)
			if(entity instanceof EntityMinecartChest)
				minecarts.add(entity);
			
		// chest counter
		chestCounter = basicNew.size() + basicEmpty.size()
			+ basicNotEmpty.size() + trappedNew.size() + trappedEmpty.size()
			+ trappedNotEmpty.size() + enderChests.size() + minecarts.size();
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
				
				// minecart interpolation
				ArrayList<AxisAlignedBB> minecartBoxes =
					new ArrayList<>(minecarts.size());
				minecarts.forEach(e -> {
					double offsetX = -(e.posX - e.lastTickPosX)
						+ (e.posX - e.lastTickPosX) * event.particlTicks;
					double offsetY = -(e.posY - e.lastTickPosY)
						+ (e.posY - e.lastTickPosY) * event.particlTicks;
					double offsetZ = -(e.posZ - e.lastTickPosZ)
						+ (e.posZ - e.lastTickPosZ) * event.particlTicks;
					minecartBoxes.add(e.boundingBox.offset(offsetX, offsetY, offsetZ));
				});
				
				GL11.glCallList(normalChests);
				
				GL11.glColor4f(0, 1, 0, 0.25F);
				renderBoxes(minecartBoxes, solidBox);
				GL11.glColor4f(0, 1, 0, 0.5F);
				renderBoxes(minecartBoxes, outlinedBox);
				
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
			
			public void openChest(BlockPos pos)
			{
				TileEntity tileEntity = mc.world.getTileEntity(pos);
				if(tileEntity instanceof TileEntityChest)
				{
					openChest = (TileEntityChest)tileEntity;
					if(openChest.adjacentChestXPos != null)
						openChest = openChest.adjacentChestXPos;
					if(openChest.adjacentChestZPos != null)
						openChest = openChest.adjacentChestZPos;
				}
			}
			
			public void closeChest(Container chest)
			{
				if(openChest == null)
					return;
				
				boolean empty = true;
				for(int i = 0; i < chest.inventorySlots.size() - 36; i++)
					if(!chest.inventorySlots.get(i).getStack().equals(null))
					{
						empty = false;
						break;
					}
				
				BlockPos pos = openChest.getPos();
				if(empty)
				{
					if(!emptyChests.contains(pos))
						emptyChests.add(pos);
					
					nonEmptyChests.remove(pos);
				}else
				{
					if(!nonEmptyChests.contains(pos))
						nonEmptyChests.add(pos);
					
					emptyChests.remove(pos);
				}
				
				openChest = null;
			}
}
