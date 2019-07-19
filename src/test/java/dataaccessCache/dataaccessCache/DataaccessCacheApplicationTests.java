package dataaccessCache.dataaccessCache;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dataaccessCache.service.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataaccessCacheApplicationTests {

	public static void main(String[] args) throws InterruptedException {
		 
		DataaccessCacheApplicationTests dataAccessCache = new DataaccessCacheApplicationTests();
 
        System.out.println("\n\n==========Test1: dataaccessTestAddRemoveObjects ==========");
        dataAccessCache.dataaccessTestAddRemoveObjects();
        System.out.println("\n\n==========Test2: dataaccessTestExpiredCacheObjects ==========");
        dataAccessCache.dataaccessTestExpiredCacheObjects();
        System.out.println("\n\n==========Test3: dataaccessTestObjectsCleanupTime ==========");
        dataAccessCache.dataaccessTestObjectsCleanupTime();
    }
 
    private void dataaccessTestAddRemoveObjects() {
 
       
    	ServiceImpl<String, String> cache = new ServiceImpl<String, String>(200, 500, 6);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
        cache.put("Microsoft", "Microsoft");
        cache.put("IBM", "IBM");
        cache.put("Facebook", "Facebook");
 
        
        cache.remove("IBM");
       
 
        cache.put("Twitter", "Twitter");
        cache.put("SAP", "SAP");
      
 
    }
 
    private void dataaccessTestExpiredCacheObjects() throws InterruptedException {
 
        // Test with dataaccessTimeToLive = 1 second
        // dataaccessTimerInterval = 1 second
        // maxItems = 10
    	ServiceImpl<String, String> cache = new ServiceImpl<String, String>(1, 1, 10);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        // Adding 3 seconds sleep.. Both above objects will be removed from
        // Cache because of timeToLiveInSeconds value
        Thread.sleep(3000);
 
      
 
    }
 
    private void dataaccessTestObjectsCleanupTime() throws InterruptedException {
        int size = 500000;
 
        // Test with timeToLiveInSeconds = 100 seconds
        // timerIntervalInSeconds = 100 seconds
        // maxItems = 500000
 
        ServiceImpl<String, String> cache = new ServiceImpl<String, String>(100, 100, 500000);
 
        for (int i = 0; i < size; i++) {
            String value = Integer.toString(i);
            cache.put(value, value);
        }
 
        Thread.sleep(200);
 
        long start = System.currentTimeMillis();
        cache.cleanup();
        double finish = (double) (System.currentTimeMillis() - start) / 1000.0;
 
        System.out.println("Cleanup times for " + size + " objects are " + finish + " s");
 
    }


}
