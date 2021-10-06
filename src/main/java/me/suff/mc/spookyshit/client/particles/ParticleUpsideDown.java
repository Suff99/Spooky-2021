package me.suff.mc.spookyshit.client.particles;

import me.suff.mc.spookyshit.utils.client.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEndRod;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.awt.*;

public class ParticleUpsideDown extends ParticleEndRod {

    public ParticleUpsideDown(World p_i46580_1_, double p_i46580_2_, double p_i46580_4_, double p_i46580_6_, double p_i46580_8_, double p_i46580_10_, double p_i46580_12_) {
        super(p_i46580_1_, p_i46580_2_, p_i46580_4_, p_i46580_6_, p_i46580_8_, p_i46580_10_, p_i46580_12_);
        this.particleScale *= rand.nextFloat() * rand.nextFloat();
        this.particleAlpha = 0.15F;
        this.setColor(Color.ORANGE.darker().darker().darker().darker().darker().getRGB());
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        GlStateManager.pushMatrix();
        RenderUtil.setLightmapTextureCoords(240, 240);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 1);
        float opacity = MathHelper.clamp(MathHelper.sin((Minecraft.getMinecraft().player.ticksExisted + partialTicks) / 10F) * 0.1F + 0.1F, 0.11F, 1F);
        GlStateManager.color(0.0F, 1, 0F, opacity);
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        RenderUtil.restoreLightMap();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
