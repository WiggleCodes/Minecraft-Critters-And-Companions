package io.github.bonsaistudi0s.crittersandcompanions.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.architectury.networking.NetworkManager;
import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public record ClientboundSilkLeashStatePacket(List<LeashData> leashDataList) implements CustomPacketPayload {

    public record LeashData(int leashOwner, IntList leashingEntities, IntList leashedByEntities) {
    }

    public static final TypeAndCodec<FriendlyByteBuf, ClientboundSilkLeashStatePacket> TYPE = new TypeAndCodec<>(
            new Type<>(CrittersAndCompanions.createId("silk_leash_state")),
            StreamCodec.of(ClientboundSilkLeashStatePacket::write, ClientboundSilkLeashStatePacket::read)
    );

    public ClientboundSilkLeashStatePacket(LeashData... leashData) {
        this(Arrays.asList(leashData));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE.type();
    }

    private static ClientboundSilkLeashStatePacket read(FriendlyByteBuf byteBuf) {
        int size = byteBuf.readVarInt();
        var leashDataList = new ArrayList<LeashData>(size);

        for (int i = 0; i < size; i++) {
            int leashOwner = byteBuf.readVarInt();
            IntList leashingEntities = readIntIdList(byteBuf);
            IntList leashedByEntities = readIntIdList(byteBuf);
            leashDataList.add(new LeashData(leashOwner, leashingEntities, leashedByEntities));
        }

        return new ClientboundSilkLeashStatePacket(leashDataList);
    }

    private static void write(FriendlyByteBuf byteBuf, ClientboundSilkLeashStatePacket packet) {
        byteBuf.writeVarInt(packet.leashDataList.size());
        for (LeashData data : packet.leashDataList) {
            byteBuf.writeVarInt(data.leashOwner);
            writeIntIdList(byteBuf, data.leashingEntities);
            writeIntIdList(byteBuf, data.leashedByEntities);
        }
    }

    private static IntList readIntIdList(FriendlyByteBuf byteBuf) {
        int size = byteBuf.readVarInt();
        IntList list = new IntArrayList(size);
        for (int i = 0; i < size; i++) {
            list.add(byteBuf.readVarInt());
        }
        return list;
    }

    private static void writeIntIdList(FriendlyByteBuf byteBuf, IntList list) {
        byteBuf.writeVarInt(list.size());
        list.forEach(byteBuf::writeVarInt);
    }

}
