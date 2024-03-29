package com.dataaccessCache.service;


import java.util.ArrayList;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import org.apache.commons.collections.MapIterator;

import org.apache.commons.collections.map.LRUMap;

@Component
public
 class ServiceImpl <T1, T2> implements Service{

	private long timeToLive;
    private LRUMap cacheMap;
 
    protected class CacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public Object value;
 
        protected CacheObject(Object value) {
            this.value = value;
        }
    }
 
    public ServiceImpl(long dataaccessTimeToLive, final long dataaccessTimerInterval, int maxItems) {
        this.timeToLive = dataaccessTimeToLive * 1000;
 
        cacheMap = new LRUMap(maxItems);
 
        if (timeToLive > 0 && dataaccessTimerInterval > 0) {
 
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(dataaccessTimerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });
 
            t.setDaemon(true);
            t.start();
        }
    }
 
    @Override
    @CachePut(value="value", key="#key")
    public void put(String key, Object value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }
 
   
    @Override
    @Cacheable(value="value", key="#key")
    public Object get(String key) {
        synchronized (cacheMap) {
            CacheObject c = (CacheObject) cacheMap.get(key);
 
            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }
 
   
    @SuppressWarnings("unchecked")
    public void cleanup() {
 
        long now = System.currentTimeMillis();
        ArrayList<String> deleteKey = null;
 
        synchronized (cacheMap) {
            MappingIterator itr = cacheMap.mapIterator();
 
            deleteKey = new ArrayList<String>((cacheMap.size() / 2) + 1);
            String key = null;
            CacheObject c = null;
 
            while (itr.hasNext()) {
                key = (String) itr.next();
                c = (CacheObject) itr.getValue();
 
                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }
 
        for (String key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
 
            Thread.yield();
        }
    }
    
    public void remove(String key) {
        synchronized (cacheMap) {
        	cacheMap.remove(key);
        }
    }

	
}
