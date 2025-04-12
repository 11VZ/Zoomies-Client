package me.vz11.zoomies.event.events;

import me.vz11.zoomies.event.Event;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends Event {
    public final Packet<?> packet;
    
    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return packet;
    }
    
    public static class Receive extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }
}