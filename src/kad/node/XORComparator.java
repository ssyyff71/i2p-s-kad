package kad.node;

import net.i2p.data.SimpleDataStructure;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Help sort Hashes in relation to a base key using the XOR metric
 *
 * @since 0.9.2 in i2psnark, moved to core in 0.9.10
 */
public class XORComparator implements Comparator<Node>, Serializable {
    private final byte[] _base;

    /**
     * @param target key to compare distances with
     */
    public XORComparator(KademliaId target) {
        _base = target.getBytes();
    }

    public int compare(Node lhs, Node rhs) {
        // same as the following but byte-by-byte for efficiency
        //byte lhsDelta[] = DataHelper.xor(lhs.getData(), _base);
        //byte rhsDelta[] = DataHelper.xor(rhs.getData(), _base);
        //return DataHelper.compareTo(lhsDelta, rhsDelta);
        long lhs_date = lhs.getInfo().getDate();
        long rhs_date = rhs.getInfo().getDate();
        byte lhsb[] = lhs.getNodeId().getBytes();
        byte rhsb[] = rhs.getNodeId().getBytes();
        if(lhs_date>rhs_date){
            lhsb[0]=1;
            rhsb[0]=0;
        }else if(lhs_date<rhs_date){
            lhsb[0]=0;
            rhsb[1]=1;
        }
        for (int i = 1; i < _base.length; i++) {
            int ld = (lhsb[i] ^ _base[i]) & 0xff;
            int rd = (rhsb[i] ^ _base[i]) & 0xff;
            if (ld < rd)
                return -1;
            if (ld > rd)
                return 1;
        }
        return 0;
    }
}
