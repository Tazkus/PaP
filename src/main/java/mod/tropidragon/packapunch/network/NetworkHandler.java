package mod.tropidragon.packapunch.network;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.network.message.ClientMessageUpgrade;
import mod.tropidragon.packapunch.network.message.ServerMessageUpgrade;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String VERSION = "1.0.0";
    private static final AtomicInteger ID_COUNT = new AtomicInteger(1);

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Divinium.MODID, "network"),
            () -> VERSION,
            it -> it.equals(VERSION),
            it -> it.equals(VERSION));

    // Register Message
    public static void init() {
        CHANNEL.registerMessage(
                ID_COUNT.getAndIncrement(),
                ClientMessageUpgrade.class,
                ClientMessageUpgrade::encode,
                ClientMessageUpgrade::decode,
                ClientMessageUpgrade::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(
                ID_COUNT.getAndIncrement(),
                ServerMessageUpgrade.class,
                ServerMessageUpgrade::encode,
                ServerMessageUpgrade::decode,
                ServerMessageUpgrade::handle);
    }

    public static void sendToClientPlayer(Object message, Player player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }

    /**
     * 发送给所有监听此实体的玩家
     */
    public static void sendToTrackingEntityAndSelf(Entity centerEntity, Object message) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> centerEntity), message);
    }

    public static void sendToAllPlayers(Object message) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }

    public static void sendToTrackingEntity(Object message, final Entity centerEntity) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> centerEntity), message);
    }

    public static void sendToDimension(Object message, final Entity centerEntity) {
        ResourceKey<Level> dimension = centerEntity.level().dimension();
        CHANNEL.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
    }
}
