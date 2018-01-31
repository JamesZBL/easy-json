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

  /**
   * 添加项
   */
  public void addItem(String name, JsonItem value) {
    if (null != value) {
      content.put(name, value);
    } else {
      content.put(name, JsonNull.getINSTANCE());
    }
  }

  /**
   * 移除项
   */
  public void removeItem(String key) {
    content.remove(key);
  }

  /**
   * 根据键获取项
   */
  public JsonItem getItem(String key) {
    return content.get(key);
  }

  /**
   * 判断是否包含键
   */
  public boolean containsItem(String key) {
    return content.containsKey(key);
  }

  /**
   * 添加属性
   * 此处属性的值为 Json 基本数据类型
   */
  public void addProperty(String key, String value) {
    addItem(key, newJsonItem(value));
  }

  /**
   * 添加属性
   * 此处属性的值为 Json 基本数据类型
   */
  public void addProperty(String key, Number value) {
    addItem(key, newJsonItem(value));
  }

  /**
   * 添加属性
   * 此处属性的值为 Json 基本数据类型
   */
  public void addProperty(String key, Boolean value) {
    addItem(key, newJsonItem(value));
  }

  /**
   * 添加属性
   * 此处属性的值为 Json 基本数据类型
   */
  public void addProperty(String key, Character value) {
    addItem(key, newJsonItem(value));
  }

  /**
   * 创建 Json 数据项
   */
  private JsonItem newJsonItem(Object obj) {
    if (null == obj) {
      return JsonNull.getINSTANCE();
    } else {
      return new JsonBasic(obj);
    }
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
