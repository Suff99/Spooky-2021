package me.suff.mc.spookyshit.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class CNSource extends DamageSource {

    private final String message;

    public CNSource(String damageTypeIn) {
        super(damageTypeIn);
        this.message = "cn." + damageTypeIn + ".source";
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
        return new TextComponentTranslation(message, entityLivingBaseIn.getName());
    }
}
