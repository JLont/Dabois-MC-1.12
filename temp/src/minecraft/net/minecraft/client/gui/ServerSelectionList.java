package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerInfo;

public class ServerSelectionList extends GuiListExtended {
   private final GuiMultiplayer field_148200_k;
   private final List<ServerListEntryNormal> field_148198_l = Lists.<ServerListEntryNormal>newArrayList();
   private final List<ServerListEntryLanDetected> field_148199_m = Lists.<ServerListEntryLanDetected>newArrayList();
   private final GuiListExtended.IGuiListEntry field_148196_n = new ServerListEntryLanScan();
   private int field_148197_o = -1;

   public ServerSelectionList(GuiMultiplayer p_i45049_1_, Minecraft p_i45049_2_, int p_i45049_3_, int p_i45049_4_, int p_i45049_5_, int p_i45049_6_, int p_i45049_7_) {
      super(p_i45049_2_, p_i45049_3_, p_i45049_4_, p_i45049_5_, p_i45049_6_, p_i45049_7_);
      this.field_148200_k = p_i45049_1_;
   }

   public GuiListExtended.IGuiListEntry func_148180_b(int p_148180_1_) {
      if (p_148180_1_ < this.field_148198_l.size()) {
         return this.field_148198_l.get(p_148180_1_);
      } else {
         p_148180_1_ = p_148180_1_ - this.field_148198_l.size();
         if (p_148180_1_ == 0) {
            return this.field_148196_n;
         } else {
            --p_148180_1_;
            return this.field_148199_m.get(p_148180_1_);
         }
      }
   }

   protected int func_148127_b() {
      return this.field_148198_l.size() + 1 + this.field_148199_m.size();
   }

   public void func_148192_c(int p_148192_1_) {
      this.field_148197_o = p_148192_1_;
   }

   protected boolean func_148131_a(int p_148131_1_) {
      return p_148131_1_ == this.field_148197_o;
   }

   public int func_148193_k() {
      return this.field_148197_o;
   }

   public void func_148195_a(ServerList p_148195_1_) {
      this.field_148198_l.clear();

      for(int i = 0; i < p_148195_1_.func_78856_c(); ++i) {
         this.field_148198_l.add(new ServerListEntryNormal(this.field_148200_k, p_148195_1_.func_78850_a(i)));
      }

   }

   public void func_148194_a(List<LanServerInfo> p_148194_1_) {
      this.field_148199_m.clear();

      for(LanServerInfo lanserverinfo : p_148194_1_) {
         this.field_148199_m.add(new ServerListEntryLanDetected(this.field_148200_k, lanserverinfo));
      }

   }

   protected int func_148137_d() {
      return super.func_148137_d() + 30;
   }

   public int func_148139_c() {
      return super.func_148139_c() + 85;
   }
}
