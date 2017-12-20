import jpcap.PacketReceiver ;
import jpcap.packet.Packet;

public class PkPirate_PacketContents implements PacketReceiver
{

    @Override
    public void receivePacket(Packet packet) {
       PkPirate_Interface.TA_OUTPUT.append(packet.toString()
               +"\n------------------------------------------"
       +"------------------------------------------------\n\n");
    }
    
}