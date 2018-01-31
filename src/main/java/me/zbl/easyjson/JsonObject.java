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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Json 中的对象类
 * Json 中的对象展现形式如：{"name":"Java","age":23,"version":9}
 *
 * @author JamesZBL
 * @date 2018-01-31
 */
public final class JsonObject extends JsonItem {

  private Map<String, JsonItem> content = new LinkedHashMap<>();

  public JsonObject() {
  }

  public void addItem(String name, JsonItem value) {
    if (null != value) {
      content.put(name, value);
    } else {
      content.put(name, JsonNull.getINSTANCE());
    }
  }

  public void removeItem(String key) {
    content.remove(key);
  }

  public boolean containsItem(String key) {
    return content.containsKey(key);
  }

  @Override
  public JsonItem itemClone() {
    JsonObject newObj = new JsonObject();
    for (Map.Entry<String, JsonItem> entry : content.entrySet()) {
      newObj.addItem(entry.getKey(), entry.getValue().itemClone());
    }
    return newObj;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof JsonObject && ((JsonObject) obj).content.equals(this.content)) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.content.hashCode();
  }
}
