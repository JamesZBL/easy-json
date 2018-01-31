/*
 * Copyright 2018 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.easyjson;

/**
 * Json 中对 null 值的包装类
 *
 * @author JamesZBL
 * @date 2018-01-31
 */
public final class JsonNull extends JsonItem {

  private static volatile JsonNull INSTANCE;

  private JsonNull() {
  }

  public static JsonNull getINSTANCE() {
    if (null == INSTANCE) {
      synchronized (JsonNull.class) {
        if (null == INSTANCE) {
          INSTANCE = new JsonNull();
        }
      }
    }
    return INSTANCE;
  }

  @Override
  public JsonItem itemClone() {
    return getINSTANCE();
  }

  /**
   * 在 Json 中 null 只存在一种情况，因此比较其类的哈希值即可
   */
  @Override
  public int hashCode() {
    return JsonNull.class.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj || obj.getClass().equals(JsonNull.class);
  }
}
