package toast.lavaMonsters.client;

import toast.lavaMonsters.CommonProxy;
import toast.lavaMonsters.EntityLavaMonster;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    // Registers render files if this is the client side.
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityLavaMonster.class, new RenderLavaMonster());
    }
}