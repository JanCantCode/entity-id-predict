package tk.jandev.entityprediction.client;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class EntitypredictionClient implements ClientModInitializer {
    final MinecraftClient mc = MinecraftClient.getInstance();
    public static EntitypredictionClient INSTANCE;
    public int newestId;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("superspeed").then(ClientCommandManager.argument("amount", IntegerArgumentType.integer()).executes(context -> {
            if ((mc.crosshairTarget instanceof BlockHitResult blockHitResult))
                for (int i = 0; i < IntegerArgumentType.getInteger(context, "amount"); i++) {
                    mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHitResult);
                    EndCrystalEntity e = new EndCrystalEntity(EntityType.END_CRYSTAL, mc.world);
                    e.setId(newestId+i);
                    mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(e, false));
                }
            return 1;
        }))));
    }
}
