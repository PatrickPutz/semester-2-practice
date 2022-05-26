package bsp2;

import java.util.HashMap;

public class PageCache {

    private HashMap<String, WebPage> cache;

    public WebPage readFromCache(String url) throws CacheMissException {
        if(cache.containsKey(url))
            return cache.get(url);
        else
            throw new CacheMissException();
    }

    public void writeToCache(WebPage webPage){
        this.cache.put(webPage.getUrl(), webPage);
    }

    public void warmUp(String pathToUrls){

    }

    public HashMap<String, WebPage> getCache() {
        return cache;
    }
}
