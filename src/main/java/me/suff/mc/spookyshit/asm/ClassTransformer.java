package me.suff.mc.spookyshit.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ClassTransformer implements IClassTransformer {

    public ClassTransformer() {

    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.client.renderer.EntityRenderer")) {
            return patchEntityRendererClass(basicClass);
        }
        return basicClass;
    }


    private byte[] patchEntityRendererClass(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);
        System.out.println("Found EntityRenderer Class: " + classNode.name);

        String methodName = CNPlugin.IN_MCP ? "updateLightMap" : "func_78472_g";

        MethodNode updateLightmap = null;

        for (MethodNode mn : classNode.methods) {
            if (mn.name.equalsIgnoreCase(methodName)) {
                updateLightmap = mn;
            }
        }

        Float m0 = new Float("0.95");
        Float m3 = new Float("0.96");

        Float a0 = new Float("0.05");
        Float a3 = new Float("0.03");

        if (updateLightmap != null) {
            System.out.println("Patched Lightmap");
            for (int i = 0; i < updateLightmap.instructions.size(); i++) {
                AbstractInsnNode an = updateLightmap.instructions.get(i);
                if (an instanceof LdcInsnNode) {
                    LdcInsnNode lin = (LdcInsnNode) an;

                    if (lin.cst.equals(m0) || lin.cst.equals(m3)) {
                        updateLightmap.instructions.insert(lin, new MethodInsnNode(Opcodes.INVOKESTATIC, "me/suff/mc/spookyshit/asm/ASMHooks", "up", "(F)F", false));
                    } else if (lin.cst.equals(a0) || lin.cst.equals(a3)) {
                        updateLightmap.instructions.insert(lin, new MethodInsnNode(Opcodes.INVOKESTATIC, "me/suff/mc/spookyshit/asm/ASMHooks", "down", "(F)F", false));
                    }
                } else if (an instanceof FieldInsnNode) {
                    FieldInsnNode fin = (FieldInsnNode) an;

                    if (fin.name.equals(CNPlugin.IN_MCP ? "gammaSetting" : "field_74333_Y")) {
                        updateLightmap.instructions.insert(fin, new MethodInsnNode(Opcodes.INVOKESTATIC, "me/suff/mc/spookyshit/asm/ASMHooks", "overrideGamma", "(F)F", false));
                    }
                } else if (an instanceof MethodInsnNode) {
                    MethodInsnNode min = (MethodInsnNode) an;
                    if (min.name.equals(CNPlugin.IN_MCP ? "updateDynamicTexture" : "func_110564_a")) {
                        System.out.println("Patched Lightmap Manipulation");
                        InsnList toInsert = new InsnList();
                        toInsert.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        toInsert.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        String lightmapColors = CNPlugin.IN_MCP ? "lightmapColors" : "field_78504_Q";
                        toInsert.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/EntityRenderer", lightmapColors, "[I"));
                        toInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/suff/mc/spookyshit/asm/ASMHooks", "modifyLightmap", "([I)[I", false));
                        toInsert.add(new FieldInsnNode(Opcodes.PUTFIELD, "net/minecraft/client/renderer/EntityRenderer", lightmapColors, "[I"));
                        updateLightmap.instructions.insertBefore(min, toInsert);
                        i += 5;
                    }
                }
            }

        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);

        return writer.toByteArray();
    }


}