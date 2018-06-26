package observer;

import net.i2p.data.Hash;
import net.i2p.data.router.RouterIdentity;
import observer.data.ObserverProperties;
import observer.netdb.NetDBImporter;
import net.i2p.data.router.RouterInfo;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class LoadRouterInfo {
    private static ObserverProperties observerProperties = new ObserverProperties();
    public Set<RouterInfo> load(){
        // import the current netDB files
        NetDBImporter importer = new NetDBImporter(observerProperties.getNetDBFolderPath());
        Set<RouterInfo> netDbEntries = importer.readNetDB().getNetDbEntries();
//        int i=1;
//        for (RouterInfo r:netDbEntries){
//            System.out.println( "*********************" );
//            System.out.println("router "+i+"'id(Hash) :"+ r.getHash() );
//            System.out.println( "date :  "+r.getDate() );
//            System.out.println( "host :"+r.getOption("HOST") );
//            System.out.println("version :  "+ r.getVersion() );
//            i++;
//            System.out.println( "*********************" );
//        }
        return  netDbEntries;
    }

    public static void main(String[] args) {
        LoadRouterInfo loadRouterInfo=new LoadRouterInfo();
        Set<RouterInfo> load = loadRouterInfo.load();
        Iterator<RouterInfo> iterator = load.iterator();
        if(iterator.hasNext()){
            RouterInfo next = iterator.next();
            Hash routingKey = next.getHash();
//            Hash hash = next.getIdentity().getHash();

            System.out.println( next );
            long date=next.getDate();
            System.out.println( "routerinfo date："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date)));
            System.out.println( date );
            System.out.println( new Date().getTime() );
//            System.out.println( hash.getData().length );
        }
    }
}
