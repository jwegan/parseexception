package com.parseexception.model;

import java.util.*;

import com.parseexception.Config;

public class ObjectCache {
	/*--------------------------------------------------------------------------
	 * Helper Inner Classes
	/*------------------------------------------------------------------------*/
	/*
	 * Class Name: CacheKey
	 * Description: Inner class to represent a key to the lookup table for the 
	 * cache
	 */
	private class CacheKey
	{
		private int hashCode;
		private Object[] indexAry;
		
		/* Constructor */
		public CacheKey(Object idx[])
		{	
			long idxrnd = 0;
			indexAry = idx;
			
			// Hash the indexes together
			for(int i = 0; i < idx.length; i++)
			{
				idxrnd ^= (i % 2 == 0)? idx[i].hashCode() : 
										((long)idx[i].hashCode()) << 32;
			}
			
			for(int i = 0; i < 11; i++)
			{
				idxrnd = (idxrnd * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
			}
			
			hashCode = (int) (idxrnd >>> (48 - 32));
		}
		
		/* Returns the hash code */
		public int hashCode()
		{
			return hashCode;
		}
		
		/* Compares both indexes & table name for equality */
		public boolean equals(Object o)
		{
			if(!(o instanceof CacheKey))
				return false;
			
			CacheKey key = (CacheKey) o;
			if(this.indexAry.length != key.indexAry.length)
				return false;
			
			for(int i = 0; i < this.indexAry.length; i++)
			{
				if(!this.indexAry[i].equals(key.indexAry[i]))
					return false;
			}
			
			return true;
		}
	}
	
	/*
	 * Class Name: CacheObject
	 * Description: Wraps and object inserted into the cache
	 */
	private class CachedObject
	{
		Object obj;
		CacheKey key;
		int reference_count;
		
		public CachedObject(Object o, CacheKey k)
		{
			obj = o;
			key = k;
			reference_count = 0;
		}
		
		public Object getObject()
		{
			return obj;
		}
		
		public synchronized void incrementHitCounter()
		{
			reference_count++;
		}
		
		public synchronized void resetHitCounter()
		{
			reference_count = 0;
		}
		
		public int getHitCounter()
		{
			return reference_count;
		}
		
		public CacheKey getKey()
		{
			return key;
		}
	}
	/*------------------------------------------------------------------------*/
	 
	/* ObjectCache's variables */
	private static ObjectCache instance = null;
	
	/* Data structures for implementing the cache. Note Hashtable is 
	 * synchronized, but LinkedList is not.
	 */
	private Hashtable<CacheKey, CachedObject> recentlyUsedLookupTable;
	private Hashtable<CacheKey, CachedObject> highlyUsedLookupTable;
	private LinkedList<CachedObject> recentlyUsedClock;
	private LinkedList<CachedObject> highlyUsedClock; 
	
	private int cacheSize;
	private int temporalHits;
	private int utilityHits;
	
	private final int temporalThreshold = 5;
	private final int utilityThreshold = 3;
	
	private ObjectCache(int size)
	{
		int tableSize = (int) (size / 0.75) + 1;
		cacheSize = size;
		recentlyUsedLookupTable = new Hashtable<CacheKey, CachedObject>(tableSize);
		highlyUsedLookupTable = new Hashtable<CacheKey, CachedObject>(tableSize);
		recentlyUsedClock = new LinkedList<CachedObject>();
		highlyUsedClock = new LinkedList<CachedObject>();
		
		temporalHits = utilityHits = 0;
	}
	
	public static ObjectCache getInstance()
	{
		if(instance == null)
		{
			instance = new ObjectCache(Config.cacheSize_objs);
		}
		
		return instance;
	}
	
	/*
	 * Method Name: lookupObject
	 * Description: Checks to see if the object identified by the indexs and
	 * 				table name are in the cache and if so returns that object
	 * 				otherwise returns null.
	 */
	public Object lookupObject(Object... index)
	{
		return lookupObjectArray(index);
	}
	
	public Object lookupObjectArray(Object index[])
	{
		// Construct key
		CacheKey key = new CacheKey(index);
		
		// Check if its in the first cache
		CachedObject obj = null;
		obj = recentlyUsedLookupTable.get(key);
		if(obj != null)
		{
			obj.incrementHitCounter();;
			if(obj.getHitCounter() < temporalThreshold)
				temporalHits++;
			else 
				utilityHits++;
			
			return obj.getObject();
		}
		
		// Check the second cache
		obj = highlyUsedLookupTable.get(key);
		if(obj != null)
		{
			obj.incrementHitCounter();
			if(obj.getHitCounter() < utilityThreshold)
				temporalHits++;
			else
				utilityHits++;
			
			return obj.getObject();
		}
		
		return null;
	}
	
	/*
	 * Method Name: addObject
	 * Description: Adds the object to the cache with a lookup key constructed
	 * 				from the index's and table name. The index & table should 
	 * 				uniquely identify the object from all other objects in the
	 * 				system. 
	 * 
	 * Implementation Details: Cache replacement algorithm uses a variant of
	 * 				the Cache Adaptive Replacement algorithm. Instead of keeping
	 * 				a history of ejected elements to calculate the memory
	 * 				allocation between the two caches, we instead classify hits 
	 * 				as either temporal or utility and calculate the allocation
	 * 				from that. Whenever room is needed in the cache we eject
	 * 				1% of the cache entries to make room.
	 * 
	 * Arguments:
	 * 		Object o - The object to insert into cache
	 * 		Object... index - The list of arguments to use as the index
	 */
	public synchronized void addObject(Object o, Object... index)
	{
		addObjectArray(o, index);
	}
	
	public synchronized void addObjectArray(Object o, Object index[])
	{
		// Just insert into cache if there is space
		CacheKey key = new CacheKey(index);
		CachedObject obj = new CachedObject(o, key);
		if(recentlyUsedClock.size() + highlyUsedClock.size() < cacheSize)
		{
			recentlyUsedClock.offerLast(obj);
			recentlyUsedLookupTable.put(key, obj);
			return;
		}
		
		// Free up space in the cache by removing 1% of the entries
		double percent = temporalHits / (temporalHits + (double) utilityHits);
		int maxRULTsize = recentlyUsedLookupTable.size();
		maxRULTsize += (int) ((cacheSize * percent) -
							   recentlyUsedLookupTable.size()) / 2;
		int removeCount = 0;
		while(removeCount < (int) (cacheSize * 0.01) + 1)
		{
			if(recentlyUsedClock.size() >= maxRULTsize)
			{
				CachedObject elem = recentlyUsedClock.removeFirst();
				recentlyUsedLookupTable.remove(elem.getKey());
				if(elem.getHitCounter() < temporalThreshold)
				{
					removeCount++;
				}else{
					// Move element to highly used cache
					elem.resetHitCounter();
					highlyUsedClock.offerLast(elem);
					highlyUsedLookupTable.put(elem.getKey(), elem);
				}
			}else
			{
				CachedObject elem = highlyUsedClock.removeFirst();
				elem.resetHitCounter();
				if(elem.getHitCounter() < utilityThreshold)
				{
					highlyUsedLookupTable.remove(elem.getKey());
					removeCount++;
				}else
				{
					// Move element to end of queue
					recentlyUsedClock.offerLast(elem);
				}
			}
		}
		
		// Insert object into cache
		recentlyUsedClock.offerLast(obj);
		recentlyUsedLookupTable.put(key, obj);
		
		// Reset hit counters
		temporalHits = utilityHits = 0;
	}
}
