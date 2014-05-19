package controllers;

import com.avaje.ebean.QueryIterator;
import com.sun.management.HotSpotDiagnosticMXBean;
import models.DumbModel;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;

import javax.management.MBeanServer;
import java.io.File;
import java.lang.management.ManagementFactory;

public class Application extends Controller {

    public static Result index() {
        return ok("Ok");
    }


    public static Result go() {

        for (int i = 0; i < 1000; i++) {
            DumbModel dumbModel = new DumbModel();
            dumbModel.text = "Hello";
            dumbModel.save();
        }
        System.out.println("ALL SAVED");
        Model.Finder<Long, DumbModel> find =
                new Model.Finder(Long.class, DumbModel.class);

        find.setBufferFetchSizeHint(1);
        System.out.println("GETTING ITERATE");
        QueryIterator<DumbModel> iterate = find
                .findIterate();

        System.out.println("CREATING HEAP DUMP");
        deleteFile("heap-dump.snapshot");

        dumpHeap("heap-dump.snapshot", true);

        while (iterate.hasNext()) {
            iterate.next();
        }

        deleteFile("heap-dump-after.snapshot");
        dumpHeap("heap-dump-after.snapshot", true);

        return ok("Done");
    }

    private static void deleteFile(String s) {
        File file = new File(s);
        if (file.exists())
            file.delete();
    }


    // All that heap dumping code from : https://blogs.oracle.com/sundararajan/entry/programmatically_dumping_heap_from_java
    private static final String HOTSPOT_BEAN_NAME =
            "com.sun.management:type=HotSpotDiagnostic";

    // field to store the hotspot diagnostic MBean
    private static volatile HotSpotDiagnosticMXBean hotspotMBean;

    static void dumpHeap(String fileName, boolean live) {
        // initialize hotspot diagnostic MBean
        initHotspotMBean();
        try {
            hotspotMBean.dumpHeap(fileName, live);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    // initialize the hotspot diagnostic MBean field
    private static void initHotspotMBean() {
        if (hotspotMBean == null) {
            synchronized (Application.class) {
                if (hotspotMBean == null) {
                    hotspotMBean = getHotspotMBean();
                }
            }
        }
    }

    // get the hotspot diagnostic MBean from the
    // platform MBean server
    private static HotSpotDiagnosticMXBean getHotspotMBean() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            HotSpotDiagnosticMXBean bean =
                    ManagementFactory.newPlatformMXBeanProxy(server,
                            HOTSPOT_BEAN_NAME, HotSpotDiagnosticMXBean.class);
            return bean;
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

}
