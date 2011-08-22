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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;

import junit.framework.TestCase;

import com.mirantis.cachemod.filter.CacheEntry;
import com.mirantis.cachemod.filter.CoherenceCacheEntry;
import com.tangosol.io.ByteArrayReadBuffer;
import com.tangosol.io.ByteArrayWriteBuffer;
import com.tangosol.io.pof.PofBufferReader;
import com.tangosol.io.pof.PofBufferWriter;
import com.tangosol.io.pof.PofContext;

public class TestUtils {

  public static byte[] serializeByJava(Object obj) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bout);
    out.writeObject(obj);
    out.close();
    return bout.toByteArray();
  }
  
  public static byte[] serializeByPof(Object obj, PofContext pofContext) throws IOException {
    ByteArrayWriteBuffer buf = new ByteArrayWriteBuffer(10000);
    PofBufferWriter writer = new PofBufferWriter(buf.getBufferOutput(), pofContext);
    writer.writeObject(0, obj);
    return buf.toByteArray();
  }
  
  public static Object deserializeByPof(byte[] pof, PofContext pofContext) throws IOException {
    ByteArrayReadBuffer buf = new ByteArrayReadBuffer(pof);
    PofBufferReader reader = new PofBufferReader(buf.getBufferInput(), pofContext);
    return reader.readObject(0);
  }
  
  public static void assertEquals(CacheEntry cacheEntry, CacheEntry cacheEntryCheck) throws Exception {
    TestCase.assertEquals(cacheEntry.getContentEncoding(), cacheEntryCheck.getContentEncoding());
    TestCase.assertEquals(cacheEntry.getContentType(), cacheEntryCheck.getContentType());
    TestCase.assertEquals(cacheEntry.getExpires(), cacheEntryCheck.getExpires());
    TestCase.assertEquals(cacheEntry.getLastModified(), cacheEntryCheck.getLastModified());
    TestCase.assertEquals(cacheEntry.getMaxAge(), cacheEntryCheck.getMaxAge());
    TestCase.assertEquals(cacheEntry.getContent().length, cacheEntryCheck.getContent().length);
  }
  
  public static CacheEntry createCacheEntry(String responseText) throws IOException {
    CacheEntry entry = new CoherenceCacheEntry();
    entry.setKey("index.html");
    entry.setContentEncoding("UTF-8");
    entry.setContentType("text/html");
    entry.setExpires(-1);
    entry.setLastModified(System.currentTimeMillis());
    entry.setLocale(new Locale("en_US"));
    entry.setMaxAge(-1);
    entry.setContent("content".getBytes());
    return entry;
  }
  

  
}
