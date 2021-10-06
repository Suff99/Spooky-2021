package me.suff.mc.spookyshit.client.render.sky;

import me.suff.mc.spookyshit.SpookyShit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.util.glu.Sphere;

public class RenderUpsideDownSky extends IRenderHandler {
    private static final ResourceLocation SKY_TEX = new ResourceLocation(SpookyShit.MODID, "textures/sky/sky.png");
    private static final RenderUpsideDownSky INSTANCE = new RenderUpsideDownSky();
    public static Sphere SKY = new Sphere();

    public static RenderUpsideDownSky getInstance() {
        return INSTANCE;
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableFog();

        // ========================== Sky box start =========================
        GlStateManager.pushMatrix();
        GlStateManager.scale(3, 3, 3);
        GlStateManager.disableCull();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(SpookyShit.MODID, "textures/sky.png"));
        SKY.setTextureFlag(true);
        GlStateManager.rotate(-90, 1, 0, 0);
        GlStateManager.disableBlend();
        GlStateManager.rotate(world.getTotalWorldTime() / 40F, 0, 1, 0);
        SKY.draw(64, 32, 32);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.popMatrix();
        // ========================== Sky box stop =========================

        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableFog();
        GlStateManager.popMatrix();
        //===================Sun stop================
    }
}
