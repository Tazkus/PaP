package mod.tropidragon.packapunch.network.message;

import java.util.function.Supplier;

import com.tacz.guns.inventory.GunSmithTableMenu;

import mod.tropidragon.packapunch.inventory.RetrofitMachineMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class ClientMessageUpgrade {
    private final int menuId;

    public ClientMessageUpgrade(int menuId) {
        this.menuId = menuId;
    }

    public static void encode(ClientMessageUpgrade message, FriendlyByteBuf buf) {
        buf.writeVarInt(message.menuId);
    }

    public static ClientMessageUpgrade decode(FriendlyByteBuf buf) {
        return new ClientMessageUpgrade(buf.readVarInt());
    }

    public static void handle(ClientMessageUpgrade message,
            Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayer entity = context.getSender();
                if (entity == null) {
                    return;
                }
                if (entity.containerMenu.containerId == message.menuId
                        && entity.containerMenu instanceof RetrofitMachineMenu menu) {
                    menu.doUpgrade(entity);
                }
            });
        }
        context.setPacketHandled(true);
    }
}
