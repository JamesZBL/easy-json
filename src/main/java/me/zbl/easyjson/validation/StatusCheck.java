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
package me.zbl.easyjson.validation;

/**
 * 状态检查
 *
 * @author JamesZBL
 * @date 2018-02-01
 */
public final class StatusCheck {

  private StatusCheck() {
  }

  /**
   * 检查对象是否为 null
   */
  public static void checkIfNull(Object o) {
    if (null == o) {
      throw new NullPointerException();
    }
  }

  /**
   * 对指定的表达式进行判断，进而判断是否为合法操作
   */
  public static void checkIfLegal(boolean b) {
    if (!b) {
      throw new IllegalArgumentException("非法操作");
    }
  }
}
