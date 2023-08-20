package tk.jandev.entityprediction.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.jandev.entityprediction.client.EntitypredictionClient;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow @Final private EntityType<?> type;

    @Shadow public abstract void remove(Entity.RemovalReason reason);

    @Inject(method="onSpawnPacket", at=@At("HEAD"))
    public void onSpawnPacket(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        EntitypredictionClient.INSTANCE.newestId = packet.getId();
    }
}
