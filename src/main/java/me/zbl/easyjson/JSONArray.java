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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Json 数组类型
 *
 * @author JamesZBL
 * @date 2018-01-31
 */
public final class JsonArray extends JsonItem implements Iterable<JsonItem> {

  private final List<JsonItem> content;

  public JsonArray() {
    content = new ArrayList<JsonItem>();
  }

  /**
   * 添加数据项
   */
  public void addItem(JsonItem item) {
    if (null != item) {
      content.add(item);
    } else {
      content.add(JsonNull.getINSTANCE());
    }
  }

  /**
   * 删除数据项
   *
   * @param i 数据项所在位置
   */
  public void removeItem(int i) {
    content.remove(i);
  }

  /**
   * 根据位置获取项
   *
   * @param i 数据项所在位置
   */
  public JsonItem getItem(int i) {
    return content.get(i);
  }

  /**
   * 添加数据
   * 此处属性的值为 Json 基本数据类型
   */
  public void addItem(String item) {
    addItem(newJsonItem(item));
  }

  /**
   * 添加数据
   * 此处属性的值为 Json 基本数据类型
   */
  public void addItem(Boolean item) {
    addItem(newJsonItem(item));
  }

  /**
   * 添加数据
   * 此处属性的值为 Json 基本数据类型
   */
  public void addItem(Character item) {
    addItem(newJsonItem(item));
  }

  /**
   * 添加数据
   * 此处属性的值为 Json 基本数据类型
   */
  public void addItem(Number item) {
    addItem(newJsonItem(item));
  }

  private JsonItem newJsonItem(Object obj) {
    if (null != obj) {
      return new JsonBasic(obj);
    } else {
      return JsonNull.getINSTANCE();
    }
  }

  @Override
  public JsonItem itemClone() {
    List<JsonItem> itemList = new ArrayList<JsonItem>();
    itemList.addAll(content);
    return null;
  }

  @Override
  public int hashCode() {
    return content.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else {
      if (obj instanceof JsonArray) {
        if (((JsonArray) obj).content.equals(content)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Iterator<JsonItem> iterator() {
    return content.iterator();
  }
}
