/*
 * Copyright 2011 Mirantis Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mirantis.coherence.filter;

import junit.framework.TestCase;

import com.mirantis.cachemod.filter.CacheEntry;
import com.tangosol.io.pof.ConfigurablePofContext;

public class PofTest extends TestCase {

  public void test() throws Exception {
    
    ConfigurablePofContext pofContext = new ConfigurablePofContext("coherence-filter-pof-config.xml");    
    
    CacheEntry cacheEntry = TestUtils.createCacheEntry("hello");
  
    byte[] entryPof = TestUtils.serializeByPof(cacheEntry, pofContext);
    System.out.println("POF len = " + entryPof.length);
    CacheEntry cacheEntryCheck = (CacheEntry) TestUtils.deserializeByPof(entryPof, pofContext);
    assertEquals(cacheEntry, cacheEntryCheck);

    byte[] blob = TestUtils.serializeByJava(cacheEntry);
    System.out.println("Java len = " + blob.length);
  }
 

  public static void assertEquals(CacheEntry cacheEntry, CacheEntry cacheEntryCheck) throws Exception {
    assertEquals(cacheEntry.getContentEncoding(), cacheEntryCheck.getContentEncoding());
    assertEquals(cacheEntry.getContentType(), cacheEntryCheck.getContentType());
    assertEquals(cacheEntry.getExpires(), cacheEntryCheck.getExpires());
    assertEquals(cacheEntry.getLastModified(), cacheEntryCheck.getLastModified());
    assertEquals(cacheEntry.getMaxAge(), cacheEntryCheck.getMaxAge());
    assertEquals(cacheEntry.getContent().length, cacheEntryCheck.getContent().length);
  }

}
