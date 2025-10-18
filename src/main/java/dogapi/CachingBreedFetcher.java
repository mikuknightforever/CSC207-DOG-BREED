package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private BreedFetcher breedFetcher;
    private Map<String, List<String>> cache;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        breedFetcher = fetcher;
        this.cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (breed == null) {
            throw new BreedNotFoundException(breed);
        }
        String key = breed.toLowerCase();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        callsMade++;
        List<String> result = breedFetcher.getSubBreeds(key);
        if (result != null && ! result.isEmpty()) {
            cache.put(key, result);
        }



        // return statement included so that the starter code can compile and run.
        return result;
    }

    public int getCallsMade() {
        return callsMade;
    }
}