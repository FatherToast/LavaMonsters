package toast.lavaMonsters.client;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import toast.lavaMonsters.EntityLavaMonster;
import toast.lavaMonsters._LavaMonsters;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLavaMonster extends RenderLiving {
    /// The texture for this renderer.
    public static final ResourceLocation[] LAVA_MONSTER_TEXTURES;
    static {
        if (EntityLavaMonster.ANIMATE_TEXTURE) {
            LAVA_MONSTER_TEXTURES = new ResourceLocation[20];
        }
        else {
            LAVA_MONSTER_TEXTURES = new ResourceLocation[1];
        }
        String path = _LavaMonsters.MODID + ":textures/models/lavaMonster_";
        for (int i = 0; i < RenderLavaMonster.LAVA_MONSTER_TEXTURES.length; i++) {
            RenderLavaMonster.LAVA_MONSTER_TEXTURES[i] = new ResourceLocation(path + Integer.toString(i) + ".png");
        }
    }

    public RenderLavaMonster() {
        super(new ModelLavaMonster(), 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase e, float f) {
        GL11.glScalef(1.25F, 1.25F, 1.25F);
    }

    /// Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (EntityLavaMonster.ANIMATE_TEXTURE)
            return RenderLavaMonster.LAVA_MONSTER_TEXTURES[ ((EntityLavaMonster) entity).getTextureIndex()];
        return RenderLavaMonster.LAVA_MONSTER_TEXTURES[0];
    }
}