package me.suff.mc.spookyshit.utils.client;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class RenderUtil {

    private static float lastBrightnessX = OpenGlHelper.lastBrightnessX;
    private static float lastBrightnessY = OpenGlHelper.lastBrightnessY;

    public static void setItemRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void setLightmapTextureCoords(float x, float y) {
        lastBrightnessX = OpenGlHelper.lastBrightnessX;
        lastBrightnessY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, x, y);
    }

    public static void restoreLightMap() {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    }
}
