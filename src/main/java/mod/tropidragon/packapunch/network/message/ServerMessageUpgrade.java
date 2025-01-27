package mod.tropidragon.packapunch.network.message;

import java.util.function.Supplier;

import mod.tropidragon.packapunch.client.gui.ArsenalMachineScreen;
import mod.tropidragon.packapunch.client.gui.RetrofitMachineScreen;
import mod.tropidragon.packapunch.common.Pap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

public class ServerMessageUpgrade {
    private final int menuId;
    private final int weaponLevel;

    public ServerMessageUpgrade(int menuId, int weaponLevel) {
        this.menuId = menuId;
        this.weaponLevel = weaponLevel;

    }

    public static void encode(ServerMessageUpgrade message, FriendlyByteBuf buf) {
        buf.writeVarInt(message.menuId);
        buf.writeVarInt(message.weaponLevel);
    }

    public static ServerMessageUpgrade decode(FriendlyByteBuf buf) {
        return new ServerMessageUpgrade(buf.readVarInt(), buf.readVarInt());
    }

    public static void handle(ServerMessageUpgrade message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> updateScreen(message.menuId, message.weaponLevel));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void updateScreen(int containerId, int weaponLevel) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.containerMenu.containerId == containerId) {
            if (Minecraft.getInstance().screen instanceof RetrofitMachineScreen screen) {
                player.displayClientMessage(Component.translatable("message.packapunch.retrofit_machine.upgraded",
                        Pap.getPaPTierSymbol(weaponLevel)), false);
            }
            if (Minecraft.getInstance().screen instanceof ArsenalMachineScreen screen) {
                player.displayClientMessage(Component.translatable("message.packapunch.arsenal_machine.upgraded",
                        Pap.getRarityTierSymbol(weaponLevel)), false);
            }
            // screen.playUpgradeAnimation()
        }

    }
}
