package me.suff.mc.spookyshit.client.handler;

import me.suff.mc.spookyshit.SpookyShit;
import me.suff.mc.spookyshit.client.particles.ParticleUpsideDown;
import me.suff.mc.spookyshit.utils.CNUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = SpookyShit.MODID)
public class ClientHandler {

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        if (Minecraft.getMinecraft().world != null && CNUtil.shouldChange(Minecraft.getMinecraft().world)) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setCanceled(true);
            event.setDensity(0.12F);
        }
    }

    @SubscribeEvent
    public static void onSetupFogColor(EntityViewRenderEvent.RenderFogEvent.FogColors event) {
        if (Minecraft.getMinecraft().world != null && CNUtil.shouldChange(Minecraft.getMinecraft().world)) {
            event.setRed(0.14F);
            event.setGreen(0.13F);
            event.setBlue(0.12F);
            /*event.setRed(0.14F);
            event.setGreen(0.15F);
            event.setBlue(0.22F);*/
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player == null) return;
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (CNUtil.shouldChange(player.world)) {
            createWorldAmbience(player);
        }
    }

    @SubscribeEvent
    public static void onColorFoliage(BiomeEvent.GetFoliageColor eve) {
        if (Minecraft.getMinecraft().player == null) return;
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (CNUtil.shouldChange(player.world)) {
            eve.setNewColor(Color.ORANGE.darker().darker().darker().darker().darker().getRGB());
        }
    }

    @SubscribeEvent
    public static void onColorGrass(BiomeEvent.GetGrassColor eve) {
        if (Minecraft.getMinecraft().player == null) return;
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (CNUtil.shouldChange(player.world)) {
            eve.setNewColor(Color.ORANGE.darker().darker().darker().darker().darker().getRGB());
        }
    }

    @SubscribeEvent
    public static void onColorWater(BiomeEvent.GetWaterColor eve) {
        if (Minecraft.getMinecraft().player == null) return;
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (CNUtil.shouldChange(player.world)) {
            eve.setNewColor(Color.GREEN.darker().darker().darker().darker().getRGB());
        }
    }

    private static void createWorldAmbience(EntityPlayer player) {
        Random random = player.world.rand;
        double originX = player.posX;
        double originY = player.posY;
        double originZ = player.posZ;
        for (int i = 0; i < 55; i++) {
            double particleX = originX + (random.nextInt(24) - random.nextInt(24));
            double particleY = originY + (random.nextInt(24) - random.nextInt(24));
            double particleZ = originZ + (random.nextInt(24) - random.nextInt(24));
            double velocityX = (random.nextDouble() - 0.5) * 0.02;
            double velocityY = (random.nextDouble() - 0.5) * 0.02;
            double velocityZ = (random.nextDouble() - 0.5) * 0.02;
            Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleUpsideDown(player.world, particleX, particleY, particleZ, velocityX, velocityY, velocityZ));

            //player.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, particleX, particleY, particleZ, velocityX, velocityY, velocityZ, Block.getStateId(Blocks.WOODEN_BUTTON.getDefaultState()));


            if (player.world.rand.nextInt(30) < 10 && player.world.rand.nextBoolean()) {
                BlockPos pos = new BlockPos(particleX + random.nextInt(500), particleY + random.nextInt(500), particleZ + random.nextInt(500));

                if (player.world.getTotalWorldTime() % 250 == 0) {
                    player.world.playSound(null, player.getPosition(), random.nextBoolean() ? SoundEvents.ENTITY_LIGHTNING_THUNDER : SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.AMBIENT, 1, 1);
                }
                player.world.addWeatherEffect(new EntityLightningBolt(player.world, pos.getX(), pos.getY(), pos.getZ(), true));
            }
        }
    }

    private static boolean isMusicSound() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return Arrays.stream(stackTrace).anyMatch(e -> e.getClassName().equals(MusicTicker.class.getName()));
    }
}

