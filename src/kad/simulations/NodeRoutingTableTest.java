package kad.simulations;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kad.JKademliaNode;
import kad.node.KademliaId;
import kad.routing.KademliaRoutingTable;
import net.i2p.data.router.RouterInfo;
import observer.LoadRouterInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Set;

public class NodeRoutingTableTest {
    public static void main(String[] args)
    {
        try
        {
            LoadRouterInfo loadRouterInfo=new LoadRouterInfo();
            Set<RouterInfo> load = loadRouterInfo.load();
            Iterator<RouterInfo> iterator = load.iterator();
            JKademliaNode[] kad =new JKademliaNode[load.size()];
            int i=0;
            while(iterator.hasNext()){
                RouterInfo routerInfo = iterator.next();
                kad[i]=new JKademliaNode("sophie",new KademliaId(routerInfo.getHash().toBase64().substring(0,20)),i);
                System.out.println( "Created Node Kad "+i+" : "+kad[i].getNode().getNodeId());
                i++;
            }
            for(int j=0;j<load.size();j++){
                for(int k=0;k<load.size();k++){
                    if(j!=k){
                        KademliaRoutingTable routingTable = kad[j].getRoutingTable();
                        routingTable.insert(kad[k].getNode());
                    }else {
                        continue;
                    }
                }
            }

            JsonArray routerTableArray = new JsonArray();



            for(int j = 0;j<kad.length;j++) {
                JsonObject routerTable= new JsonObject();
                routerTable.addProperty(String.valueOf(j),kad[j].getRoutingTable().toString());
                routerTableArray.add(routerTable);
                System.out.println(kad[j].getRoutingTable());
                //System.out.println(jNode[1].getRoutingTable());
            }

            File f = new java.io.File("./routingtable.txt");
            FileOutputStream fOutputStream = new FileOutputStream(f);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOutputStream);
            for(int j = 0;j<kad.length;j++) {
                JsonObject routerTable= new JsonObject();
                routerTable.addProperty(String.valueOf(j),kad[j].getRoutingTable().toString());
                routerTableArray.add(routerTable);
                outputStreamWriter.write(kad[j].getRoutingTable().toString()+'\n');
                outputStreamWriter.flush();
            }
            outputStreamWriter.close();
            fOutputStream.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
